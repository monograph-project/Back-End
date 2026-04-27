package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {

    List<Task> findByRepoOwnerAndRepoNameOrderByNumberDesc(
            String repoOwner, String repoName);

    Optional<Task> findByRepoOwnerAndRepoNameAndNumber(
            String repoOwner, String repoName, Integer number);

    List<Task> findByRepoOwnerAndRepoNameAndMilestoneId(
            String repoOwner, String repoName, ObjectId milestoneId);

    List<Task> findByRepoOwnerAndRepoNameAndAssignedTo(
            String repoOwner, String repoName, String assignedTo);

    List<Task> findByRepoOwnerAndRepoNameAndStatus(
            String repoOwner, String repoName, String status);

    List<Task> findByRepoOwnerAndRepoNameAndAssignedToAndStatus(
            String repoOwner, String repoName, String assignedTo, String status);

    @Query(value = "{'repo_owner': ?0, 'repo_name': ?1, 'milestone_id': ?2}", count = true)
    long countByMilestone(String repoOwner, String repoName, ObjectId milestoneId);

    @Query(value = "{'repo_owner': ?0, 'repo_name': ?1, 'milestone_id': ?2, 'status': ?3}", count = true)
    long countByMilestoneAndStatus(String repoOwner, String repoName,
                                   ObjectId milestoneId, String status);

    Optional<Task> findTopByRepoOwnerAndRepoNameOrderByNumberDesc(
            String repoOwner, String repoName);

    @Query("{'repo_owner': ?0, 'repo_name': ?1, '$text': {'$search': ?2}}")
    List<Task> searchTasks(String repoOwner, String repoName, String searchTerm);
}
