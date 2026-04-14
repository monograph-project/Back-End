package com.final_project.faculty_service.repository;

import com.final_project.faculty_service.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
    Page<Employee> findByIsDeletedIsFalse(Pageable pageable);
    Optional<Employee> findByIdAndIsDeletedIsFalse(String  id);
}
