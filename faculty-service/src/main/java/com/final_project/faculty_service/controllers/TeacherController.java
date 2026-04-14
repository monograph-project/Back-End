package com.final_project.faculty_service.controllers;

import com.final_project.faculty_service.DTO.request.TeacherRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.TeacherResponse;
import com.final_project.faculty_service.services.TeacherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    @GetMapping
    public ResponseEntity<PageResponse<TeacherResponse>> findAll(Pageable pageable){
        return new ResponseEntity<>(teacherService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> create(@Valid @RequestBody TeacherRequest request){
        return new ResponseEntity<>(teacherService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> findById(@PathVariable String id){
        return new ResponseEntity<>(teacherService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> update(@PathVariable String id,@Valid @RequestBody TeacherRequest request){
        return new ResponseEntity<>(teacherService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

