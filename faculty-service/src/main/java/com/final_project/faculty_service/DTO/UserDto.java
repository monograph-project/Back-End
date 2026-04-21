package com.final_project.faculty_service.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
}


