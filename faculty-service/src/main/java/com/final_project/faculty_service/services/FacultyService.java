package com.final_project.faculty_service.services;

import com.final_project.faculty_service.DTO.mapper.FacultyMapper;
import com.final_project.faculty_service.DTO.request.FacultyRequest;
import com.final_project.faculty_service.DTO.response.DepartmentResponse;
import com.final_project.faculty_service.DTO.response.FacultyResponse;
import com.final_project.faculty_service.DTO.response.PageResponse;
import com.final_project.faculty_service.helper.Helper;
import com.final_project.faculty_service.models.*;
import com.final_project.faculty_service.repository.EmployeeRepository;
import com.final_project.faculty_service.repository.FacultyRepository;
import com.final_project.faculty_service.repository.UniversityRepository;
import com.final_project.faculty_service.services.exception.ResourceBadRequest;
import com.final_project.faculty_service.services.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;
    private final EmployeeRepository employeeRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final FacultyMapper facultyMapper;
    private final WebClient fileWebClient;

    public FacultyService(FacultyRepository facultyRepository, UniversityRepository universityRepository, EmployeeRepository employeeRepository, SequenceGeneratorService sequenceGeneratorService, FacultyMapper facultyMapper,  @Qualifier("fileServiceClient") WebClient fileWebClient) {
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
        this.employeeRepository = employeeRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.facultyMapper = facultyMapper;
        this.fileWebClient = fileWebClient;
    }

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

    public FacultyResponse updateLogo(String de, MultipartFile logo){
        Faculty st = facultyRepository.findByIdAndIsDeletedIsFalse(de)
                .orElseThrow(() -> new ResourceNotFoundException("University Not Found with "+de));
        MultipartBodyBuilder bodyBuilder = new  MultipartBodyBuilder();
        bodyBuilder.part("file", logo.getResource());
        String updatedLogo =  fileWebClient.post()
                .uri("/file/faculty/logo/{id}", st.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        st.setLogo(updatedLogo);
        facultyRepository.save(st);
        return facultyMapper.toResponse(st);
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
