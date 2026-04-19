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
}