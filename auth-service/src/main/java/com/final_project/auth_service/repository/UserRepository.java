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