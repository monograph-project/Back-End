package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Article Repository - handles article persistence
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    // Find by slug (for URL routing)
    Optional<Article> findBySlug(String slug);

    // Find published articles by author
    Page<Article> findByAuthorIdAndStatusOrderByPublishedAtDesc(
            String authorId,
            String status,
            Pageable pageable
    );

    // Find all published articles (for feed)
    @Query("{ 'status': 'PUBLISHED', 'visibility': 'PUBLIC' }")
    Page<Article> findPublishedArticles(Pageable pageable);

    // Find articles by tags
    Page<Article> findByMetadataTagsInAndStatusAndVisibility(
            List<String> tags,
            String status,
            String visibility,
            Pageable pageable
    );

    // Find articles by category
    Page<Article> findByMetadataCategoryAndStatusAndVisibility(
            String category,
            String status,
            String visibility,
            Pageable pageable
    );

    // Find articles by author and status
    List<Article> findByAuthorIdAndStatus(String authorId, String status);

    // Count articles by author
    Long countByAuthorIdAndStatus(String authorId, String status);

    // Find articles published after a date
    List<Article> findByPublishedAtAfterAndStatusOrderByPublishedAtDesc(
            LocalDateTime publishedAt,
            String status
    );

    // Delete soft-deleted articles (archives older than 30 days)
    @Query("{ 'status': 'ARCHIVED', 'archivedAt': { $lt: ?0 } }")
    void deleteOldArchives(LocalDateTime threshold);
}



