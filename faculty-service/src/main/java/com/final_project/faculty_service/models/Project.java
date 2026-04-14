package com.final_project.faculty_service.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Project {
    @Id
    private String id;
    private String projectName;
    @DBRef
    private Group group;
    @DBRef
    private Teacher teacher;
    private String projectRepository;
    private boolean isDeleted;
}
