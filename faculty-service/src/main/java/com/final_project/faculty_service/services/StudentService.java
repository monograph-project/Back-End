package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.mapper.StudentMapper;
import com.final_project.faculty_service.DTO.request.StudentRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
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
        long seq = sequenceGeneratorService.generateSequence("sequence_student");
        Student student = studentMapper.toEntity(studentRequest);

        String abb = Helper.generateAbbreviation(dep.getFaculty().getName());

        student.setBatch(batch);
        student.setSemester(seme);
        student.setDepartment(dep);
        student.setCode(abb +"-" + batch.getYear() +"-"+seme.getAcademicYear().getName().split("-")[0]+"-"+ seq);
        Student result = studentRepository.save(student);
        return studentMapper.toResponse(result);
    }

    public void delete(String id){
        Student curr = studentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        curr.setDeleted(true);
        studentRepository.save(curr);
    }


}
