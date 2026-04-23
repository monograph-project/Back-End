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