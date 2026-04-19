package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {

    Optional<Permission> findByName(String name);

    Optional<Permission> findByPermissionKey(String permissionKey);

    Optional<Permission> findByResourceAndAction(String resource, String action);

    List<Permission> findByIsActive(Boolean isActive);

    List<Permission> findByIsSystemPermissionFalse();
}