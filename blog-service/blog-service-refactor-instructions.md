# Blog Service Refactor Instructions

This file explains exactly which changes should be applied to the current `blog-service` based on the uploaded `blog-service.md` and `file-service.md`.

The goal is to keep your OpenAPI docs, keep the working file-service style, remove duplicated DTO patterns in blog-service, and make article creation flexible enough for text, image/photo, video, code, quote, embed, and divider content.

---

## 1. What I found

Your blog-service already has useful pieces:

- `ArticleController`
- `FileUploadController`
- `FileServiceClient`
- `FileUploadService`
- `ArticleService`
- `UserCacheService`
- `OpenApiConfig`
- Redis cache
- MongoDB article storage
- flexible content-related DTOs

But the DTO layer has too many overlapping content block classes:

```text
ContentBlockDTO
ContentBlockRequest
ContentBlockResponse
FlexibleContentBlockRequest
TextBlockDTO
ImageBlockDTO
VideoBlockDTO
CodeBlockDTO
QuoteBlockDTO
EmbedBlockDTO
DividerBlockDTO
HeadingBlockDTO
```

Replace those with one flexible content model.

---

## 2. Target article model

Each article should have:

```text
title
description
authorId
slug
tags
visibility
status
coverImageFileId
coverImageUrl
blocks[]
```

Each block should be:

```json
{
  "type": "TEXT",
  "order": 0,
  "data": {
    "text": "Hello"
  }
}
```

Allowed block types:

```text
TEXT
HEADING
IMAGE
VIDEO
CODE
QUOTE
EMBED
DIVIDER
```

---

## 3. Apply changes in this order

1. Add `ArticleBlockType.java`
2. Add `ArticleBlockRequest.java`
3. Add `ArticleBlockResponse.java`
4. Replace `CreateArticleRequest.java`
5. Replace `UpdateArticleRequest.java`
6. Normalize `Article.ContentBlock`
7. Replace `ContentBlockValidator.java`
8. Update `ReadTimeCalculator.java`
9. Update `FileServiceClient.java`
10. Update `FileUploadService.java`
11. Update `ArticleService.java`
12. Update `ArticleController.java`
13. Remove old duplicated DTO imports
14. Run `mvn clean install`

---

## 4. Add `ArticleBlockType.java`

Path:

```text
src/main/java/com/final_project/blog_service/model/ArticleBlockType.java
```

```java
package com.final_project.blog_service.model;

public enum ArticleBlockType {
    TEXT,
    HEADING,
    IMAGE,
    VIDEO,
    CODE,
    QUOTE,
    EMBED,
    DIVIDER
}
```

---

## 5. Add `ArticleBlockRequest.java`

Path:

```text
src/main/java/com/final_project/blog_service/dto/ArticleBlockRequest.java
```

```java
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleBlockType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Flexible article content block")
public class ArticleBlockRequest {

    @NotNull
    @Schema(
            description = "Block type",
            example = "TEXT",
            allowableValues = {"TEXT", "HEADING", "IMAGE", "VIDEO", "CODE", "QUOTE", "EMBED", "DIVIDER"}
    )
    private ArticleBlockType type;

    @PositiveOrZero
    @Schema(description = "Block order in article", example = "0")
    private Integer order;

    @NotNull
    @Schema(description = "Flexible block payload. Required fields depend on block type.")
    private Map<String, Object> data;
}
```

---

## 6. Add `ArticleBlockResponse.java`

Path:

```text
src/main/java/com/final_project/blog_service/dto/ArticleBlockResponse.java
```

```java
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleBlockType;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleBlockResponse {
    private ArticleBlockType type;
    private Integer order;
    private Map<String, Object> data;
}
```

---

## 7. Replace `CreateArticleRequest.java`

Path:

```text
src/main/java/com/final_project/blog_service/dto/CreateArticleRequest.java
```

```java
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleVisiblity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create flexible article request")
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
```

---

## 8. Replace `UpdateArticleRequest.java`

Path:

```text
src/main/java/com/final_project/blog_service/dto/UpdateArticleRequest.java
```

```java
package com.final_project.blog_service.dto;

import com.final_project.blog_service.model.ArticleVisiblity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Update article request")
public class UpdateArticleRequest {

    @Size(max = 180)
    private String title;

    @Size(max = 500)
    private String description;

    @Valid
    private List<ArticleBlockRequest> blocks;

    private List<String> tags;

    private ArticleVisiblity visibility;

    private String coverImageFileId;

    private String coverImageUrl;
}
```

---

## 9. Normalize `Article.java`

In your `Article` model, keep all existing fields that already work, but normalize content blocks to this style:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public static class ContentBlock {
    private ArticleBlockType type;
    private Integer order;
    private Map<String, Object> data;
}
```

The article should store:

```java
@Builder.Default
private List<ContentBlock> blocks = new ArrayList<>();
```

Use `fileId` as the permanent link to file-service. Keep `url` or `cdnUrl` as display/cache data only.

---

## 10. Replace `ContentBlockValidator.java`

Path:

```text
src/main/java/com/final_project/blog_service/utile/ContentBlockValidator.java
```

```java
package com.final_project.blog_service.utile;

import com.final_project.blog_service.dto.ArticleBlockRequest;
import com.final_project.blog_service.model.ArticleBlockType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ContentBlockValidator {

    public void validate(List<ArticleBlockRequest> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            throw new IllegalArgumentException("Article must contain at least one content block");
        }

        for (ArticleBlockRequest block : blocks) {
            validateBlock(block);
        }
    }

    private void validateBlock(ArticleBlockRequest block) {
        if (block.getType() == null) {
            throw new IllegalArgumentException("Block type is required");
        }

        if (block.getData() == null) {
            throw new IllegalArgumentException("Block data is required");
        }

        Map<String, Object> data = block.getData();
        ArticleBlockType type = block.getType();

        switch (type) {
            case TEXT -> requireText(data, "text", "Text block requires text");
            case HEADING -> {
                requireText(data, "text", "Heading block requires text");
                Object level = data.get("level");
                if (!(level instanceof Number number) || number.intValue() < 1 || number.intValue() > 6) {
                    throw new IllegalArgumentException("Heading level must be between 1 and 6");
                }
            }
            case IMAGE -> {
                requireText(data, "fileId", "Image block requires fileId");
                requireText(data, "url", "Image block requires url");
                requireText(data, "alt", "Image block requires alt text");
            }
            case VIDEO -> {
                requireText(data, "fileId", "Video block requires fileId");
                requireText(data, "url", "Video block requires url");
            }
            case CODE -> {
                requireText(data, "code", "Code block requires code");
                requireText(data, "language", "Code block requires language");
            }
            case QUOTE -> requireText(data, "text", "Quote block requires text");
            case EMBED -> {
                requireText(data, "provider", "Embed block requires provider");
                requireText(data, "url", "Embed block requires url");
            }
            case DIVIDER -> {
                // no required fields
            }
        }
    }

    private void requireText(Map<String, Object> data, String key, String message) {
        Object value = data.get(key);
        if (!(value instanceof String text) || text.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
```

---

## 11. Update `ReadTimeCalculator.java`

```java
public int calculateFromBlocks(List<ArticleBlockRequest> blocks) {
    if (blocks == null || blocks.isEmpty()) {
        return 1;
    }

    int words = 0;

    for (ArticleBlockRequest block : blocks) {
        if (block.getData() == null) continue;

        Object text = block.getData().get("text");
        if (text instanceof String value && !value.isBlank()) {
            words += value.trim().split("\s+").length;
        }

        Object code = block.getData().get("code");
        if (code instanceof String value && !value.isBlank()) {
            words += value.trim().split("\s+").length / 2;
        }
    }

    return Math.max(1, (int) Math.ceil(words / 200.0));
}
```

---

## 12. Update file-service communication

Keep `FileServiceClient`, but normalize it.

Path:

```text
src/main/java/com/final_project/blog_service/client/FileServiceClient.java
```

```java
package com.final_project.blog_service.client;

import com.final_project.blog_service.dto.FileCdnUrlResponse;
import com.final_project.blog_service.dto.FileMetadataResponse;
import com.final_project.blog_service.dto.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "file-service",
        url = "${app.service.file-url}"
)
public interface FileServiceClient {

    @PostMapping(value = "/file/blog/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileUploadResponse uploadBlogFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("ownerId") String ownerId,
            @RequestParam("subFolder") String subFolder,
            @RequestParam("category") String category
    );

    @GetMapping("/file/blog/{fileId}")
    FileMetadataResponse getFileMetadata(@PathVariable("fileId") String fileId);

    @GetMapping("/file/blog/{fileId}/url")
    FileCdnUrlResponse getCdnUrl(@PathVariable("fileId") String fileId);

    @DeleteMapping("/file/blog/{fileId}")
    void deleteFile(@PathVariable("fileId") String fileId);
}
```

If your real file-service endpoint uses different parameter names, keep the same method names but adjust only the Feign mapping.

---

## 13. Replace `FileUploadService.java`

Path:

```text
src/main/java/com/final_project/blog_service/service/FileUploadService.java
```

```java
package com.final_project.blog_service.service;

import com.final_project.blog_service.client.FileServiceClient;
import com.final_project.blog_service.dto.FileUploadResponse;
import com.final_project.blog_service.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileServiceClient fileServiceClient;

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    private static final long MAX_VIDEO_SIZE = 500 * 1024 * 1024;

    private static final Set<String> IMAGE_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    private static final Set<String> VIDEO_TYPES = Set.of(
            "video/mp4", "video/webm", "video/ogg", "video/quicktime"
    );

    public FileUploadResponse uploadArticleImage(MultipartFile file, String authorId, String articleId) {
        validate(file, IMAGE_TYPES, MAX_IMAGE_SIZE, "image");
        return upload(file, authorId, articleId, "image");
    }

    public FileUploadResponse uploadArticleVideo(MultipartFile file, String authorId, String articleId) {
        validate(file, VIDEO_TYPES, MAX_VIDEO_SIZE, "video");
        return upload(file, authorId, articleId, "video");
    }

    private FileUploadResponse upload(MultipartFile file, String authorId, String articleId, String subFolder) {
        try {
            return fileServiceClient.uploadBlogFile(
                    file,
                    authorId,
                    articleId != null ? articleId : "drafts",
                    subFolder
            );
        } catch (Exception ex) {
            log.error("File-service upload failed", ex);
            throw new FileUploadException("Failed to upload " + subFolder + " file");
        }
    }

    public void deleteFile(String fileId) {
        try {
            fileServiceClient.deleteFile(fileId);
        } catch (Exception ex) {
            log.warn("Failed to delete file {} from file-service", fileId, ex);
        }
    }

    private void validate(MultipartFile file, Set<String> allowedTypes, long maxSize, String label) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException(label + " file is required");
        }

        if (file.getSize() > maxSize) {
            throw new FileUploadException(label + " file is too large");
        }

        if (file.getContentType() == null || !allowedTypes.contains(file.getContentType())) {
            throw new FileUploadException("Unsupported " + label + " type: " + file.getContentType());
        }

        String filename = file.getOriginalFilename();
        if (filename == null || filename.contains("..") || filename.contains("/") || filename.contains("\")) {
            throw new FileUploadException("Invalid filename");
        }
    }
}
```

---

## 14. Add multipart article creation endpoint

In `ArticleController`, keep your existing JSON endpoint and add this endpoint.

```java
@PostMapping(value = "/articles/with-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@Operation(
        summary = "Create article with files",
        description = "Creates a flexible article and uploads cover/inline files through file-service"
)
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<ArticleResponse> createArticleWithFiles(
        @RequestPart("title") String title,
        @RequestPart(value = "description", required = false) String description,
        @RequestPart("blocks") String blocksJson,
        @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
        @RequestPart(value = "inlineFiles", required = false) List<MultipartFile> inlineFiles,
        @AuthenticationPrincipal Jwt jwt
) {
    ArticleResponse response = articleService.createArticleWithFiles(
            title,
            description,
            blocksJson,
            coverImage,
            inlineFiles,
            jwt.getSubject()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

Required imports:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
```

---

## 15. Update `ArticleService.createArticle(...)`

Use this flow:

```java
@Transactional
public ArticleResponse createArticle(CreateArticleRequest request, String authorId) {
    contentBlockValidator.validate(request.getBlocks());

    validateFileReferences(request.getBlocks());

    Article article = Article.builder()
            .authorId(authorId)
            .title(request.getTitle())
            .slug(slugUtil.generateSlug(request.getTitle()))
            .description(request.getDescription())
            .blocks(mapBlocks(request.getBlocks()))
            .tags(request.getTags() != null ? request.getTags() : List.of())
            .coverImageFileId(request.getCoverImageFileId())
            .coverImageUrl(request.getCoverImageUrl())
            .visibility(request.getVisibility())
            .status(ArticleStatus.DRAFT)
            .readTimeMinutes(readTimeCalculator.calculateFromBlocks(request.getBlocks()))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Article saved = articleRepository.save(article);
    return mapToResponse(saved);
}
```

Helpers:

```java
private List<Article.ContentBlock> mapBlocks(List<ArticleBlockRequest> requests) {
    return requests.stream()
            .map(block -> Article.ContentBlock.builder()
                    .type(block.getType())
                    .order(block.getOrder())
                    .data(block.getData())
                    .build())
            .toList();
}

private void validateFileReferences(List<ArticleBlockRequest> blocks) {
    for (ArticleBlockRequest block : blocks) {
        if (block.getType() == ArticleBlockType.IMAGE || block.getType() == ArticleBlockType.VIDEO) {
            Object fileId = block.getData().get("fileId");
            if (fileId == null || fileId.toString().isBlank()) {
                throw new IllegalArgumentException(block.getType() + " block requires fileId");
            }
            fileServiceClient.getFileMetadata(fileId.toString());
        }
    }
}
```

---

## 16. Add `ArticleService.createArticleWithFiles(...)`

```java
@Transactional
public ArticleResponse createArticleWithFiles(
        String title,
        String description,
        String blocksJson,
        MultipartFile coverImage,
        List<MultipartFile> inlineFiles,
        String authorId
) {
    try {
        List<ArticleBlockRequest> blocks = objectMapper.readValue(
                blocksJson,
                new com.fasterxml.jackson.core.type.TypeReference<List<ArticleBlockRequest>>() {}
        );

        String coverFileId = null;
        String coverUrl = null;

        if (coverImage != null && !coverImage.isEmpty()) {
            FileUploadResponse cover = fileUploadService.uploadArticleImage(coverImage, authorId, "drafts");
            coverFileId = cover.getFileId();
            coverUrl = cover.getCdnUrl();
        }

        if (inlineFiles != null && !inlineFiles.isEmpty()) {
            for (ArticleBlockRequest block : blocks) {
                if ((block.getType() == ArticleBlockType.IMAGE || block.getType() == ArticleBlockType.VIDEO)
                        && block.getData().containsKey("uploadIndex")) {

                    int index = ((Number) block.getData().get("uploadIndex")).intValue();
                    MultipartFile file = inlineFiles.get(index);

                    FileUploadResponse uploaded = block.getType() == ArticleBlockType.IMAGE
                            ? fileUploadService.uploadArticleImage(file, authorId, "drafts")
                            : fileUploadService.uploadArticleVideo(file, authorId, "drafts");

                    block.getData().put("fileId", uploaded.getFileId());
                    block.getData().put("url", uploaded.getCdnUrl());
                    block.getData().remove("uploadIndex");
                }
            }
        }

        CreateArticleRequest request = CreateArticleRequest.builder()
                .title(title)
                .description(description)
                .blocks(blocks)
                .coverImageFileId(coverFileId)
                .coverImageUrl(coverUrl)
                .build();

        return createArticle(request, authorId);

    } catch (Exception ex) {
        throw new IllegalArgumentException("Invalid multipart article request: " + ex.getMessage(), ex);
    }
}
```

Add these fields to `ArticleService` if missing:

```java
private final ObjectMapper objectMapper;
private final FileUploadService fileUploadService;
private final FileServiceClient fileServiceClient;
private final ContentBlockValidator contentBlockValidator;
private final ReadTimeCalculator readTimeCalculator;
```

---

## 17. Update response mapping

Your `ArticleResponse` should return blocks as:

```java
private List<ArticleBlockResponse> blocks;
```

Mapping helper:

```java
private List<ArticleBlockResponse> mapBlocksToResponse(List<Article.ContentBlock> blocks) {
    if (blocks == null) {
        return List.of();
    }

    return blocks.stream()
            .map(block -> ArticleBlockResponse.builder()
                    .type(block.getType())
                    .order(block.getOrder())
                    .data(block.getData())
                    .build())
            .toList();
}
```

---

## 18. Keep OpenAPI docs

Do not delete `OpenApiConfig.java`.

Only update text examples if needed.

Keep the bearer token security scheme:

```java
.addSecuritySchemes("bearerAuth",
        new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
)
```

---

## 19. Fix Keycloak config

Your uploaded blog `.env` uses:

```text
KEYCLOAK_SERVER_URL=localhost:8444
KEYCLOAK_REALM=fina_project
```

Change to:

```text
KEYCLOAK_SERVER_URL=http://localhost:8444
KEYCLOAK_REALM=final-project
```

In `application.yaml`, keep only:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KEYCLOAK_SERVER_URL}/realms/${KEYCLOAK_REALM}
```

Do not configure both `issuer-uri` and `jwk-set-uri`.

---

## 20. Example JSON-only create article request

```json
{
  "title": "University System Architecture",
  "description": "An article about service-based university platforms.",
  "tags": ["spring", "microservices", "university"],
  "visibility": "PUBLIC",
  "blocks": [
    {
      "type": "HEADING",
      "order": 0,
      "data": {
        "text": "Introduction",
        "level": 1
      }
    },
    {
      "type": "TEXT",
      "order": 1,
      "data": {
        "text": "This article explains how our services communicate."
      }
    },
    {
      "type": "IMAGE",
      "order": 2,
      "data": {
        "fileId": "existing-file-id",
        "url": "http://localhost:9000/blogs/image.png",
        "alt": "Architecture diagram",
        "caption": "Service architecture"
      }
    }
  ]
}
```

---

## 21. Example multipart create article request

```text
POST /api/v1/articles/with-files
Content-Type: multipart/form-data
Authorization: Bearer <token>
```

Parts:

```text
title = Campus News
description = New campus facilities
blocks = [
  {
    "type": "TEXT",
    "order": 0,
    "data": {"text": "Today we opened a new building."}
  },
  {
    "type": "IMAGE",
    "order": 1,
    "data": {"uploadIndex": 0, "alt": "New building", "caption": "Opening day"}
  }
]
inlineFiles[0] = image.png
coverImage = cover.png
```

The service uploads files to file-service, replaces `uploadIndex` with `fileId` and `url`, then saves the article.

---

## 22. Final target structure

```text
dto/
  ArticleBlockRequest.java
  ArticleBlockResponse.java
  ArticlePreviewResponse.java
  ArticleResponse.java
  AuthorResponse.java
  CreateArticleRequest.java
  CreateCommentRequest.java
  ErrorResponse.java
  FileCdnUrlResponse.java
  FileMetadataResponse.java
  FileUploadResponse.java
  PaginatedResponse.java
  PublishArticleRequest.java
  SearchArticleRequest.java
  SuccessResponse.java
  UpdateArticleRequest.java
  UserAuthorResponse.java
  UserExistsResponse.java
  UserPreferencesResponse.java
  UserProfileResponse.java
```

Remove old block-specific DTOs after all imports are replaced.

---

## 23. Final rules

- Blog-service stores article structure.
- File-service stores files.
- Article blocks reference files by `fileId`.
- URLs are display/cache values, not source of truth.
- Author id must come from JWT subject, not request body.
- Keep OpenAPI docs.
- Use one flexible block DTO instead of many duplicated block DTOs.
