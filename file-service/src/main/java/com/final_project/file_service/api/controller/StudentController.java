package com.final_project.file_service.api.controller;

import com.final_project.file_service.domain.model.FileCategory;
import com.final_project.file_service.domain.model.FileMetadata;
import com.final_project.file_service.domain.model.OwnerType;
import com.final_project.file_service.domain.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final FileService fileService;

    @PostMapping("/profile")
    public String uploadProfile(@RequestParam MultipartFile file,
                                @RequestParam String studentId) throws IOException {
        FileMetadata fileMetadata = new FileMetadata(
                file.getOriginalFilename(),
                file.getContentType(),
                studentId,
                OwnerType.STUDENT,
                FileCategory.PROFILE,
                null
        );
        return fileService.upload(fileMetadata, file.getInputStream());
    }

    @GetMapping("/profile")
    public String downloadProfile(@RequestParam String studentId) throws IOException {

    }
}
