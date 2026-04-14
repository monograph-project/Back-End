package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface UserRepository extends MongoRepository<UserDocument, org.bson.types.ObjectId> {

    Optional<UserDocument> findByUsername(String username);

    Optional<UserDocument> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
