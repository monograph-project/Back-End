package com.final_project.blog_service.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Article entity representing a blog post
 * Supports flexible content blocks (text, images, videos, code, etc.)
 * Design inspired by Medium and Notion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "articles")
@CompoundIndexes({
        @CompoundIndex(name = "author_published_idx", def = "{'authorId': 1, 'publishedAt': -1}"),
        @CompoundIndex(name = "status_published_idx", def = "{'status': 1, 'publishedAt': -1}"),
        @CompoundIndex(name = "tags_published_idx", def = "{'metadata.tags': 1, 'publishedAt': -1}")
})
public class Article {

    @Id
    private String id;

    @Indexed(unique = true)
    private String slug;  // URL-friendly identifier

    @Indexed
    private String authorId;

    private String title;

    private String subtitle;

    private Content content;

    private Metadata metadata;

    @Builder.Default
    private String status = "DRAFT";  // DRAFT, PUBLISHED, ARCHIVED

    @Builder.Default
    private String visibility = "PUBLIC";  // PUBLIC, PRIVATE, UNLISTED

    private Stats stats;

    private Seo seo;

    @Builder.Default
    private List<EditHistory> editHistory = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;

    private LocalDateTime archivedAt;

    /**
     * Flexible content block structure
     * Supports: text, heading, image, video, code, quote, embed, divider
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {

        @Builder.Default
        private List<ContentBlock> blocks = new ArrayList<>();

        @Builder.Default
        private Integer estimatedReadTime = 1;  // in minutes
    }

    /**
     * Individual content block
     * Data field is polymorphic - structure depends on block type
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContentBlock {
        private String type;  // text, heading, image, video, code, quote, embed, divider
        private JsonNode data;  // flexible JSON structure based on type
    }

    /**
     * Block type definitions for reference:
     *
     * TEXT:
     * { type: "text", data: { text: "Your text here" } }
     *
     * HEADING:
     * { type: "heading", data: { level: 1, text: "Heading" } }
     *
     * IMAGE:
     * { type: "image", data: {
     *     fileId: "file_123",
     *     fileUrl: "https://cdn.../image.jpg",
     *     alt: "alt text",
     *     caption: "caption",
     *     width: 800, height: 600
     *   } }
     *
     * VIDEO:
     * { type: "video", data: {
     *     fileId: "video_123",
     *     videoUrl: "https://cdn.../video.mp4",
     *     thumbnailUrl: "https://cdn.../thumb.jpg",
     *     duration: 120
     *   } }
     *
     * CODE:
     * { type: "code", data: {
     *     language: "javascript",
     *     code: "const x = 1;",
     *     showLineNumbers: true
     *   } }
     *
     * QUOTE:
     * { type: "quote", data: {
     *     text: "Quote text",
     *     attribution: "Author name"
     *   } }
     *
     * EMBED:
     * { type: "embed", data: {
     *     provider: "youtube|twitter|codepen|gist",
     *     embedUrl: "https://...",
     *     title: "Embed title"
     *   } }
     *
     * DIVIDER:
     * { type: "divider", data: {} }
     */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {

        @Builder.Default
        private List<String> tags = new ArrayList<>();

        private String category;

        private String description;  // SEO meta description

        @Builder.Default
        private List<String> keywords = new ArrayList<>();  // SEO keywords

        private String coverImageUrl;  // from file service
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Stats {

        @Builder.Default
        private Long views = 0L;  // page views

        @Builder.Default
        private Long reads = 0L;  // engaged reads (2+ minutes)

        @Builder.Default
        private Long likes = 0L;

        @Builder.Default
        private Long commentCount = 0L;

        @Builder.Default
        private Long shareCount = 0L;

        private LocalDateTime lastEngagedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Seo {
        private String publishedUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditHistory {
        private Integer version;
        private LocalDateTime updatedAt;
        private String editorId;
        private String summary;
    }
}