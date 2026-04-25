package com.final_project.file_service.api.controller;

import com.final_project.file_service.api.dto.FileCdnUrlResponse;
import com.final_project.file_service.api.dto.FileMetadataResponse;
import com.final_project.file_service.api.dto.FileUploadResponse;
import com.final_project.file_service.domain.model.FileCategory;
import com.final_project.file_service.domain.model.FileMetadata;
import com.final_project.file_service.domain.model.FileRecord;
import com.final_project.file_service.domain.model.OwnerType;
import com.final_project.file_service.domain.ports.FileStoragePort;
import com.final_project.file_service.domain.service.FileRecordService;
import com.final_project.file_service.domain.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/file/blog")
public class BlogController {
    private final FileService fileService;
    private final FileRecordService fileRecordService;

    private final FileStoragePort fileStoragePort;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse uploadBlogFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("ownerId") String ownerId,
            @RequestParam("article") String article
    ) throws IOException {
        FileMetadata fileMetadata = new  FileMetadata(
                file.getOriginalFilename(),
                file.getContentType(),
                ownerId,
                OwnerType.USER,
                FileCategory.BLOG,
                article
        );
        String  url =  fileService.upload(fileMetadata, file.getInputStream());
        return FileUploadResponse
                .builder()
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .cdnUrl(url)
                .originalFilename(file.getOriginalFilename())
                .build();
    }

    @GetMapping("/{fileId}")
    public FileMetadataResponse getFileMetadata(@PathVariable("fileId") String fileId){
        FileRecord file = fileRecordService.findByOwnerIdAndCategory(fileId, FileCategory.BLOG);
        String url =  fileStoragePort.generatePresignedUrl(file.getBucket(), file.getFileName());
        return FileMetadataResponse
                .builder()
                .fileId(file.getId())
                .cdnUrl(url)
                .originalFilename(file.getFileName())
                .build();

    }

    @GetMapping("/{articleId}/user/{userId}")
    public List<String > blogContents (
            @PathVariable("articleId") String articleId,
            @PathVariable("userId") String userId
            ){
        List<FileRecord> files = fileRecordService.findAllByOwnerIdAndSubFolder( userId, articleId);
        return  files.stream().map(f -> fileStoragePort.generatePresignedUrl(f.getBucket(), f.getFileName())).collect(Collectors.toList());
    }
//    @DeleteMapping("/file/blog/{fileId}/article/{articleId}")
//   public  void deleteFile(@PathVariable("fileId") String fileId, @PathVariable String articleId){
//        FileRecord file = fileRecordService.findByOwnerIdAndCategory(fileId, FileCategory.BLOG);
//
//        FileMetadata fileMetadata = new   FileMetadata(
//            file.getFileName(),
//
//        )
//        fileService.delete();
//    }


}
