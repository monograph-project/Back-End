package com.final_project.blog_service.dto.request;

import com.final_project.blog_service.model.ArticleVisiblity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @NotBlank
    @Size(max = 180)
    @Schema(example = "How to use microservices in university systems")
    private String title;

    @Size(max = 500)
    @Schema(example = "A practical article about Spring Boot microservices.")
    private String description;

    @Builder.Default
    @Valid
    @Schema(description = "Flexible content blocks: text, image, video, code, quote, embed, divider")
    private List<ArticleBlockRequest> blocks = new ArrayList<>();

    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Builder.Default
    private ArticleVisiblity visibility = ArticleVisiblity.PUBLIC;

    @Schema(description = "Optional cover image file id from file-service")
    private String coverImageFileId;

    @Schema(description = "Optional cover image URL returned by file-service")
    private String coverImageUrl;

}
