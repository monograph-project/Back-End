package com.final_project.faculty_service.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AcademicYearGroupResponse {
    private String name;
    private Date startDate;
    private Date endDate;
}
