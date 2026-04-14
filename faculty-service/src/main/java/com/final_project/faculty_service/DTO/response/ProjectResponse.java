package com.final_project.faculty_service.DTO.response;

import lombok.Data;

@Data
public class ProjectResponse {
    private String id;
    private String projectName;
    private ProjectGroupResponse group;
    private GroupTeacherResponse teacher;
    private String projectRepository;
}
