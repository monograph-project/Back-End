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

    @GetMapping("/file/blog/post/{postId}")
    String getPostBlog(@PathVariable("postId") String postId);

    @PostMapping(value = "/file/blog/post/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String postPost(
            @PathVariable("postId") String postId,
            @RequestPart("file") MultipartFile file
    );

    @PostMapping(value = "/file/blog/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileUploadResponse uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("fileType") String fileType,
            @RequestParam(value = "metadata", required = false) String metadata
    );

    @GetMapping("/file/blog/{fileId}")
    FileMetadataResponse getFileMetadata(@PathVariable("fileId") String fileId);

    @DeleteMapping("/file/blog/{fileId}")
    void deleteFile(@PathVariable("fileId") String fileId);

    @GetMapping("/file/blog/{fileId}/url")
    FileCdnUrlResponse getCdnUrl(@PathVariable("fileId") String fileId);
}