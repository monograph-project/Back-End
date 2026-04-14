package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VicRepositoryRepository extends MongoRepository<VicRepositoryDocument, String> {

    Optional<VicRepositoryDocument> findByOwnerIgnoreCaseAndNameIgnoreCase(String owner, String name);

    boolean existsByOwnerIgnoreCaseAndNameIgnoreCase(String owner, String name);
}
