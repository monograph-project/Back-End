package com.final_project.faculty_service.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupResponse {
    private String id;
    private String name;
    private List<StudentResponseGroupResponse> groupMembers;
    private StudentResponseGroupResponse groupLeader;
    private AcademicYearResponse academicYearResponse;
}
