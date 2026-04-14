package com.final_project.faculty_service.DTO.request;

import com.final_project.faculty_service.models.SemesterType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class SemesterRequest {
    @NotBlank(message = "Academic year is required")
    private String academicYear;

    @NotNull(message = "Semester type is required")
    private SemesterType type;

    @NotBlank(message = "Semester name is required")
    private String name;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private Date startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private Date endDate;
}
