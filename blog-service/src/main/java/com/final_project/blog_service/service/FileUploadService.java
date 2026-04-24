package com.final_project.blog_service.service;
import com.final_project.blog_service.client.FileServiceClient;
import com.final_project.blog_service.dto.FileUploadResponse;
import com.final_project.blog_service.dto.ImageUploadResponse;
import com.final_project.blog_service.dto.VideoUploadResponse;
import com.final_project.blog_service.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileServiceClient fileServiceClient;
    @Value("${app.file-upload.max-image-size:10485760}") // 10MB
    private long maxImageSize;
    @Value("${app.file-upload.max-video-size:536870912}") // 500MB
    private long maxVideoSize;
    private static final String[] ALLOWED_IMAGE_TYPES = {
            "image/jpeg", "image/png", "image/webp", "image/gif"
    };

    private static final String[] ALLOWED_VIDEO_TYPES = {
            "video/mp4", "video/webm", "video/ogg", "video/quicktime"
    };

    /**
     * Upload image file
     */
    @Transactional
    public ImageUploadResponse uploadImage(
            MultipartFile file,
            String alt,
            String caption,
            String userId
    ) {
        log.info("Uploading image for user: {}", userId);

        // Validate file
        validateFile(file, ALLOWED_IMAGE_TYPES, maxImageSize, "Image");

        try {
            // Upload to File Service
            FileUploadResponse response = fileServiceClient.uploadFile(
                    file,
                    "image",
                    null
            );

            log.info("Image uploaded successfully: {}", response.getFileId());

            // Return formatted response
            return ImageUploadResponse.builder()
                    .fileId(response.getFileId())
                    .fileUrl(response.getCdnUrl())
                    .thumbnailUrl(response.getThumbnailUrl())
                    .width(response.getImageWidth())
                    .height(response.getImageHeight())
                    .alt(alt)
                    .caption(caption)
                    .mimeType(response.getMimeType())
                    .fileSize(response.getFileSize())
                    .uploadedAt(response.getUploadedAt())
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload image: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to upload image: " + e.getMessage(), e);
        }
    }

    /**
     * Upload video file
     */
    @Transactional
    public VideoUploadResponse uploadVideo(
            MultipartFile file,
            String title,
            String description,
            String userId
    ) {
        log.info("Uploading video for user: {}", userId);

        // Validate file
        validateFile(file, ALLOWED_VIDEO_TYPES, maxVideoSize, "Video");

        try {
            // Upload to File Service
            FileUploadResponse response = fileServiceClient.uploadFile(
                    file,
                    "video",
                    null
            );

            log.info("Video uploaded successfully: {}", response.getFileId());

            // Return formatted response
            return VideoUploadResponse.builder()
                    .fileId(response.getFileId())
                    .fileUrl(response.getCdnUrl())
                    .thumbnailUrl(response.getThumbnailUrl())
                    .duration(response.getVideoDurationSeconds())
                    .title(title)
                    .description(description)
                    .mimeType(response.getMimeType())
                    .fileSize(response.getFileSize())
                    .processingStatus(response.getProcessingStatus())
                    .uploadedAt(response.getUploadedAt())
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload video: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to upload video: " + e.getMessage(), e);
        }
    }

    /**
     * Delete file
     */
    @Transactional
    public void deleteFile(String fileId, String userId) {
        log.info("Deleting file: {} for user: {}", fileId, userId);

        try {
            fileServiceClient.deleteFile(fileId);
            log.info("File deleted successfully: {}", fileId);
        } catch (Exception e) {
            log.error("Failed to delete file: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    /**
     * Validate file before upload
     */
    private void validateFile(
            MultipartFile file,
            String[] allowedTypes,
            long maxSize,
            String fileTypeName
    ) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException(fileTypeName + " file is required");
        }

        // Check file size
        if (file.getSize() > maxSize) {
            long maxMB = maxSize / 1024 / 1024;
            throw new FileUploadException(
                    fileTypeName + " file is too large. Max size: " + maxMB + "MB"
            );
        }

        // Check MIME type
        String mimeType = file.getContentType();
        if (mimeType == null || !isAllowedMimeType(mimeType, allowedTypes)) {
            throw new FileUploadException(
                    "Invalid " + fileTypeName.toLowerCase() + " format. " +
                            "Supported formats: " + String.join(", ", allowedTypes)
            );
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        if (filename == null || !isValidFilename(filename)) {
            throw new FileUploadException("Invalid filename");
        }
    }

    /**
     * Check if MIME type is allowed
     */
    private boolean isAllowedMimeType(String mimeType, String[] allowedTypes) {
        for (String allowedType : allowedTypes) {
            if (mimeType.equals(allowedType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate filename
     */
    private boolean isValidFilename(String filename) {
        // Check for path traversal
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            return false;
        }

        // Check file extension
        return filename.matches("^[a-zA-Z0-9._-]+\\.(jpg|jpeg|png|gif|webp|mp4|webm|ogg|mov)$");
    }
}
