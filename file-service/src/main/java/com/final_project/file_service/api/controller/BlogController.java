package com.final_project.file_service.api.controller;

import com.final_project.file_service.domain.model.FileCategory;
import com.final_project.file_service.domain.model.FileMetadata;
import com.final_project.file_service.domain.model.FileRecord;
import com.final_project.file_service.domain.model.OwnerType;
import com.final_project.file_service.domain.ports.FileStoragePort;
import com.final_project.file_service.domain.service.FileRecordService;
import com.final_project.file_service.domain.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/file/blog")
public class BlogController {
    private final FileService fileService;
    private final FileRecordService fileRecordService;

    private final FileStoragePort fileStoragePort;
    @PostMapping("/post/{postId}")
    public String upload(@RequestParam MultipartFile file, String postId) throws IOException {
        FileMetadata fileMetadata = new  FileMetadata(
                file.getOriginalFilename(),
                file.getContentType(),
                postId,
                OwnerType.USER,
                FileCategory.BLOG,
                null

        );
        return fileService.upload(fileMetadata, file.getInputStream());
    }

    @GetMapping("/post/{postId}")
    public String getBlogPost(@PathVariable String postId) throws IOException{
        FileRecord file = fileRecordService.findByOwnerIdAndCategory(postId, FileCategory.BLOG);
        return fileStoragePort.generatePresignedUrl(file.getBucket(), file.getFileName());
    }


}
