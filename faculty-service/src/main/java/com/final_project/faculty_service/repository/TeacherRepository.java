package com.final_project.faculty_service.repository;

import com.final_project.faculty_service.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher,String> {
    Page<Teacher> findByIsDeletedIsFalse(Pageable pageable);
    Optional<Teacher> findByIdAndIsDeletedIsFalse(String  id);
}
