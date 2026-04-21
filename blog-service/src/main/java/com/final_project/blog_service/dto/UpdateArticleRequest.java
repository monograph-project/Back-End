package com.final_project.blog_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

/**
 * ============= CREATE/UPDATE REQUESTS =============
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Update Article Request",
        description = "Request body for updating an existing blog article"
)
public class UpdateArticleRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    @Schema(
            title = "Article Title",
            example = "Updated Title",
            minLength = 3,
            maxLength = 200
    )
    private String title;

    @Size(max = 500, message = "Subtitle must not exceed 500 characters")
    @Schema(
            title = "Article Subtitle",
            example = "Updated subtitle",
            maxLength = 500
    )
    private String subtitle;

    @NotEmpty(message = "Article must have at least one content block")
    @Valid
    @ArraySchema(
            schema = @Schema(implementation = ContentBlockRequest.class),
            minItems = 1
    )
    private List<ContentBlockRequest> blocks;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Schema(
            title = "Article Category",
            example = "Technology",
            maxLength = 100
    )
    private String category;

    @Schema(
            title = "Article Tags",
            example = "[\"javascript\", \"web-dev\"]"
    )
    private List<String> tags;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            title = "Article Description",
            example = "Updated description",
            maxLength = 500
    )
    private String description;

    @Schema(
            title = "Cover Image URL",
            description = "URL of the cover image from File Service",
            example = "https://cdn.example.com/images/article-cover.jpg",
            format = "url"
    )
    private String coverImageUrl;
}


/**
 * ============= RESPONSE DTOs =============
 */









/**
 * ============= COMMENT DTOs =============
 */






/**
 * ============= ENGAGEMENT DTOs =============
 */




/**
 * ============= PAGINATION RESPONSE =============
 */


/**
 * ============= SEARCH DTOs =============
 */


/**
 * ============= ERROR RESPONSE =============
 */



/**
 * ============= SUCCESS RESPONSE =============
 */
