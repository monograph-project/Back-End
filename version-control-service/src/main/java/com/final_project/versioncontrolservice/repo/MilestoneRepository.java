package com.final_project.versioncontrolservice.repo;


import com.final_project.versioncontrolservice.model.Milestone;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MilestoneRepository extends MongoRepository<Milestone, ObjectId> {

    List<Milestone> findByRepoOwnerAndRepoNameOrderByNumberDesc(
            String repoOwner, String repoName);

    Optional<Milestone> findByRepoOwnerAndRepoNameAndNumber(
            String repoOwner, String repoName, Integer number);

    List<Milestone> findByRepoOwnerAndRepoNameAndStatus(
            String repoOwner, String repoName, String status);

    @Query(value = "{'repo_owner': ?0, 'repo_name': ?1}", count = true)
    long countByRepo(String repoOwner, String repoName);

    @Query(value = "{'repo_owner': ?0, 'repo_name': ?1, 'status': 'open'}", count = true)
    long countOpenByRepo(String repoOwner, String repoName);

    Optional<Milestone> findTopByRepoOwnerAndRepoNameOrderByNumberDesc(
            String repoOwner, String repoName);
}
