package com.final_project.faculty_service.services;


import com.final_project.faculty_service.DTO.mapper.TeacherMapper;
import com.final_project.faculty_service.DTO.request.TeacherRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.TeacherResponse;
import com.final_project.faculty_service.models.Department;
import com.final_project.faculty_service.models.Teacher;
import com.final_project.faculty_service.repository.DepartmentRepository;
import com.final_project.faculty_service.repository.TeacherRepository;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    public PageResponse<TeacherResponse> findAll(Pageable pageable) {
        Page<Teacher> teacherPage = teacherRepository.findByIsDeletedIsFalse(pageable);
        List<TeacherResponse> studentResponses = teacherPage
                .getContent()
                .stream()
                .map(teacherMapper::toResponse)
                .toList();
        return PageResponse.<TeacherResponse>builder()
                .data(studentResponses)
                .page(teacherPage.getNumber())
                .size(teacherPage.getSize())
                .totalElements(teacherPage.getTotalElements())
                .totalPages(teacherPage.getTotalPages())
                .last(teacherPage.isLast())
                .build();
    }

    public TeacherResponse update(String id , TeacherRequest request){
        Teacher currentRpoItem = teacherRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found:" + id));

        Department department = departmentRepository.findByIdAndIsDeletedIsFalse(request.getDepartment())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found:" + request.getDepartment()));

        Teacher teacher = teacherMapper.toEntity(request);
        teacher.setId(id);
        teacher.setDepartment(department);
        teacher.setCode(currentRpoItem.getCode());
        teacherRepository.save(teacher);
        return teacherMapper.toResponse(teacher);
    }

    public TeacherResponse findById(String id){
        var current = teacherRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found:" + id));

        return teacherMapper.toResponse(current);
    }

    public TeacherResponse create(TeacherRequest request){

        Teacher mappedTeacher = teacherMapper.toEntity(request);

        Department department = departmentRepository.findByIdAndIsDeletedIsFalse(request.getDepartment())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found:" + request.getDepartment()));

        mappedTeacher.setDepartment(department);
        teacherRepository.save(mappedTeacher);
        return teacherMapper.toResponse(mappedTeacher);
    }

    public void delete(String id){
        Teacher curr = teacherRepository.findByIdAndIsDeletedIsFalse(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found:" + id));
        curr.setDeleted(true);
        teacherRepository.save(curr);
    }

}
