package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.RoleDTO;
import com.final_project.faculty_service.DTO.UserDto;
import com.final_project.faculty_service.DTO.mapper.StudentMapper;
import com.final_project.faculty_service.DTO.request.SignupRequest;
import com.final_project.faculty_service.DTO.request.StudentRequest;
import com.final_project.faculty_service.DTO.response.AuthResponse;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.DTO.response.StudentResponse;
import com.final_project.faculty_service.helper.Helper;
import com.final_project.faculty_service.models.Batch;
import com.final_project.faculty_service.models.Department;
import com.final_project.faculty_service.models.Semester;
import com.final_project.faculty_service.models.Student;
import com.final_project.faculty_service.repository.BatchRepository;
import com.final_project.faculty_service.repository.DepartmentRepository;
import com.final_project.faculty_service.repository.SemesterRepository;
import com.final_project.faculty_service.repository.StudentRepository;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StudentService {
    private final AuthService authService;
    private final StudentRepository studentRepository;
    private final SemesterRepository   semesterRepository;
    private final DepartmentRepository departmentRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final BatchRepository batchRepository;
    private final StudentMapper studentMapper;
    public PageResponse<StudentResponse> findAll(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findByIsDeletedIsFalse(pageable);
        List<StudentResponse> studentResponses = studentPage
                .getContent()
                .stream()
                .map(studentMapper::toResponse)
                .toList();
        return PageResponse.<StudentResponse>builder()
                .data(studentResponses)
                .page(studentPage.getNumber())
                .size(studentPage.getSize())
                .totalElements(studentPage.getTotalElements())
                .totalPages(studentPage.getTotalPages())
                .last(studentPage.isLast())
                .build();
    }

    public StudentResponse update(String id , StudentRequest studentRequest){
        Student currentStudent = studentRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Student mappedStudent = studentMapper.toEntity(studentRequest);
        Department currentDepartment = departmentRepository.findByIdAndIsDeletedIsFalse(mappedStudent.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Semester semester = semesterRepository.findByIdAndIsDeletedIsFalse(mappedStudent.getSemester().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
        mappedStudent.setDepartment(currentDepartment);
        mappedStudent.setId(id);
        mappedStudent.setSemester(semester);
        mappedStudent.setBatch(currentStudent.getBatch());
        mappedStudent.setCode(currentStudent.getCode());
        studentRepository.save(mappedStudent);
        return studentMapper.toResponse(mappedStudent);
    }

    public StudentResponse findById(String id){
        Student current = studentRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return studentMapper.toResponse(current);
    }

    public StudentResponse create(StudentRequest studentRequest){
        Semester seme = semesterRepository.findByIdAndIsDeletedIsFalse(studentRequest.getSemester())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
        Department dep =  departmentRepository.findByIdAndIsDeletedIsFalse(studentRequest.getDepartment())
                .orElseThrow(() ->  new ResourceNotFoundException("Department not found"));
        Batch batch = batchRepository.findByIdAndIsDeletedIsFalse(studentRequest.getBatch())
                .orElseThrow(() ->  new ResourceNotFoundException("Batch not found"));
        SignupRequest signupRequest = getSignupRequest(studentRequest);

        long seq = sequenceGeneratorService.generateSequence("sequence_student");
        Student student = studentMapper.toEntity(studentRequest);

        String abb = Helper.generateAbbreviation(dep.getFaculty().getName());

        student.setBatch(batch);
        student.setSemester(seme);
        student.setDepartment(dep);
        student.setCode(abb +"-" + batch.getYear() +"-"+seme.getAcademicYear().getName().split("-")[0]+"-"+ seq);

        Student result = studentRepository.save(student);
        signupRequest.setEntityId(result.getId());
        UserDto response = authService.createUser(signupRequest);
        if(response.getId().isEmpty()){
            throw new ResourceNotFoundException("User couldn't save ");
        }
        RoleDTO role = authService.getRole(studentRequest.getRole());
        if (role.getId().isEmpty()){
            throw new ResourceNotFoundException("Not Found Role");
        }
        authService.assignRoleToUser(response.getId(), role.getId());


        return studentMapper.toResponse(result);
    }

    private static SignupRequest getSignupRequest(StudentRequest studentRequest) {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setProfile(studentRequest.getProfilePicture());
        signupRequest.setFirstName(studentRequest.getFirstName());
        signupRequest.setLastName(studentRequest.getLastName());
        signupRequest.setPhoneNumber(studentRequest.getPhone());
        signupRequest.setEmail(studentRequest.getEmail());
        signupRequest.setPassword(studentRequest.getPassword());
        signupRequest.setUsername(studentRequest.getUsername());
        signupRequest.setPrivacyAgreed(true);
        signupRequest.setTermsAgreed(true);

        signupRequest.setUserType("STUDENT");
        return signupRequest;
    }

    public void delete(String id){
        Student curr = studentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        curr.setDeleted(true);
        studentRepository.save(curr);
    }


}
