package com.final_project.faculty_service.repository;

import com.final_project.faculty_service.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProjectRepository extends MongoRepository<Project,String> {
    Page<Project> findByIsDeletedIsFalse(Pageable pageable);
    Optional<Project> findByIdAndIsDeletedIsFalse(String  id);
}
