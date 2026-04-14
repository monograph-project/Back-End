package com.final_project.faculty_service.DTO.request;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class BatchRequest {


    @NotBlank(message = "Batch name is required")
    @Size(max = 100, message = "Batch name must be at most 100 characters")
    private String name;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Batch type is required")
    private String type;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private Date startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be today or in the future")
    private Date endDate;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    private Boolean isActive = true;

    @NotBlank(message = "Academic year is required")
    private String academicYear;
}
