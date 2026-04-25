package com.final_project.file_service.domain.service;

import com.final_project.file_service.domain.model.FileCategory;
import com.final_project.file_service.domain.model.FileRecord;
import com.final_project.file_service.domain.repo.FileRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileRecordService {
    private final FileRecordRepository fileRecordRepository;
    public void add(FileRecord fileRecord) {
        fileRecordRepository.save(fileRecord);
    }

    public void remove(FileRecord fileRecord) {
        fileRecordRepository.delete(fileRecord);
    }
    public FileRecord findByOwnerIdAndCategory(String ownerId, FileCategory category) {
        FileRecord file =  fileRecordRepository.findFirstByOwnerIdAndCategory(ownerId, category)
                .orElseThrow(() -> new FileNotFound("File Not Found"));
        return file;
    }
    public FileRecord updateFileName(String ownerId, FileCategory category, String fileName) {
        FileRecord file = fileRecordRepository.findFirstByOwnerIdAndCategory(ownerId, category)
                .orElseThrow(() -> new FileNotFound("File Not Found"));
        file.setFileName(fileName);
        fileRecordRepository.save(file);
        return file;
    }

    public List<FileRecord> findAllByOwnerIdAndSubFolder(String ownerId, String subFolder){

        return fileRecordRepository.findAllByOwnerIdAndSubFolder(ownerId, subFolder)
                .orElseThrow(() -> new FileNotFound("File Not Found"));
    }

    public FileRecord findOneByOwnerIdAndSubFolder(String ownerId, String subFolder){
        return fileRecordRepository.findFirstByOwnerIdAndSubFolder(ownerId, subFolder)
                .orElseThrow(() -> new FileNotFound("File Not Found"));
    }
}
