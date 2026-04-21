package com.final_project.blog_service.repo;

import com.final_project.blog_service.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comment Repository - handles comment persistence
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    // Get all top-level comments for an article
    Page<Comment> findByArticleIdAndParentCommentIdIsNullAndStatusOrderByCreatedAtDesc(
            String articleId,
            String status,
            Pageable pageable
    );

    // Get all replies to a specific comment
    Page<Comment> findByParentCommentIdAndStatusOrderByCreatedAtAsc(
            String parentCommentId,
            String status,
            Pageable pageable
    );

    // Count total comments on an article
    Long countByArticleIdAndStatus(String articleId, String status);

    // Count replies to a comment
    Long countByParentCommentIdAndStatus(String parentCommentId, String status);

    // Get comments by user
    Page<Comment> findByAuthorIdAndStatusOrderByCreatedAtDesc(
            String authorId,
            String status,
            Pageable pageable
    );

    // Find deleted comments for cleanup
    List<Comment> findByStatusAndUpdatedAtBefore(String status, LocalDateTime threshold);

    // Check if comment exists
    boolean existsByIdAndStatus(String id, String status);
}
