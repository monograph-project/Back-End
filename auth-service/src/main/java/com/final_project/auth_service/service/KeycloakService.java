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