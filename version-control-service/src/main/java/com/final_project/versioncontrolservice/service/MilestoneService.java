package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.Milestone;
import com.final_project.versioncontrolservice.model.Task;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import com.final_project.versioncontrolservice.repo.MilestoneRepository;
import com.final_project.versioncontrolservice.repo.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;
    private final RepositoryService vicRepositoryService;

    public MilestoneService(MilestoneRepository milestoneRepository,
                            TaskRepository taskRepository,
                            RepositoryService vicRepositoryService) {
        this.milestoneRepository = milestoneRepository;
        this.taskRepository = taskRepository;
        this.vicRepositoryService = vicRepositoryService;
    }

    /**
     * Create a new milestone
     */
    public Milestone createMilestone(String owner, String repo,
                                             MilestoneRequest request, String username) {
        // Validate permissions
        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, username)) {
            throw new ForbiddenException("only repository admins can create milestones");
        }

        // Generate milestone number
        int number = getNextMilestoneNumber(owner, repo);

        Milestone milestone = new Milestone();
        milestone.setRepoOwner(owner);
        milestone.setRepoName(repo);
        milestone.setNumber(number);
        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setDueDate(request.getDueDate());
        milestone.setCreatedAt(Instant.now());
        milestone.setUpdatedAt(Instant.now());
        milestone.setCreatedBy(username);
        milestone.setStatus("open");

        // Academic fields
        milestone.setMaxScore(request.getMaxScore());
        milestone.setPassingScore(request.getPassingScore());
        milestone.setRubric(request.getRubric());
        milestone.setRequiredTasks(request.getRequiredTasks());
        milestone.setCompletionPercentage(0.0);

        return milestoneRepository.save(milestone);
    }

    /**
     * Update milestone progress based on task completion
     */
    public void updateMilestoneProgress(String owner, String repo, ObjectId milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone not found"));

        // Count tasks by status
        long totalTasks = taskRepository.countByMilestone(owner, repo, milestoneId);
        long completedTasks = taskRepository.countByMilestoneAndStatus(owner, repo, milestoneId, "completed");
        long inProgressTasks = taskRepository.countByMilestoneAndStatus(owner, repo, milestoneId, "in_progress");
        long openTasks = taskRepository.countByMilestoneAndStatus(owner, repo, milestoneId, "open");

        milestone.setTotalTasks((int) totalTasks);
        milestone.setCompletedTasks((int) completedTasks);
        milestone.setInProgressTasks((int) inProgressTasks);
        milestone.setOpenTasks((int) openTasks);

        // Calculate completion percentage
        if (totalTasks > 0) {
            double percentage = ((double) completedTasks / totalTasks) * 100.0;
            milestone.setCompletionPercentage(Math.round(percentage * 100.0) / 100.0);
        } else {
            milestone.setCompletionPercentage(0.0);
        }

        // Auto-close if all required tasks completed
        if (milestone.getRequiredTasks() != null && completedTasks >= milestone.getRequiredTasks()) {
            milestone.setStatus("closed");
            milestone.setClosedAt(Instant.now());
        }

        milestone.setUpdatedAt(Instant.now());
        milestoneRepository.save(milestone);
    }

    /**
     * Get milestones with detailed progress
     */
    public List<MilestoneResponse> getMilestones(String owner, String repo) {
        List<Milestone> milestones = milestoneRepository
                .findByRepoOwnerAndRepoNameOrderByNumberDesc(owner, repo);

        return milestones.stream()
                .map(m -> {
                    MilestoneResponse response = MilestoneResponse.fromDocument(m);

                    // Get tasks for this milestone
                    List<Task> tasks = taskRepository
                            .findByRepoOwnerAndRepoNameAndMilestoneId(owner, repo, m.getId());

                    response.setTasks(tasks.stream()
                            .map(TaskResponse::fromDocument)
                            .collect(Collectors.toList()));

                    return response;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get milestone by number
     */
    public Milestone getMilestone(String owner, String repo, int number) {
        return milestoneRepository.findByRepoOwnerAndRepoNameAndNumber(owner, repo, number)
                .orElseThrow(() -> new NotFoundException("milestone #" + number + " not found"));
    }

    /**
     * Close a milestone
     */
    public Milestone closeMilestone(String owner, String repo, int number, String username) {
        Milestone milestone = getMilestone(owner, repo, number);

        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, username)) {
            throw new ForbiddenException("only repository admins can close milestones");
        }

        milestone.setStatus("closed");
        milestone.setClosedAt(Instant.now());
        milestone.setUpdatedAt(Instant.now());

        return milestoneRepository.save(milestone);
    }

    /**
     * Reopen a milestone
     */
    public Milestone reopenMilestone(String owner, String repo, int number, String username) {
        Milestone milestone = getMilestone(owner, repo, number);

        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, username)) {
            throw new ForbiddenException("only repository admins can reopen milestones");
        }

        milestone.setStatus("open");
        milestone.setClosedAt(null);
        milestone.setUpdatedAt(Instant.now());

        return milestoneRepository.save(milestone);
    }

    private int getNextMilestoneNumber(String owner, String repo) {
        return milestoneRepository.findTopByRepoOwnerAndRepoNameOrderByNumberDesc(owner, repo)
                .map(m -> m.getNumber() + 1)
                .orElse(1);
    }

    // ─── Request/Response DTOs ────────────────────────────────────────────

    public static class MilestoneRequest {
        private String title;
        private String description;
        private Instant dueDate;
        private Integer maxScore;
        private Integer passingScore;
        private String rubric;
        private Integer requiredTasks;

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Instant getDueDate() { return dueDate; }
        public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }
        public Integer getMaxScore() { return maxScore; }
        public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
        public Integer getPassingScore() { return passingScore; }
        public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }
        public String getRubric() { return rubric; }
        public void setRubric(String rubric) { this.rubric = rubric; }
        public Integer getRequiredTasks() { return requiredTasks; }
        public void setRequiredTasks(Integer requiredTasks) { this.requiredTasks = requiredTasks; }
    }

    public static class MilestoneResponse {
        private String id;
        private int number;
        private String title;
        private String description;
        private Instant dueDate;
        private Instant createdAt;
        private Instant updatedAt;
        private Instant closedAt;
        private String createdBy;
        private String status;
        private Integer maxScore;
        private Integer passingScore;
        private String rubric;
        private Integer requiredTasks;
        private Double completionPercentage;
        private Integer totalTasks;
        private Integer openTasks;
        private Integer completedTasks;
        private Integer inProgressTasks;
        private List<TaskResponse> tasks;

        public static MilestoneResponse fromDocument(Milestone doc) {
            MilestoneResponse response = new MilestoneResponse();
            response.setId(doc.getId().toHexString());
            response.setNumber(doc.getNumber());
            response.setTitle(doc.getTitle());
            response.setDescription(doc.getDescription());
            response.setDueDate(doc.getDueDate());
            response.setCreatedAt(doc.getCreatedAt());
            response.setUpdatedAt(doc.getUpdatedAt());
            response.setClosedAt(doc.getClosedAt());
            response.setCreatedBy(doc.getCreatedBy());
            response.setStatus(doc.getStatus());
            response.setMaxScore(doc.getMaxScore());
            response.setPassingScore(doc.getPassingScore());
            response.setRubric(doc.getRubric());
            response.setRequiredTasks(doc.getRequiredTasks());
            response.setCompletionPercentage(doc.getCompletionPercentage());
            response.setTotalTasks(doc.getTotalTasks());
            response.setOpenTasks(doc.getOpenTasks());
            response.setCompletedTasks(doc.getCompletedTasks());
            response.setInProgressTasks(doc.getInProgressTasks());
            return response;
        }

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Instant getDueDate() { return dueDate; }
        public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
        public Instant getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
        public Instant getClosedAt() { return closedAt; }
        public void setClosedAt(Instant closedAt) { this.closedAt = closedAt; }
        public String getCreatedBy() { return createdBy; }
        public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public Integer getMaxScore() { return maxScore; }
        public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
        public Integer getPassingScore() { return passingScore; }
        public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }
        public String getRubric() { return rubric; }
        public void setRubric(String rubric) { this.rubric = rubric; }
        public Integer getRequiredTasks() { return requiredTasks; }
        public void setRequiredTasks(Integer requiredTasks) { this.requiredTasks = requiredTasks; }
        public Double getCompletionPercentage() { return completionPercentage; }
        public void setCompletionPercentage(Double completionPercentage) { this.completionPercentage = completionPercentage; }
        public Integer getTotalTasks() { return totalTasks; }
        public void setTotalTasks(Integer totalTasks) { this.totalTasks = totalTasks; }
        public Integer getOpenTasks() { return openTasks; }
        public void setOpenTasks(Integer openTasks) { this.openTasks = openTasks; }
        public Integer getCompletedTasks() { return completedTasks; }
        public void setCompletedTasks(Integer completedTasks) { this.completedTasks = completedTasks; }
        public Integer getInProgressTasks() { return inProgressTasks; }
        public void setInProgressTasks(Integer inProgressTasks) { this.inProgressTasks = inProgressTasks; }
        public List<TaskResponse> getTasks() { return tasks; }
        public void setTasks(List<TaskResponse> tasks) { this.tasks = tasks; }
    }

    public static class TaskResponse {
        private String id;
        private int number;
        private String title;
        private String description;
        private String assignedTo;
        private String status;
        private String priority;
        private List<String> labels;
        private Instant dueDate;
        private Integer maxScore;
        private Integer earnedScore;
        private Integer milestoneNumber;
        private Instant completedAt;
        private String reviewedBy;
        private String reviewComments;
        private String submissionUrl;
        private String submissionBranch;
        private List<Task.RequirementCheck> requirementsChecklist;
        private Integer commentsCount;

        public static TaskResponse fromDocument(Task doc) {
            TaskResponse response = new TaskResponse();
            response.setId(doc.getId().toHexString());
            response.setNumber(doc.getNumber());
            response.setTitle(doc.getTitle());
            response.setDescription(doc.getDescription());
            response.setAssignedTo(doc.getAssignedTo());
            response.setStatus(doc.getStatus());
            response.setPriority(doc.getPriority());
            response.setLabels(doc.getLabels());
            response.setDueDate(doc.getDueDate());
            response.setMaxScore(doc.getMaxScore());
            response.setEarnedScore(doc.getEarnedScore());
            response.setMilestoneNumber(doc.getMilestoneNumber());
            response.setCompletedAt(doc.getCompletedAt());
            response.setReviewedBy(doc.getReviewedBy());
            response.setReviewComments(doc.getReviewComments());
            response.setSubmissionUrl(doc.getSubmissionUrl());
            response.setSubmissionBranch(doc.getSubmissionBranch());
            response.setRequirementsChecklist(doc.getRequirementsChecklist());
            response.setCommentsCount(doc.getCommentsCount());
            return response;
        }

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getAssignedTo() { return assignedTo; }
        public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }
        public Instant getDueDate() { return dueDate; }
        public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }
        public Integer getMaxScore() { return maxScore; }
        public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
        public Integer getEarnedScore() { return earnedScore; }
        public void setEarnedScore(Integer earnedScore) { this.earnedScore = earnedScore; }
        public Integer getMilestoneNumber() { return milestoneNumber; }
        public void setMilestoneNumber(Integer milestoneNumber) { this.milestoneNumber = milestoneNumber; }
        public Instant getCompletedAt() { return completedAt; }
        public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
        public String getReviewedBy() { return reviewedBy; }
        public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }
        public String getReviewComments() { return reviewComments; }
        public void setReviewComments(String reviewComments) { this.reviewComments = reviewComments; }
        public String getSubmissionUrl() { return submissionUrl; }
        public void setSubmissionUrl(String submissionUrl) { this.submissionUrl = submissionUrl; }
        public String getSubmissionBranch() { return submissionBranch; }
        public void setSubmissionBranch(String submissionBranch) { this.submissionBranch = submissionBranch; }
        public List<Task.RequirementCheck> getRequirementsChecklist() { return requirementsChecklist; }
        public void setRequirementsChecklist(List<Task.RequirementCheck> requirementsChecklist) { this.requirementsChecklist = requirementsChecklist; }
        public Integer getCommentsCount() { return commentsCount; }
        public void setCommentsCount(Integer commentsCount) { this.commentsCount = commentsCount; }
    }
}
