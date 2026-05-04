package com.final_project.faculty_service.controllers;

import com.final_project.faculty_service.DTO.request.ProjectRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.ProjectResponse;
import com.final_project.faculty_service.services.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;
    @GetMapping
    public ResponseEntity<PageResponse<ProjectResponse>> findAll(Pageable pageable){
        return new ResponseEntity<>(projectService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request){
        return new ResponseEntity<>(projectService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> findById(@PathVariable String id){
        return new ResponseEntity<>(projectService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/student/{student}")
    public ResponseEntity<ProjectResponse> getProjectByStudentId(@PathVariable String id, @PathVariable String student){
        return new ResponseEntity<>(projectService.getProjectByStudentId(id, student), HttpStatus.OK);
    }

    @GetMapping("/{id}/teacher/{teacher}")
    public ResponseEntity<ProjectResponse> getProjectByTeacherId(@PathVariable String id, @PathVariable String teacher){
        return new ResponseEntity<>(projectService.getProjectByTeacherId(id, teacher), HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByStudentId(@PathVariable String id){
        return new ResponseEntity<>(projectService.getProjectsByStudentId(id), HttpStatus.OK);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByTeacherId(@PathVariable String id){
        return new ResponseEntity<>(projectService.getProjectsByTeacherId(id), HttpStatus.OK);
    }

    @GetMapping("/teacher/{id}/student/{student}")
    public ResponseEntity<ProjectResponse> findProjectByteacherAndStudent(@PathVariable String id, @PathVariable String student){
        return new ResponseEntity<>(projectService.findProjectByteacherAndStudent(id, student), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable String id,@Valid @RequestBody ProjectRequest request){
        return new ResponseEntity<>(projectService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
