package com.final_project.faculty_service.controllers;

import com.final_project.faculty_service.DTO.request.GroupRequest;
import com.final_project.faculty_service.DTO.response.GroupResponse;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.services.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<PageResponse<GroupResponse>> findAll(Pageable pageable){
        return new ResponseEntity<>(groupService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest request){
        return new ResponseEntity<>(groupService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findById(@PathVariable String id){
        return new ResponseEntity<>(groupService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> update(@PathVariable String id, @Valid @RequestBody GroupRequest request){
        return new ResponseEntity<>(groupService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
