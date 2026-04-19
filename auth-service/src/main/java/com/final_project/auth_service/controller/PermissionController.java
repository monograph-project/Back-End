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
