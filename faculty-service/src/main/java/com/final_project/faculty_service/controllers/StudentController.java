package com.final_project.faculty_service.controllers;

import com.final_project.faculty_service.DTO.request.StudentRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.StudentResponse;
import com.final_project.faculty_service.services.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
@CrossOrigin
public class StudentController {
    private StudentService studentService;
    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getAll(Pageable pageable){
        return ResponseEntity.ok(studentService.findAll(pageable));
    }
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request){
        return new ResponseEntity<>(studentService.create(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable String  id){
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable String id,@Valid  @RequestBody StudentRequest request){
        return new ResponseEntity<>(studentService.update(id, request), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        studentService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
