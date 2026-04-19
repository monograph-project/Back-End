package com.final_project.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to verify email with token")
public class EmailVerificationRequest {

    @NotBlank(message = "Verification token is required")
    @JsonProperty("verification_token")
    @Schema(description = "Email verification token from email link")
    private String verificationToken;
}
