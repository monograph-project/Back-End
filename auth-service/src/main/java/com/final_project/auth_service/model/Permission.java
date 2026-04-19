package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Permission document for fine-grained access control.
 */
@Document(collection = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    @Indexed(unique = true)
    private String permissionKey;

    @Indexed
    private String resource;

    private String action;

    @Builder.Default
    private Boolean isSystemPermission = false;

    @Builder.Default
    private Boolean isActive = true;

    /**
     * Store related role IDs instead of JPA relation.
     */
    @Builder.Default
    private Set<String> roleIds = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getFullPermissionKey() {
        return resource + ":" + action;
    }
}