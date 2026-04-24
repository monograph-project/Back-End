# Auth Service → Keycloak-Only Refactor Pack

This refactor converts the current `auth-service` into a **Keycloak-only identity service**.

It is based on the uploaded `logic.md`, which currently shows:
- MongoDB dependencies and repositories for `User`, `Role`, and `Permission`
- `AuthenticationService` using `UserRepository` and `RoleRepository`
- `RoleService` treating MongoDB as the authority and Keycloak as a sync target
- `PermissionService` storing permissions in MongoDB
- `SecurityConfig` reading only realm roles
- `pom.xml` containing both `keycloak-spring-boot-starter` and `spring-boot-starter-data-mongodb`

Those are the main architectural problems to remove.

---

## 1) Target architecture

### Keep
- Keycloak as the only source of truth for:
  - users
  - passwords
  - roles
  - permissions (model them as Keycloak client roles)
  - groups
  - tokens
- Spring Security resource server for token validation
- `keycloak-admin-client` for admin operations
- Kafka if you still want events
- `AuditLog` only if it is business/audit metadata, not identity truth

### Remove
- MongoDB as the identity source
- local user/role/permission storage
- JWT generation with your own `JWT_SECRET`
- Keycloak Spring Boot adapter
- “Mongo is the authority, Keycloak sync is best effort” pattern

---

## 2) Delete these files/classes

Delete these if they are only used for auth identity storage:

```text
src/main/java/com/final_project/auth_service/model/User.java
src/main/java/com/final_project/auth_service/model/Role.java
src/main/java/com/final_project/auth_service/model/Permission.java
src/main/java/com/final_project/auth_service/repository/UserRepository.java
src/main/java/com/final_project/auth_service/repository/RoleRepository.java
src/main/java/com/final_project/auth_service/repository/PermissionRepository.java
```

Keep `AuditLog` only if it stores app-side audit events and not canonical identity data.

---

## 3) pom.xml replacement notes

### Remove

```xml
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-spring-boot-starter</artifactId>
    <version>${keycloak.version}</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

### Keep

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-admin-client</artifactId>
    <version>${keycloak.version}</version>
</dependency>
```

---

## 4) .env replacement

Use a dedicated admin client for the auth service.

```dotenv
KEYCLOAK_SERVER_URL=http://localhost:8080
KEYCLOAK_REALM=platform
KEYCLOAK_ADMIN_CLIENT_ID=auth-service-admin
KEYCLOAK_ADMIN_CLIENT_SECRET=change-me
KEYCLOAK_PUBLIC_CLIENT_ID=frontend

SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI=http://localhost:8080/realms/platform/protocol/openid-connect/certs
SPRING_CORS_ALLOWED_ORIGINS=http://localhost:3000

GOOGLE_CLIENT_ID=change-me
GOOGLE_CLIENT_SECRET=change-me

MAIL_HOST=change-me
MAIL_USERNAME=change-me
MAIL_PORT=587
MAIL_PASSWORD=change-me
```

Remove local JWT secret and DB credentials if they were only used for auth identity storage.

---

## 5) application.yaml replacement

```yaml
spring:
  application:
    name: auth-service
  config:
    import: optional:configserver:http://localhost:8888
  profiles:
    active: native
  kafka:
    bootstrap-servers: http://localhost:9092
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: ${SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI}

keycloak:
  server-url: ${KEYCLOAK_SERVER_URL}
  realm: ${KEYCLOAK_REALM}
  admin:
    client-id: ${KEYCLOAK_ADMIN_CLIENT_ID}
    client-secret: ${KEYCLOAK_ADMIN_CLIENT_SECRET}
  public-client-id: ${KEYCLOAK_PUBLIC_CLIENT_ID}

google:
  client-id: ${GOOGLE_CLIENT_ID}
  client-secret: ${GOOGLE_CLIENT_SECRET}

spring:
  cors:
    allowed-origins: ${SPRING_CORS_ALLOWED_ORIGINS:*}
```

If you already externalize these through a config server, mirror the same keys there.

---

## 6) Replace `KeycloakConfig.java`

```java
package com.final_project.auth_service.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.client-secret}")
    private String adminClientSecret;

    @Value("${keycloak.public-client-id}")
    private String publicClientId;

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getRealm() {
        return realm;
    }

    public String getAdminClientId() {
        return adminClientId;
    }

    public String getAdminClientSecret() {
        return adminClientSecret;
    }

    public String getPublicClientId() {
        return publicClientId;
    }

    public String tokenUrl() {
        return serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
    }

    public String logoutUrl() {
        return serverUrl + "/realms/" + realm + "/protocol/openid-connect/logout";
    }
}
```

---

## 7) Replace `SecurityConfig.java`

This version reads:
- OAuth scopes
- realm roles
- client roles from `resource_access`

```java
package com.final_project.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Value("${spring.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/signup",
                                "/api/v1/auth/google",
                                "/api/v1/auth/refresh-token",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password",
                                "/api/v1/auth/verify-email",
                                "/api/v1/auth/resend-verification-email",
                                "/health",
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Set<GrantedAuthority> authorities = new HashSet<>();

            Collection<GrantedAuthority> scopeAuthorities = scopeConverter.convert(jwt);
            if (scopeAuthorities != null) {
                authorities.addAll(scopeAuthorities);
            }

            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null) {
                Object roles = realmAccess.get("roles");
                if (roles instanceof Collection<?> roleList) {
                    for (Object role : roleList) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase().replace("-", "_")));
                    }
                }
            }

            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null) {
                resourceAccess.forEach((clientId, value) -> {
                    if (value instanceof Map<?, ?> clientMap) {
                        Object roles = clientMap.get("roles");
                        if (roles instanceof Collection<?> roleList) {
                            for (Object role : roleList) {
                                authorities.add(new SimpleGrantedAuthority(
                                        "ROLE_" + clientId.toUpperCase().replace("-", "_") + "_" + role.toString().toUpperCase().replace(".", "_").replace("-", "_")
                                ));
                            }
                        }
                    }
                });
            }

            return authorities;
        });

        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        if ("*".equals(allowedOrigins)) {
            config.setAllowCredentials(false);
            config.addAllowedOriginPattern("*");
        } else {
            config.setAllowCredentials(true);
            Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)
                    .forEach(config::addAllowedOrigin);
        }

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## 8) Replace `KeycloakService.java`

This becomes the main Keycloak wrapper.

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.config.KeycloakConfig;
import com.final_project.auth_service.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;

    private RealmResource realm() {
        return keycloak.realm(keycloakConfig.getRealm());
    }

    public String createUser(String username, String email, String firstName, String lastName, String password, boolean enabled, boolean emailVerified) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(enabled);
        user.setEmailVerified(emailVerified);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = realm().users().create(user);
        if (response.getStatus() != 201) {
            throw new KeycloakException("Failed to create user in Keycloak. HTTP " + response.getStatus());
        }

        String location = response.getHeaderString("Location");
        return location.substring(location.lastIndexOf('/') + 1);
    }

    public Optional<UserRepresentation> findUserByUsername(String username) {
        List<UserRepresentation> users = realm().users().searchByUsername(username, true);
        return users.stream().findFirst();
    }

    public Optional<UserRepresentation> findUserByEmail(String email) {
        List<UserRepresentation> users = realm().users().searchByEmail(email, true);
        return users.stream().findFirst();
    }

    public UserRepresentation getUserById(String userId) {
        try {
            return realm().users().get(userId).toRepresentation();
        } catch (Exception e) {
            throw new KeycloakException("User not found in Keycloak: " + userId, e);
        }
    }

    public void updateUser(String userId, UserRepresentation userRepresentation) {
        try {
            realm().users().get(userId).update(userRepresentation);
        } catch (Exception e) {
            throw new KeycloakException("Failed to update user in Keycloak: " + userId, e);
        }
    }

    public void setPassword(String userId, String newPassword, boolean temporary) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(temporary);
        realm().users().get(userId).resetPassword(credential);
    }

    public void sendVerifyEmail(String userId) {
        realm().users().get(userId).sendVerifyEmail();
    }

    public void sendResetPasswordEmail(String userId) {
        realm().users().get(userId).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public void assignRealmRoles(String userId, List<String> roleNames) {
        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> realm().roles().get(roleName).toRepresentation())
                .toList();
        realm().users().get(userId).roles().realmLevel().add(roles);
    }

    public void removeRealmRoles(String userId, List<String> roleNames) {
        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> realm().roles().get(roleName).toRepresentation())
                .toList();
        realm().users().get(userId).roles().realmLevel().remove(roles);
    }

    public void createRealmRole(String roleName, String description) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription(description);
        realm().roles().create(role);
    }

    public void createClientRole(String clientId, String roleName, String description) {
        ClientResource clientResource = clientResource(clientId);
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription(description);
        clientResource.roles().create(role);
    }

    public void assignClientRoles(String userId, String clientId, List<String> roleNames) {
        ClientResource clientResource = clientResource(clientId);
        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> clientResource.roles().get(roleName).toRepresentation())
                .toList();
        realm().users().get(userId).roles().clientLevel(clientUuid(clientId)).add(roles);
    }

    public void removeClientRoles(String userId, String clientId, List<String> roleNames) {
        ClientResource clientResource = clientResource(clientId);
        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> clientResource.roles().get(roleName).toRepresentation())
                .toList();
        realm().users().get(userId).roles().clientLevel(clientUuid(clientId)).remove(roles);
    }

    public List<RoleRepresentation> getRealmRoles() {
        return realm().roles().list();
    }

    public List<RoleRepresentation> getClientRoles(String clientId) {
        return clientResource(clientId).roles().list();
    }

    public void deleteUser(String userId) {
        realm().users().delete(userId);
    }

    public void setEnabled(String userId, boolean enabled) {
        UserResource userResource = realm().users().get(userId);
        UserRepresentation user = userResource.toRepresentation();
        user.setEnabled(enabled);
        userResource.update(user);
    }

    private ClientResource clientResource(String clientId) {
        return realm().clients().get(clientUuid(clientId));
    }

    private String clientUuid(String clientId) {
        ClientsResource clients = realm().clients();
        List<ClientRepresentation> found = clients.findByClientId(clientId);
        if (found == null || found.isEmpty()) {
            throw new KeycloakException("Keycloak client not found: " + clientId);
        }
        return found.get(0).getId();
    }
}
```

---

## 9) Replace `AuthenticationService.java`

This version stops using local repositories and local JWT creation.

```java
package com.final_project.auth_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.auth_service.config.KeycloakConfig;
import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.event.PasswordChangedEvent;
import com.final_project.auth_service.event.UserRegisteredEvent;
import com.final_project.auth_service.exception.DuplicateUserException;
import com.final_project.auth_service.exception.KeycloakException;
import com.final_project.auth_service.exception.UnauthorizedException;
import com.final_project.auth_service.kafka.AuthEventPublisher;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final AuthEventPublisher eventPublisher;
    private final KeycloakConfig keycloakConfig;

    @Value("${app.default-role:faculty-user}")
    private String defaultRole;

    public AuthResponse login(LoginRequest request) {
        return requestToken("password", Map.of(
                "username", request.getUsernameOrEmail(),
                "password", request.getPassword()
        ));
    }

    public AuthResponse signup(SignupRequest request) {
        ensureUserDoesNotExist(request.getUsername(), request.getEmail());

        String userId = keycloakService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                true,
                false
        );

        keycloakService.assignRealmRoles(userId, List.of(defaultRole));
        keycloakService.sendVerifyEmail(userId);

        auditLogService.logAuditEvent(userId, "USER_REGISTERED", "USER", userId, "User registered in Keycloak", "SUCCESS");
        eventPublisher.publishUserRegistered(new UserRegisteredEvent(userId, request.getEmail(), request.getUsername()));

        return requestToken("password", Map.of(
                "username", request.getUsername(),
                "password", request.getPassword()
        ));
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
                String tempPassword = java.util.UUID.randomUUID().toString();
                String userId = keycloakService.createUser(username, email, givenName, familyName, tempPassword, true, true);
                keycloakService.assignRealmRoles(userId, List.of(defaultRole));
                auditLogService.logAuditEvent(userId, "USER_REGISTERED_GOOGLE", "USER", userId, "Google user created in Keycloak", "SUCCESS");
            }

            throw new UnsupportedOperationException("Google login should exchange through frontend OIDC flow or a dedicated brokered Keycloak flow.");
        } catch (GeneralSecurityException | IOException e) {
            throw new UnauthorizedException("Google authentication failed", e);
        }
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return requestToken("refresh_token", Map.of(
                "refresh_token", request.getRefreshToken()
        ));
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        keycloakService.setPassword(userId, request.getNewPassword(), false);
        auditLogService.logAuditEvent(userId, "PASSWORD_CHANGED", "USER", userId, "Password changed in Keycloak", "SUCCESS");
        eventPublisher.publishPasswordChanged(new PasswordChangedEvent(userId, request.getIpAddress()));
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        UserRepresentation user = keycloakService.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("User not found for email"));
        keycloakService.sendResetPasswordEmail(user.getId());
        auditLogService.logAuditEvent(user.getId(), "PASSWORD_RESET_REQUESTED", "USER", user.getId(), "Password reset email requested", "SUCCESS");
    }

    public void resetPassword(ResetPasswordRequest request) {
        throw new UnsupportedOperationException("Use Keycloak execute-actions-email or frontend reset flow instead of local reset tokens.");
    }

    public void verifyEmail(EmailVerificationRequest request) {
        throw new UnsupportedOperationException("Use Keycloak verify-email action flow instead of local email verification tokens.");
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

    private AuthResponse requestToken(String grantType, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", keycloakConfig.getPublicClientId());
        form.add("grant_type", grantType);
        params.forEach(form::add);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(keycloakConfig.tokenUrl(), entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new KeycloakException("Failed to obtain token from Keycloak");
        }

        try {
            JsonNode node = objectMapper.readTree(response.getBody());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken(node.path("access_token").asText());
            authResponse.setRefreshToken(node.path("refresh_token").asText());
            authResponse.setTokenType(node.path("token_type").asText("Bearer"));
            authResponse.setExpiresIn(node.path("expires_in").asLong());
            return authResponse;
        } catch (Exception e) {
            throw new KeycloakException("Failed to parse Keycloak token response", e);
        }
    }
}
```

### Important note about login
This uses the **resource owner password** pattern because your current controller shape already has `/login` with username/password. For production, the cleaner model is frontend OIDC login redirect. If you keep direct login, make sure the Keycloak client is configured for it and accept the tradeoffs.

---

## 10) Replace `UserService.java`

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.dto.AuthorResponse;
import com.final_project.auth_service.dto.CreateUserRequest;
import com.final_project.auth_service.dto.UpdateUserRequest;
import com.final_project.auth_service.dto.UserDTO;
import com.final_project.auth_service.exception.DuplicateUserException;
import com.final_project.auth_service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final KeycloakService keycloakService;
    private final AuditLogService auditLogService;

    public UserDTO createUser(CreateUserRequest request) {
        if (keycloakService.findUserByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUserException("username", request.getUsername());
        }
        if (keycloakService.findUserByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateUserException("email", request.getEmail());
        }

        String userId = keycloakService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                true,
                false
        );

        if (request.getRoleNames() != null && !request.getRoleNames().isEmpty()) {
            keycloakService.assignRealmRoles(userId, request.getRoleNames());
        }

        auditLogService.logAuditEvent(userId, "USER_CREATED", "USER", userId, "User created in Keycloak", "SUCCESS");
        return toDTO(keycloakService.getUserById(userId));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(String id) {
        return toDTO(keycloakService.getUserById(id));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        return keycloakService.findUserByUsername(username)
                .map(this::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found by username: " + username));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        return keycloakService.findUserByEmail(email)
                .map(this::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found by email: " + email));
    }

    public UserDTO updateUser(String userId, UpdateUserRequest request) {
        UserRepresentation user = keycloakService.getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setEnabled(request.getEnabled());
        keycloakService.updateUser(userId, user);
        return toDTO(keycloakService.getUserById(userId));
    }

    public void deleteUser(String userId) {
        keycloakService.deleteUser(userId);
        auditLogService.logAuditEvent(userId, "USER_DELETED", "USER", userId, "User deleted from Keycloak", "SUCCESS");
    }

    public AuthorResponse getUserAsAuthor(String id) {
        UserRepresentation user = keycloakService.getUserById(id);
        AuthorResponse response = new AuthorResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName((user.getFirstName() == null ? "" : user.getFirstName()) + " " + (user.getLastName() == null ? "" : user.getLastName()));
        return response;
    }

    private UserDTO toDTO(UserRepresentation user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEnabled(user.isEnabled());
        dto.setEmailVerified(user.isEmailVerified());
        return dto;
    }
}
```

---

## 11) Replace `RoleService.java`

Realm roles should live in Keycloak.

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.dto.RoleDTO;
import com.final_project.auth_service.dto.CreateRoleRequest;
import com.final_project.auth_service.dto.UpdateRoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoleService {

    private final KeycloakService keycloakService;
    private final AuditLogService auditLogService;

    public RoleDTO createRole(CreateRoleRequest request) {
        keycloakService.createRealmRole(request.getName(), request.getDescription());
        auditLogService.logAuditEvent(null, "ROLE_CREATED", "ROLE", request.getName(), "Realm role created in Keycloak", "SUCCESS");
        return new RoleDTO(null, request.getName(), request.getDescription(), null, true, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        return keycloakService.getRealmRoles().stream().map(this::toDTO).toList();
    }

    public void assignRoleToUser(String roleName, String userId) {
        keycloakService.assignRealmRoles(userId, List.of(roleName));
        auditLogService.logAuditEvent(userId, "ROLE_ASSIGNED", "USER", userId, "Assigned realm role " + roleName, "SUCCESS");
    }

    public void removeRoleFromUser(String roleName, String userId) {
        keycloakService.removeRealmRoles(userId, List.of(roleName));
        auditLogService.logAuditEvent(userId, "ROLE_REMOVED", "USER", userId, "Removed realm role " + roleName, "SUCCESS");
    }

    private RoleDTO toDTO(RoleRepresentation role) {
        RoleDTO dto = new RoleDTO();
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setIsActive(true);
        return dto;
    }
}
```

### Controller path fix
Your current `RoleController` uses `/{roleId}/assign-to-user/{userId}` and treats roles as DB records. In Keycloak-only mode, use role names for realm roles:

```text
POST /api/v1/roles/{roleName}/assign-to-user/{userId}
DELETE /api/v1/roles/{roleName}/remove-from-user/{userId}
```

---

## 12) Replace `PermissionService.java`

Permissions should become **client roles**.

Example:
- `file.read`
- `file.upload`
- `config.write`

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.dto.CreatePermissionRequest;
import com.final_project.auth_service.dto.PermissionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PermissionService {

    private final KeycloakService keycloakService;
    private final AuditLogService auditLogService;

    public PermissionDTO createPermission(CreatePermissionRequest request) {
        String clientId = request.getClientId();
        String roleName = request.getResource().toLowerCase() + "." + request.getAction().toLowerCase();
        keycloakService.createClientRole(clientId, roleName, request.getDescription());

        auditLogService.logAuditEvent(null, "PERMISSION_CREATED", "CLIENT_ROLE", roleName, "Client role created in Keycloak for client " + clientId, "SUCCESS");

        PermissionDTO dto = new PermissionDTO();
        dto.setName(roleName);
        dto.setDescription(request.getDescription());
        dto.setResource(request.getResource());
        dto.setAction(request.getAction());
        dto.setPermissionKey(roleName);
        dto.setIsActive(true);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<PermissionDTO> getPermissionsByClient(String clientId) {
        return keycloakService.getClientRoles(clientId).stream()
                .map(role -> toDTO(clientId, role))
                .toList();
    }

    public void assignPermissionToUser(String clientId, String roleName, String userId) {
        keycloakService.assignClientRoles(userId, clientId, List.of(roleName));
        auditLogService.logAuditEvent(userId, "CLIENT_ROLE_ASSIGNED", "USER", userId, "Assigned client role " + roleName + " for client " + clientId, "SUCCESS");
    }

    public void removePermissionFromUser(String clientId, String roleName, String userId) {
        keycloakService.removeClientRoles(userId, clientId, List.of(roleName));
        auditLogService.logAuditEvent(userId, "CLIENT_ROLE_REMOVED", "USER", userId, "Removed client role " + roleName + " for client " + clientId, "SUCCESS");
    }

    private PermissionDTO toDTO(String clientId, RoleRepresentation role) {
        PermissionDTO dto = new PermissionDTO();
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setPermissionKey(clientId + ":" + role.getName());
        dto.setIsActive(true);
        return dto;
    }
}
```

### DTO note
Your current `CreatePermissionRequest` likely does not include `clientId`. Add it:

```java
private String clientId;
```

---

## 13) Controller-level changes

### `AuthenticationController`
Keep these routes:
- `/login`
- `/signup`
- `/refresh-token`
- `/forgot-password`
- `/resend-verification-email`
- `/change-password/{userId}`
- `/me`

### Remove local-token semantics from:
- `/reset-password`
- `/verify-email`

Those should defer to Keycloak action-email flows, not local token parsing.

### `PermissionController`
Replace “permission DB CRUD” with client-role operations.

Suggested routes:

```text
POST   /api/v1/permissions
GET    /api/v1/permissions/client/{clientId}
POST   /api/v1/permissions/client/{clientId}/role/{roleName}/assign-to-user/{userId}
DELETE /api/v1/permissions/client/{clientId}/role/{roleName}/remove-from-user/{userId}
```

### `RoleController`
Use only realm-role operations.

### `UserController`
Use only Keycloak user operations.

---

## 14) Authorization model for your microservices

Use one realm:

```text
platform
```

Use one client per microservice:

```text
frontend
control-version-service
weblog-service
faculty-service
file-service
config-service
notification-service
auth-service-admin
```

### Realm roles
```text
platform-admin
operator
faculty-user
```

### Client roles

#### control-version-service
```text
version.read
version.write
version.audit
```

#### weblog-service
```text
weblog.read
weblog.export
```

#### faculty-service
```text
faculty.read
faculty.write
faculty.admin
```

#### file-service
```text
file.read
file.upload
file.delete
```

#### config-service
```text
config.read
config.write
```

#### notification-service
```text
notification.send
notification.read
```

In code, these client roles become authorities like:

```text
ROLE_FILE_SERVICE_FILE_READ
ROLE_CONFIG_SERVICE_CONFIG_WRITE
```

So your endpoint protection can look like:

```java
@PreAuthorize("hasAuthority('ROLE_FILE_SERVICE_FILE_READ')")
@PreAuthorize("hasAuthority('ROLE_CONFIG_SERVICE_CONFIG_WRITE')")
@PreAuthorize("hasRole('PLATFORM_ADMIN')")
```

---

## 15) What to do with `AuditLogService`

### Safe to keep
If it stores:
- signup audit events
- password changed event metadata
- role assignment logs
- admin actions

### Not safe to keep as truth
If it stores canonical users/roles/permissions.

---

## 16) Things that must be removed from the current logic

From the uploaded code, these patterns must disappear:

### In `AuthenticationService`
- `UserRepository`
- `RoleRepository`
- `PasswordEncoder` for storing local passwords
- local JWT generation via `jjwt`
- local refresh token logic

### In `RoleService`
- “MongoDB authority + Keycloak sync”
- `RoleRepository`
- `UserRepository`
- saving Keycloak IDs back into Mongo roles

### In `PermissionService`
- `PermissionRepository`
- `RoleRepository`
- Mongo CRUD for permissions

---

## 17) Minimal migration order

1. Remove Mongo repositories and models for identity
2. Remove `keycloak-spring-boot-starter`
3. Remove `jjwt` dependencies and local JWT logic
4. Replace `KeycloakConfig`
5. Replace `SecurityConfig`
6. Replace `KeycloakService`
7. Replace `AuthenticationService`
8. Replace `UserService`
9. Replace `RoleService`
10. Replace `PermissionService`
11. Adjust DTOs so permission creation includes `clientId`
12. Update controller routes where role IDs were previously DB IDs
13. In Keycloak, create the realm, clients, roles, and admin client

---

## 18) Final outcome

After this refactor:
- Keycloak is the only identity source
- Mongo is no longer used for auth identity
- auth-service becomes an orchestration/admin API
- permissions are represented as Keycloak client roles
- each service can authorize locally using Keycloak JWTs

