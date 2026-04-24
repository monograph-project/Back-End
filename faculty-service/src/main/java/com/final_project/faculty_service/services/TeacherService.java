package com.final_project.faculty_service.services;


import com.final_project.faculty_service.DTO.RoleDTO;
import com.final_project.faculty_service.DTO.UserDto;
import com.final_project.faculty_service.DTO.mapper.TeacherMapper;
import com.final_project.faculty_service.DTO.request.SignupRequest;
import com.final_project.faculty_service.DTO.request.TeacherRequest;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.TeacherResponse;
import com.final_project.faculty_service.models.Department;
import com.final_project.faculty_service.models.Teacher;
import com.final_project.faculty_service.repository.DepartmentRepository;
import com.final_project.faculty_service.repository.TeacherRepository;
import com.final_project.faculty_service.services.exception.ResourceExist;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import com.final_project.faculty_service.services.exception.UserWithEmailExsit;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private AuthService authService;
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

    @Transactional
    public TeacherResponse create(TeacherRequest request){
        boolean isExistByEmail = teacherRepository.existsTeacherByEmailAndIsDeletedIsFalse(request.getEmail())
                ;
        if (isExistByEmail){
            throw new UserWithEmailExsit("All Ready user with this email exsit");
        }
        boolean isExistByPersonalInformation = teacherRepository.existsTeacherByFirstNameAndFatherNameAndLastName(request.getFirstName(), request.getFatherName(), request.getLastName());
        if (isExistByPersonalInformation){
         throw   new ResourceExist("User with this infomratione exist");
        }
        SignupRequest signupRequest = mapToSignupRequest(request);
        UserDto response = authService.createUser(signupRequest);
        if(response.getId().isEmpty()){
            throw new ResourceNotFoundException("User couldn't save ");
        }
        authService.assignRoleToUser(response.getId(), "teacher-user");

        Teacher mappedTeacher = teacherMapper.toEntity(request);

        Department department = departmentRepository.findByIdAndIsDeletedIsFalse(request.getDepartment())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found:" + request.getDepartment()));

        mappedTeacher.setDepartment(department);
        mappedTeacher.setKeycloakId(response.getId());
        Teacher result =  teacherRepository.save(mappedTeacher);
        return teacherMapper.toResponse(result);
    }

    public void delete(String id){
        Teacher curr = teacherRepository.findByIdAndIsDeletedIsFalse(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found:" + id));
        curr.setDeleted(true);
        teacherRepository.save(curr);
    }
    private static SignupRequest mapToSignupRequest(TeacherRequest request){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName(request.getProfileUrl());
        signupRequest.setLastName(request.getLastName());
        signupRequest.setFirstName(request.getFirstName());
        signupRequest.setPrivacyAgreed(true);
        signupRequest.setUsername(request.getUserName());
        signupRequest.setTermsAgreed(true);
        signupRequest.setEmail(request.getEmail());
        signupRequest.setPassword(request.getPassword());
        signupRequest.setPhoneNumber(request.getPhone());
        return signupRequest;
    }


}
