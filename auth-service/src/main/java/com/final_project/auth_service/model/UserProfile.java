package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * UserProfile document for extended user information.
 */
@Document(collection = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String profilePictureUrl;

    private String bio;

    private String about;

    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String stateProvince;

    private String postalCode;

    private String country;

    private String company;

    private String jobTitle;

    private String department;

    private String githubUrl;

    private String linkedinUrl;

    private String twitterUrl;

    private String websiteUrl;

    @Builder.Default
    private String languagePreference = "en";

    @Builder.Default
    private String timezone = "UTC";

    @Builder.Default
    private Boolean notificationsEnabled = true;

    @Builder.Default
    private Boolean emailNotifications = true;

    @Builder.Default
    private Boolean pushNotifications = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    @Builder.Default
    private Long version = 0L;
}