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