package com.final_project.faculty_service.DTO.response;

import com.final_project.faculty_service.DTO.RepositoryDTO;
import lombok.Data;

@Data
public class ProjectResponse {
    private String id;
    private String projectName;
    private GroupResponse group;
    private GroupTeacherResponse teacher;
    private RepositoryDTO projectRepository;
}
