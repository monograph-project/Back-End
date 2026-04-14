package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.PullRequestDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PullRequestRepository extends MongoRepository<PullRequestDocument, ObjectId> {

    List<PullRequestDocument> findByRepoOwnerAndRepoName(String repoOwner, String repoName);

    Optional<PullRequestDocument> findByIdAndRepoOwnerAndRepoName(ObjectId id, String repoOwner, String repoName);
}
