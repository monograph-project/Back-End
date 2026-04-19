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