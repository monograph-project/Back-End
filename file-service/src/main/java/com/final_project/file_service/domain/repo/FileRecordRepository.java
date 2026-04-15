package com.final_project.file_service.domain.repo;

import com.final_project.file_service.domain.model.FileRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRecordRepository extends MongoRepository<FileRecord, String > {
    Optional<FileRecord> findFirstByOwnerIdAndCategory(String ownerId, String category);
}
