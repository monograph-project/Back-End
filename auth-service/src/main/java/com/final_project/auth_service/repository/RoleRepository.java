package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);

    Optional<Role> findByRoleKey(String roleKey);

    Optional<Role> findByKeycloakId(String keycloakId);

    List<Role> findByIsActive(Boolean isActive);

    List<Role> findByIsSystemRoleFalse();
}