package com.final_project.auth_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.auth_service.config.KeycloakConfig;
import com.final_project.auth_service.dto.AuthResponse;
import com.final_project.auth_service.dto.ChangePasswordRequest;
import com.final_project.auth_service.dto.EmailVerificationRequest;
import com.final_project.auth_service.dto.ForgotPasswordRequest;
import com.final_project.auth_service.dto.GoogleOAuth2Request;
import com.final_project.auth_service.dto.LoginRequest;
import com.final_project.auth_service.dto.RefreshTokenRequest;
import com.final_project.auth_service.dto.ResendVerificationEmailRequest;
import com.final_project.auth_service.dto.ResetPasswordRequest;
import com.final_project.auth_service.dto.SignupRequest;
import com.final_project.auth_service.dto.UserDTO;
import com.final_project.auth_service.event.PasswordChangedEvent;
import com.final_project.auth_service.event.ResetPasswordEvent;
import com.final_project.auth_service.event.UserRegisteredEvent;
import com.final_project.auth_service.exception.DuplicateUserException;
import com.final_project.auth_service.exception.InvalidUserException;
import com.final_project.auth_service.exception.KeycloakException;
import com.final_project.auth_service.exception.UnauthorizedException;
import com.final_project.auth_service.kafka.AuthEventPublisher;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationService {

    private final KeycloakService keycloakService;
    private final AuditLogService auditLogService;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KeycloakConfig keycloakConfig;
    private final AuthEventPublisher eventPublisher;
    private final JwtDecoder jwtDecoder;

    private final JwtEncoder jwtEncoder;

    @Value("${app.default-role:faculty-user}")
    private String defaultRole;

    public AuthResponse login(LoginRequest request) {
        UserRepresentation user = resolveUser(request.getUsernameOrEmail());
        AuthResponse authResponse = requestToken("password", Map.of(
                "username", user.getUsername(),
                "password", request.getPassword()
        ));
        authResponse.setUser(toDTO(user));

        auditLogService.logAuditEvent(user.getId(), "LOGIN_SUCCESS", "USER", user.getId(), "User logged in through Keycloak", "SUCCESS");
        return authResponse;
    }

    public AuthResponse signup(SignupRequest request) {
        if (!Boolean.TRUE.equals(request.getTermsAgreed()) || !Boolean.TRUE.equals(request.getPrivacyAgreed())) {
            throw new InvalidUserException("You must agree to terms and privacy policy");
        }

        ensureUserDoesNotExist(request.getUsername(), request.getEmail());

        String userId = keycloakService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                true,
                true,
                Map.of()
        );

        keycloakService.assignRealmRoles(userId, List.of(defaultRole));
        auditLogService.logAuditEvent(userId, "USER_REGISTERED", "USER", userId, "User registered in Keycloak", "SUCCESS");
        eventPublisher.publishUserRegister(UserRegisteredEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(userId)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .registrationSource("WEB")
                .occurredAt(LocalDateTime.now())
                .build());

        AuthResponse authResponse = requestToken("password", Map.of(
                "username", request.getUsername(),
                "password", request.getPassword()
        ));
        authResponse.setUser(toDTO(keycloakService.getUserById(userId)));
        authResponse.setMessage("Signup successful. Verify your email in Keycloak");
        return authResponse;
    }

    public AuthResponse googleOAuth2Login(GoogleOAuth2Request request) {
        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(request.getIdToken());
            if (idToken == null) {
                throw new UnauthorizedException("Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String givenName = (String) payload.get("given_name");
            String familyName = (String) payload.get("family_name");
            String username = email.split("@")[0];

            Optional<UserRepresentation> existing = keycloakService.findUserByEmail(email);
            if (existing.isEmpty()) {
                String tempPassword = UUID.randomUUID().toString();
                String userId = keycloakService.createUser(
                        username,
                        email,
                        givenName,
                        familyName,
                        tempPassword,
                        true,
                        true,
                        Map.of()
                );
                keycloakService.assignRealmRoles(userId, List.of(defaultRole));
                auditLogService.logAuditEvent(userId, "USER_REGISTERED_GOOGLE", "USER", userId, "Google user created in Keycloak", "SUCCESS");
            }

            throw new UnsupportedOperationException("Google login should flow through Keycloak OIDC or brokered login instead of a local token exchange.");
        } catch (GeneralSecurityException | IOException exception) {
            throw new UnauthorizedException("Google authentication failed");
        }
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        AuthResponse authResponse =  requestToken("refresh_token", Map.of("refresh_token", request.getRefreshToken()));
        Jwt jwt = jwtDecoder.decode(authResponse.getAccessToken());
        UserRepresentation user = keycloakService.getUserById(jwt.getSubject());
        authResponse.setUser(toDTO(user));
        return authResponse;
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidUserException("Passwords do not match");
        }

        UserRepresentation user = keycloakService.getUserById(userId);
        requestToken("password", Map.of(
                "username", user.getUsername(),
                "password", request.getCurrentPassword(),
                "scope", "openid"
        ));

        keycloakService.setPassword(userId, request.getNewPassword(), false);
        auditLogService.logAuditEvent(userId, "PASSWORD_CHANGED", "USER", userId, "Password changed in Keycloak", "SUCCESS");
        eventPublisher.publishPasswordChange(PasswordChangedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(userId)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .changeType("CHANGED")
                .ipAddress(request.getIpAddress())
                .occurredAt(LocalDateTime.now())
                .userAgent("KEYCLOAK")
                .build());
    }

    private String generateShortLivedToken(Map<String, Object> customClaims, long durationMinutes) {
        Instant now = Instant.now();

        // 1. Explicitly define the Header with HS256
        JwsHeader jwsHeader = JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(durationMinutes * 60));

        customClaims.forEach(claimsBuilder::claim);

        // 2. Pass BOTH the header and the claims to the encoder
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsBuilder.build())).getTokenValue();
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        UserRepresentation user = resolveUser(request.getEmail());

        Map<String, Object> claims = Map.of(
                "sub", user.getId(),
                "email", user.getEmail(),
                "type", "PASSWORD_RESET"

        );
        String resetToken = generateShortLivedToken(claims, 15);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        String resetUrl = baseUrl+"/reset-password?token=" + resetToken;
        eventPublisher.publishResetPasswordEvent(
                ResetPasswordEvent
                        .builder()
                        .eventId(UUID.randomUUID().toString())
                        .occurredAt(LocalDateTime.now())
                        .userId(user.getId())
                        .email(user.getEmail())
                        .resetToken(resetUrl)
                        .changeType("RESET")
                        .userAgent("KDR-ECONONMY-FACULTY")
                        .firstName(user.getFirstName())
                        .ipAddress(request.getIp())
                        .build()
        );
//        keycloakService.sendResetPasswordEmail(user.getId());
        auditLogService.logAuditEvent(user.getId(), "PASSWORD_RESET_REQUESTED", "USER", user.getId(), "Password reset email requested", "SUCCESS");
    }

    public void resetPassword(ResetPasswordRequest request) {
        try {
            // 1. Decode and verify the JWT (Signature and Expiry checked automatically)
            Jwt jwt = jwtDecoder.decode(request.getResetToken());

            // 2. Security Check: Ensure this is specifically a reset token
            if (!"PASSWORD_RESET".equals(jwt.getClaim("type"))) {
                throw new InvalidUserException("This token cannot be used for password reset");
            }

            String userId = jwt.getSubject(); // Extract user ID from 'sub' claim

            // 3. Update the password in Keycloak
            keycloakService.setPassword(userId, request.getNewPassword(), false);

            // 4. Log the success
            auditLogService.logAuditEvent(userId, "PASSWORD_RESET_SUCCESS", "USER", userId, "User successfully reset password via stateless token", "SUCCESS");

        } catch (JwtException e) {
            // Triggered if the token is tampered with, expired, or invalid
            log.error("Invalid password reset token: {}", e.getMessage());
            throw new InvalidUserException("The reset link is invalid or has expired. Please request a new one.");
        }
    }



    public void verifyEmail(EmailVerificationRequest request) {
        throw new UnsupportedOperationException("Use Keycloak email verification actions instead of local verification tokens.");
    }

    public void resendVerificationEmail(ResendVerificationEmailRequest request) {
        UserRepresentation user = keycloakService.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("User not found for email"));
        keycloakService.sendVerifyEmail(user.getId());
    }

    private void ensureUserDoesNotExist(String username, String email) {
        if (keycloakService.findUserByUsername(username).isPresent()) {
            throw new DuplicateUserException("username", username);
        }
        if (keycloakService.findUserByEmail(email).isPresent()) {
            throw new DuplicateUserException("email", email);
        }
    }

    private UserRepresentation resolveUser(String usernameOrEmail) {
        Optional<UserRepresentation> byEmail = usernameOrEmail.contains("@")
                ? keycloakService.findUserByEmail(usernameOrEmail)
                : Optional.empty();
        return byEmail.or(() -> keycloakService.findUserByUsername(usernameOrEmail))
                .orElseThrow(() -> new UnauthorizedException("Invalid email/username or password"));
    }

    private AuthResponse requestToken(String grantType, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", "frontend");
        form.add("grant_type", grantType);

        params.forEach(form::add);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(keycloakConfig.tokenUrl(), entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new KeycloakException("Failed to obtain token from Keycloak");
        }

        try {
            JsonNode node = objectMapper.readTree(response.getBody());
            return AuthResponse.builder()
                    .accessToken(node.path("access_token").asText())
                    .refreshToken(node.path("refresh_token").asText())
                    .tokenType(node.path("token_type").asText("Bearer"))
                    .expiresIn(node.path("expires_in").asLong())
                    .message("Authentication successful")
                    .build();
        } catch (Exception exception) {
            throw new KeycloakException("Failed to parse Keycloak token response", exception);
        }
    }

    private UserDTO toDTO(UserRepresentation user) {

        String profilePicture = null;

        Map<String, List<String>> attributes = user.getAttributes();

        if (attributes != null && attributes.containsKey("profile")) {
            List<String> values = attributes.get("profile");

            if (values != null && !values.isEmpty()) {
                profilePicture = values.get(0);
            }
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .photoUrl(profilePicture) // 👈 set here
                .status(Boolean.TRUE.equals(user.isEnabled()) ? "ACTIVE" : "DISABLED")
                .emailVerified(user.isEmailVerified())
                .roles(Set.copyOf(keycloakService.getUserRealmRoleNames(user.getId())))
                .build();
    }
    public void logout(RefreshTokenRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", keycloakConfig.getPublicClientId());
        form.add("refresh_token", request.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        restTemplate.postForEntity(keycloakConfig.logoutUrl(), entity, String.class);
    }
}
