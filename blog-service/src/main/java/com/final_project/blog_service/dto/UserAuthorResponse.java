package com.final_project.blog_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAuthorResponse  {
    private String id;
    private String userName;
    private String email;
    private String profile;
    private String entityId;
    private String userType;
}
