package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.SharedPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Share Request",
        description = "Request to track sharing of an article"
)
public class ShareRequest {

    @NotBlank(message = "Platform is required")
    @Pattern(
            regexp = "^(TWITTER|FACEBOOK|LINKEDIN|COPY_LINK|EMAIL)$",
            message = "Platform must be one of: TWITTER, FACEBOOK, LINKEDIN, COPY_LINK, EMAIL"
    )
    @Schema(
            title = "Platform",
            description = "The platform where the article was shared",
            example = "TWITTER",
            allowableValues = {"TWITTER", "FACEBOOK", "LINKEDIN", "COPY_LINK", "EMAIL"}
    )
    private SharedPlatform platform;

    @Size(max = 500, message = "Custom message must not exceed 500 characters")
    @Schema(
            title = "Custom Message",
            description = "Optional custom message when sharing",
            example = "Check out this amazing article!",
            maxLength = 500
    )
    private String customMessage;
}
