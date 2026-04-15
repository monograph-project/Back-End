package com.final_project.file_service.domain.service;

import com.final_project.file_service.domain.model.FileMetadata;
import com.final_project.file_service.domain.ports.BucketStrategy;
import com.final_project.file_service.domain.ports.FileStoragePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class FileService {
    @Qualifier("fileStoragePort")
    private final FileStoragePort storage;
    @Qualifier("bucketStrategy")
    private final BucketStrategy strategy;

    public String upload(FileMetadata metadata, InputStream stream) {
        String bucket = strategy.resolveBucket(metadata);
        String key = strategy.resolveKey(metadata);

        storage.upload(bucket, key, stream, metadata.getContentType());
        return storage.generatePresignedUrl(bucket, key);
    }

    public InputStream download(FileMetadata metadata) {
        return storage.download(
                strategy.resolveBucket(metadata),
                strategy.resolveKey(metadata)
        );
    }

    public void delete(FileMetadata metadata) {
        storage.delete(
                strategy.resolveBucket(metadata),
                strategy.resolveKey(metadata)
        );
    }
}
