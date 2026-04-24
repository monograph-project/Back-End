# auth-service logic

## Project Structure

```text
auth-service/
  .env
  pom.xml
  src/main/resources/application.yaml
  src/main/resources/templates/db/changelog/db.changelog-master.yaml
  src/main/java/com/final_project/auth_service/AuthServiceApplication.java
  src/main/java/com/final_project/auth_service/config/AppProperties.java
  src/main/java/com/final_project/auth_service/config/GoogleOAuth2Config.java
  src/main/java/com/final_project/auth_service/config/KafkaProducerConfig.java
  src/main/java/com/final_project/auth_service/config/KeycloakConfig.java
  src/main/java/com/final_project/auth_service/config/RemoteIP.java
  src/main/java/com/final_project/auth_service/config/RestTemplateConfig.java
  src/main/java/com/final_project/auth_service/config/SecurityConfig.java
  src/main/java/com/final_project/auth_service/config/SwaggerConfig.java
  src/main/java/com/final_project/auth_service/config/WebClientConfig.java
  src/main/java/com/final_project/auth_service/controller/AuthenticationController.java
  src/main/java/com/final_project/auth_service/controller/PermissionController.java
  src/main/java/com/final_project/auth_service/controller/RoleController.java
  src/main/java/com/final_project/auth_service/controller/UserController.java
  src/main/java/com/final_project/auth_service/dto/AssignPermissionsToRoleRequest.java
  src/main/java/com/final_project/auth_service/dto/AssignRolesToUserRequest.java
  src/main/java/com/final_project/auth_service/dto/AuthorResponse.java
  src/main/java/com/final_project/auth_service/dto/AuthResponse.java
  src/main/java/com/final_project/auth_service/dto/BulkCreatePermissionsRequest.java
  src/main/java/com/final_project/auth_service/dto/BulkCreateRolesRequest.java
  src/main/java/com/final_project/auth_service/dto/ChangePasswordRequest.java
  src/main/java/com/final_project/auth_service/dto/CreatePermissionRequest.java
  src/main/java/com/final_project/auth_service/dto/CreateRoleRequest.java
  src/main/java/com/final_project/auth_service/dto/CreateUserRequest.java
  src/main/java/com/final_project/auth_service/dto/EmailVerificationRequest.java
  src/main/java/com/final_project/auth_service/dto/ForgotPasswordRequest.java
  src/main/java/com/final_project/auth_service/dto/GoogleOAuth2Request.java
  src/main/java/com/final_project/auth_service/dto/LoginHistoryDTO.java
  src/main/java/com/final_project/auth_service/dto/LoginRequest.java
  src/main/java/com/final_project/auth_service/dto/PermissionDTO.java
  src/main/java/com/final_project/auth_service/dto/RefreshTokenRequest.java
  src/main/java/com/final_project/auth_service/dto/ResendVerificationEmailRequest.java
  src/main/java/com/final_project/auth_service/dto/ResetPasswordRequest.java
  src/main/java/com/final_project/auth_service/dto/RoleDTO.java
  src/main/java/com/final_project/auth_service/dto/SignupRequest.java
  src/main/java/com/final_project/auth_service/dto/TokenValidationResponse.java
  src/main/java/com/final_project/auth_service/dto/UpdatePermissionRequest.java
  src/main/java/com/final_project/auth_service/dto/UpdateRoleRequest.java
  src/main/java/com/final_project/auth_service/dto/UpdateUserRequest.java
  src/main/java/com/final_project/auth_service/dto/UserDTO.java
  src/main/java/com/final_project/auth_service/event/PasswordChangedEvent.java
  src/main/java/com/final_project/auth_service/event/UserRegisteredEvent.java
  src/main/java/com/final_project/auth_service/exception/DuplicateUserException.java
  src/main/java/com/final_project/auth_service/exception/ForbiddenException.java
  src/main/java/com/final_project/auth_service/exception/GlobalExceptionHandler.java
  src/main/java/com/final_project/auth_service/exception/InvalidUserException.java
  src/main/java/com/final_project/auth_service/exception/KeycloakException.java
  src/main/java/com/final_project/auth_service/exception/UnauthorizedException.java
  src/main/java/com/final_project/auth_service/exception/UserNotFoundException.java
  src/main/java/com/final_project/auth_service/exception/UserServiceException.java
  src/main/java/com/final_project/auth_service/kafka/AuthEventPublisher.java
  src/main/java/com/final_project/auth_service/model/AuditLog.java
  src/main/java/com/final_project/auth_service/model/Permission.java
  src/main/java/com/final_project/auth_service/model/Role.java
  src/main/java/com/final_project/auth_service/model/User.java
  src/main/java/com/final_project/auth_service/repository/AuditLogRepository.java
  src/main/java/com/final_project/auth_service/repository/PermissionRepository.java
  src/main/java/com/final_project/auth_service/repository/RoleRepository.java
  src/main/java/com/final_project/auth_service/repository/UserRepository.java
  src/main/java/com/final_project/auth_service/service/AuditLogService.java
  src/main/java/com/final_project/auth_service/service/AuthenticationService.java
  src/main/java/com/final_project/auth_service/service/IPAdressResolver.java
  src/main/java/com/final_project/auth_service/service/KeycloakService.java
  src/main/java/com/final_project/auth_service/service/PermissionService.java
  src/main/java/com/final_project/auth_service/service/RoleService.java
  src/main/java/com/final_project/auth_service/service/UserService.java
```

## .env

<!-- File: .env -->

```dotenv
DB_USER=<REDACTED>
DB_PASSWORD=<REDACTED>

KEYCLOAK_SERVER_URL=<REDACTED>

KEYCLOAK_REALM=<REDACTED>
CORS_ALLOWED_ORIGINS=<REDACTED>
KEYCLOAK_CLIENT_ID=<REDACTED>
KEYCLOAK_CLIENT_SECRET=<REDACTED>
RABBIT_PASSWORD=<REDACTED>
RABBIT_USERNAME=<REDACTED>
RABBIT_PORT=<REDACTED>
RABBIT_HOST=<REDACTED>

JWT_SECRET=<REDACTED>

GOOGLE_CLIENT_ID=<REDACTED>
GOOGLE_CLIENT_SECRET=<REDACTED>

MAIL_HOST=<REDACTED>
MAIL_USERNAME=<REDACTED>
MAIL_PORT=<REDACTED>
MAIL_PASSWORD=<REDACTED>
```

## pom.xml

<!-- File: pom.xml -->

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.13</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.final_project</groupId>
	<artifactId>auth-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name/>
	<description/>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
		<keycloak.version>23.0.0</keycloak.version>
		<maven.compiler.source>17</maven.compiler.source>
		<maven.compiler.target>17</maven.compiler.target>
		<springdoc.version>2.1.0</springdoc.version>
		<spring-cloud.version>2025.0.2</spring-cloud.version>
	</properties>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<dependencies>
		<!-- Spring Boot Starters -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>

		<!-- Database -->

		<!-- Keycloak -->
		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-admin-client</artifactId>
			<version>${keycloak.version}</version>
		</dependency>

		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-spring-boot-starter</artifactId>
			<version>${keycloak.version}</version>
		</dependency>

		<!-- OpenAPI/Swagger -->

		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.5</version>
		</dependency>
		<!-- Lombok -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<!-- JWT -->
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

		<!-- Micrometer for Metrics -->
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>

		<!-- Apache Commons -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
		</dependency>

		<!-- Jackson -->
		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
		</dependency>

		<!-- Testing -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
		<dependency>
			<groupId>com.google.auth</groupId>
			<artifactId>google-auth-library-oauth2-http</artifactId>
			<version>1.11.0</version>
		</dependency>

		<dependency>
			<groupId>com.google.http-client</groupId>
			<artifactId>google-http-client-gson</artifactId>
			<version>1.43.2</version>
		</dependency>

		<dependency>
			<groupId>com.google.apis</groupId>
			<artifactId>google-api-services-oauth2</artifactId>
			<version>v2-rev157-1.25.0</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>

		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>17</source>
					<target>17</target>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<includes>
						<include>**/*Test.java</include>
						<include>**/*Tests.java</include>
					</includes>
				</configuration>
			</plugin>

		</plugins>
	</build>
</project>
```

## src/main/resources/application.yaml

<!-- File: src/main/resources/application.yaml -->

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
```

## src/main/resources/templates/db/changelog/db.changelog-master.yaml

<!-- File: src/main/resources/templates/db/changelog/db.changelog-master.yaml -->

```yaml
databaseChangeLog:
  - changeSet:
      id: 1
      author: elyas
      changes:
        - createTable:
            tableName: test_table
            columns:
              - column:
                  name: id
                  type: UUID
              - column:
                  name: name
                  type: VARCHAR(255)
```

## src/main/java/com/final_project/auth_service/AuthServiceApplication.java

<!-- File: src/main/java/com/final_project/auth_service/AuthServiceApplication.java -->

```java
package com.final_project.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
```

## src/main/java/com/final_project/auth_service/config/AppProperties.java

<!-- File: src/main/java/com/final_project/auth_service/config/AppProperties.java -->

```java
package com.final_project.auth_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private Kafka kafka = new  Kafka();

    @Data
    public static class Kafka{
        private Topics topics = new  Topics();

        @Data
        public static class Topics{
            private String userRegistered = "user.registered";
            private String changePassword = "change.password";

        }
    }
}
```

## src/main/java/com/final_project/auth_service/config/GoogleOAuth2Config.java

<!-- File: src/main/java/com/final_project/auth_service/config/GoogleOAuth2Config.java -->

```java
package com.final_project.auth_service.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Configuration
@Slf4j
public class GoogleOAuth2Config {

    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret:}")
    private String googleClientSecret;

    /**
     * Creates Google ID Token Verifier bean.
     *
     * Used to verify Google ID tokens received from frontend.
     *
     * @return GoogleIdTokenVerifier configured with Google client ID
     */
    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier() {
        log.info("Initializing Google ID Token Verifier");

        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    /**
     * @return Google Client ID
     */
    public String getGoogleClientId() {
        return googleClientId;
    }

    /**
     * @return Google Client Secret
     */
    public String getGoogleClientSecret() {
        return googleClientSecret;
    }
}
```

## src/main/java/com/final_project/auth_service/config/KafkaProducerConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/KafkaProducerConfig.java -->

```java
package com.final_project.auth_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final AppProperties appProperties;

    @Bean
    public NewTopic userRegisteredTopic(){
        return TopicBuilder
                .name(appProperties.getKafka().getTopics().getUserRegistered())
                .build();
    }

    @Bean
    public NewTopic passwordChangedTopic(){
        return TopicBuilder
                .name(appProperties.getKafka().getTopics().getChangePassword())
                .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, 3);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }





}
```

## src/main/java/com/final_project/auth_service/config/KeycloakConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/KeycloakConfig.java -->

```java
package com.final_project.auth_service.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Keycloak configuration for OAuth 2.0 authentication and authorization.
 *
 * Provides Keycloak admin client for:
 * - User management in Keycloak
 * - Role assignment
 * - Group management
 * - Client administration
 */
@Configuration
public class KeycloakConfig {

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;


    /**
     * Creates Keycloak admin client for server-side operations.
     *
     * @return Configured Keycloak admin client
     */
    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }

    /**
     * @return Keycloak server URL
     */
    public String getKeycloakServerUrl() {
        return keycloakServerUrl;
    }

    /**
     * @return Keycloak realm name
     */
    public String getRealm() {
        return realm;
    }

    /**
     * @return Keycloak client ID
     */
    public String getClientId() {
        return clientId;
    }
    public String getTokenURl(){
        return keycloakServerUrl + "/realms/" + this.realm + "/protocol/openid-connect/token";
    }
    public String keycloakSecretToken(){
        return this.clientSecret;
    }
}
```

## src/main/java/com/final_project/auth_service/config/RemoteIP.java

<!-- File: src/main/java/com/final_project/auth_service/config/RemoteIP.java -->

```java
package com.final_project.auth_service.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteIP {
}
```

## src/main/java/com/final_project/auth_service/config/RestTemplateConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/RestTemplateConfig.java -->

```java
package com.final_project.auth_service.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .build();
    }
}
```

## src/main/java/com/final_project/auth_service/config/SecurityConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/SecurityConfig.java -->

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
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/logout").permitAll()
                        .requestMatchers("/api/v1/auth/signup").permitAll()
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
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
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }
                }
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
        config.setAllowedHeaders(Arrays.asList("*"));
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

## src/main/java/com/final_project/auth_service/config/SwaggerConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/SwaggerConfig.java -->

```java
package com.final_project.auth_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration for API documentation.
 *
 * Provides:
 * - API information and metadata
 * - OAuth 2.0 security scheme
 * - Contact and license information
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures OpenAPI documentation with OAuth 2.0 security scheme.
     *
     * @return Configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Microservice API")
                        .version("1.0.0")
                        .description("Comprehensive user management service with authentication, authorization, and Keycloak integration")
                        .contact(new Contact()
                                .name("Microservices Team")
                                .email("support@example.com")
                                .url("https://example.com")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT token")
                        )
                );
    }
}
```

## src/main/java/com/final_project/auth_service/config/WebClientConfig.java

<!-- File: src/main/java/com/final_project/auth_service/config/WebClientConfig.java -->

```java
package com.final_project.auth_service.config;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for Spring WebClient used in service-to-service communication.
 *
 * Provides:
 * - Connection pooling
 * - Timeout configuration
 * - Logging support
 * - Error handling
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient bean with custom configuration.
     *
     * Configures:
     * - Connection timeouts
     * - Read/Write timeouts
     * - Connection pooling
     * - Request/Response logging
     *
     * @return Configured WebClient builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("user-service-pool")
                .maxConnections(100)
                .pendingAcquireTimeout(java.time.Duration.ofMillis(60000))
                .pendingAcquireMaxCount(1000)
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .responseTimeout(java.time.Duration.ofSeconds(60))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS))
                );

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10))
                .build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(exchangeStrategies);
    }

    /**
     * Creates a WebClient bean for inter-service communication.
     *
     * @param builder WebClient builder
     * @return Configured WebClient
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
```

## src/main/java/com/final_project/auth_service/controller/AuthenticationController.java

<!-- File: src/main/java/com/final_project/auth_service/controller/AuthenticationController.java -->

```java
package com.final_project.auth_service.controller;

import com.final_project.auth_service.config.RemoteIP;
import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for authentication endpoints.
 *
 * Provides endpoints for:
 * - User login with email/username and password
 * - User signup/registration
 * - Google OAuth2 "Sign in with Google"
 * - Token management (refresh, verify)
 * - Password management (change, reset, forgot)
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoints for authentication, login, signup, and password management")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Login user with email/username and password.
     *
     * @param request Login request with credentials
     * @return Auth response with tokens
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login with email/username and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Account disabled or locked"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request for: {}", request.getUsernameOrEmail());
        AuthResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Register new user (signup).
     *
     * @param request Signup request
     * @return Auth response with tokens
     */
    @PostMapping("/signup")
    @Operation(summary = "Register new user", description = "Create a new user account with email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Signup successful", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Email or username already exists")
    })
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        log.info("Signup request for: {}", request.getEmail());
        AuthResponse response = authenticationService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login with Google OAuth2 (Sign in with Google).
     *
     * Frontend flow:
     * 1. User clicks "Sign in with Google"
     * 2. Google authenticates user and returns ID token
     * 3. Frontend sends ID token to this endpoint
     * 4. Server verifies token and returns access token
     *
     * @param request Google OAuth2 request with ID token
     * @return Auth response with tokens
     */
    @PostMapping("/google")
    @Operation(summary = "Sign in with Google", description = "Authenticate using Google OAuth2 ID token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Google authentication successful", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or missing ID token"),
            @ApiResponse(responseCode = "401", description = "Google token verification failed")
    })
    public ResponseEntity<AuthResponse> googleOAuth2Login(@Valid @RequestBody GoogleOAuth2Request request) {
        log.info("Google OAuth2 login request");
        AuthResponse response = authenticationService.googleOAuth2Login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Refresh access token.
     *
     * @param request Refresh token request
     * @return New auth response with refreshed access token
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Get a new access token using refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Token refresh request");
        AuthResponse response = authenticationService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Change user password (requires authentication).
     *
     * @param userId User ID
     * @param request Change password request
     * @return Success response
     */
    @PostMapping("/change-password/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "Bearer Token")
    @Operation(summary = "Change password", description = "Change user password (requires current password)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable String userId,
            @Valid @RequestBody ChangePasswordRequest request,
            @RemoteIP String ip
            ) {
        log.info("Change password request for user: {}", userId);

        request.setIpAddress(ip);
        authenticationService.changePassword(userId, request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password changed successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Request password reset (forgot password).
     *
     * @param request Forgot password request
     * @return Success response
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Request password reset email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset email sent"),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Forgot password request for: {}", request.getEmail());
        authenticationService.forgotPassword(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset email sent. Check your email for instructions");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Reset password with token from email.
     *
     * @param request Reset password request with token
     * @return Success response
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password using token from email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or mismatched passwords"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired reset token")
    })
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Password reset request");
        authenticationService.resetPassword(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Verify email with token.
     *
     * @param request Email verification request with token
     * @return Success response
     */
    @PostMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verify email using token from verification email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired verification token")
    })
    public ResponseEntity<Map<String, String>> verifyEmail(@Valid @RequestBody EmailVerificationRequest request) {
        log.info("Email verification request");
        // TODO: Implement email verification logic

        Map<String, String> response = new HashMap<>();
        response.put("message", "Email verified successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Resend verification email.
     *
     * @param request Resend verification email request
     * @return Success response
     */
    @PostMapping("/resend-verification-email")
    @Operation(summary = "Resend verification email", description = "Resend email verification token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verification email sent"),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    public ResponseEntity<Map<String, String>> resendVerificationEmail(
            @Valid @RequestBody ResendVerificationEmailRequest request) {
        log.info("Resend verification email request for: {}", request.getEmail());
        // TODO: Implement resend verification logic

        Map<String, String> response = new HashMap<>();
        response.put("message", "Verification email sent. Check your email");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Logout user.
     *
     * @return Success response
     */
    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "Bearer Token")
    @Operation(summary = "Logout user", description = "Logout and invalidate token on client side")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Map<String, String>> logout() {
        log.info("Logout request");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful. Please clear the token on client side");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Check login status and get current user info.
     *
     * @return Current user information
     */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "Bearer Token")
    @Operation(summary = "Get current user", description = "Get information about currently authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        log.info("Get current user request");
        // This is handled by SecurityContext
        // Return user info from principal

        Map<String, Object> response = new HashMap<>();
        response.put("status", "authenticated");

        return ResponseEntity.ok(response);
    }
}
```

## src/main/java/com/final_project/auth_service/controller/PermissionController.java

<!-- File: src/main/java/com/final_project/auth_service/controller/PermissionController.java -->

```java
package com.final_project.auth_service.controller;


import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.dto.CreatePermissionRequest;
import com.final_project.auth_service.dto.UpdatePermissionRequest;
import com.final_project.auth_service.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for permission management endpoints.
 *
 * Provides complete CRUD operations for:
 * - Creating fine-grained permissions
 * - Reading/retrieving permissions
 * - Updating permission details
 * - Deleting permissions
 * - Filtering and querying permissions
 */
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Permission Management", description = "Complete permission management endpoints with CRUD operations")
@SecurityRequirement(name = "Bearer Token")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * Create a new permission.
     *
     * @param request Permission creation request
     * @return Created permission with details
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new permission", description = "Creates a new fine-grained permission with resource and action")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Permission created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Permission with name or key already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Admin role required")
    })
    public ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        log.info("Creating new permission: {}", request.getName());
        PermissionDTO createdPermission = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    /**
     * Get permission by ID.
     *
     * @param permissionId Permission ID
     * @return Permission details
     */
    @GetMapping("/{permissionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get permission by ID", description = "Retrieves a specific permission by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found and returned"),
            @ApiResponse(responseCode = "404", description = "Permission not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<PermissionDTO> getPermissionById(
            @PathVariable
            @Parameter(description = "Permission ID", example = "507f1f77bcf86cd799439012")
            String permissionId) {
        log.info("Fetching permission: {}", permissionId);
        PermissionDTO permission = permissionService.getPermissionById(permissionId);
        return ResponseEntity.ok(permission);
    }

    /**
     * Get permission by name.
     *
     * @param permissionName Permission name
     * @return Permission details
     */
    @GetMapping("/name/{permissionName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get permission by name", description = "Retrieves a permission by its unique name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<PermissionDTO> getPermissionByName(
            @PathVariable
            @Parameter(description = "Permission name", example = "USER_CREATE")
            String permissionName) {
        log.info("Fetching permission by name: {}", permissionName);
        PermissionDTO permission = permissionService.getPermissionByName(permissionName);
        return ResponseEntity.ok(permission);
    }

    /**
     * Get all permissions (with optional filtering).
     *
     * @param activeOnly Filter to show only active permissions
     * @return List of permissions
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get all permissions", description = "Retrieves all permissions with optional filtering")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getAllPermissions(
            @RequestParam(defaultValue = "false")
            @Parameter(description = "Show only active permissions")
            boolean activeOnly) {
        log.info("Fetching all permissions, activeOnly={}", activeOnly);
        List<PermissionDTO> permissions = activeOnly ?
                permissionService.getAllActivePermissions() :
                permissionService.getAllPermissions();

        Map<String, Object> response = new HashMap<>();
        response.put("total", permissions.size());
        response.put("permissions", permissions);
        response.put("activeOnly", activeOnly);

        return ResponseEntity.ok(response);
    }

    /**
     * Get permissions by resource.
     *
     * @param resource Resource name
     * @return List of permissions for the resource
     */
    @GetMapping("/resource/{resource}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get permissions by resource", description = "Retrieves all permissions for a specific resource")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permissions retrieved")
    })
    public ResponseEntity<Map<String, Object>> getPermissionsByResource(
            @PathVariable
            @Parameter(description = "Resource name", example = "USER")
            String resource) {
        log.info("Fetching permissions for resource: {}", resource);
        List<PermissionDTO> permissions = permissionService.getPermissionsByResource(resource);

        Map<String, Object> response = new HashMap<>();
        response.put("resource", resource);
        response.put("total", permissions.size());
        response.put("permissions", permissions);

        return ResponseEntity.ok(response);
    }

    /**
     * Get permission by resource and action.
     *
     * @param resource Resource name
     * @param action Action name
     * @return Permission details
     */
    @GetMapping("/resource/{resource}/action/{action}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get permission by resource and action", description = "Retrieves permission by resource:action combination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<PermissionDTO> getPermissionByResourceAndAction(
            @PathVariable
            @Parameter(description = "Resource name", example = "USER")
            String resource,
            @PathVariable
            @Parameter(description = "Action name", example = "READ")
            String action) {
        log.info("Fetching permission for resource={}, action={}", resource, action);
        PermissionDTO permission = permissionService.getPermissionByResourceAndAction(resource, action);
        return ResponseEntity.ok(permission);
    }

    /**
     * Update permission details.
     *
     * @param permissionId Permission ID
     * @param request Update request
     * @return Updated permission
     */
    @PutMapping("/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update permission", description = "Updates permission information (name, description, status)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Permission not found"),
            @ApiResponse(responseCode = "409", description = "Permission name already exists")
    })
    public ResponseEntity<PermissionDTO> updatePermission(
            @PathVariable String permissionId,
            @Valid @RequestBody UpdatePermissionRequest request) {
        log.info("Updating permission: {}", permissionId);
        PermissionDTO updatedPermission = permissionService.updatePermission(permissionId, request);
        return ResponseEntity.ok(updatedPermission);
    }

    /**
     * Delete a permission.
     *
     * @param permissionId Permission ID
     * @return Success response
     */
    @DeleteMapping("/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete permission", description = "Deletes a permission (system permissions cannot be deleted)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Permission not found"),
            @ApiResponse(responseCode = "403", description = "Cannot delete system permission")
    })
    public ResponseEntity<Void> deletePermission(@PathVariable String permissionId) {
        log.info("Deleting permission: {}", permissionId);
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Assign permission to role.
     *
     * @param permissionId Permission ID
     * @param roleId Role ID
     * @return Success response
     */
    @PostMapping("/{permissionId}/assign-to-role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign permission to role", description = "Assigns a permission to a role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Permission or role not found"),
            @ApiResponse(responseCode = "409", description = "Permission already assigned to role")
    })
    public ResponseEntity<Map<String, String>> assignPermissionToRole(
            @PathVariable String permissionId,
            @PathVariable String roleId) {
        log.info("Assigning permission {} to role {}", permissionId, roleId);
        permissionService.assignPermissionToRole(permissionId, roleId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Permission assigned to role successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Remove permission from role.
     *
     * @param permissionId Permission ID
     * @param roleId Role ID
     * @return Success response
     */
    @DeleteMapping("/{permissionId}/remove-from-role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove permission from role", description = "Removes a permission from a role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission removed successfully"),
            @ApiResponse(responseCode = "404", description = "Permission or role not found")
    })
    public ResponseEntity<Map<String, String>> removePermissionFromRole(
            @PathVariable String permissionId,
            @PathVariable String roleId) {
        log.info("Removing permission {} from role {}", permissionId, roleId);
        permissionService.removePermissionFromRole(permissionId, roleId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Permission removed from role successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Get list of available resources.
     *
     * @return List of unique resources
     */
    @GetMapping("/resources/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get available resources", description = "Retrieves list of all unique resources with permissions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resources retrieved")
    })
    public ResponseEntity<Map<String, Object>> getAvailableResources() {
        log.info("Fetching available resources");
        List<String> resources = permissionService.getAvailableResources();

        Map<String, Object> response = new HashMap<>();
        response.put("total", resources.size());
        response.put("resources", resources);

        return ResponseEntity.ok(response);
    }

    /**
     * Get permission statistics.
     *
     * @return Statistics about permissions
     */
    @GetMapping("/stats/count")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get permission statistics", description = "Returns count of permissions by resource and status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics retrieved")
    })
    public ResponseEntity<Map<String, Object>> getPermissionStats() {
        log.info("Fetching permission statistics");
        Map<String, Object> stats = permissionService.getPermissionStatistics();
        return ResponseEntity.ok(stats);
    }
}
```

## src/main/java/com/final_project/auth_service/controller/RoleController.java

<!-- File: src/main/java/com/final_project/auth_service/controller/RoleController.java -->

```java
package com.final_project.auth_service.controller;


import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for role management endpoints.
 *
 * Provides complete CRUD operations for:
 * - Creating roles with permissions
 * - Reading/retrieving roles
 * - Updating role information
 * - Deleting roles
 * - Managing permissions within roles
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Role Management", description = "Complete role management endpoints with CRUD operations")
@SecurityRequirement(name = "Bearer Token")
public class RoleController {

    private final RoleService roleService;

    /**
     * Create a new role.
     *
     * @param request Role creation request
     * @return Created role with details
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new role", description = "Creates a new role with name, description, and optional permissions")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Role with name or key already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Admin role required")
    })
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody CreateRoleRequest request) {
        log.info("Creating new role: {}", request.getName());
        RoleDTO createdRole = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    /**
     * Get role by ID.
     *
     * @param roleId Role ID
     * @return Role details
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get role by ID", description = "Retrieves a specific role by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found and returned"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<RoleDTO> getRoleById(
            @PathVariable
            @Parameter(description = "Role ID", example = "507f1f77bcf86cd799439011")
            String roleId) {
        log.info("Fetching role: {}", roleId);
        RoleDTO role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(role);
    }

    /**
     * Get role by name.
     *
     * @param roleName Role name
     * @return Role details
     */
    @GetMapping("/name/{roleName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Get role by name", description = "Retrieves a role by its unique name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleDTO> getRoleByName(
            @PathVariable
            @Parameter(description = "Role name", example = "ADMIN")
            String roleName) {
        log.info("Fetching role by name: {}", roleName);
        RoleDTO role = roleService.getRoleByName(roleName);
        return ResponseEntity.ok(role);
    }

    /**
     * Get all roles (with optional filtering).
     *
     * @param activeOnly Filter to show only active roles
     * @return List of roles
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER')")
    @Operation(summary = "Get all roles", description = "Retrieves all roles with optional filtering")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getAllRoles(
            @RequestParam(defaultValue = "false")
            @Parameter(description = "Show only active roles")
            boolean activeOnly) {
        log.info("Fetching all roles, activeOnly={}", activeOnly);
        List<RoleDTO> roles = activeOnly ? roleService.getAllActiveRoles() : roleService.getAllRoles();

        Map<String, Object> response = new HashMap<>();
        response.put("total", roles.size());
        response.put("roles", roles);
        response.put("activeOnly", activeOnly);

        return ResponseEntity.ok(response);
    }

    /**
     * Update role details.
     *
     * @param roleId Role ID
     * @param request Update request with new details
     * @return Updated role
     */
    @PutMapping("/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update role", description = "Updates role information (name, description, status)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "409", description = "Role name already exists")
    })
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable String roleId,
            @Valid @RequestBody UpdateRoleRequest request) {
        log.info("Updating role: {}", roleId);
        RoleDTO updatedRole = roleService.updateRole(roleId, request);
        return ResponseEntity.ok(updatedRole);
    }

    /**
     * Delete a role.
     *
     * @param roleId Role ID
     * @return Success response
     */
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete role", description = "Deletes a role (system roles cannot be deleted)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "403", description = "Cannot delete system role")
    })
    public ResponseEntity<Void> deleteRole(@PathVariable String roleId) {
        log.info("Deleting role: {}", roleId);
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add permission to role.
     *
     * @param roleId Role ID
     * @param permissionId Permission ID
     * @return Updated role with new permission
     */
    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add permission to role", description = "Assigns a permission to a role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission added successfully"),
            @ApiResponse(responseCode = "404", description = "Role or permission not found"),
            @ApiResponse(responseCode = "409", description = "Permission already assigned to role")
    })
    public ResponseEntity<RoleDTO> addPermissionToRole(
            @PathVariable
            @Parameter(description = "Role ID")
            String roleId,
            @PathVariable
            @Parameter(description = "Permission ID")
            String permissionId) {
        log.info("Adding permission {} to role {}", permissionId, roleId);
        RoleDTO updatedRole = roleService.addPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok(updatedRole);
    }

    /**
     * Remove permission from role.
     *
     * @param roleId Role ID
     * @param permissionId Permission ID
     * @return Updated role without the permission
     */
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove permission from role", description = "Removes a permission from a role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission removed successfully"),
            @ApiResponse(responseCode = "404", description = "Role or permission not found")
    })
    public ResponseEntity<RoleDTO> removePermissionFromRole(
            @PathVariable String roleId,
            @PathVariable String permissionId) {
        log.info("Removing permission {} from role {}", permissionId, roleId);
        RoleDTO updatedRole = roleService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.ok(updatedRole);
    }

    /**
     * Assign role to user.
     *
     * @param roleId Role ID
     * @param userId User ID
     * @return Success response
     */
    @PostMapping("/{roleId}/assign-to-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign role to user", description = "Assigns a role to a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Role or user not found"),
            @ApiResponse(responseCode = "409", description = "User already has this role")
    })
    public ResponseEntity<Map<String, String>> assignRoleToUser(
            @PathVariable String roleId,
            @PathVariable String userId) {
        log.info("Assigning role {} to user {}", roleId, userId);
        roleService.assignRoleToUser(roleId, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Role assigned successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Remove role from user.
     *
     * @param roleId Role ID
     * @param userId User ID
     * @return Success response
     */
    @DeleteMapping("/{roleId}/remove-from-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove role from user", description = "Removes a role from a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role removed successfully"),
            @ApiResponse(responseCode = "404", description = "Role or user not found")
    })
    public ResponseEntity<Map<String, String>> removeRoleFromUser(
            @PathVariable String roleId,
            @PathVariable String userId) {
        log.info("Removing role {} from user {}", roleId, userId);
        roleService.removeRoleFromUser(roleId, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Role removed successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    /**
     * Get role count statistics.
     *
     * @return Statistics about roles
     */
    @GetMapping("/stats/count")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get role statistics", description = "Returns count of active and inactive roles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics retrieved")
    })
    public ResponseEntity<Map<String, Object>> getRoleStats() {
        log.info("Fetching role statistics");
        Map<String, Object> stats = roleService.getRoleStatistics();
        return ResponseEntity.ok(stats);
    }
}
```

## src/main/java/com/final_project/auth_service/controller/UserController.java

<!-- File: src/main/java/com/final_project/auth_service/controller/UserController.java -->

```java
package com.final_project.auth_service.controller;


import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * REST controller for user management endpoints.
 *
 * Provides endpoints for:
 * - User CRUD operations
 * - User search
 * - User status management
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "Endpoints for user management operations")
@SecurityRequirement(name = "Bearer Token")
public class UserController {

    private final UserService userService;

    /**
     * Create a new user.
     *
     * @param request User creation request
     * @return Created user DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        log.info("Creating new user: {}", request.getUsername());
        UserDTO user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return User DTO
     */
    @GetMapping("/author/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW', 'USER')")
    @Operation(summary = "Get user by ID", description = "Retrieves user information by unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AuthorResponse> getUserAsAuthor(
            @PathVariable String id) {
        AuthorResponse user = userService.getUserAsAuthor(id);
        return ResponseEntity.ok(user);
    }
    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return User DTO
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW')")
    @Operation(summary = "Get user by ID", description = "Retrieves user information by unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable String id) {
        log.info("Fetching user: {}", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Get user by username.
     *
     * @param username Username
     * @return User DTO
     */
    @GetMapping("/by-username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW')")
    @Operation(summary = "Get user by username", description = "Retrieves user information by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserByUsername(
            @PathVariable String username) {
        log.info("Fetching user by username: {}", username);
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    /**
     * Get user by email.
     *
     * @param email Email address
     * @return User DTO
     */
    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW')")
    @Operation(summary = "Get user by email", description = "Retrieves user information by email address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Update user information.
     *
     * @param id User ID
     * @param request Update request
     * @return Updated user DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN') or @userService.getUserById(#id).id == authentication.principal.name")
    @Operation(summary = "Update user", description = "Updates user information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("Updating user: {}", id);
        UserDTO user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    /**
     * Delete user.
     *
     * @param id User ID
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Deletes a user (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @PathVariable String id) {
        log.info("Deleting user: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search users.
     *
     * @param search Search term
     * @return Page of user DTOs
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW')")
    @Operation(summary = "Search users", description = "Searches users by email, username, or name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed")
    })
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false, defaultValue = "") String search) {
        List<UserDTO> users = userService.searchUsers(search);
        return ResponseEntity.ok(users);
    }

    /**
     * Get all active users.
     *
     * @param page Page number (default 0)
     * @param size Page size (default 20)
     * @return Page of active user DTOs
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN', 'USER_VIEW')")
    @Operation(summary = "Get all active users", description = "Retrieves all active users with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Fetching all users: page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDTO> users = userService.getAllActiveUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Suspend user account.
     *
     * @param id User ID
     */
    @PostMapping("/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Suspend user", description = "Suspends a user account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User suspended"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> suspendUser(
            @PathVariable String id) {
        log.info("Suspending user: {}", id);
        userService.suspendUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activate user account.
     *
     * @param id User ID
     */
    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate user", description = "Activates a user account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User activated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> activateUser(
            @PathVariable String id) {
        log.info("Activating user: {}", id);
        userService.activateUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lock user account.
     *
     * @param id User ID
     * @param durationMinutes Lock duration in minutes
     */
    @PostMapping("/{id}/lock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lock user account", description = "Locks a user account for specified duration")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User locked"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> lockUser(
            @PathVariable String id,
            @RequestParam(defaultValue = "30") int durationMinutes) {
        log.info("Locking user: {} for {} minutes", id, durationMinutes);
        userService.lockUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verify user email.
     *
     * @param id User ID
     */
    @PostMapping("/{id}/verify-email")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "Verify user email", description = "Marks user email as verified")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Email verified"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> verifyEmail(
            @PathVariable String id) {
        log.info("Verifying email for user: {}", id);
        userService.verifyUserEmail(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stats/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER_ADMIN')")
    @Operation(summary = "stats of user", description = "Marks how many users ")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "USER founds"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Map<String, Object>> getUserStats() {
        Map<String, Object> stats = userService.getUserStatistics();
        return ResponseEntity.ok(stats);
    }
}
```

## src/main/java/com/final_project/auth_service/dto/AssignPermissionsToRoleRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/AssignPermissionsToRoleRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to assign multiple permissions to a role")
public class AssignPermissionsToRoleRequest {

    @NotEmpty(message = "At least one permission ID is required")
    @JsonProperty("permission_ids")
    @Schema(description = "List of permission IDs to assign")
    private Set<String> permissionIds;
}
```

## src/main/java/com/final_project/auth_service/dto/AssignRolesToUserRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/AssignRolesToUserRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to assign multiple roles to a user")
public class AssignRolesToUserRequest {

    @NotEmpty(message = "At least one role ID is required")
    @JsonProperty("role_ids")
    @Schema(description = "List of role IDs to assign")
    private Set<String> roleIds;
}
```

## src/main/java/com/final_project/auth_service/dto/AuthorResponse.java

<!-- File: src/main/java/com/final_project/auth_service/dto/AuthorResponse.java -->

```java
package com.final_project.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorResponse {
    private String id;
    private String userName;
    private String email;
    private String profile;
    private String entityId;
    private String userType;
}
```

## src/main/java/com/final_project/auth_service/dto/AuthResponse.java

<!-- File: src/main/java/com/final_project/auth_service/dto/AuthResponse.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentication response with tokens")
public class AuthResponse {

    @JsonProperty("access_token")
    @Schema(description = "JWT access token for API calls")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Schema(description = "JWT refresh token for token renewal")
    private String refreshToken;

    @JsonProperty("token_type")
    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";

    @JsonProperty("expires_in")
    @Schema(description = "Token expiration time in seconds", example = "3600")
    private Long expiresIn;

    @JsonProperty("user")
    @Schema(description = "User information")
    private UserDTO user;

    @JsonProperty("message")
    @Schema(description = "Success message")
    private String message;
}
```

## src/main/java/com/final_project/auth_service/dto/BulkCreatePermissionsRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/BulkCreatePermissionsRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create multiple permissions at once")
public class BulkCreatePermissionsRequest {

    @NotEmpty(message = "At least one permission is required")
    @JsonProperty("permissions")
    @Schema(description = "List of permissions to create")
    private java.util.List<CreatePermissionRequest> permissions;
}
```

## src/main/java/com/final_project/auth_service/dto/BulkCreateRolesRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/BulkCreateRolesRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create multiple roles at once")
public class BulkCreateRolesRequest {

    @NotEmpty(message = "At least one role is required")
    @JsonProperty("roles")
    @Schema(description = "List of roles to create")
    private java.util.List<CreateRoleRequest> roles;
}
```

## src/main/java/com/final_project/auth_service/dto/ChangePasswordRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/ChangePasswordRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to change user password")
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    @JsonProperty("current_password")
    @Schema(description = "Current password", example = "OldPassword123!")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain uppercase, lowercase, number, and special character"
    )
    @JsonProperty("new_password")
    @Schema(description = "New password", example = "NewPassword123!")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    @JsonProperty("confirm_password")
    @Schema(description = "Confirm new password", example = "NewPassword123!")
    private String confirmPassword;

    private String ipAddress;
}
```

## src/main/java/com/final_project/auth_service/dto/CreatePermissionRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/CreatePermissionRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create a new permission")
public class CreatePermissionRequest {

    @NotBlank(message = "Permission name is required")
    @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters")
    @JsonProperty("name")
    @Schema(description = "Permission name", example = "USER_READ")
    private String name;

    @JsonProperty("description")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Permission description", example = "Permission to read user information")
    private String description;

    @NotBlank(message = "Resource is required")
    @Size(min = 2, max = 50, message = "Resource must be between 2 and 50 characters")
    @JsonProperty("resource")
    @Schema(description = "Resource name", example = "USER")
    private String resource;

    @NotBlank(message = "Action is required")
    @Size(min = 2, max = 50, message = "Action must be between 2 and 50 characters")
    @JsonProperty("action")
    @Schema(description = "Action name", example = "READ")
    private String action;

    @JsonProperty("is_system_permission")
    @Schema(description = "Whether this is a system permission (cannot be deleted)", example = "false")
    private Boolean isSystemPermission = false;
}
```

## src/main/java/com/final_project/auth_service/dto/CreateRoleRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/CreateRoleRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create a new role")
public class CreateRoleRequest {

    @NotBlank(message = "Role name is required")
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
    @JsonProperty("name")
    @Schema(description = "Role name", example = "ADMIN")
    private String name;

    @JsonProperty("description")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Role description", example = "Administrator with full access")
    private String description;

    @NotBlank(message = "Role key is required")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role key must contain only uppercase letters and underscores")
    @Size(min = 3, max = 50, message = "Role key must be between 3 and 50 characters")
    @JsonProperty("role_key")
    @Schema(description = "Role key for Keycloak sync", example = "ADMIN_ROLE")
    private String roleKey;

    @JsonProperty("permission_ids")
    @Schema(description = "Initial permission IDs to assign to this role")
    private Set<String> permissionIds;

    @JsonProperty("is_system_role")
    @Schema(description = "Whether this is a system role (cannot be deleted)", example = "false")
    private Boolean isSystemRole = false;
}
```

## src/main/java/com/final_project/auth_service/dto/CreateUserRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/CreateUserRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.auth_service.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create a new user")
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @JsonProperty("username")
    @Schema(description = "Username", example = "john.doe")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must not exceed 50 characters")
    @JsonProperty("first_name")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must not exceed 50 characters")
    @JsonProperty("last_name")
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @JsonProperty("phone_number")
    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;

    @JsonProperty("roles")
    @Schema(description = "Initial user roles")
    private Set<String> roles;

    @JsonProperty("user_type")
    @Schema(description = "user blongs to for instance teacher, student ... ")
    private User.UserType userType;

    @JsonProperty("entity_id")
    @Schema(description = "Entity that belongs to this user", example = "teacher account with id: 8902323")
    private String entityId;

    @JsonProperty("profile")
    @Schema(description = "http:u023i23")
    private String profile;

    @JsonProperty("password")
    @Schema( description =  "password for user ")
    private String password;
}
```

## src/main/java/com/final_project/auth_service/dto/EmailVerificationRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/EmailVerificationRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to verify email with token")
public class EmailVerificationRequest {

    @NotBlank(message = "Verification token is required")
    @JsonProperty("verification_token")
    @Schema(description = "Email verification token from email link")
    private String verificationToken;
}
```

## src/main/java/com/final_project/auth_service/dto/ForgotPasswordRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/ForgotPasswordRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to initiate password reset")
public class ForgotPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;
}
```

## src/main/java/com/final_project/auth_service/dto/GoogleOAuth2Request.java

<!-- File: src/main/java/com/final_project/auth_service/dto/GoogleOAuth2Request.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to login with Google OAuth2")
public class GoogleOAuth2Request {

    @NotBlank(message = "ID token is required")
    @JsonProperty("id_token")
    @Schema(description = "Google ID token from frontend", example = "eyJhbGciOiJSUzI1NiIs...")
    private String idToken;

    @JsonProperty("access_token")
    @Schema(description = "Google access token (optional)", example = "ya29.a0AfH6SMBx...")
    private String accessToken;

    @JsonProperty("device_id")
    @Schema(description = "Device identifier for tracking", example = "device-123")
    private String deviceId;
}
```

## src/main/java/com/final_project/auth_service/dto/LoginHistoryDTO.java

<!-- File: src/main/java/com/final_project/auth_service/dto/LoginHistoryDTO.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * LoginHistoryDTO - DTO for login history.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User login history entry")
public class LoginHistoryDTO {

    @JsonProperty("id")
    @Schema(description = "Login history ID")
    private String id;

    @JsonProperty("user_id")
    @Schema(description = "User ID")
    private String userId;

    @JsonProperty("timestamp")
    @Schema(description = "Login timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("ip_address")
    @Schema(description = "IP address of login")
    private String ipAddress;

    @JsonProperty("user_agent")
    @Schema(description = "Browser/device user agent")
    private String userAgent;

    @JsonProperty("device_type")
    @Schema(description = "Device type (mobile, desktop, tablet)")
    private String deviceType;

    @JsonProperty("location")
    @Schema(description = "Geographic location (if available)")
    private String location;

    @JsonProperty("success")
    @Schema(description = "Whether login was successful")
    private Boolean success;

    @JsonProperty("failure_reason")
    @Schema(description = "Reason for login failure (if unsuccessful)")
    private String failureReason;
}
```

## src/main/java/com/final_project/auth_service/dto/LoginRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/LoginRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequest - Request DTO for user login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to login with credentials")
public class LoginRequest {

    @NotBlank(message = "Email or username is required")
    @JsonProperty("username_or_email")
    @Schema(description = "Email address or username", example = "john.doe@example.com")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    @Schema(description = "User password", example = "SecurePassword123!")
    private String password;

    @JsonProperty("remember_me")
    @Schema(description = "Remember this device", example = "false")
    private Boolean rememberMe = false;
}
```

## src/main/java/com/final_project/auth_service/dto/PermissionDTO.java

<!-- File: src/main/java/com/final_project/auth_service/dto/PermissionDTO.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * PermissionDTO - Response DTO for permission information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Permission information response")
public class PermissionDTO {

    @JsonProperty("id")
    @Schema(description = "Permission unique identifier")
    private String id;


    @JsonProperty("name")
    @Schema(description = "Permission name")
    private String name;


    private String permissionKey;
    private boolean  isSystemPermission;

    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("description")
    @Schema(description = "Permission description")
    private String description;

    @JsonProperty("resource")
    @Schema(description = "Resource name", example = "USER")
    private String resource;

    @JsonProperty("action")
    @Schema(description = "Action name", example = "READ")
    private String action;
}
```

## src/main/java/com/final_project/auth_service/dto/RefreshTokenRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/RefreshTokenRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to refresh access token")
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    @JsonProperty("refresh_token")
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIs...")
    private String refreshToken;
}
```

## src/main/java/com/final_project/auth_service/dto/ResendVerificationEmailRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/ResendVerificationEmailRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResendVerificationEmailRequest - Request DTO to resend verification email.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to resend verification email")
public class ResendVerificationEmailRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;
}
```

## src/main/java/com/final_project/auth_service/dto/ResetPasswordRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/ResetPasswordRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to reset password with token")
public class ResetPasswordRequest {

    @NotBlank(message = "Reset token is required")
    @JsonProperty("reset_token")
    @Schema(description = "Password reset token from email")
    private String resetToken;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain uppercase, lowercase, number, and special character"
    )
    @JsonProperty("new_password")
    @Schema(description = "New password", example = "NewPassword123!")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    @JsonProperty("confirm_password")
    @Schema(description = "Confirm new password", example = "NewPassword123!")
    private String confirmPassword;
}
```

## src/main/java/com/final_project/auth_service/dto/RoleDTO.java

<!-- File: src/main/java/com/final_project/auth_service/dto/RoleDTO.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.auth_service.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Role information response")
public class RoleDTO {

    @JsonProperty("id")
    @Schema(description = "Role unique identifier")
    private String id;

    @JsonProperty("name")
    @Schema(description = "Role name", example = "ADMIN")
    private String name;

    @JsonProperty("description")
    @Schema(description = "Role description")
    private String description;

    private String roleKey;
    private boolean isSystemRole;
    private boolean isActive;
    private Set<String> permissionIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("permissions")
    @Schema(description = "Permissions assigned to this role")
    private Set<PermissionDTO> permissions;
}
```

## src/main/java/com/final_project/auth_service/dto/SignupRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/SignupRequest.java -->

```java
package com.final_project.auth_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SignupRequest - Request DTO for user registration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to register a new user")
public class SignupRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain alphanumeric characters, dots, and underscores")
    @JsonProperty("username")
    @Schema(description = "Username", example = "john.doe")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain uppercase, lowercase, number, and special character"
    )
    @JsonProperty("password")
    @Schema(description = "Password (min 8 chars, uppercase, lowercase, number, special char)", example = "SecurePass123!")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must not exceed 50 characters")
    @JsonProperty("first_name")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must not exceed 50 characters")
    @JsonProperty("last_name")
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @JsonProperty("phone_number")
    @Schema(description = "Phone number (optional)", example = "+1234567890")
    private String phoneNumber;

    @JsonProperty("terms_agreed")
    @Schema(description = "Agreement to terms and conditions", example = "true")
    private Boolean termsAgreed = false;

    @JsonProperty("privacy_agreed")
    @Schema(description = "Agreement to privacy policy", example = "true")
    private Boolean privacyAgreed = false;
}
```

## src/main/java/com/final_project/auth_service/dto/TokenValidationResponse.java

<!-- File: src/main/java/com/final_project/auth_service/dto/TokenValidationResponse.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TokenValidationResponse - Response DTO for token validation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Token validation response")
public class TokenValidationResponse {

    @JsonProperty("valid")
    @Schema(description = "Whether token is valid", example = "true")
    private Boolean valid;

    @JsonProperty("user_id")
    @Schema(description = "User ID from token")
    private String userId;

    @JsonProperty("username")
    @Schema(description = "Username from token")
    private String username;

    @JsonProperty("email")
    @Schema(description = "Email from token")
    private String email;

    @JsonProperty("roles")
    @Schema(description = "User roles from token")
    private java.util.List<String> roles;

    @JsonProperty("expires_at")
    @Schema(description = "Token expiration time")
    private LocalDateTime expiresAt;

    @JsonProperty("message")
    @Schema(description = "Message (error if not valid)")
    private String message;
}
```

## src/main/java/com/final_project/auth_service/dto/UpdatePermissionRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/UpdatePermissionRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to update an existing permission")
public class UpdatePermissionRequest {

    @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters")
    @JsonProperty("name")
    @Schema(description = "Updated permission name")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @JsonProperty("description")
    @Schema(description = "Updated description")
    private String description;

    @JsonProperty("is_active")
    @Schema(description = "Whether permission is active", example = "true")
    private Boolean isActive;
}
```

## src/main/java/com/final_project/auth_service/dto/UpdateRoleRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/UpdateRoleRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to update an existing role")
public class UpdateRoleRequest {

    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
    @JsonProperty("name")
    @Schema(description = "Updated role name", example = "ADMIN")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @JsonProperty("description")
    @Schema(description = "Updated description")
    private String description;

    @JsonProperty("is_active")
    @Schema(description = "Whether role is active", example = "true")
    private Boolean isActive;
}
```

## src/main/java/com/final_project/auth_service/dto/UpdateUserRequest.java

<!-- File: src/main/java/com/final_project/auth_service/dto/UpdateUserRequest.java -->

```java
package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to update user information")
public class UpdateUserRequest {

    @JsonProperty("first_name")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @JsonProperty("last_name")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @JsonProperty("phone_number")
    @Schema(description = "Phone number", example = "+1234567890")
    private String phoneNumber;

    @JsonProperty("status")
    @Schema(description = "User status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"})
    private String status;

    private Boolean emailVerified;
    private Boolean twoFactorEnabled;

    @JsonProperty("roles")
    @Schema(description = "User roles")
    private Set<String> roles;
}
```

## src/main/java/com/final_project/auth_service/dto/UserDTO.java

<!-- File: src/main/java/com/final_project/auth_service/dto/UserDTO.java -->

```java
package com.final_project.auth_service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.auth_service.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Objects for User API endpoints.
 */

/**
 * UserDTO - Response DTO for user information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User information response")
public class UserDTO {

    @JsonProperty("id")
    @Schema(description = "User unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @JsonProperty("user_name")
    @Schema(description = "User username", example = "john.doe")
    private String username;

    @JsonProperty("email")
    @Schema(description = "User email address", example = "john.doe@example.com")
    private String email;

    @JsonProperty("first_name")
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @JsonProperty("phone_number")
    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;

    @JsonProperty("status")
    @Schema(description = "User account status", example = "ACTIVE")
    private String status;

    @JsonProperty("email_verified")
    @Schema(description = "Whether email is verified", example = "true")
    private Boolean emailVerified;

    @JsonProperty("two_factor_enabled")
    @Schema(description = "Whether two-factor authentication is enabled", example = "false")
    private Boolean twoFactorEnabled;

    @JsonProperty("last_login")
    @Schema(description = "Last login timestamp")
    private LocalDateTime lastLogin;

    @JsonProperty("roles")
    @Schema(description = "User roles")
    private Set<RoleDTO> roles;

    @JsonProperty("created_at")
    @Schema(description = "Account creation timestamp")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @JsonProperty("entity_id")
    @Schema(description = "Entity that belongs to this user", example = "teacher account with id: 8902323")
    private String entityId;

    @JsonProperty("profile")
    @Schema(description = "http:u023i23")
    private String profile;

    @JsonProperty("user_type")
    @Schema(description = "user type like teacher.. student")
    private User.UserType userType;
}
```

## src/main/java/com/final_project/auth_service/event/PasswordChangedEvent.java

<!-- File: src/main/java/com/final_project/auth_service/event/PasswordChangedEvent.java -->

```java
package com.final_project.auth_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordChangedEvent {
    private String eventId;
    private String userId;
    private String email;
    private String firstName;
    private String changeType;          // CHANGED, RESET
    private String ipAddress;           // for security notice
    private String userAgent;
    private LocalDateTime occurredAt;
}
```

## src/main/java/com/final_project/auth_service/event/UserRegisteredEvent.java

<!-- File: src/main/java/com/final_project/auth_service/event/UserRegisteredEvent.java -->

```java
package com.final_project.auth_service.event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisteredEvent {
    private String eventId;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String verificationToken;
    private String registrationSource;      // WEB, MOBILE, API
    private LocalDateTime occurredAt;
}
```

## src/main/java/com/final_project/auth_service/exception/DuplicateUserException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/DuplicateUserException.java -->

```java
package com.final_project.auth_service.exception;

/**
 * Exception thrown for duplicate user creation.
 */
public class DuplicateUserException extends UserServiceException {
    public DuplicateUserException(String field, String value) {
        super("A user already exists with " + field + ": " + value);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/ForbiddenException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/ForbiddenException.java -->

```java
package com.final_project.auth_service.exception;

/**
 * Exception thrown for forbidden access.
 */
public class ForbiddenException extends UserServiceException {
    public ForbiddenException(String message) {
        super("Forbidden: " + message);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/GlobalExceptionHandler.java

<!-- File: src/main/java/com/final_project/auth_service/exception/GlobalExceptionHandler.java -->

```java
package com.final_project.auth_service.exception;


import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for REST API.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("User not found: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DuplicateUserException.
     */
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(
            DuplicateUserException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("CONFLICT")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("Duplicate user: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles InvalidUserException.
     */
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserException(
            InvalidUserException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("INVALID_REQUEST")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("Invalid user data: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UnauthorizedException.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("Unauthorized access: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles ForbiddenException.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            ForbiddenException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("Forbidden access: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles KeycloakException.
     */
    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<ErrorResponse> handleKeycloakException(
            KeycloakException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("KEYCLOAK_ERROR")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.error("Keycloak error", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getField() + ": " + error.getDefaultMessage())
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("VALIDATION_ERROR")
                .message("Input validation failed")
                .validationErrors(errors)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.warn("Validation error: {}", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL_ERROR")
                .message("An unexpected error occurred")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.error("Unexpected error", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error response DTO.
     */
    @Data
    @Builder
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private List<String> validationErrors;
    }
}
```

## src/main/java/com/final_project/auth_service/exception/InvalidUserException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/InvalidUserException.java -->

```java
package com.final_project.auth_service.exception;

public class InvalidUserException extends UserServiceException {
    public InvalidUserException(String message) {
        super("Invalid user data: " + message);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/KeycloakException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/KeycloakException.java -->

```java
package com.final_project.auth_service.exception;


/**
 * Exception thrown for Keycloak integration errors.
 */
public class KeycloakException extends UserServiceException {
    public KeycloakException(String message) {
        super("Keycloak error: " + message);
    }

    public KeycloakException(String message, Throwable cause) {
        super("Keycloak error: " + message + ""+ cause);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/UnauthorizedException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/UnauthorizedException.java -->

```java
package com.final_project.auth_service.exception;

/**
 * Exception thrown for unauthorized access.
 */
public class UnauthorizedException extends UserServiceException {
    public UnauthorizedException(String message) {
        super("Unauthorized: " + message);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/UserNotFoundException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/UserNotFoundException.java -->

```java
package com.final_project.auth_service.exception;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String userId) {
        super("User not found with ID: " + userId);
    }

    public UserNotFoundException(String field, String value) {
        super("User not found with " + field + ": " + value);
    }
}
```

## src/main/java/com/final_project/auth_service/exception/UserServiceException.java

<!-- File: src/main/java/com/final_project/auth_service/exception/UserServiceException.java -->

```java
package com.final_project.auth_service.exception;


public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

## src/main/java/com/final_project/auth_service/kafka/AuthEventPublisher.java

<!-- File: src/main/java/com/final_project/auth_service/kafka/AuthEventPublisher.java -->

```java
package com.final_project.auth_service.kafka;

import com.final_project.auth_service.config.AppProperties;
import com.final_project.auth_service.event.PasswordChangedEvent;
import com.final_project.auth_service.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AppProperties appProperties;

    public void publishUserRegister(UserRegisteredEvent userRegisteredEvent){
        String topic = appProperties.getKafka().getTopics().getUserRegistered();
        kafkaTemplate.send(topic, userRegisteredEvent.getUserId(), userRegisteredEvent)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Field to publish USER_REGISTERED event. userId {} eventId {} ", userRegisteredEvent.getUserId(), userRegisteredEvent.getEventId());
                    }
                    else{
                        log.info("PUBLISH USER_REGISTERED EVENT. topic {} partition {} offset {} and eventId {} ",
                                res.getProducerRecord().topic(),
                                res.getProducerRecord().partition(),
                                res.getRecordMetadata().hasOffset(),
                                userRegisteredEvent.getEventId()


                                );
                    }
                });

    }
    public void publishPasswordChange(PasswordChangedEvent event){
        String topic = appProperties.getKafka().getTopics().getChangePassword();
        kafkaTemplate.send(topic, event.getEventId(), event)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish PASSWORD_CHANGED event. userId={} eventId={}",
                                event.getUserId(), event.getEventId(), ex);
                    }
                    else{
                        log.info("Published PASSWORD_CHANGED event. topic={} partition={} offset={} eventId={}",
                                res.getRecordMetadata().topic(),
                                res.getRecordMetadata().partition(),
                                res.getRecordMetadata().offset(),
                                event.getEventId());
                    }
                });
    }

}
```

## src/main/java/com/final_project/auth_service/model/AuditLog.java

<!-- File: src/main/java/com/final_project/auth_service/model/AuditLog.java -->

```java
package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * AuditLog document for tracking all user-related operations.
 */
@Document(collection = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String action;

    @Indexed
    private String entityType;

    private String entityId;

    private String description;

    private String oldValue;

    private String newValue;

    @Builder.Default
    private String status = "SUCCESS";

    private String ipAddress;

    private String userAgent;

    private String requestId;

    @CreatedDate
    private LocalDateTime createdAt;

    public static class Action {
        public static final String USER_CREATED = "USER_CREATED";
        public static final String USER_UPDATED = "USER_UPDATED";
        public static final String USER_DELETED = "USER_DELETED";
        public static final String USER_ACTIVATED = "USER_ACTIVATED";
        public static final String USER_SUSPENDED = "USER_SUSPENDED";
        public static final String USER_LOCKED = "USER_LOCKED";
        public static final String PASSWORD_CHANGED = "PASSWORD_CHANGED";
        public static final String PASSWORD_RESET = "PASSWORD_RESET";
        public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
        public static final String LOGIN_FAILURE = "LOGIN_FAILURE";
        public static final String LOGOUT = "LOGOUT";
        public static final String PERMISSION_GRANTED = "PERMISSION_GRANTED";
        public static final String PERMISSION_REVOKED = "PERMISSION_REVOKED";
        public static final String ROLE_ASSIGNED = "ROLE_ASSIGNED";
        public static final String ROLE_REMOVED = "ROLE_REMOVED";
        public static final String EMAIL_VERIFIED = "EMAIL_VERIFIED";
        public static final String TWO_FACTOR_ENABLED = "TWO_FACTOR_ENABLED";
        public static final String TWO_FACTOR_DISABLED = "TWO_FACTOR_DISABLED";
    }

    public static class EntityType {
        public static final String USER = "USER";
        public static final String ROLE = "ROLE";
        public static final String PERMISSION = "PERMISSION";
        public static final String PROFILE = "PROFILE";
    }
}
```

## src/main/java/com/final_project/auth_service/model/Permission.java

<!-- File: src/main/java/com/final_project/auth_service/model/Permission.java -->

```java
package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Permission document for fine-grained access control.
 */
@Document(collection = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    @Indexed(unique = true)
    private String permissionKey;

    @Indexed
    private String resource;

    private String action;

    @Builder.Default
    private Boolean isSystemPermission = false;

    @Builder.Default
    private Boolean isActive = true;

    /**
     * Store related role IDs instead of JPA relation.
     */
    @Builder.Default
    private Set<String> roleIds = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getFullPermissionKey() {
        return resource + ":" + action;
    }
}
```

## src/main/java/com/final_project/auth_service/model/Role.java

<!-- File: src/main/java/com/final_project/auth_service/model/Role.java -->

```java
package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Role document for RBAC.
 */
@Document(collection = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    @Indexed(unique = true)
    private String roleKey;

    private String keycloakId;

    @Builder.Default
    private Boolean isSystemRole = false;

    @Builder.Default
    private Boolean isActive = true;

    /**
     * Store permission IDs instead of ManyToMany.
     */
    @Builder.Default
    private Set<String> permissionIds = new HashSet<>();

    /**
     * Optional reverse linkage.
     * Usually not required unless you need quick reverse lookup.
     */
    @Builder.Default
    private Set<String> userIds = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public void addPermission(String permissionId) {
        if (permissionIds == null) {
            permissionIds = new HashSet<>();
        }
        permissionIds.add(permissionId);
    }

    public void removePermission(String permissionId) {
        if (permissionIds != null) {
            permissionIds.remove(permissionId);
        }
    }
}
```

## src/main/java/com/final_project/auth_service/model/User.java

<!-- File: src/main/java/com/final_project/auth_service/model/User.java -->

```java
package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User document representing a user in the system.
 */
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @Indexed(unique = true, sparse = true)
    private String keycloakId;

    private String phoneNumber;

    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Builder.Default
    private Boolean emailVerified = false;

    @Builder.Default
    private Boolean twoFactorEnabled = false;

    private LocalDateTime lastLogin;

    private String lastLoginIp;

    private LocalDateTime lastPasswordChange;

    @Builder.Default
    private Integer failedLoginAttempts = 0;

    private LocalDateTime lockedUntil;

    private String password;

    @Builder.Default
    private Set<String> roleIds = new HashSet<>();


    private String profile;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    private LocalDateTime deletedAt;

    private String deletedBy;

    private UserType userType;

    private String entityId;
    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        DELETED
    }
    public enum UserType{
        TEACHER,
        STUDENT,
        USER,
        EMPLOYEE
    }

    public boolean isAccountLocked() {
        return lockedUntil != null && LocalDateTime.now().isBefore(lockedUntil);
    }

    public boolean isEnabled() {
        return status == UserStatus.ACTIVE && !isAccountLocked();
    }

    public String getFullName() {
        String first = firstName != null ? firstName : "";
        String last = lastName != null ? lastName : "";
        return (first + " " + last).trim();
    }
}
```

## src/main/java/com/final_project/auth_service/repository/AuditLogRepository.java

<!-- File: src/main/java/com/final_project/auth_service/repository/AuditLogRepository.java -->

```java
package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    Page<AuditLog> findByUserId(String userId, Pageable pageable);

    Page<AuditLog> findByAction(String action, Pageable pageable);

    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    Page<AuditLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<AuditLog> findByStatus(String status, Pageable pageable);

    default Page<AuditLog> findFailedOperations(Pageable pageable) {
        return findByStatus("FAILURE", pageable);
    }

    long countByAction(String action);

    long countByStatusAndCreatedAtAfter(String status, LocalDateTime after);
}
```

## src/main/java/com/final_project/auth_service/repository/PermissionRepository.java

<!-- File: src/main/java/com/final_project/auth_service/repository/PermissionRepository.java -->

```java
package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {

    Optional<Permission> findByName(String name);

    Optional<Permission> findByPermissionKey(String permissionKey);

    Optional<Permission> findByResourceAndAction(String resource, String action);

    List<Permission> findByIsActive(Boolean isActive);

    List<Permission> findByIsSystemPermissionFalse();
}
```

## src/main/java/com/final_project/auth_service/repository/RoleRepository.java

<!-- File: src/main/java/com/final_project/auth_service/repository/RoleRepository.java -->

```java
package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);

    Optional<Role> findByRoleKey(String roleKey);

    Optional<Role> findByKeycloakId(String keycloakId);

    List<Role> findByIsActive(Boolean isActive);

    List<Role> findByIsSystemRoleFalse();
}
```

## src/main/java/com/final_project/auth_service/repository/UserRepository.java

<!-- File: src/main/java/com/final_project/auth_service/repository/UserRepository.java -->

```java
package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for User document database operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByKeycloakId(String keycloakId);
    Page<User> findByStatus(User.UserStatus status, Pageable pageable);
    Page<User> findByStatusAndDeletedAtIsNull(User.UserStatus status, Pageable pageable);

    @Query("{ '$or': [ " +
            "{ 'email': { $regex: ?0, $options: 'i' } }, " +
            "{ 'username': { $regex: ?0, $options: 'i' } }, " +
            "{ 'firstName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'lastName': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Page<User> searchUsers(String searchTerm, Pageable pageable);
    List<User> findByLastLoginBeforeAndStatus(LocalDateTime date, User.UserStatus status);
    List<User> findByLockedUntilIsNotNullAndLockedUntilBefore(LocalDateTime now);
    long countByStatus(User.UserStatus status);
    void deleteByDeletedAtIsNotNull();
}
```

## src/main/java/com/final_project/auth_service/service/AuditLogService.java

<!-- File: src/main/java/com/final_project/auth_service/service/AuditLogService.java -->

```java
package com.final_project.auth_service.service;
import com.final_project.auth_service.model.AuditLog;
import com.final_project.auth_service.repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * Service for audit logging operations.
 *
 * Handles:
 * - Logging user actions
 * - Recording authentication events
 * - Tracking permission changes
 * - Storing audit trails asynchronously
 */
@Service
@Slf4j
@AllArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    /**
     * Log an audit event asynchronously.
     *
     * @param userId User ID performing the action
     * @param action Action name
     * @param entityType Entity type affected
     * @param entityId Entity ID affected
     * @param description Action description
     * @param status Operation status
     */
    @Async
    @Transactional
    public void logAuditEvent(String userId, String action, String entityType, String entityId, String description, String status) {
        logAuditEvent(userId, action, entityType, entityId, description, null, null, status);
    }

    /**
     * Log an audit event with old and new values.
     *
     * @param userId User ID performing the action
     * @param action Action name
     * @param entityType Entity type affected
     * @param entityId Entity ID affected
     * @param description Action description
     * @param oldValue Previous value
     * @param newValue New value
     * @param status Operation status
     */
    @Async
    @Transactional
    public void logAuditEvent(String userId, String action, String entityType, String entityId, String description, String oldValue, String newValue, String status) {
        try {
            String ipAddress = getClientIpAddress();
            String userAgent = getUserAgent();
            String requestId = getRequestId();

            AuditLog auditLog = AuditLog.builder()
                    .userId(userId)
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .description(description)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .status(status)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .requestId(requestId)
                    .createdAt(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
            log.debug("Audit log saved: {} - {} - {}", userId, action, entityId);

        } catch (Exception e) {
            log.error("Failed to save audit log: {} - {} - {}", userId, action, entityId, e);
        }
    }

    /**
     * Get audit logs for a specific user.
     *
     * @param userId User ID
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getUserAuditLogs(String userId, Pageable pageable) {
        return auditLogRepository.findByUserId(userId, pageable);
    }

    /**
     * Get audit logs for a specific action.
     *
     * @param action Action name
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getActionAuditLogs(String action, Pageable pageable) {
        return auditLogRepository.findByAction(action, pageable);
    }

    /**
     * Get audit logs for a specific entity.
     *
     * @param entityType Entity type
     * @param entityId Entity ID
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getEntityAuditLogs(String entityType, String entityId, Pageable pageable) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable);
    }

    /**
     * Get failed audit operations.
     *
     * @param pageable Pagination info
     * @return Page of failed audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getFailedOperations(Pageable pageable) {
        return auditLogRepository.findFailedOperations(pageable);
    }

    /**
     * Get audit logs by date range.
     *
     * @param startDate Start date
     * @param endDate End date
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return auditLogRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    /**
     * Get client IP address from request.
     *
     * @return Client IP address
     */
    private String getClientIpAddress() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String ipAddress = attributes.getRequest().getHeader("X-Forwarded-For");
                if (ipAddress == null || ipAddress.isEmpty()) {
                    ipAddress = attributes.getRequest().getRemoteAddr();
                }
                return ipAddress;
            }
        } catch (Exception e) {
            log.debug("Could not get client IP address", e);
        }
        return "UNKNOWN";
    }

    /**
     * Get user agent from request.
     *
     * @return User agent
     */
    private String getUserAgent() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest().getHeader("User-Agent");
            }
        } catch (Exception e) {
            log.debug("Could not get user agent", e);
        }
        return "UNKNOWN";
    }

    /**
     * Get request ID from request.
     *
     * @return Request ID
     */
    private String getRequestId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest().getHeader("X-Request-ID");
            }
        } catch (Exception e) {
            log.debug("Could not get request ID", e);
        }
        return null;
    }
}
```

## src/main/java/com/final_project/auth_service/service/AuthenticationService.java

<!-- File: src/main/java/com/final_project/auth_service/service/AuthenticationService.java -->

```java
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
```

## src/main/java/com/final_project/auth_service/service/IPAdressResolver.java

<!-- File: src/main/java/com/final_project/auth_service/service/IPAdressResolver.java -->

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.config.RemoteIP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class IPAdressResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RemoteIP.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String ipaddress = request.getHeader("X-Forwarded-For");
        if (ipaddress == null || ipaddress.isEmpty()) {
            return request.getRemoteAddr();
        }
        return ipaddress.split(",")[0];
    }
}
```

## src/main/java/com/final_project/auth_service/service/KeycloakService.java

<!-- File: src/main/java/com/final_project/auth_service/service/KeycloakService.java -->

```java
package com.final_project.auth_service.service;
import com.final_project.auth_service.dto.CreateUserRequest;
import com.final_project.auth_service.exception.KeycloakException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * Service for Keycloak integration.
 *
 * Handles:
 * - User creation/deletion in Keycloak
 * - Password management
 * - Role assignment/removal
 * - User enable/disable
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    /**
     * Create a new user in Keycloak.
     *
     * @param request User creation request
     * @return Keycloak user ID
     */
    public String createKeycloakUser(CreateUserRequest request) {
        try {

            UsersResource usersResource = getUsersResource();
            UserRepresentation user = new UserRepresentation();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEnabled(true);
            user.setEmailVerified(false);

            // Create user and get ID from response
            var response = usersResource.create(user);
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            log.info("User created in Keycloak with ID: {}", userId);
            return userId;

        } catch (Exception e) {
            log.error("Failed to create user in Keycloak: {}", request.getUsername(), e);
            throw new KeycloakException("Failed to create user in Keycloak", e);
        }
    }

    /**
     * Delete a user from Keycloak.
     *
     * @param userId Keycloak user ID
     */
    public void deleteKeycloakUser(String userId) {
        try {
            log.info("Deleting user from Keycloak: {}", userId);

            UsersResource usersResource = getUsersResource();
            usersResource.delete(userId);

            log.info("User deleted from Keycloak: {}", userId);

        } catch (Exception e) {
            log.error("Failed to delete user from Keycloak: {}", userId, e);
            throw new KeycloakException("Failed to delete user from Keycloak", e);
        }
    }

    /**
     * Assign a role to a user.
     *
     * @param userId Keycloak user ID
     * @param roleName Role name
     */
    public void assignRoleToUser(String userId, String roleName) {
        try {
            log.info("Assigning role {} to user {}", roleName, userId);

            RealmResource realmResource = getRealmResource();
            UsersResource usersResource = realmResource.users();

            var role = realmResource.roles().get(roleName).toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));

            log.info("Role assigned successfully");

        } catch (Exception e) {
            log.error("Failed to assign role to user: {}", userId, e);
            throw new KeycloakException("Failed to assign role to user", e);
        }
    }

    /**
     * Remove a role from a user.
     *
     * @param userId Keycloak user ID
     * @param roleName Role name
     */
    public void removeRoleFromUser(String userId, String roleName) {
        try {
            log.info("Removing role {} from user {}", roleName, userId);

            RealmResource realmResource = getRealmResource();
            UsersResource usersResource = realmResource.users();

            var role = realmResource.roles().get(roleName).toRepresentation();
            usersResource.get(userId).roles().realmLevel().remove(Collections.singletonList(role));

            log.info("Role removed successfully");

        } catch (Exception e) {
            log.error("Failed to remove role from user: {}", userId, e);
            throw new KeycloakException("Failed to remove role from user", e);
        }
    }

    /**
     * Disable a user in Keycloak.
     *
     * @param userId Keycloak user ID
     */
    public void disableKeycloakUser(String userId) {
        try {
            log.info("Disabling user in Keycloak: {}", userId);

            UsersResource usersResource = getUsersResource();
            UserRepresentation user = usersResource.get(userId).toRepresentation();
            user.setEnabled(false);
            usersResource.get(userId).update(user);

            log.info("User disabled in Keycloak");

        } catch (Exception e) {
            log.error("Failed to disable user in Keycloak: {}", userId, e);
            throw new KeycloakException("Failed to disable user in Keycloak", e);
        }
    }

    /**
     * Enable a user in Keycloak.
     *
     * @param userId Keycloak user ID
     */
    public void enableKeycloakUser(String userId) {
        try {
            log.info("Enabling user in Keycloak: {}", userId);

            UsersResource usersResource = getUsersResource();
            UserRepresentation user = usersResource.get(userId).toRepresentation();
            user.setEnabled(true);
            usersResource.get(userId).update(user);

            log.info("User enabled in Keycloak");

        } catch (Exception e) {
            log.error("Failed to enable user in Keycloak: {}", userId, e);
            throw new KeycloakException("Failed to enable user in Keycloak", e);
        }
    }

    /**
     * Set password for a user.
     *
     * @param userId Keycloak user ID
     * @param password New password
     */
    public void setUserPassword(String userId, String password) {
        try {
            log.info("Setting password for user: {}", userId);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);

            UsersResource usersResource = getUsersResource();
            usersResource.get(userId).resetPassword(credential);

            log.info("Password set successfully");

        } catch (Exception e) {
            log.error("Failed to set password for user: {}", userId, e);
            throw new KeycloakException("Failed to set password for user", e);
        }
    }

    /**
     * Create a new role in Keycloak.
     *
     * @param roleName Role name
     * @param description Role description
     * @return Keycloak role ID
     */
    public String createKeycloakRole(String roleName, String description) {
        try {
            log.info("Creating role in Keycloak: {}", roleName);

            RealmResource realmResource = getRealmResource();

            RoleRepresentation role = new RoleRepresentation();
            role.setName(roleName);
            role.setDescription(description);

            realmResource.roles().create(role);

            // Get the created role to retrieve its ID
            RoleRepresentation createdRole = realmResource.roles().get(roleName).toRepresentation();
            String roleId = createdRole.getId();

            log.info("Role created in Keycloak with ID: {}", roleId);
            return roleId;

        } catch (Exception e) {
            log.error("Failed to create role in Keycloak: {}", roleName, e);
            throw new KeycloakException("Failed to create role in Keycloak", e);
        }
    }

    /**
     * Delete a role from Keycloak.
     *
     * @param roleId Keycloak role ID
     */
    public void deleteKeycloakRole(String roleId) {
        try {
            log.info("Deleting role from Keycloak: {}", roleId);

            RealmResource realmResource = getRealmResource();
            realmResource.roles().deleteRole(roleId);

            log.info("Role deleted from Keycloak: {}", roleId);

        } catch (Exception e) {
            log.error("Failed to delete role from Keycloak: {}", roleId, e);
            throw new KeycloakException("Failed to delete role from Keycloak", e);
        }
    }

    /**
     * Get role representation from Keycloak.
     *
     * @param roleName Role name
     * @return Role representation
     */
    public RoleRepresentation getKeycloakRole(String roleName) {
        try {
            log.info("Fetching role from Keycloak: {}", roleName);

            RealmResource realmResource = getRealmResource();
            return realmResource.roles().get(roleName).toRepresentation();

        } catch (Exception e) {
            log.error("Failed to get role from Keycloak: {}", roleName, e);
            throw new KeycloakException("Failed to get role from Keycloak", e);
        }
    }

    /**
     * Update a role in Keycloak.
     *
     * @param roleName Role name
     * @param description New description
     */
    public void updateKeycloakRole(String roleName, String description) {
        try {
            log.info("Updating role in Keycloak: {}", roleName);

            RealmResource realmResource = getRealmResource();
            RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
            role.setDescription(description);
            realmResource.roles().get(roleName).update(role);

            log.info("Role updated in Keycloak: {}", roleName);

        } catch (Exception e) {
            log.error("Failed to update role in Keycloak: {}", roleName, e);
            throw new KeycloakException("Failed to update role in Keycloak", e);
        }
    }

    /**
     * Get users resource from realm.
     *
     * @return UsersResource
     */
    private UsersResource getUsersResource() {
        return getRealmResource().users();
    }

    /**
     * Get realm resource.
     *
     * @return RealmResource
     */
    private RealmResource getRealmResource() {
        return keycloak.realm(realm);
    }
    public void login(String usernameOrEmail, String  password) {

    }
}
```

## src/main/java/com/final_project/auth_service/service/PermissionService.java

<!-- File: src/main/java/com/final_project/auth_service/service/PermissionService.java -->

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.model.*;
import com.final_project.auth_service.repository.*;
import com.final_project.auth_service.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for permission management operations with MongoDB.
 *
 * Handles:
 * - Permission CRUD operations
 * - Resource-action permission model
 * - Permission-role relationship management
 * - Permission queries and filtering
 * - Audit logging
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final AuditLogService auditLogService;

    /**
     * Create a new permission.
     *
     * @param request Permission creation request
     * @return Created permission DTO
     */
    public PermissionDTO createPermission(CreatePermissionRequest request) {
        log.info("Creating new permission: {}", request.getName());

        // Check if permission name already exists
        Optional<Permission> existingByName = permissionRepository.findByName(request.getName());
        if (existingByName.isPresent()) {
            log.warn("Permission with name already exists: {}", request.getName());
            throw new DuplicateUserException("name", request.getName());
        }

        // Create permission key (RESOURCE:ACTION)
        String permissionKey = request.getResource() + ":" + request.getAction();

        // Check if permission key already exists
        Optional<Permission> existingByKey = permissionRepository.findByPermissionKey(permissionKey);
        if (existingByKey.isPresent()) {
            log.warn("Permission with key already exists: {}", permissionKey);
            throw new DuplicateUserException("permissionKey", permissionKey);
        }

        // Create permission
        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .resource(request.getResource())
                .action(request.getAction())
                .permissionKey(permissionKey)
                .isSystemPermission(request.getIsSystemPermission() != null ? request.getIsSystemPermission() : false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        Permission savedPermission = permissionRepository.save(permission);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_CREATED",
                "PERMISSION",
                savedPermission.getId(),
                "Permission created: " + savedPermission.getName(),
                "SUCCESS"
        );

        log.info("Permission created successfully: {}", savedPermission.getId());
        return toDTO(savedPermission);
    }

    /**
     * Get permission by ID.
     *
     * @param permissionId Permission ID
     * @return Permission DTO
     */
    @Transactional(readOnly = true)
    public PermissionDTO getPermissionById(String permissionId) {
        log.info("Fetching permission: {}", permissionId);
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> {
                    log.error("Permission not found: {}", permissionId);
                    return new InvalidUserException("Permission not found with ID: " + permissionId);
                });
        return toDTO(permission);
    }

    /**
     * Get permission by name.
     *
     * @param name Permission name
     * @return Permission DTO
     */
    @Transactional(readOnly = true)
    public PermissionDTO getPermissionByName(String name) {
        log.info("Fetching permission by name: {}", name);
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("Permission not found: {}", name);
                    return new InvalidUserException("Permission not found with name: " + name);
                });
        return toDTO(permission);
    }

    /**
     * Get permission by resource and action.
     *
     * @param resource Resource name
     * @param action Action name
     * @return Permission DTO
     */
    @Transactional(readOnly = true)
    public PermissionDTO getPermissionByResourceAndAction(String resource, String action) {
        log.info("Fetching permission for resource={}, action={}", resource, action);
        String permissionKey = resource + ":" + action;
        Permission permission = permissionRepository.findByPermissionKey(permissionKey)
                .orElseThrow(() -> {
                    log.error("Permission not found: {}", permissionKey);
                    return new InvalidUserException("Permission not found for resource: " + resource + ", action: " + action);
                });
        return toDTO(permission);
    }

    /**
     * Get all permissions.
     *
     * @return List of all permission DTOs
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllPermissions() {
        log.info("Fetching all permissions");
        return permissionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active permissions.
     *
     * @return List of active permission DTOs
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllActivePermissions() {
        log.info("Fetching all active permissions");
        return permissionRepository.findByIsActive(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get permissions by resource.
     *
     * @param resource Resource name
     * @return List of permission DTOs for the resource
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> getPermissionsByResource(String resource) {
        log.info("Fetching permissions for resource: {}", resource);
        return permissionRepository.findAll().stream()
                .filter(p -> p.getResource().equals(resource))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update permission.
     *
     * @param permissionId Permission ID
     * @param request Update request
     * @return Updated permission DTO
     */
    public PermissionDTO updatePermission(String permissionId, UpdatePermissionRequest request) {
        log.info("Updating permission: {}", permissionId);

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> {
                    log.error("Permission not found: {}", permissionId);
                    return new InvalidUserException("Permission not found with ID: " + permissionId);
                });

        // Store old value for audit
        String oldValue = permission.toString();

        // Update fields
        if (request.getName() != null) {
            // Check if new name already exists (excluding current permission)
            Optional<Permission> existingByName = permissionRepository.findByName(request.getName());
            if (existingByName.isPresent() && !existingByName.get().getId().equals(permissionId)) {
                throw new DuplicateUserException("name", request.getName());
            }
            permission.setName(request.getName());
        }

        if (request.getDescription() != null) {
            permission.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            permission.setIsActive(request.getIsActive());
        }

        permission.setUpdatedAt(LocalDateTime.now());
        Permission updatedPermission = permissionRepository.save(permission);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_UPDATED",
                "PERMISSION",
                permissionId,
                "Permission updated",
                oldValue,
                updatedPermission.toString(),
                "SUCCESS"
        );

        log.info("Permission updated successfully: {}", permissionId);
        return toDTO(updatedPermission);
    }

    /**
     * Delete permission.
     *
     * @param permissionId Permission ID
     */
    public void deletePermission(String permissionId) {
        log.info("Deleting permission: {}", permissionId);

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> {
                    log.error("Permission not found: {}", permissionId);
                    return new InvalidUserException("Permission not found with ID: " + permissionId);
                });

        // Prevent deletion of system permissions
        if (permission.getIsSystemPermission()) {
            log.warn("Attempt to delete system permission: {}", permissionId);
            throw new InvalidUserException("Cannot delete system permission: " + permission.getName());
        }

        permissionRepository.deleteById(permissionId);

        // Remove from all roles
        roleRepository.findAll().forEach(role -> {
            if (role.getPermissionIds() != null && role.getPermissionIds().contains(permissionId)) {
                role.getPermissionIds().remove(permissionId);
                role.setUpdatedAt(LocalDateTime.now());
                roleRepository.save(role);
            }
        });

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_DELETED",
                "PERMISSION",
                permissionId,
                "Permission deleted: " + permission.getName(),
                "SUCCESS"
        );

        log.info("Permission deleted successfully: {}", permissionId);
    }

    /**
     * Assign permission to role.
     *
     * @param permissionId Permission ID
     * @param roleId Role ID
     */
    public void assignPermissionToRole(String permissionId, String roleId) {
        log.info("Assigning permission {} to role {}", permissionId, roleId);

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new InvalidUserException("Permission not found with ID: " + permissionId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new InvalidUserException("Role not found with ID: " + roleId));

        if (role.getPermissionIds() == null) {
            role.setPermissionIds(new HashSet<>());
        }

        if (role.getPermissionIds().contains(permissionId)) {
            log.warn("Permission already assigned to role: {}", permissionId);
            throw new DuplicateUserException("permissionId", permissionId);
        }

        role.getPermissionIds().add(permissionId);
        role.setUpdatedAt(LocalDateTime.now());
        roleRepository.save(role);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_ASSIGNED_TO_ROLE",
                "ROLE",
                roleId,
                "Permission assigned: " + permission.getName(),
                "SUCCESS"
        );

        log.info("Permission assigned to role successfully");
    }

    /**
     * Remove permission from role.
     *
     * @param permissionId Permission ID
     * @param roleId Role ID
     */
    public void removePermissionFromRole(String permissionId, String roleId) {
        log.info("Removing permission {} from role {}", permissionId, roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new InvalidUserException("Role not found with ID: " + roleId));

        if (role.getPermissionIds() != null) {
            role.getPermissionIds().remove(permissionId);
            role.setUpdatedAt(LocalDateTime.now());
            roleRepository.save(role);
        }

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_REMOVED_FROM_ROLE",
                "ROLE",
                roleId,
                "Permission removed: " + permissionId,
                "SUCCESS"
        );

        log.info("Permission removed from role successfully");
    }

    /**
     * Get list of available resources.
     *
     * @return List of unique resources
     */
    @Transactional(readOnly = true)
    public List<String> getAvailableResources() {
        log.info("Fetching available resources");
        return permissionRepository.findAll().stream()
                .map(Permission::getResource)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Get permission statistics.
     *
     * @return Statistics map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getPermissionStatistics() {
        log.info("Fetching permission statistics");

        long totalPermissions = permissionRepository.count();
        long activePermissions = permissionRepository.findByIsActive(true).size();
        long inactivePermissions = totalPermissions - activePermissions;
        long systemPermissions = permissionRepository.findAll().stream()
                .filter(p -> p.getIsSystemPermission() != null && p.getIsSystemPermission())
                .count();

        // Count by resource
        Map<String, Long> byResource = permissionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Permission::getResource, Collectors.counting()));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPermissions", totalPermissions);
        stats.put("activePermissions", activePermissions);
        stats.put("inactivePermissions", inactivePermissions);
        stats.put("systemPermissions", systemPermissions);
        stats.put("customPermissions", totalPermissions - systemPermissions);
        stats.put("byResource", byResource);

        return stats;
    }

    /**
     * Convert Permission entity to DTO.
     *
     * @param permission Permission entity
     * @return Permission DTO
     */
    private PermissionDTO toDTO(Permission permission) {
        return PermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .resource(permission.getResource())
                .action(permission.getAction())
                .permissionKey(permission.getPermissionKey())
                .isSystemPermission(permission.getIsSystemPermission())
                .isActive(permission.getIsActive())
                .updatedAt(permission.getUpdatedAt())
                .createdAt(permission.getCreatedAt())
                .build();
    }
}
```

## src/main/java/com/final_project/auth_service/service/RoleService.java

<!-- File: src/main/java/com/final_project/auth_service/service/RoleService.java -->

```java
package com.final_project.auth_service.service;

import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.model.*;
import com.final_project.auth_service.repository.*;
import com.final_project.auth_service.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for role management operations with MongoDB.
 *
 * Handles:
 * - Role CRUD operations
 * - Permission assignment to roles
 * - Role-user relationship management
 * - Role queries and filtering
 * - Audit logging
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final KeycloakService keycloakService;

    /**
     * Create a new role.
     *
     * Architecture: MongoDB Authority + Keycloak Sync
     * 1. Create in MongoDB (AUTHORITY)
     * 2. Sync to Keycloak (NON-BLOCKING)
     *
     * @param request Role creation request
     * @return Created role DTO
     */
    public RoleDTO createRole(CreateRoleRequest request) {
        log.info("Creating new role: {}", request.getName());

        // Check if role name already exists
        Optional<Role> existingByName = roleRepository.findByName(request.getName());
        if (existingByName.isPresent()) {
            log.warn("Role with name already exists: {}", request.getName());
            throw new DuplicateUserException("name", request.getName());
        }

        // Check if role key already exists
        Optional<Role> existingByKey = roleRepository.findByRoleKey(request.getRoleKey());
        if (existingByKey.isPresent()) {
            log.warn("Role with key already exists: {}", request.getRoleKey());
            throw new DuplicateUserException("roleKey", request.getRoleKey());
        }

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .roleKey(request.getRoleKey())
                .isSystemRole(request.getIsSystemRole() != null ? request.getIsSystemRole() : false)
                .isActive(true)
                .permissionIds(new HashSet<>(request.getPermissionIds() != null ? request.getPermissionIds() : new HashSet<>()))
                .createdAt(LocalDateTime.now())
                .build();

        Role savedRole = roleRepository.save(role);
        log.info("Role created in MongoDB: {}", savedRole.getId());

        syncRoleToKeycloak(savedRole);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "ROLE_CREATED",
                "ROLE",
                savedRole.getId(),
                "Role created: " + savedRole.getName(),
                "SUCCESS"
        );

        log.info("Role created successfully: {}", savedRole.getId());
        return toDTO(savedRole);
    }

    /**
     * Sync role to Keycloak (non-blocking, non-transactional).
     * If sync fails, log warning but don't fail the operation.
     * MongoDB is the source of truth.
     *
     * @param role Role to sync to Keycloak
     */
    private void syncRoleToKeycloak(Role role) {
        try {
            log.info("Syncing role to Keycloak: {}", role.getName());
            String keycloakId = keycloakService.createKeycloakRole(
                    role.getName(),
                    role.getDescription()
            );
            role.setKeycloakId(keycloakId);
            roleRepository.save(role);
            log.info("Role synced to Keycloak successfully with ID: {}", keycloakId);
        } catch (Exception e) {
            log.warn(" Failed to sync role to Keycloak: {}. " +
                            "Role exists in MongoDB (authority). " +
                            "Keycloak sync can be retried later.",
                    role.getName(), e);

        }
    }

    /**
     * Get role by ID.
     *
     * @param roleId Role ID
     * @return Role DTO
     */
    @Transactional(readOnly = true)
    public RoleDTO getRoleById(String roleId) {
        log.info("Fetching role: {}", roleId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", roleId);
                    return new InvalidUserException("Role not found with ID: " + roleId);
                });
        return toDTO(role);
    }

    /**
     * Get role by name.
     *
     * @param name Role name
     * @return Role DTO
     */
    @Transactional(readOnly = true)
    public RoleDTO getRoleByName(String name) {
        log.info("Fetching role by name: {}", name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", name);
                    return new InvalidUserException("Role not found with name: " + name);
                });
        return toDTO(role);
    }

    /**
     * Get all roles.
     *
     * @return List of all role DTOs
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        log.info("Fetching all roles");
        return roleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active roles.
     *
     * @return List of active role DTOs
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllActiveRoles() {
        log.info("Fetching all active roles");
        return roleRepository.findByIsActive(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update role.
     *
     * @param roleId Role ID
     * @param request Update request
     * @return Updated role DTO
     */
    public RoleDTO updateRole(String roleId, UpdateRoleRequest request) {
        log.info("Updating role: {}", roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", roleId);
                    return new InvalidUserException("Role not found with ID: " + roleId);
                });

        // Store old value for audit
        String oldValue = role.toString();

        // Update fields
        if (request.getName() != null) {
            // Check if new name already exists (excluding current role)
            Optional<Role> existingByName = roleRepository.findByName(request.getName());
            if (existingByName.isPresent() && !existingByName.get().getId().equals(roleId)) {
                throw new DuplicateUserException("name", request.getName());
            }
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            role.setIsActive(request.getIsActive());
        }

        role.setUpdatedAt(LocalDateTime.now());
        Role updatedRole = roleRepository.save(role);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "ROLE_UPDATED",
                "ROLE",
                roleId,
                "Role updated",
                oldValue,
                updatedRole.toString(),
                "SUCCESS"
        );

        log.info("Role updated successfully: {}", roleId);
        return toDTO(updatedRole);
    }

    /**
     * Delete role.
     *
     * Architecture: MongoDB Authority + Keycloak Sync
     * 1. Delete from MongoDB (AUTHORITY)
     * 2. Delete from Keycloak (NON-BLOCKING)
     *
     * @param roleId Role ID
     */
    public void deleteRole(String roleId) {
        log.info("Deleting role: {}", roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", roleId);
                    return new InvalidUserException("Role not found with ID: " + roleId);
                });

        // Prevent deletion of system roles
        if (role.getIsSystemRole()) {
            log.warn("Attempt to delete system role: {}", roleId);
            throw new InvalidUserException("Cannot delete system role: " + role.getName());
        }

        roleRepository.deleteById(roleId);
        log.info("Role deleted from MongoDB: {}", roleId);

        if (role.getKeycloakId() != null) {
            deleteRoleFromKeycloak(role);
        }

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "ROLE_DELETED",
                "ROLE",
                roleId,
                "Role deleted: " + role.getName(),
                "SUCCESS"
        );

        log.info("Role deleted successfully: {}", roleId);
    }

    /**
     * Delete role from Keycloak (non-blocking, non-transactional).
     * If deletion fails, log warning but don't fail the operation.
     * MongoDB is the source of truth - role is already deleted there.
     *
     * @param role Role to delete from Keycloak
     */
    private void deleteRoleFromKeycloak(Role role) {
        try {
            log.info("Deleting role from Keycloak: {}", role.getName());
            keycloakService.deleteKeycloakRole(role.getKeycloakId());
            log.info("Role deleted from Keycloak successfully");
        } catch (Exception e) {
            log.warn("⚠️ Failed to delete role from Keycloak: {}. " +
                            "Role already deleted from MongoDB (authority). " +
                            "Keycloak cleanup can be retried later.",
                    role.getName(), e);
            // ✅ MongoDB deletion succeeded - continue without failing
            // Keycloak deletion can be retried manually
        }
    }

    /**
     * Add permission to role.
     *
     * @param roleId Role ID
     * @param permissionId Permission ID
     * @return Updated role DTO
     */
    public RoleDTO addPermissionToRole(String roleId, String permissionId) {
        log.info("Adding permission {} to role {}", permissionId, roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", roleId);
                    return new InvalidUserException("Role not found with ID: " + roleId);
                });

        // Check if permission ID is valid (could validate against permission repository)
        if (role.getPermissionIds() == null) {
            role.setPermissionIds(new HashSet<>());
        }

        if (role.getPermissionIds().contains(permissionId)) {
            log.warn("Permission already assigned to role: {}", permissionId);
            throw new DuplicateUserException("permissionId", permissionId);
        }

        role.getPermissionIds().add(permissionId);
        role.setUpdatedAt(LocalDateTime.now());
        Role updatedRole = roleRepository.save(role);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_ADDED_TO_ROLE",
                "ROLE",
                roleId,
                "Permission added to role: " + permissionId,
                "SUCCESS"
        );

        log.info("Permission added to role successfully");
        return toDTO(updatedRole);
    }

    /**
     * Remove permission from role.
     *
     * @param roleId Role ID
     * @param permissionId Permission ID
     * @return Updated role DTO
     */
    public RoleDTO removePermissionFromRole(String roleId, String permissionId) {
        log.info("Removing permission {} from role {}", permissionId, roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.error("Role not found: {}", roleId);
                    return new InvalidUserException("Role not found with ID: " + roleId);
                });

        if (role.getPermissionIds() != null) {
            role.getPermissionIds().remove(permissionId);
        }

        role.setUpdatedAt(LocalDateTime.now());
        Role updatedRole = roleRepository.save(role);

        // Log audit
        auditLogService.logAuditEvent(
                null,
                "PERMISSION_REMOVED_FROM_ROLE",
                "ROLE",
                roleId,
                "Permission removed from role: " + permissionId,
                "SUCCESS"
        );

        log.info("Permission removed from role successfully");
        return toDTO(updatedRole);
    }

    /**
     * Assign role to user.
     *
     * @param roleId Role ID
     * @param userId User ID
     */
    public void assignRoleToUser(String roleId, String userId) {
        log.info("Assigning role {} to user {}", roleId, userId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new InvalidUserException("Role not found with ID: " + roleId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found with ID: " + userId));

        if (user.getRoleIds() == null) {
            user.setRoleIds(new HashSet<>());
        }

        if (user.getRoleIds().contains(roleId)) {
            log.warn("User already has this role: {}", roleId);
            throw new DuplicateUserException("roleId", roleId);
        }

        user.getRoleIds().add(roleId);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Assign in Keycloak
        if (role.getKeycloakId() != null && user.getKeycloakId() != null) {
            try {
                keycloakService.assignRoleToUser(user.getKeycloakId(), role.getName());
            } catch (Exception e) {
                log.warn("Failed to assign role in Keycloak: {}", e.getMessage());
            }
        }

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "ROLE_ASSIGNED",
                "USER",
                userId,
                "Role assigned: " + role.getName(),
                "SUCCESS"
        );

        log.info("Role assigned to user successfully");
    }

    /**
     * Remove role from user.
     *
     * @param roleId Role ID
     * @param userId User ID
     */
    public void removeRoleFromUser(String roleId, String userId) {
        log.info("Removing role {} from user {}", roleId, userId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new InvalidUserException("Role not found with ID: " + roleId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found with ID: " + userId));

        if (user.getRoleIds() != null) {
            user.getRoleIds().remove(roleId);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        // Remove from Keycloak
        if (role.getKeycloakId() != null && user.getKeycloakId() != null) {
            try {
                keycloakService.removeRoleFromUser(user.getKeycloakId(), role.getName());
            } catch (Exception e) {
                log.warn("Failed to remove role in Keycloak: {}", e.getMessage());
            }
        }

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "ROLE_REMOVED",
                "USER",
                userId,
                "Role removed: " + role.getName(),
                "SUCCESS"
        );

        log.info("Role removed from user successfully");
    }

    /**
     * Get role statistics.
     *
     * @return Statistics map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getRoleStatistics() {
        log.info("Fetching role statistics");

        long totalRoles = roleRepository.count();
        long activeRoles = roleRepository.findByIsActive(true).size();
        long inactiveRoles = totalRoles - activeRoles;
        long systemRoles = roleRepository.findAll().stream()
                .filter(r -> r.getIsSystemRole() != null && r.getIsSystemRole())
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRoles", totalRoles);
        stats.put("activeRoles", activeRoles);
        stats.put("inactiveRoles", inactiveRoles);
        stats.put("systemRoles", systemRoles);
        stats.put("customRoles", totalRoles - systemRoles);

        return stats;
    }

    /**
     * Convert Role entity to DTO.
     *
     * @param role Role entity
     * @return Role DTO
     */
    private RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .roleKey(role.getRoleKey())
                .isSystemRole(role.getIsSystemRole())
                .isActive(role.getIsActive())
                .permissionIds(role.getPermissionIds())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}
```

## src/main/java/com/final_project/auth_service/service/UserService.java

<!-- File: src/main/java/com/final_project/auth_service/service/UserService.java -->

```java
package com.final_project.auth_service.service;
import com.final_project.auth_service.dto.*;
import com.final_project.auth_service.event.UserRegisteredEvent;
import com.final_project.auth_service.exception.*;
import com.final_project.auth_service.exception.UserNotFoundException;
import com.final_project.auth_service.kafka.AuthEventPublisher;
import com.final_project.auth_service.model.User;
import com.final_project.auth_service.repository.RoleRepository;
import com.final_project.auth_service.repository.UserRepository;
import jakarta.ws.rs.SeBootstrap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for user management operations.
 *
 * Handles:
 * - User CRUD operations
 * - User status management
 * - Role assignment
 * - Password management
 * - Account locking
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuditLogService auditLogService;
    private final KeycloakService keycloakService;
    private final PasswordEncoder passwordEncoder;
    private final AuthEventPublisher authEventPublisher;

    /**
     * Create a new user.
     *
     * @param request User creation request
     * @return Created user DTO
     */
    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        log.info("Creating new user: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("User with email already exists: {}", request.getEmail());
            throw new DuplicateUserException("email", request.getEmail());
        }

        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("User with username already exists: {}", request.getUsername());
            throw new DuplicateUserException("username", request.getUsername());
        }

        // Create Keycloak user first
        String keycloakId = keycloakService.createKeycloakUser(request);

        // Create local user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .keycloakId(keycloakId)
                .status(User.UserStatus.ACTIVE)
                .emailVerified(false)
                .twoFactorEnabled(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .failedLoginAttempts(0)
                .roleIds(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .profile(request.getProfile())
                .entityId(request.getEntityId())
                .userType(request.getUserType())
                .build();
        User savedUser = userRepository.save(user);
        try {
            UserRegisteredEvent event =  UserRegisteredEvent
                    .builder()
                    .userId(savedUser.getId())
                    .eventId(UUID.randomUUID().toString())
                    .occurredAt(LocalDateTime.now())
                    .email(savedUser.getEmail())
                    .firstName(savedUser.getFirstName())
                    .lastName(savedUser.getLastName())
                    .registrationSource("WEB")
                    .verificationToken("token..sdf")
                    .build();
            authEventPublisher.publishUserRegister(event);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        // Log audit
        auditLogService.logAuditEvent(
                savedUser.getId(),
                "USER_CREATED",
                "USER",
                savedUser.getId(),
                "User created: " + savedUser.getEmail(),
                "SUCCESS"
        );

        log.info("User created successfully: {}", savedUser.getId());
        return toDTO(savedUser);
    }

    /**
     * Get user by ID.
     *
     * @param userId User ID
     * @return User DTO
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(String userId) {
        log.info("Fetching user: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                });
        return toDTO(user);
    }

    /**
     * Get user by email.
     *
     * @param email Email address
     * @return User DTO
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new UserNotFoundException("email", email);
                });
        return toDTO(user);
    }

    /**
     * Get user by username.
     *
     * @param username Username
     * @return User DTO
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UserNotFoundException("username", username);
                });
        return toDTO(user);
    }

    /**
     * Get all users.
     *
     * @return List of all user DTOs
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active users.
     *
     * @return List of active user DTOs
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllActiveUsers(Pageable pageable) {
        log.info("Fetching all active users");
        Page<User> usersPage =  userRepository.findByStatus(User.UserStatus.ACTIVE, pageable);
        return usersPage.map(this::toDTO);
    }

    /**
     * Get users by role.
     *
     * @param roleId Role ID
     * @return List of user DTOs with the role
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(String roleId) {
        log.info("Fetching users with role: {}", roleId);
        return userRepository.findAll().stream()
                .filter(user -> user.getRoleIds() != null && user.getRoleIds().contains(roleId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search users by email or username (partial match).
     *
     * @param searchTerm Search term
     * @return List of matching user DTOs
     */
    @Transactional(readOnly = true)
    public List<UserDTO> searchUsers(String searchTerm) {
        log.info("Searching users with term: {}", searchTerm);
        String lowerTerm = searchTerm.toLowerCase();

        return userRepository.findAll().stream()
                .filter(user ->
                        user.getEmail().toLowerCase().contains(lowerTerm) ||
                                user.getUsername().toLowerCase().contains(lowerTerm) ||
                                (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(lowerTerm)) ||
                                (user.getLastName() != null && user.getLastName().toLowerCase().contains(lowerTerm))
                )
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get users with pagination.
     *
     * @param pageable Pagination info
     * @return Page of user DTOs
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersPage(Pageable pageable) {
        log.info("Fetching users with pagination");
        List<User> allUsers = userRepository.findAll();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allUsers.size());

        List<UserDTO> pageContent = allUsers.subList(start, end).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageable, allUsers.size());
    }

    /**
     * Update user.
     *
     * @param userId User ID
     * @param request Update request
     * @return Updated user DTO
     */
    public UserDTO updateUser(String userId, UpdateUserRequest request) {
        log.info("Updating user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                });

        // Store old value for audit
        String oldValue = user.toString();

        // Update fields
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getEmailVerified() != null) {
            user.setEmailVerified(request.getEmailVerified());
        }

        if (request.getTwoFactorEnabled() != null) {
            user.setTwoFactorEnabled(request.getTwoFactorEnabled());
        }

        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_UPDATED",
                "USER",
                userId,
                "User updated",
                oldValue,
                updatedUser.toString(),
                "SUCCESS"
        );

        log.info("User updated successfully: {}", userId);
        return toDTO(updatedUser);
    }

    /**
     * Delete user.
     *
     * @param userId User ID
     */
    public void deleteUser(String userId) {
        log.info("Deleting user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found: {}", userId);
                    return new UserNotFoundException(userId);
                });

        // Soft delete - mark as deleted
        user.setStatus(User.UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Delete from Keycloak
        if (user.getKeycloakId() != null) {
            try {
                keycloakService.deleteKeycloakUser(user.getKeycloakId());
            } catch (Exception e) {
                log.warn("Failed to delete user from Keycloak: {}", e.getMessage());
            }
        }

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_DELETED",
                "USER",
                userId,
                "User deleted",
                "SUCCESS"
        );

        log.info("User deleted successfully: {}", userId);
    }

    /**
     * Suspend user account.
     *
     * @param userId User ID
     */
    public void suspendUser(String userId) {
        log.info("Suspending user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setStatus(User.UserStatus.SUSPENDED);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_SUSPENDED",
                "USER",
                userId,
                "User suspended",
                "SUCCESS"
        );

        log.info("User suspended successfully: {}", userId);
    }

    /**
     * Activate/restore user account.
     *
     * @param userId User ID
     */
    public void activateUser(String userId) {
        log.info("Activating user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setStatus(User.UserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_ACTIVATED",
                "USER",
                userId,
                "User activated",
                "SUCCESS"
        );

        log.info("User activated successfully: {}", userId);
    }

    /**
     * Unlock user account (remove lock).
     *
     * @param userId User ID
     */
    public void unlockUser(String userId) {
        log.info("Unlocking user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setLockedUntil(null);
        user.setFailedLoginAttempts(0);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_UNLOCKED",
                "USER",
                userId,
                "User unlocked",
                "SUCCESS"
        );

        log.info("User unlocked successfully: {}", userId);
    }

    /**
     * Verify user email.
     *
     * @param userId User ID
     */
    public void verifyUserEmail(String userId) {
        log.info("Verifying email for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setEmailVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "EMAIL_VERIFIED",
                "USER",
                userId,
                "Email verified",
                "SUCCESS"
        );

        log.info("Email verified successfully for user: {}", userId);
    }

    /**
     * Enable two-factor authentication for user.
     *
     * @param userId User ID
     */
    public void enableTwoFactorAuth(String userId) {
        log.info("Enabling 2FA for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setTwoFactorEnabled(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "2FA_ENABLED",
                "USER",
                userId,
                "Two-factor authentication enabled",
                "SUCCESS"
        );

        log.info("2FA enabled successfully for user: {}", userId);
    }

    /**
     * Disable two-factor authentication for user.
     *
     * @param userId User ID
     */
    public void disableTwoFactorAuth(String userId) {
        log.info("Disabling 2FA for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setTwoFactorEnabled(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "2FA_DISABLED",
                "USER",
                userId,
                "Two-factor authentication disabled",
                "SUCCESS"
        );

        log.info("2FA disabled successfully for user: {}", userId);
    }

    /**
     * Get user statistics.
     *
     * @return Statistics map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getUserStatistics() {
        log.info("Fetching user statistics");

        List<User> allUsers = userRepository.findAll();
        long totalUsers = allUsers.size();
        long activeUsers = allUsers.stream()
                .filter(u -> u.getStatus() == User.UserStatus.ACTIVE)
                .count();
        long suspendedUsers = allUsers.stream()
                .filter(u -> u.getStatus() == User.UserStatus.SUSPENDED)
                .count();
        long deletedUsers = allUsers.stream()
                .filter(u -> u.getStatus() == User.UserStatus.DELETED)
                .count();
        long emailVerifiedUsers = allUsers.stream()
                .filter(User::getEmailVerified)
                .count();
        long twoFactorEnabledUsers = allUsers.stream()
                .filter(User::getTwoFactorEnabled)
                .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);
        stats.put("suspendedUsers", suspendedUsers);
        stats.put("deletedUsers", deletedUsers);
        stats.put("emailVerifiedUsers", emailVerifiedUsers);
        stats.put("twoFactorEnabledUsers", twoFactorEnabledUsers);

        return stats;
    }

    public void lockUser(String userId) {
        log.info("Locking user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setLockedUntil(LocalDateTime.now().plusMinutes(30));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Log audit
        auditLogService.logAuditEvent(
                userId,
                "USER_LOCKED",
                "USER",
                userId,
                "User account locked",
                "SUCCESS"
        );

        log.info("User locked successfully: {}", userId);
    }
    /**
     * Convert User entity to DTO.
     *
     * @param user User entity
     * @return User DTO
     */
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().name())
                .emailVerified(user.getEmailVerified())
                .twoFactorEnabled(user.getTwoFactorEnabled())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .profile(user.getProfile())
                .entityId(user.getEntityId())
                .userType(user.getUserType())
                .build();
    }

    public AuthorResponse getUserAsAuthor(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return AuthorResponse
                .builder()
                .userType(user.getUserType().name())
                .email(user.getEmail())
                .entityId(user.getEntityId())
                .profile(user.getProfile())
                .id(user.getId())
                .userName(user.getUsername())
                .build();
    }
}
```
