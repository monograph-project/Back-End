package com.final_project.blog_service.service;
import com.final_project.blog_service.exception.ResourceNotFoundException;
import com.final_project.blog_service.exception.UnauthorizedException;
import com.final_project.blog_service.utile.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.final_project.blog_service.dto.*;
import  com.final_project.blog_service.model.*;
import com.final_project.blog_service.repo.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Article Service - Core business logic for article management
 * Handles: create, update, publish, delete articles and comments
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ShareRepository shareRepository;
    private final ReadingHistoryRepository readingHistoryRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String ARTICLE_CACHE_KEY = "article:";
    private static final String ARTICLE_STATS_CACHE_KEY = "article:stats:";
    private static final int CACHE_TTL_MINUTES = 5;

    // ============= ARTICLE MANAGEMENT =============

    /**
     * Create a new draft article
     */
    @Transactional
    public ArticleResponse createArticle(String authorId, CreateArticleRequest request) {
        log.info("Creating article for author: {}", authorId);

        Article article = Article.builder()
                .authorId(authorId)
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .slug(generateUniqueSlug(request.getTitle()))
                .status("DRAFT")
                .visibility("PUBLIC")
                .content(Article.Content.builder()
                        .blocks(mapContentBlocks(request.getBlocks()))
                        .estimatedReadTime(calculateReadTime(request.getBlocks()))
                        .build()
                )
                .metadata(Article.Metadata.builder()
                        .tags(request.getTags())
                        .category(request.getCategory())
                        .description(request.getDescription())
                        .build()
                )
                .stats(Article.Stats.builder()
                        .views(0L)
                        .reads(0L)
                        .likes(0L)
                        .commentCount(0L)
                        .shareCount(0L)
                        .build()
                )
                .editHistory(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Article saved = articleRepository.save(article);
        log.info("Article created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    /**
     * Update article (draft or published)
     */
    @Transactional
    public ArticleResponse updateArticle(String articleId, String authorId, UpdateArticleRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        log.info("Updating article: {}", articleId);

        article.setTitle(request.getTitle());
        article.setSubtitle(request.getSubtitle());
        article.getContent().setBlocks(mapContentBlocks(request.getBlocks()));
        article.getContent().setEstimatedReadTime(calculateReadTime(request.getBlocks()));

        article.getMetadata().setTags(request.getTags());
        article.getMetadata().setCategory(request.getCategory());
        article.getMetadata().setDescription(request.getDescription());
        article.getMetadata().setCoverImageUrl(request.getCoverImageUrl());

        article.setUpdatedAt(LocalDateTime.now());

        // Track edit history
        addEditHistory(article, authorId, "Updated content");

        Article updated = articleRepository.save(article);
        invalidateCache(articleId);

        return mapToResponse(updated);
    }

    /**
     * Publish an article (transition from DRAFT to PUBLISHED)
     */
    @Transactional
    public ArticleResponse publishArticle(String articleId, String authorId, PublishArticleRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        if (!"DRAFT".equals(article.getStatus())) {
            throw new IllegalStateException("Only draft articles can be published");
        }

        log.info("Publishing article: {}", articleId);

        article.setStatus("PUBLISHED");
        article.setVisibility(request.getVisibility());
        article.setPublishedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        addEditHistory(article, authorId, "Published article");

        Article published = articleRepository.save(article);
        invalidateCache(articleId);

        // TODO: Trigger publish event for notifications
        // publishEvent(new ArticlePublishedEvent(published));

        return mapToResponse(published);
    }

    /**
     * Get article by ID (with cache)
     */
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(String articleId) {
        String cacheKey = ARTICLE_CACHE_KEY + articleId;

        // Try cache first
        String cachedArticle = redisTemplate.opsForValue().get(cacheKey);
        if (cachedArticle != null) {
            log.debug("Cache hit for article: {}", articleId);
            // Would deserialize here in production
        }

        Article article = getArticleByIdOrThrow(articleId);

        // Cache the result
        redisTemplate.opsForValue().set(cacheKey, article.getId(), CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        // Increment views asynchronously
        incrementViewsAsync(articleId);

        return mapToResponse(article);
    }

    /**
     * Get article by slug (for URL routing)
     */
    @Transactional(readOnly = true)
    public ArticleResponse getArticleBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found: " + slug));

        return mapToResponse(article);
    }

    /**
     * Get user's articles with pagination
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<ArticlePreviewResponse> getUserArticles(String authorId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Article> articles = articleRepository.findByAuthorIdAndStatusOrderByPublishedAtDesc(
                authorId,
                "PUBLISHED",
                pageable
        );

        return mapToPaginatedResponse(articles, page, pageSize);
    }

    /**
     * Get published articles feed (paginated)
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<ArticlePreviewResponse> getPublishedArticles(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Article> articles = articleRepository.findPublishedArticles(pageable);

        return mapToPaginatedResponse(articles, page, pageSize);
    }

    /**
     * Soft delete article (archive)
     */
    @Transactional
    public void deleteArticle(String articleId, String authorId) {
        Article article = getArticleByIdOrThrow(articleId);
        validateAuthor(article, authorId);

        log.info("Archiving article: {}", articleId);

        article.setStatus("ARCHIVED");
        article.setArchivedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);
        invalidateCache(articleId);
    }

    // ============= COMMENT MANAGEMENT =============

    /**
     * Post a comment on an article
     */
    @Transactional
    public CommentResponse postComment(String articleId, String authorId, CreateCommentRequest request) {
        Article article = getArticleByIdOrThrow(articleId);

        Comment comment = Comment.builder()
                .articleId(articleId)
                .parentCommentId(null)  // Top-level comment
                .authorId(authorId)
                .body(request.getBody())
                .status("PUBLISHED")
                .engagement(Comment.Engagement.builder()
                        .likes(0L)
                        .replyCount(0L)
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);

        // Update article comment count
        article.getStats().setCommentCount(article.getStats().getCommentCount() + 1);
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Comment posted on article: {}", articleId);

        return mapCommentToResponse(saved);
    }

    /**
     * Reply to a comment
     */
    @Transactional
    public CommentResponse replyToComment(String articleId, String parentCommentId,
                                          String authorId, CreateCommentRequest request) {
        Article article = getArticleByIdOrThrow(articleId);
        Comment parentComment = getCommentByIdOrThrow(parentCommentId);

        Comment reply = Comment.builder()
                .articleId(articleId)
                .parentCommentId(parentCommentId)
                .authorId(authorId)
                .body(request.getBody())
                .status("PUBLISHED")
                .engagement(Comment.Engagement.builder()
                        .likes(0L)
                        .replyCount(0L)
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(reply);

        // Update parent comment reply count
        parentComment.getEngagement().setReplyCount(parentComment.getEngagement().getReplyCount() + 1);
        commentRepository.save(parentComment);

        // Update article comment count
        article.getStats().setCommentCount(article.getStats().getCommentCount() + 1);
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Reply posted on comment: {}", parentCommentId);

        return mapCommentToResponse(saved);
    }

    /**
     * Get comments for an article (threaded)
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<CommentThreadResponse> getArticleComments(String articleId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Comment> comments = commentRepository.findByArticleIdAndParentCommentIdIsNullAndStatusOrderByCreatedAtDesc(
                articleId,
                "PUBLISHED",
                pageable
        );

        List<CommentThreadResponse> threads = comments.getContent().stream()
                .map(this::buildCommentThread)
                .toList();

        return PaginatedResponse.<CommentThreadResponse>builder()
                .data(threads)
                .pagination(PaginatedResponse.PaginationMetadata.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .totalCount(comments.getTotalElements())
                        .totalPages(comments.getTotalPages())
                        .hasNext(comments.hasNext())
                        .hasPrevious(comments.hasPrevious())
                        .build()
                )
                .build();
    }

    /**
     * Delete a comment (soft delete)
     */
    @Transactional
    public void deleteComment(String commentId, String authorId) {
        Comment comment = getCommentByIdOrThrow(commentId);

        if (!comment.getAuthorId().equals(authorId)) {
            throw new UnauthorizedException("Not authorized to delete this comment");
        }

        log.info("Deleting comment: {}", commentId);

        comment.setStatus("DELETED");
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    // ============= ENGAGEMENT MANAGEMENT =============

    /**
     * Like an article
     */
    @Transactional
    public LikeResponse likeArticle(String articleId, String userId) {
        Article article = getArticleByIdOrThrow(articleId);

        // Check if already liked
        Optional<Like> existingLike = likeRepository.findByUserIdAndArticleId(userId, articleId);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("Article already liked");
        }

        Like like = Like.builder()
                .userId(userId)
                .articleId(articleId)
                .createdAt(LocalDateTime.now())
                .build();

        Like saved = likeRepository.save(like);

        // Update article like count
        article.getStats().setLikes(article.getStats().getLikes() + 1);
        article.getStats().setLastEngagedAt(LocalDateTime.now());
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article liked: {}", articleId);

        return mapLikeToResponse(saved);
    }

    /**
     * Unlike an article
     */
    @Transactional
    public void unlikeArticle(String articleId, String userId) {
        Article article = getArticleByIdOrThrow(articleId);

        likeRepository.deleteByUserIdAndArticleId(userId, articleId);

        // Update article like count
        article.getStats().setLikes(Math.max(0, article.getStats().getLikes() - 1));
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article unliked: {}", articleId);
    }

    /**
     * Track share
     */
    @Transactional
    public ShareResponse shareArticle(String articleId, String userId, ShareRequest request) {
        Article article = getArticleByIdOrThrow(articleId);

        Share share = Share.builder()
                .userId(userId)
                .articleId(articleId)
                .platform(request.getPlatform())
                .metadata(Share.Metadata.builder()
                        .customMessage(request.getCustomMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        Share saved = shareRepository.save(share);

        // Update article share count
        article.getStats().setShareCount(article.getStats().getShareCount() + 1);
        article.getStats().setLastEngagedAt(LocalDateTime.now());
        articleRepository.save(article);
        invalidateCache(articleId);

        log.info("Article shared: {} on {}", articleId, request.getPlatform());

        return mapShareToResponse(saved);
    }

    // ============= HELPER METHODS =============

    private String generateUniqueSlug(String title) {
        String baseSlug = SlugUtil.generateSlug(title);
        String slug = baseSlug;
        int counter = 1;

        while (articleRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + counter++;
        }

        return slug;
    }

    private Integer calculateReadTime(List<ContentBlockRequest> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            return 1;
        }

        int wordCount = blocks.stream()
                .mapToInt(block -> {
                    if ("text".equals(block.getType()) && block.getData().has("text")) {
                        return block.getData().get("text").asText().split("\\s+").length;
                    }
                    return 0;
                })
                .sum();

        // Average reading speed: 200 words per minute
        return Math.max(1, (wordCount + 199) / 200);
    }

    private List<Article.ContentBlock> mapContentBlocks(List<ContentBlockRequest> requests) {
        if (requests == null) {
            return new ArrayList<>();
        }

        return requests.stream()
                .map(req -> Article.ContentBlock.builder()
                        .type(req.getType())
                        .data(req.getData())
                        .build()
                )
                .toList();
    }

    private void addEditHistory(Article article, String editorId, String summary) {
        Article.EditHistory history = new Article.EditHistory();
        history.setVersion((int) (articleRepository.countByAuthorIdAndStatus(article.getAuthorId(), article.getStatus()) + 1));
        history.setUpdatedAt(LocalDateTime.now());
        history.setEditorId(editorId);
        history.setSummary(summary);

        article.getEditHistory().add(history);
    }

    private void incrementViewsAsync(String articleId) {
        // TODO: Implement async increment (use event bus/message queue)
        log.debug("Incrementing views for article: {}", articleId);
    }

    private void validateAuthor(Article article, String userId) {
        if (!article.getAuthorId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to modify this article");
        }
    }

    private void invalidateCache(String articleId) {
        redisTemplate.delete(ARTICLE_CACHE_KEY + articleId);
        redisTemplate.delete(ARTICLE_STATS_CACHE_KEY + articleId);
    }

    private CommentThreadResponse buildCommentThread(Comment comment) {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Comment> replies = commentRepository.findByParentCommentIdAndStatusOrderByCreatedAtAsc(
                comment.getId(),
                "PUBLISHED",
                pageable
        );

        List<CommentThreadResponse> replyThreads = replies.getContent().stream()
                .map(this::buildCommentThread)
                .toList();

        return CommentThreadResponse.builder()
                .comment(mapCommentToResponse(comment))
                .replies(replyThreads)
                .build();
    }

    // ============= MAPPING METHODS =============

    private Article getArticleByIdOrThrow(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found: " + id));
    }

    private Comment getCommentByIdOrThrow(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
    }

    private ArticleResponse mapToResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .content(ContentResponse.builder()
                        .blocks(article.getContent().getBlocks().stream()
                                .map(b -> ContentBlockResponse.builder()
                                        .type(b.getType())
                                        .data(b.getData())
                                        .build()
                                )
                                .toList()
                        )
                        .estimatedReadTime(article.getContent().getEstimatedReadTime())
                        .build()
                )
                .metadata(MetadataResponse.builder()
                        .tags(article.getMetadata().getTags())
                        .category(article.getMetadata().getCategory())
                        .description(article.getMetadata().getDescription())
                        .coverImageUrl(article.getMetadata().getCoverImageUrl())
                        .build()
                )
                .status(article.getStatus())
                .visibility(article.getVisibility())
                .stats(StatsResponse.builder()
                        .views(article.getStats().getViews())
                        .reads(article.getStats().getReads())
                        .likes(article.getStats().getLikes())
                        .commentCount(article.getStats().getCommentCount())
                        .shareCount(article.getStats().getShareCount())
                        .lastEngagedAt(article.getStats().getLastEngagedAt())
                        .build()
                )
                .publishedAt(article.getPublishedAt())
                .updatedAt(article.getUpdatedAt())
                .createdAt(article.getCreatedAt())
                .estimatedReadTime(article.getContent().getEstimatedReadTime())
                .build();
    }

    private PaginatedResponse<ArticlePreviewResponse> mapToPaginatedResponse(Page<Article> page, int pageNum, int pageSize) {
        List<ArticlePreviewResponse> previews = page.getContent().stream()
                .map(this::mapToPreview)
                .toList();

        return PaginatedResponse.<ArticlePreviewResponse>builder()
                .data(previews)
                .pagination(PaginatedResponse.PaginationMetadata.builder()
                        .page(pageNum)
                        .pageSize(pageSize)
                        .totalCount(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNext(page.hasNext())
                        .hasPrevious(page.hasPrevious())
                        .build()
                )
                .build();
    }

    private ArticlePreviewResponse mapToPreview(Article article) {
        return ArticlePreviewResponse.builder()
                .id(article.getId())
                .slug(article.getSlug())
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .coverImageUrl(article.getMetadata().getCoverImageUrl())
                .description(article.getMetadata().getDescription())
                .stats(StatsResponse.builder()
                        .views(article.getStats().getViews())
                        .reads(article.getStats().getReads())
                        .likes(article.getStats().getLikes())
                        .commentCount(article.getStats().getCommentCount())
                        .shareCount(article.getStats().getShareCount())
                        .build()
                )
                .publishedAt(article.getPublishedAt())
                .estimatedReadTime(article.getContent().getEstimatedReadTime())
                .tags(article.getMetadata().getTags())
                .build();
    }

    private CommentResponse mapCommentToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .articleId(comment.getArticleId())
                .parentCommentId(comment.getParentCommentId())
                .body(comment.getBody())
                .engagement(CommentEngagementResponse.builder()
                        .likes(comment.getEngagement().getLikes())
                        .replyCount(comment.getEngagement().getReplyCount())
                        .build()
                )
                .createdAt(comment.getCreatedAt())
                .editedAt(comment.getEditedAt())
                .build();
    }

    private LikeResponse mapLikeToResponse(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .userId(like.getUserId())
                .articleId(like.getArticleId())
                .createdAt(like.getCreatedAt())
                .build();
    }

    private ShareResponse mapShareToResponse(Share share) {
        return ShareResponse.builder()
                .id(share.getId())
                .userId(share.getUserId())
                .articleId(share.getArticleId())
                .platform(share.getPlatform())
                .createdAt(share.getCreatedAt())
                .build();
    }
}