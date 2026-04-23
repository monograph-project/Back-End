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