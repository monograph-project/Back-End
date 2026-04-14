package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.mapper.DepartmentMapper;
import com.final_project.faculty_service.DTO.mapper.FacultyMapper;
import com.final_project.faculty_service.DTO.request.DepartmentRequest;
import com.final_project.faculty_service.DTO.response.DepartmentResponse;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.models.Department;
import com.final_project.faculty_service.models.Employee;
import com.final_project.faculty_service.models.Faculty;
import com.final_project.faculty_service.repository.DepartmentRepository;
import com.final_project.faculty_service.repository.EmployeeRepository;
import com.final_project.faculty_service.repository.FacultyRepository;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final DepartmentMapper departmentMapper;
    private final EmployeeRepository employeeRepository;
    public PageResponse<DepartmentResponse> findAll(Pageable pageable) {
        Page<Department> departmentPage = departmentRepository.findByIsDeletedIsFalse(pageable);
        List<DepartmentResponse> departmentResponses = departmentPage
                .getContent()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
        return PageResponse.<DepartmentResponse>builder()
                .data(departmentResponses)
                .page(departmentPage.getNumber())
                .size(departmentPage.getSize())
                .totalElements(departmentPage.getTotalElements())
                .totalPages(departmentPage.getTotalPages())
                .last(departmentPage.isLast())
                .build();
    }

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest){
        Department department =   departmentMapper.toEntity(departmentRequest);

        Faculty faculty = facultyRepository.findByIdAndIsDeletedIsFalse(department.getFaculty().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Faculty not found"));

        Employee headOf = employeeRepository.findByIdAndIsDeletedIsFalse(department.getHeadOfDepartment().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
        long sequence = sequenceGeneratorService.generateSequence("seq_department");
        department.setCode(faculty.getShortName()+"-"+"FAC"+sequence);
        department.setHeadOfDepartment(headOf);
        department.setFaculty(faculty);
        departmentRepository.save(department);
        return departmentMapper.toResponse(department);
    }

    public DepartmentResponse findById(String id){
        Department currentDepartment = departmentRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found"));
        return departmentMapper.toResponse(currentDepartment);
   }

   public DepartmentResponse updateDepartment(String id, DepartmentRequest departmentRequest){
        Department currentDepartment = departmentRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found"));
        Department mappedDepartment = departmentMapper.toEntity(departmentRequest);


        Faculty faculty = facultyRepository.findByIdAndIsDeletedIsFalse(mappedDepartment.getFaculty().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Faculty not found"));
        Employee dean = employeeRepository.findByIdAndIsDeletedIsFalse(mappedDepartment.getHeadOfDepartment().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
        mappedDepartment.setHeadOfDepartment(dean);
        mappedDepartment.setFaculty(faculty);
        mappedDepartment.setCode(currentDepartment.getCode());
        mappedDepartment.setId(id);

        Department updatedDepartment = departmentRepository.save(mappedDepartment);
        return departmentMapper.toResponse(updatedDepartment);
   }

   public void deleteDepartment(String id){
        var currentDepartment = departmentRepository.findByIdAndIsDeletedIsFalse(id);
        if (currentDepartment.isEmpty()){
            throw new ResourceNotFoundException("Department not found:" + id);
        }
        departmentRepository.deleteById(id);
   }
}
