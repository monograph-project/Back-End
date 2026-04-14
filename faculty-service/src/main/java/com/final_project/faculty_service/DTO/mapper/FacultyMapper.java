package com.final_project.faculty_service.DTO.mapper;

import com.final_project.faculty_service.DTO.request.FacultyRequest;
import com.final_project.faculty_service.DTO.response.EmployeeResponseInFaculty;
import com.final_project.faculty_service.DTO.response.FacultyResponse;
import com.final_project.faculty_service.DTO.response.UniversityResponseInFaculty;
import com.final_project.faculty_service.models.Employee;
import com.final_project.faculty_service.models.Faculty;
import com.final_project.faculty_service.models.University;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FacultyMapper implements BaseMapper<FacultyRequest, FacultyResponse, Faculty> {

    @Override
    public Faculty toEntity(FacultyRequest faculty) {
        if(faculty == null) {
            return null;
        }
        Faculty currentFaculty = new Faculty();

        University university = new University();
        university.setId(faculty.getUniversity());
        currentFaculty.setUniversity(university);

        Employee em = new  Employee();
        em.setId(faculty.getDeanOfFaculty());
        currentFaculty.setDeanOfFaculty(em);


        currentFaculty.setCode(faculty.getCode());
        currentFaculty.setShortName(faculty.getShortName());

        currentFaculty.setDescription(faculty.getDescription());
        currentFaculty.setName(faculty.getName());
        currentFaculty.setEstablishDate(faculty.getEstablishDate());
        currentFaculty.setEmail(faculty.getEmail());
        currentFaculty.setPhone(faculty.getPhone());
        currentFaculty.setCode(faculty.getCode());
        return currentFaculty;
    }

    @Override
    public FacultyResponse toResponse(Faculty faculty) {
        if (faculty == null){
            return null;
        }
        FacultyResponse facultyResponse = new FacultyResponse();
        if (faculty.getUniversity() != null) {
            facultyResponse.setUniversity(new UniversityResponseInFaculty(
                    faculty.getUniversity().getId(),
                    faculty.getUniversity().getName(),
                    faculty.getUniversity().getEmail(),
                    faculty.getUniversity().getCode(),
                    faculty.getUniversity().getLogo()
            ));
        }
        if (faculty.getDeanOfFaculty() != null) {
            facultyResponse.setDeanOfFaculty(new EmployeeResponseInFaculty(
                    faculty.getDeanOfFaculty().getId(),
                    faculty.getDeanOfFaculty().getFirstName(),
                    faculty.getDeanOfFaculty().getLastName(),
                    faculty.getDeanOfFaculty().getEmail(),
                    faculty.getDeanOfFaculty().getPhone(),
                    faculty.getDeanOfFaculty().getEducationRank(),
                    faculty.getDeanOfFaculty().getFacultyPosition()

            ));
        }
        facultyResponse.setId(faculty.getId());
        facultyResponse.setShortName(faculty.getShortName());
        facultyResponse.setCode(faculty.getCode());
        facultyResponse.setName(faculty.getName());
        facultyResponse.setDescription(faculty.getDescription());
        facultyResponse.setEstablishDate(faculty.getEstablishDate());
        facultyResponse.setCode(faculty.getCode());
        facultyResponse.setShortName(faculty.getShortName());
        facultyResponse.setEmail(faculty.getEmail());
        facultyResponse.setPhone(faculty.getPhone());
        facultyResponse.setUpdateAt(faculty.getUpdatedAt().toString());
        facultyResponse.setCreateAt(faculty.getCreatedAt().toString());
        return facultyResponse;
    }
}
