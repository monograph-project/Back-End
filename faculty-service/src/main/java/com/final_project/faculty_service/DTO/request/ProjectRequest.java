package com.final_project.faculty_service.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 50, message = "Project name must be between 3 and 50 characters")
    private String projectName;
    @NotBlank(message = "group is required")
    private String  group;
    @NotBlank(message = "teacher is required")
    private String  teacher;

    private String projectRepository;
}
