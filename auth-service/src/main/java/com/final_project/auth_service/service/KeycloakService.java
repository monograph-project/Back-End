package com.final_project.auth_service.service;

import com.final_project.auth_service.config.KeycloakConfig;
import com.final_project.auth_service.exception.KeycloakException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.spi.ResteasyClientProvider;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

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


    public String createUser(String username,
                             String email,
                             String firstName,
                             String lastName,
                             String password,
                             boolean enabled,
                             boolean emailVerified,
                             Map<String, List<String>> attributes) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(enabled);
        user.setEmailVerified(emailVerified);
        user.setAttributes(attributes);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = realm().users().create(user);
        try {

            if (response.getStatus() != 201) {
                throw new KeycloakException("Failed to create user in Keycloak. HTTP " + response.getStatus());
            }

            String location = response.getHeaderString("Location");
            return location.substring(location.lastIndexOf('/') + 1);
        } finally {
            response.close();
        }
    }

    public Optional<UserRepresentation> findUserByUsername(String username) {
        return realm().users().searchByUsername(username, true).stream().findFirst();
    }

    public Optional<UserRepresentation> findUserByEmail(String email) {
        return realm().users().searchByEmail(email, true).stream().findFirst();
    }

    public UserRepresentation getUserById(String userId) {
        try {
            return realm().users().get(userId).toRepresentation();
        } catch (Exception exception) {
            throw new KeycloakException("User not found in Keycloak: " + userId, exception);
        }
    }

    public List<UserRepresentation> getUsers() {
        System.out.println(keycloakConfig.getRealm());
        System.out.println(keycloakConfig.getAdminClientId());
        return realm().users().list();
    }

    public List<UserRepresentation> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return getUsers();
        }
        return realm().users().search(searchTerm.trim());
    }

    public void updateUser(String userId, UserRepresentation userRepresentation) {
        try {
            realm().users().get(userId).update(userRepresentation);
        } catch (Exception exception) {
            throw new KeycloakException("Failed to update user in Keycloak: " + userId, exception);
        }
    }

    public void deleteUser(String userId) {
        try {
            realm().users().delete(userId);
        } catch (Exception exception) {
            throw new KeycloakException("Failed to delete user in Keycloak: " + userId, exception);
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

    public void setUserEnabled(String userId, boolean enabled) {
        UserRepresentation user = getUserById(userId);
        user.setEnabled(enabled);
        updateUser(userId, user);
    }

    public void markEmailVerified(String userId, boolean emailVerified) {
        UserRepresentation user = getUserById(userId);
        user.setEmailVerified(emailVerified);
        updateUser(userId, user);
    }

    public List<String> getUserRealmRoleNames(String userId) {
        return realm().users().get(userId).roles().realmLevel().listAll().stream()
                .map(RoleRepresentation::getName)
                .toList();
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

    public void replaceRealmRoles(String userId, List<String> roleNames) {
        var roleScope = realm().users().get(userId).roles().realmLevel();
        List<RoleRepresentation> existing = roleScope.listAll();
        if (!existing.isEmpty()) {
            roleScope.remove(existing);
        }
        if (roleNames != null && !roleNames.isEmpty()) {
            assignRealmRoles(userId, roleNames);
        }
    }

    public void createRealmRole(String roleName, String description) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription(description);
        realm().roles().create(role);
    }

    public List<RoleRepresentation> getRealmRoles() {
        return realm().roles().list();
    }
    public RoleRepresentation getClientRole(String clientId, String roleName) {
        try {
            return clientResource(clientId)
                    .roles()
                    .get(roleName)
                    .toRepresentation();
        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new KeycloakException(
                    "Client role not found: " + roleName + " in client: " + clientId, e
            );
        } catch (Exception e) {
            throw new KeycloakException(
                    "Failed to get client role: " + roleName, e
            );
        }
    }

    public RoleRepresentation getRealmRole(String roleName) {
        return realm().roles().get(roleName).toRepresentation();
    }

    public void updateRealmRole(String roleName, String newName, String description) {
        RoleRepresentation role = realm().roles().get(roleName).toRepresentation();
        if (newName != null && !newName.isBlank()) {
            role.setName(newName);
        }
        role.setDescription(description);
        realm().roles().get(roleName).update(role);
    }

    public void deleteRealmRole(String roleName) {
        realm().roles().deleteRole(roleName);
    }

    public void createClientRole(String clientId, String roleName, String description) {
        ClientResource clientResource = clientResource(clientId);
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription(description);
        clientResource.roles().create(role);
    }

    public List<RoleRepresentation> getClientRoles(String clientId) {
        return clientResource(clientId).roles().list();
    }

    public void deleteClientRole(String clientId, String roleName) {
        clientResource(clientId).roles().deleteRole(roleName);
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

    private ClientResource clientResource(String clientId) {
        return realm().clients().get(clientUuid(clientId));
    }

    private String clientUuid(String clientId) {
        List<ClientRepresentation> clients = realm().clients().findByClientId(clientId);
        if (clients.isEmpty()) {
            throw new KeycloakException("Keycloak client not found: " + clientId);
        }
        return clients.get(0).getId();
    }
}
