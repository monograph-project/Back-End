package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.SessionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<SessionDocument, org.bson.types.ObjectId> {

    Optional<SessionDocument> findByToken(String token);
}
