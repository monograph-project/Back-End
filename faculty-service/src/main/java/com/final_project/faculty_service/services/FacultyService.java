package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.mapper.FacultyMapper;
import com.final_project.faculty_service.DTO.request.FacultyRequest;
import com.final_project.faculty_service.DTO.response.FacultyResponse;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.helper.Helper;
import com.final_project.faculty_service.models.Employee;
import com.final_project.faculty_service.models.Faculty;
import com.final_project.faculty_service.models.FacultyPosition;
import com.final_project.faculty_service.models.University;
import com.final_project.faculty_service.repository.EmployeeRepository;
import com.final_project.faculty_service.repository.FacultyRepository;
import com.final_project.faculty_service.repository.UniversityRepository;
import com.final_project.faculty_service.services.exception.ResourceBadRequest;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;
    private final EmployeeRepository employeeRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final FacultyMapper facultyMapper;
    public PageResponse<FacultyResponse> findAll(Pageable pageable) {
        Page<Faculty> facultyPage = facultyRepository.findByIsDeletedIsFalse(pageable);
        List<FacultyResponse> facs = facultyPage.getContent()
                .stream()
                .filter( curr -> !curr.isDeleted())
                .map(facultyMapper::toResponse)
                .toList();
       return PageResponse.<FacultyResponse>builder()
               .data(facs)
                .page(facultyPage.getNumber())
                .size(facultyPage.getSize())
                .totalPages(facultyPage.getTotalPages())
                .totalElements(facultyPage.getTotalElements())
                .last(facultyPage.isLast())
                .build();
    }

    public FacultyResponse createFaculty(FacultyRequest facultyRequest){
        Faculty faculty =  facultyMapper.toEntity(facultyRequest);

        University university = universityRepository.findByIdAndIsDeletedIsFalse(faculty.getUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("university Not Found"));


        Employee employee = employeeRepository.findByIdAndIsDeletedIsFalse(faculty.getDeanOfFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("employee Not Found"));

//        if (employee.get().getFacultyPosition().equals(FacultyPosition.DEAN)){
//            throw new ResourceBadRequest("The Employee Doesn't have the Required Position");
//        }
        long seq = sequenceGeneratorService.generateSequence("faculty_seq");
        faculty.setUniversity(university);
        faculty.setDeanOfFaculty(employee);

        faculty.setCode(Helper.generateAbbreviation(university.getName())+"-"+university.getShortName()+seq);
        Faculty fac =  facultyRepository.save(faculty);
        return facultyMapper.toResponse(fac);
    }

    public FacultyResponse findById(String id){
        Faculty fac = facultyRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Faculty Id is not found: " +id));
        University un = universityRepository.findByIdAndIsDeletedIsFalse(fac.getUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("University Not found"));

        Employee emp = employeeRepository.findByIdAndIsDeletedIsFalse(fac.getDeanOfFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not found"));
        fac.setDeanOfFaculty(emp);
        fac.setUniversity(un);
        return facultyMapper.toResponse(fac);
    }
    public FacultyResponse updateFaculty(String id, FacultyRequest facultyRequest){
        Faculty savedFaculty =  facultyRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Faculty Id is not found: " +id));

        Faculty faculty = facultyMapper.toEntity(facultyRequest);
        University university =  universityRepository.findByIdAndIsDeletedIsFalse(faculty.getUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("University Not found"));
        Employee empl = employeeRepository.findByIdAndIsDeletedIsFalse(faculty.getDeanOfFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not found"));

        faculty.setId(id);
        faculty.setDeanOfFaculty(empl);
        faculty.setUniversity(university);
        faculty.setCode(savedFaculty.getCode());
        faculty.setShortName(savedFaculty.getShortName());
        var updatedFaculty = facultyRepository.save(faculty);
        return facultyMapper.toResponse(updatedFaculty);
    }
    public void deleteFaculty(String id){
        Faculty currentFac =  facultyRepository.findByIdAndIsDeletedIsFalse(id)
                        .orElseThrow(() -> new ResourceNotFoundException("The Faculty Id is not found: " +id));
        currentFac.setDeleted(true);
        facultyRepository.save(currentFac);
    }
    public PageResponse<FacultyResponse> findAllByUniversity(String id,Pageable pageable){
        University university = universityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("University Not Found"));
        Page<Faculty> facultyPage = facultyRepository.findByUniversityAndIsDeletedIsFalse(university, pageable);
        List<FacultyResponse> facultyResponses =
                facultyPage
                        .stream()
                        .map(facultyMapper::toResponse)
                        .toList();
        return PageResponse.<FacultyResponse> builder()
                .data(facultyResponses)
                .totalElements(facultyPage.getTotalElements())
                .totalPages(facultyPage.getTotalPages())
                .size(facultyPage.getSize())
                .page(facultyPage.getNumber())
                .build();
    }

}
