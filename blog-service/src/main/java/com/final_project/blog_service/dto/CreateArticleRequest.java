package com.final_project.blog_service.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        title = "Create Article Request",
        description = "Request body for creating a new blog article",
        example = "{\"title\": \"My First Blog Post\", \"subtitle\": \"An exciting journey\", \"blocks\": [...], \"category\": \"Technology\", \"tags\": [\"javascript\", \"web-dev\"], \"description\": \"A comprehensive guide\"}"
)
public class CreateArticleRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    @Schema(
            title = "Article Title",
            description = "The main title of the article",
            example = "Getting Started with Spring Boot 3",
            minLength = 3,
            maxLength = 200
    )
    private String title;

    @Size(max = 500, message = "Subtitle must not exceed 500 characters")
    @Schema(
            title = "Article Subtitle",
            description = "A brief subtitle or summary of the article",
            example = "A comprehensive guide to building modern web applications",
            maxLength = 500
    )
    private String subtitle;

    @NotEmpty(message = "Article must have at least one content block")
    @Valid
    @ArraySchema(
            schema = @Schema(implementation = ContentBlockRequest.class),
            minItems = 1,
            arraySchema = @Schema(
                    title = "Content Blocks",
                    description = "Array of content blocks (text, images, videos, code, etc.)"
            )
    )
    private List<ContentBlockRequest> blocks;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    @Schema(
            title = "Article Category",
            description = "The category or topic of the article",
            example = "Technology",
            maxLength = 100
    )
    private String category;

    @Schema(
            title = "Article Tags",
            description = "List of tags for categorization and search",
            example = "[\"javascript\", \"web-development\", \"spring-boot\"]"

    )
    private List<String> tags;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            title = "Article Description",
            description = "SEO meta description for the article",
            example = "Learn how to build modern web applications using Spring Boot 3 and MongoDB",
            maxLength = 500
    )
    private String description;
}
