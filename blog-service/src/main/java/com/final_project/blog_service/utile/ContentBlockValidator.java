package com.final_project.blog_service.utile;

import com.fasterxml.jackson.databind.JsonNode;
import com.final_project.blog_service.dto.ContentBlockRequest;

import java.util.List;

public class ContentBlockValidator {

    private static final List<String> VALID_BLOCK_TYPES = List.of(
            "text", "heading", "image", "video", "code", "quote", "embed", "divider"
    );

    public static boolean isValidBlockType(String type) {
        return VALID_BLOCK_TYPES.contains(type);
    }

    public static void validateBlock(ContentBlockRequest block) {
        if (!isValidBlockType(block.getType())) {
            throw new IllegalArgumentException("Invalid block type: " + block.getType());
        }

        JsonNode data = block.getData();

        switch (block.getType()) {
            case "text":
                if (!data.has("text") || data.get("text").asText().isEmpty()) {
                    throw new IllegalArgumentException("Text block must have non-empty 'text' field");
                }
                break;

            case "heading":
                if (!data.has("level") || !data.has("text")) {
                    throw new IllegalArgumentException("Heading block must have 'level' and 'text' fields");
                }
                int level = data.get("level").asInt();
                if (level < 1 || level > 6) {
                    throw new IllegalArgumentException("Heading level must be 1-6");
                }
                break;

            case "image":
                if (!data.has("fileUrl")) {
                    throw new IllegalArgumentException("Image block must have 'fileUrl'");
                }
                break;

            case "code":
                if (!data.has("code")) {
                    throw new IllegalArgumentException("Code block must have 'code' field");
                }
                break;

            case "quote":
                if (!data.has("text")) {
                    throw new IllegalArgumentException("Quote block must have 'text' field");
                }
                break;

            case "embed":
                if (!data.has("provider") || !data.has("embedUrl")) {
                    throw new IllegalArgumentException("Embed block must have 'provider' and 'embedUrl'");
                }
                break;
        }
    }
}
