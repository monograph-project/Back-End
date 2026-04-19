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
