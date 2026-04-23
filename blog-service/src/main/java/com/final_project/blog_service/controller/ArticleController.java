package com.final_project.blog_service.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.blog_service.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.final_project.blog_service.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Article Controller - REST API for Blog Articles
 *
 * All endpoints are documented with Swagger/OpenAPI annotations for:
 * - Automatic API documentation
 * - Interactive Swagger UI
 * - Request/response validation
 * - Security requirements
 * - Error handling
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@Tags({
        @Tag(
                name = "Articles",
                description = "Article management endpoints - create, read, update, publish articles"
        )
})
public class ArticleController {

    private final ArticleService articleService;
    private final ObjectMapper objectMapper;

    /**
     * Create article with multipart support
     *
     * Flexible endpoint supporting:
     * - Pure JSON article (no files)
     * - Articles with embedded image/video files
     * - Mixed content blocks
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create article (with file upload support)",
            description = """
            Creates a new article with flexible content.
            
            Supports both:
            1. Pure JSON articles (application/json) - for text-only content
            2. Multipart articles with embedded files - for rich media content
            
            When using multipart:
            - Send 'article' as JSON with file references
            - Send image/video files separately
            - Reference files by their multipart field name in JSON
            
            Example flow:
            1. Use file upload endpoints first to get fileIds
            2. Create article JSON referencing those fileIds
            3. Send as multipart with optional additional files
            """,
            tags = {"Articles"},
            operationId = "createArticleWithFiles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - validation failed or malformed JSON"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid"
            ),
            @ApiResponse(
                    responseCode = "413",
                    description = "Payload too large"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> createArticleWithFiles(
            @RequestPart("article") String articleJsonString,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "files", required = false) MultipartFile[] additionalFiles,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        try {
            // Parse article JSON
            CreateArticleWithFilesRequest request = objectMapper.readValue(
                    articleJsonString,
                    CreateArticleWithFilesRequest.class
            );

            // Process files and update file references in content blocks
            request = processAndMapFiles(request, coverImage, additionalFiles, userId);

            // Create article
            ArticleResponse response = articleService.createArticleWithFiles(userId, request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            log.error("Failed to parse article JSON: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid article JSON format", e);
        }
    }

    /**
     * Update article with new files
     */
    @PutMapping(value = "/{articleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update article (with file support)",
            description = "Updates an article and optionally uploads new files",
            tags = {"Articles"},
            operationId = "updateArticleWithFiles"
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> updateArticleWithFiles(
            @PathVariable String articleId,
            @RequestPart("article") String articleJsonString,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "files", required = false) MultipartFile[] additionalFiles,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        try {
            UpdateArticleRequest request = objectMapper.readValue(
                    articleJsonString,
                    UpdateArticleRequest.class
            );

            ArticleResponse response = articleService.updateArticle(articleId, userId, request);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid article JSON format", e);
        }
    }
    /**
     * Alternative: Create article with pure JSON (without files)
     * For backward compatibility and text-only articles
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create article (JSON only)",
            description = "Creates an article using pure JSON. Use file upload endpoints separately for files.",
            tags = {"Articles"},
            operationId = "createArticleJson"
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> createArticleJson(
            @Valid @RequestBody CreateArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.createArticle(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    /**
     * Create a new draft article
     */
    @PostMapping("/{userId}")
    @Operation(
            summary = "Create a new article (draft)",
            description = "Creates a new draft article. Requires authentication (JWT token).",
            tags = {"Articles"},
            operationId = "createArticle"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ArticleResponse> createArticle(
            @Valid @RequestBody CreateArticleRequest request,
            @PathVariable String userId
    ) {

        ArticleResponse response = articleService.createArticle(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get article by ID
     */
    @GetMapping("/{articleId}")
    @Operation(
            summary = "Get article by ID",
            description = "Retrieves a single article by its ID. Public endpoint.",
            tags = {"Articles"},
            operationId = "getArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The unique identifier of the article (MongoDB ObjectId)",
                    example = "6507a1b2c3d4e5f6g7h8i9j0",
                    required = true,
                    in = ParameterIn.PATH,
                    schema = @Schema(type = "string")
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable
            @Parameter(description = "Article ID")
            String articleId
    ) {
        ArticleResponse response = articleService.getArticleById(articleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/slug/{slug}")
    @Operation(
            summary = "Get article by slug",
            description = "Retrieves an article using its URL-friendly slug. Public endpoint.",
            tags = {"Articles"},
            operationId = "getArticleBySlug"
    )
    @Parameters({
            @Parameter(
                    name = "slug",
                    description = "The URL-friendly article identifier (e.g., 'my-first-blog-post')",
                    example = "my-first-blog-post",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<ArticleResponse> getArticleBySlug(
            @PathVariable String slug
    ) {
        ArticleResponse response = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(response);
    }

    /**
     * Update article
     */
    @PutMapping("/{articleId}")
    @Operation(
            summary = "Update article",
            description = "Updates an existing article. Only the author can update. Article can be in DRAFT or PUBLISHED status.",
            tags = {"Articles"},
            operationId = "updateArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    example = "6507a1b2c3d4e5f6g7h8i9j0",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Not the article author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable String articleId,
            @Valid @RequestBody UpdateArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
            ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.updateArticle(articleId, userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Publish article (transition from DRAFT to PUBLISHED)
     */
    @PatchMapping("/{articleId}/publish")
    @Operation(
            summary = "Publish article",
            description = "Publishes a draft article. Transitions status from DRAFT to PUBLISHED. Only author can publish.",
            tags = {"Articles"},
            operationId = "publishArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Article published successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - article not in DRAFT status"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ArticleResponse> publishArticle(
            @PathVariable String articleId,
            @Valid @RequestBody PublishArticleRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        ArticleResponse response = articleService.publishArticle(articleId, userId, request);
        return ResponseEntity.ok(response);
    }


    /**
     * Delete/Archive article
     */
    @DeleteMapping("/{articleId}")
    @Operation(
            summary = "Delete/Archive article",
            description = "Soft deletes (archives) an article. Only the author can delete. Data is retained for GDPR compliance.",
            tags = {"Articles"},
            operationId = "deleteArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Article deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        articleService.deleteArticle(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get published articles (feed)
     */
    @GetMapping
    @Operation(
            summary = "Get published articles (feed)",
            description = "Retrieves a paginated list of all published articles. Perfect for the main feed. Public endpoint.",
            tags = {"Articles"},
            operationId = "getPublishedArticles"
    )
    @Parameters({
            @Parameter(
                    name = "page",
                    description = "Page number (zero-indexed). Default is 0.",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "integer", minimum = "0")
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Number of articles per page. Default is 20, max 100.",
                    example = "20",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "integer", minimum = "1", maximum = "100")
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    public ResponseEntity<PaginatedResponse<ArticlePreviewResponse>> getPublishedArticles(
            @RequestParam(defaultValue = "0")
            @Parameter(description = "Page number")
            int page,

            @RequestParam(defaultValue = "20")
            @Parameter(description = "Page size")
            int pageSize
    ) {
        PaginatedResponse<ArticlePreviewResponse> response = articleService.getPublishedArticles(page, pageSize);
        return ResponseEntity.ok(response);
    }

    /**
     * Get author's articles
     */
    @GetMapping("/author/{authorId}")
    @Operation(
            summary = "Get author's articles",
            description = "Retrieves all published articles by a specific author. Public endpoint.",
            tags = {"Articles"},
            operationId = "getAuthorArticles"
    )
    @Parameters({
            @Parameter(
                    name = "authorId",
                    description = "The author's user ID",
                    example = "user_123",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "page",
                    description = "Page number (zero-indexed)",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Number of articles per page",
                    example = "20",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author not found"
            )
    })
    public ResponseEntity<PaginatedResponse<ArticlePreviewResponse>> getAuthorArticles(
            @PathVariable String authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {

        PaginatedResponse<ArticlePreviewResponse> response = articleService.getUserArticles(authorId, page, pageSize);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/author/{authorId}/{articleId}")
    @Operation(
            summary = "Get author's specific  article",
            description = "Retrieves a specific published article by a specific author and article id. Public endpoint.",
            tags = {"Articles"},
            operationId = "getAuthorArticles"
    )
    @Parameters({
            @Parameter(
                    name = "authorId",
                    description = "The author's user ID",
                    example = "user_123",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "articleId",
                    description = "the article Id",
                    example = "0",
                    required = false,
                    in = ParameterIn.PATH
            )

    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author not found"
            )
    })
    public ResponseEntity<ArticleResponse> getArticleAuthor(
            @PathVariable String authorId,
            @PathVariable String articleId
    ){
        ArticleResponse response = articleService.getArticleByAuthorAndId(authorId, articleId);
        return ResponseEntity.ok(response);
    }



    /**
     * Post a comment on an article
     */
    @PostMapping("/{articleId}/comments")
    @Operation(
            summary = "Post a comment",
            description = "Posts a top-level comment on an article. Requires authentication.",
            tags = {"Comments"},
            operationId = "postComment"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to comment on",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment posted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT missing"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    public ResponseEntity<CommentResponse> postComment(
            @PathVariable String articleId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        CommentResponse response = articleService.postComment(articleId, userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get comments on an article (threaded)
     */
    @GetMapping("/{articleId}/comments")
    @Operation(
            summary = "Get article comments (threaded)",
            description = "Retrieves all comments on an article in a threaded structure. Public endpoint.",
            tags = {"Comments"},
            operationId = "getArticleComments"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "page",
                    description = "Page number",
                    example = "0",
                    required = false,
                    in = ParameterIn.QUERY
            ),
            @Parameter(
                    name = "pageSize",
                    description = "Comments per page",
                    example = "10",
                    required = false,
                    in = ParameterIn.QUERY
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    public ResponseEntity<PaginatedResponse<CommentThreadResponse>> getArticleComments(
            @PathVariable String articleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PaginatedResponse<CommentThreadResponse> response = articleService.getArticleComments(articleId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    /**
     * Reply to a comment
     */
    @PostMapping("/{articleId}/comments/{parentCommentId}/reply")
    @Operation(
            summary = "Reply to a comment",
            description = "Posts a reply to an existing comment. Creates a threaded discussion.",
            tags = {"Comments"},
            operationId = "replyToComment"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article ID",
                    required = true,
                    in = ParameterIn.PATH
            ),
            @Parameter(
                    name = "parentCommentId",
                    description = "The comment ID to reply to",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Reply posted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article or comment not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable String articleId,
            @PathVariable String parentCommentId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        CommentResponse response = articleService.replyToComment(articleId, parentCommentId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Delete a comment
     */
    @DeleteMapping("/comments/{commentId}")
    @Operation(
            summary = "Delete a comment",
            description = "Deletes (soft delete) a comment. Only the author can delete.",
            tags = {"Comments"},
            operationId = "deleteComment"
    )
    @Parameters({
            @Parameter(
                    name = "commentId",
                    description = "The comment ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Comment deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - not the author"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String commentId,
            @AuthenticationPrincipal Jwt jwt
    ) {


        String userId = jwt.getSubject();
        articleService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
    /**
     * Like an article
     */
    @PostMapping("/{articleId}/like")
    @Operation(
            summary = "Like an article",
            description = "Adds a like to an article. Each user can like each article only once.",
            tags = {"Engagement"},
            operationId = "likeArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to like",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Article liked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LikeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Article already liked by this user"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<LikeResponse> likeArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        LikeResponse response = articleService.likeArticle(articleId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Unlike an article
     */
    @DeleteMapping("/{articleId}/like")
    @Operation(
            summary = "Unlike an article",
            description = "Removes a like from an article.",
            tags = {"Engagement"},
            operationId = "unlikeArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to unlike",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Article unliked successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> unlikeArticle(
            @PathVariable String articleId,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String userId = jwt.getSubject();
        articleService.unlikeArticle(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Share an article
     */
    @PostMapping("/{articleId}/share")
    @Operation(
            summary = "Share an article",
            description = "Tracks the sharing of an article on various platforms (Twitter, Facebook, LinkedIn, email, etc.)",
            tags = {"Engagement"},
            operationId = "shareArticle"
    )
    @Parameters({
            @Parameter(
                    name = "articleId",
                    description = "The article to share",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Share tracked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShareResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Article not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ShareResponse> shareArticle(
            @PathVariable String articleId,
            @Valid @RequestBody ShareRequest request,
            @AuthenticationPrincipal  Jwt jwt
    ) {
        String userId = jwt.getSubject();

        ShareResponse response = articleService.shareArticle(articleId, userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    private CreateArticleWithFilesRequest processAndMapFiles(
            CreateArticleWithFilesRequest request,
            MultipartFile coverImage,
            MultipartFile[] additionalFiles,
            String userId
    ) {
        // Process cover image if provided
        if (coverImage != null && !coverImage.isEmpty()) {
            // Upload cover image would be done here
            // This is handled by separate file upload endpoints
            log.debug("Cover image provided but should be uploaded separately");
        }

        // Additional files are processed by their references in content blocks
        // The content blocks should reference files by fileId from prior uploads

        return request;
    }
}
