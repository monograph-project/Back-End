package com.final_project.auth_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.auth_service.config.KeycloakConfig;
import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.event.PasswordChangedEvent;
import com.final_project.auth_service.exception.*;
import com.final_project.auth_service.kafka.AuthEventPublisher;
import com.final_project.auth_service.model.*;
import com.final_project.auth_service.repository.RoleRepository;
import com.final_project.auth_service.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for user authentication operations.
 *
 * Handles:
 * - User login with email/username and password
 * - User signup/registration
 * - Google OAuth2 authentication
 * - JWT token generation
 * - Token refresh
 * - Password management
 */
@Service
@Slf4j
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final KeycloakService keycloakService;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String jwtSecret;
    private final long jwtExpirationMs;
    private final long refreshTokenExpirationMs;
    private final String googleClientId;
    private final String defaultRole;
    private final AuthEventPublisher eventPublisher;
    private String keycloakServerUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    public AuthenticationService(
            @Value("${keycloak.server-url}") String keycloakServerUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret,
            UserRepository userRepository,
            RoleRepository roleRepository,
            KeycloakService keycloakService,
            AuditLogService auditLogService,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate,
            GoogleIdTokenVerifier googleIdTokenVerifier,
            ObjectMapper objectMapper,
            @Value("${app.security.jwt.secret}") String jwtSecret,
            @Value("${app.security.jwt.expiration:3600000}") long jwtExpirationMs,
            @Value("${app.security.jwt.refresh-expiration:604800000}") long refreshTokenExpirationMs,
            @Value("${google.client-id}") String googleClientId,
            @Value("${app.default-role}") String defaultRole,
            AuthEventPublisher eventPublisher
    ) {
        this.keycloakServerUrl = keycloakServerUrl;
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.realm = realm;
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.keycloakService = keycloakService;
        this.auditLogService = auditLogService;
        this.passwordEncoder = passwordEncoder;
        this.googleIdTokenVerifier = googleIdTokenVerifier;
        this.jwtSecret = jwtSecret;
        this.restTemplate = restTemplate;
        this.jwtExpirationMs = jwtExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.googleClientId = googleClientId;
        this.defaultRole = defaultRole;
    }


    /**
     * Login user with email/username and password.
     *
     * @param request Login request with credentials
     * @return Auth response with tokens
     */
    public AuthResponse login(LoginRequest request) {

        // Find user by email or username
        User user = userRepository.findByEmail(request.getUsernameOrEmail())
                .or(() -> userRepository.findByUsername(request.getUsernameOrEmail()))
                .orElseThrow(() -> {
                    auditLogService.logAuditEvent(
                            null,
                            "LOGIN_FAILURE",
                            "USER",
                            null,
                            "Login failed: User not found",
                            "FAILURE"
                    );
                    return new InvalidUserException("Invalid email/username or password");
                });

        // Check if account is active
        if (!user.isEnabled()) {
            auditLogService.logAuditEvent(
                    user.getId(),
                    "LOGIN_FAILURE",
                    "USER",
                    user.getId(),
                    "Login failed: Account is " + user.getStatus(),
                    "FAILURE"
            );
            throw new UnauthorizedException("Account is " + user.getStatus().name().toLowerCase());
        }

        // Check if account is locked
        if (user.isAccountLocked()) {
            auditLogService.logAuditEvent(
                    user.getId(),
                    "LOGIN_FAILURE",
                    "USER",
                    user.getId(),
                    "Login failed: Account is locked",
                    "FAILURE"
            );
            throw new UnauthorizedException("Account is locked. Please try again later");
        }

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Increment failed attempts
            int failedAttempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
            user.setFailedLoginAttempts(failedAttempts);

            // Lock account after 5 failed attempts
            if (failedAttempts >= 5) {
                user.setLockedUntil(LocalDateTime.now().plusMinutes(30));
                userRepository.save(user);

                auditLogService.logAuditEvent(
                        user.getId(),
                        "USER_LOCKED",
                        "USER",
                        user.getId(),
                        "Account locked due to failed login attempts",
                        "FAILURE"
                );
            } else {
                userRepository.save(user);
            }

            auditLogService.logAuditEvent(
                    user.getId(),
                    "LOGIN_FAILURE",
                    "USER",
                    user.getId(),
                    "Login failed: Invalid password (attempt " + failedAttempts + ")",
                    "FAILURE"
            );

            throw new InvalidUserException("Invalid email/username or password");
        }

        // Reset failed attempts on successful login
        user.setFailedLoginAttempts(0);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Log successful login
        auditLogService.logAuditEvent(
                user.getId(),
                "LOGIN_SUCCESS",
                "USER",
                user.getId(),
                "User logged in successfully",
                "SUCCESS"
        );

        log.info("User logged in successfully: {}", user.getUsername());


        // Generate tokens
        return generateResponseTokenWithKeycloak(getTokensFromKeycloak(user.getUsername(), user.getPassword()),user, "Login successful");
    }

    /**
     * Register new user (signup).
     *
     * @param request Signup request
     * @return Auth response with tokens
     */
    public AuthResponse signup(SignupRequest request) {

        // Validate terms agreement
        if (!request.getTermsAgreed() || !request.getPrivacyAgreed()) {
            throw new InvalidUserException("You must agree to terms and privacy policy");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateUserException("email", request.getEmail());
        }

        // Check for duplicate username
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUserException("username", request.getUsername());
        }

        // Create Keycloak user
        CreateUserRequest keycloakRequest = CreateUserRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        String keycloakId = keycloakService.createKeycloakUser(keycloakRequest);

        // Set password in Keycloak
        keycloakService.setUserPassword(keycloakId, request.getPassword());

        // Create local user entity
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .keycloakId(keycloakId)
                .password(passwordEncoder.encode(request.getPassword()))
                .status(User.UserStatus.ACTIVE)
                .emailVerified(false)
                .twoFactorEnabled(false)
                .failedLoginAttempts(0)
                .build();

        // Assign default role
        Role userRole = roleRepository.findByName(defaultRole)
                .orElseThrow(() -> new InvalidUserException("Default role not found: " + defaultRole));
        user.getRoleIds().add(userRole.getId());

        // Assign role in Keycloak
        keycloakService.assignRoleToUser(keycloakId, userRole.getName());

        User savedUser = userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                savedUser.getId(),
                "USER_CREATED",
                "USER",
                savedUser.getId(),
                "User registered via signup",
                "SUCCESS"
        );

        log.info("User registered successfully: {}", savedUser.getUsername());

        return generateAuthResponse(savedUser, "Signup successful. Please verify your email");
    }

    /**
     * Login/Register with Google OAuth2.
     *
     * @param request Google OAuth2 request with ID token
     * @return Auth response with tokens
     */
    public AuthResponse googleOAuth2Login(GoogleOAuth2Request request) {
        log.info("Google OAuth2 login attempt");

        try {
            // Verify Google ID token
            GoogleIdToken idToken = googleIdTokenVerifier.verify(request.getIdToken());
            if (idToken == null) {
                throw new UnauthorizedException("Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Extract user info from token
            String googleId = payload.getSubject();
            String email = payload.getEmail();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String picture = (String) payload.get("picture");

            log.info("Google ID Token verified for: {}", email);

            // Check if user exists
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        // Create new user from Google info
                        log.info("Creating new user from Google info: {}", email);

                        CreateUserRequest keycloakRequest = CreateUserRequest.builder()
                                .username(email.split("@")[0] + "_" + System.currentTimeMillis())
                                .email(email)
                                .firstName(firstName != null ? firstName : "")
                                .lastName(lastName != null ? lastName : "")
                                .build();

                        String keycloakId = keycloakService.createKeycloakUser(keycloakRequest);

                        User newUser = User.builder()
                                .username(keycloakRequest.getUsername())
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .keycloakId(keycloakId)
                                .status(User.UserStatus.ACTIVE)
                                .emailVerified(true)  // Google accounts are pre-verified
                                .twoFactorEnabled(false)
                                .failedLoginAttempts(0)
                                .build();

                        // Assign default role
                        Role userRole = roleRepository.findByName(defaultRole)
                                .orElse(null);
                        if (userRole != null) {
                            newUser.getRoleIds().add(userRole.getId());
                            keycloakService.assignRoleToUser(keycloakId, userRole.getName());
                        }

                        User savedUser = userRepository.save(newUser);

                        auditLogService.logAuditEvent(
                                savedUser.getId(),
                                "USER_CREATED_GOOGLE",
                                "USER",
                                savedUser.getId(),
                                "User created via Google OAuth2",
                                "SUCCESS"
                        );

                        return savedUser;
                    });

            // Check if account is enabled
            if (!user.isEnabled()) {
                throw new UnauthorizedException("Account is " + user.getStatus().name().toLowerCase());
            }

            // Update last login
            user.setLastLogin(LocalDateTime.now());
            user.setFailedLoginAttempts(0);
            userRepository.save(user);

            // Log successful login
            auditLogService.logAuditEvent(
                    user.getId(),
                    "LOGIN_SUCCESS_GOOGLE",
                    "USER",
                    user.getId(),
                    "User logged in via Google OAuth2",
                    "SUCCESS"
            );

            log.info("Google OAuth2 login successful: {}", user.getEmail());

            return generateAuthResponse(user, "Google login successful");

        } catch (IOException e) {
            log.error("Google ID token verification failed", e);
            throw new UnauthorizedException("Google authentication failed: " + e.getMessage());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Change user password.
     *
     * @param userId User ID
     * @param request Change password request
     */
    @Transactional
    public void changePassword(String userId, ChangePasswordRequest request) {
        log.info("Changing password for user: {}", userId);

        // Validate passwords match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidUserException("Passwords do not match");
        }

        // Validate new password is different from old
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new InvalidUserException("New password must be different from current password");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidUserException("Current password is incorrect");
        }

        // Update password locally
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setLastPasswordChange(LocalDateTime.now());
        User savedUser =  userRepository.save(user);

        // Update password in Keycloak
        keycloakService.setUserPassword(user.getKeycloakId(), request.getNewPassword());

        try{
            PasswordChangedEvent event = PasswordChangedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .email(savedUser.getEmail())
                    .firstName(savedUser.getFirstName())
                    .changeType("CHANGED")
                    .userId(savedUser.getId())
                    .ipAddress(request.getIpAddress())
                    .occurredAt(LocalDateTime.now())
                    .userAgent(savedUser.getUserType().toString())
                    .build();
            eventPublisher.publishPasswordChange(event);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
        auditLogService.logAuditEvent(
                userId,
                "PASSWORD_CHANGED",
                "USER",
                userId,
                "Password changed successfully",
                "SUCCESS"
        );

        log.info("Password changed successfully for user: {}", userId);
    }

    /**
     * Refresh access token using refresh token.
     *
     * @param request Refresh token request
     * @return New auth response with refreshed token
     */
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Refreshing access token");

        try {
            // Parse refresh token
            String userIdFromToken = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(request.getRefreshToken())
                    .getBody()
                    .getSubject();

            User user = userRepository.findById(userIdFromToken)
                    .orElseThrow(() -> new UserNotFoundException(userIdFromToken));

            if (!user.isEnabled()) {
                throw new UnauthorizedException("Account is disabled");
            }

            log.info("Access token refreshed for user: {}", user.getUsername());

            return generateAuthResponse(user, "Token refreshed successfully");

        } catch (Exception e) {
            log.error("Token refresh failed", e);
            throw new UnauthorizedException("Invalid refresh token: " + e.getMessage());
        }
    }

    /**
     * Forgot password - initiate password reset.
     *
     * @param request Forgot password request
     */
    public void forgotPassword(ForgotPasswordRequest request) {
        log.info("Forgot password request for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email", request.getEmail()));

        // Generate reset token
        String resetToken = generateResetToken(user.getId());

        // In production, send email with reset link
        // For now, just log the token
        log.info("Password reset token for {}: {}", user.getEmail(), resetToken);

        auditLogService.logAuditEvent(
                user.getId(),
                "PASSWORD_RESET_REQUESTED",
                "USER",
                user.getId(),
                "Password reset requested",
                "SUCCESS"
        );
    }

    /**
     * Reset password with token.
     *
     * @param request Reset password request
     */
    public void resetPassword(ResetPasswordRequest request) {
        log.info("Resetting password with token");

        // Validate passwords match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidUserException("Passwords do not match");
        }

        try {
            // Verify reset token
            String userIdFromToken = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(request.getResetToken())
                    .getBody()
                    .getSubject();

            User user = userRepository.findById(userIdFromToken)
                    .orElseThrow(() -> new UserNotFoundException(userIdFromToken));

            // Update password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setLastPasswordChange(LocalDateTime.now());
            userRepository.save(user);

            // Update password in Keycloak
            keycloakService.setUserPassword(user.getKeycloakId(), request.getNewPassword());

            auditLogService.logAuditEvent(
                    user.getId(),
                    "PASSWORD_RESET",
                    "USER",
                    user.getId(),
                    "Password reset successfully",
                    "SUCCESS"
            );

            log.info("Password reset successfully for user: {}", user.getId());

        } catch (Exception e) {
            log.error("Password reset failed", e);
            throw new InvalidUserException("Invalid or expired reset token");
        }
    }

    /**
     * Generate authentication response with tokens.
     *
     * @param user User entity
     * @param message Success message
     * @return Auth response
     */
    private AuthResponse generateAuthResponse(User user, String message) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus().name())
                .emailVerified(user.getEmailVerified())
                .twoFactorEnabled(user.getTwoFactorEnabled())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpirationMs / 1000)
                .user(userDTO)
                .message(message)
                .build();
    }

    /**
     * Generate access token.
     *
     * @param user User entity
     * @return JWT access token
     */
    private String generateAccessToken(User user) {
        List<String> roleNames = roleRepository.findAllById(user.getRoleIds())
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Generate refresh token.
     *
     * @param user User entity
     * @return JWT refresh token
     */
    private String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Generate password reset token.
     *
     * @param userId User ID
     * @return Reset token (valid for 1 hour)
     */
    private String generateResetToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Get signing key for JWT.
     *
     * @return Signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private AuthResponse generateResponseTokenWithKeycloak(String keycloakTokenResponse, User user, String message) {

        try {
            JsonNode jsonNode = objectMapper.readTree(keycloakTokenResponse);
            String accessToken = jsonNode.path("access_token").asText(null);
            String refreshToken = jsonNode.path("refresh_token").asText(null);
            String idToken = jsonNode.path("id_token").asText(null);
            String tokenType = jsonNode.path("token_type").asText(null);
            long expiresIn = jsonNode.path("expires_in").asLong(0);
            long refreshExpiresIn = jsonNode.path("refresh_expires_in").asLong(0);
            String scope = jsonNode.path("scope").asText(null);


            UserDTO userDTO = null;
            if (user != null) {
                userDTO = UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .status(user.getStatus().name())
                        .emailVerified(user.getEmailVerified())
                        .twoFactorEnabled(user.getTwoFactorEnabled())
                        .lastLogin(user.getLastLogin())
                        .createdAt(user.getCreatedAt())
                        .build();
            }
            return AuthResponse.builder()
                    .accessToken(accessToken)              // ✅ FROM KEYCLOAK
                    .refreshToken(refreshToken)            // ✅ FROM KEYCLOAK
                    .tokenType("Bearer")
                    .expiresIn(expiresIn)
                    .user(userDTO)
                    .message(message)
                    .build();

        } catch (Exception e) {
            log.error("Failed to parse Keycloak token response", e);
            throw new UnauthorizedException("Failed to process authentication response");
        }
    }
    private String getTokensFromKeycloak(String username, String password) {
        try {
            String url = keycloakServerUrl +"/realms/"+realm+"/protocol/openid-connect/token";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");                    // Password grant
            body.add("client_id", clientId);              // Your client secret
            body.add("username", username.toLowerCase());                        // User's username
            body.add("password", password);                        // User's password
            body.add("client_secret", clientSecret);
            body.add("scope", "openid profile email roles");      // Request scopes
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new UnauthorizedException("Failed to obtain tokens from Keycloak");
            }

            return response.getBody();

        } catch (Exception e) {
            throw new UnauthorizedException("Failed to authenticate with Keycloak: " + e.getMessage());
        }
    }
}