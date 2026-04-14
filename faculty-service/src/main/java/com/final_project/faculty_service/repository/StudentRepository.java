package com.final_project.faculty_service.repository;

import com.final_project.faculty_service.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {
    Page<Student> findByIsDeletedIsFalse(Pageable pageable);
   Optional< Student> findByIdAndIsDeletedIsFalse(String  id);
    Optional<List<Student>> findAllByIdAndIsDeletedIsFalse(List<String> ids);
}
