package com.final_project.versioncontrolservice.service;


import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.*;
import com.final_project.versioncontrolservice.repo.*;
import com.final_project.versioncontrolservice.websocket.WebSocketEvents;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskCommentRepository commentRepository;
    private final SubmissionRepository submissionRepository;
    private final RepositoryService vicRepositoryService;
    private final MilestoneService milestoneService;
    private final WebSocketNotificationService notificationService;

    public TaskService(TaskRepository taskRepository,
                       MilestoneRepository milestoneRepository,
                       TaskCommentRepository commentRepository,
                       SubmissionRepository submissionRepository,
                       RepositoryService vicRepositoryService,
                       MilestoneService milestoneService,
                       WebSocketNotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.commentRepository = commentRepository;
        this.submissionRepository = submissionRepository;
        this.vicRepositoryService = vicRepositoryService;
        this.milestoneService = milestoneService;
        this.notificationService = notificationService;
    }

    /**
     * Create a new task (with optional milestone assignment)
     */
    public Task createTask(String owner, String repo, TaskRequest request, String username) {
        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, username)) {
            throw new ForbiddenException("you don't have permission to create tasks");
        }

        int number = getNextTaskNumber(owner, repo);

        Task task = new Task();
        task.setRepoOwner(owner);
        task.setRepoName(repo);
        task.setNumber(number);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedBy(username);
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setStatus("open");
        task.setPriority(request.getPriority() != null ? request.getPriority() : "medium");
        task.setLabels(request.getLabels() != null ? request.getLabels() : new ArrayList<>());
        task.setDueDate(request.getDueDate());
        task.setEstimatedHours(request.getEstimatedHours());
        task.setMaxScore(request.getMaxScore());

        // Requirements checklist
        if (request.getRequirements() != null) {
            task.setRequirementsChecklist(
                    request.getRequirements().stream()
                            .map(req -> {
                                Task.RequirementCheck check = new Task.RequirementCheck();
                                check.setRequirement(req);
                                check.setCompleted(false);
                                return check;
                            })
                            .collect(Collectors.toList())
            );
        }

        // Link to milestone if provided
        if (request.getMilestoneNumber() != null) {
            Milestone milestone = milestoneRepository
                    .findByRepoOwnerAndRepoNameAndNumber(owner, repo, request.getMilestoneNumber())
                    .orElseThrow(() -> new NotFoundException("milestone #" + request.getMilestoneNumber() + " not found"));

            task.setMilestoneId(milestone.getId());
            task.setMilestoneNumber(milestone.getNumber());

            // Update milestone progress
            milestoneService.updateMilestoneProgress(owner, repo, milestone.getId());
        }

        Task saved = taskRepository.save(task);

        // Send notification
        sendTaskNotification(owner, repo, saved, "created", username);

        return saved;
    }

    /**
     * Assign task to a user
     */
    public Task assignTask(String owner, String repo, int taskNumber,
                                   String assignee, String assignedBy) {
        Task task = getTask(owner, repo, taskNumber);

        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, assignedBy)) {
            throw new ForbiddenException("you don't have permission to assign tasks");
        }

        task.setAssignedTo(assignee);
        task.setAssignedAt(Instant.now());
        task.setStatus("in_progress");
        task.setUpdatedAt(Instant.now());

        Task updated = taskRepository.save(task);

        // Update milestone if task belongs to one
        if (task.getMilestoneId() != null) {
            milestoneService.updateMilestoneProgress(owner, repo, task.getMilestoneId());
        }

        // Send notification to assignee
        notificationService.sendUserNotification(assignee,
                WebSocketEvents.NotificationEvent.builder()
                        .type("task_assigned")
                        .title("New Task Assigned")
                        .message("You've been assigned to task #" + taskNumber + ": " + task.getTitle())
                        .url("/repos/" + owner + "/" + repo + "/tasks/" + taskNumber)
                        .createdAt(Instant.now())
                        .build()
        );

        return updated;
    }

    /**
     * Submit work for a task
     */
    public Submission submitTask(String owner, String repo, int taskNumber,
                                         SubmissionRequest request, String username) {
        Task task = getTask(owner, repo, taskNumber);

        // Verify user is assigned to this task
        if (!username.equals(task.getAssignedTo())) {
            throw new ForbiddenException("you are not assigned to this task");
        }

        Submission submission = new Submission();
        submission.setTaskId(task.getId());
        submission.setSubmittedBy(username);
        submission.setSubmittedAt(Instant.now());
        submission.setDescription(request.getDescription());
        submission.setBranchName(request.getBranchName());
        submission.setCommitHash(request.getCommitHash());
        submission.setPullRequestUrl(request.getPullRequestUrl());
        submission.setFiles(request.getFiles());
        submission.setStatus("submitted");
        submission.setRevisionCount(0);

        // Update task status
        task.setStatus("in_review");
        task.setSubmissionUrl(request.getPullRequestUrl());
        task.setSubmissionBranch(request.getBranchName());
        task.setSubmissionCommit(request.getCommitHash());
        task.setUpdatedAt(Instant.now());
        taskRepository.save(task);

        Submission saved = submissionRepository.save(submission);

        // Notify repo admins
        notifyAdminsAboutSubmission(owner, repo, task, username);

        return saved;
    }

    /**
     * Review/grading a task submission
     */
    public Task reviewTask(String owner, String repo, int taskNumber,
                                   ReviewRequest request, String reviewer) {
        Task task = getTask(owner, repo, taskNumber);

        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, reviewer)) {
            throw new ForbiddenException("only admins can review/grading tasks");
        }

        // Create review comment
        TaskComment comment = new TaskComment();
        comment.setTaskId(task.getId());
        comment.setRepoOwner(owner);
        comment.setRepoName(repo);
        comment.setAuthor(reviewer);
        comment.setBody(request.getFeedback());
        comment.setCreatedAt(Instant.now());
        comment.setUpdatedAt(Instant.now());
        comment.setReview(true);
        comment.setReviewScore(request.getScore());
        commentRepository.save(comment);

        // Update task
        task.setReviewedBy(reviewer);
        task.setReviewedAt(Instant.now());
        task.setReviewComments(request.getFeedback());
        task.setEarnedScore(request.getScore());

        if (request.isApproved()) {
            task.setStatus("completed");
            task.setCompletedAt(Instant.now());
        } else {
            task.setStatus("in_progress");  // Back to in_progress for revisions
        }

        task.setUpdatedAt(Instant.now());
        task.setCommentsCount((int) commentRepository.countByTaskId(task.getId()));

        Task updated = taskRepository.save(task);

        // Update milestone progress
        if (task.getMilestoneId() != null) {
            milestoneService.updateMilestoneProgress(owner, repo, task.getMilestoneId());
        }

        // Notify student
        notificationService.sendUserNotification(task.getAssignedTo(),
                WebSocketEvents.NotificationEvent.builder()
                        .type("task_reviewed")
                        .title("Task Reviewed")
                        .message("Task #" + taskNumber + " has been reviewed. Score: " + request.getScore())
                        .url("/repos/" + owner + "/" + repo + "/tasks/" + taskNumber)
                        .createdAt(Instant.now())
                        .build()
        );

        return updated;
    }

    /**
     * Get student dashboard with all assigned tasks
     */
    public StudentDashboard getStudentDashboard(String owner, String repo, String username) {
        List<Task> tasks = taskRepository
                .findByRepoOwnerAndRepoNameAndAssignedTo(owner, repo, username);

        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(t -> "completed".equals(t.getStatus())).count();
        long inProgressTasks = tasks.stream().filter(t -> "in_progress".equals(t.getStatus())).count();
        long inReviewTasks = tasks.stream().filter(t -> "in_review".equals(t.getStatus())).count();
        long openTasks = tasks.stream().filter(t -> "open".equals(t.getStatus())).count();

        // Calculate total score
        int totalEarnedScore = tasks.stream()
                .filter(t -> t.getEarnedScore() != null)
                .mapToInt(Task::getEarnedScore)
                .sum();

        int totalPossibleScore = tasks.stream()
                .filter(t -> t.getMaxScore() != null)
                .mapToInt(Task::getMaxScore)
                .sum();

        return StudentDashboard.builder()
                .username(username)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .inProgressTasks(inProgressTasks)
                .inReviewTasks(inReviewTasks)
                .openTasks(openTasks)
                .totalEarnedScore(totalEarnedScore)
                .totalPossibleScore(totalPossibleScore)
                .scorePercentage(totalPossibleScore > 0 ?
                        (double) totalEarnedScore / totalPossibleScore * 100 : 0)
                .tasks(tasks.stream()
                        .map(MilestoneService.TaskResponse::fromDocument)
                        .collect(Collectors.toList()))
                .build();
    }

    // ─── Helper Methods ───────────────────────────────────────────────────

    private Task getTask(String owner, String repo, int number) {
        return taskRepository.findByRepoOwnerAndRepoNameAndNumber(owner, repo, number)
                .orElseThrow(() -> new NotFoundException("task #" + number + " not found"));
    }

    private int getNextTaskNumber(String owner, String repo) {
        return taskRepository.findTopByRepoOwnerAndRepoNameOrderByNumberDesc(owner, repo)
                .map(t -> t.getNumber() + 1)
                .orElse(1);
    }

    private void sendTaskNotification(String owner, String repo, Task task,
                                      String action, String username) {
        WebSocketEvents.RepositoryActivity activity = WebSocketEvents.RepositoryActivity.builder()
                .type("task_" + action)
                .description("Task #" + task.getNumber() + " " + action + ": " + task.getTitle())
                .actor(WebSocketEvents.UserInfo.builder().username(username).build())
                .timestamp(Instant.now())
                .metadata(Map.of(
                        "task_number", task.getNumber(),
                        "task_id", task.getId().toHexString()
                ))
                .build();

        notificationService.broadcastActivity(owner, repo, activity);
    }

    private void notifyAdminsAboutSubmission(String owner, String repo,
                                             Task task, String submitter) {
        // Get repo admins and notify them
        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);

        // Notify owner
        notificationService.sendUserNotification(meta.getOwner().getUsername(),
                WebSocketEvents.NotificationEvent.builder()
                        .type("new_submission")
                        .title("New Submission")
                        .message("Task #" + task.getNumber() + " submitted by " + submitter)
                        .url("/repos/" + owner + "/" + repo + "/tasks/" + task.getNumber())
                        .createdAt(Instant.now())
                        .build()
        );
    }

    // ─── Request/Response DTOs ────────────────────────────────────────────

    public static class TaskRequest {
        private String title;
        private String description;
        private Integer milestoneNumber;
        private String priority;
        private List<String> labels;
        private Instant dueDate;
        private Integer estimatedHours;
        private Integer maxScore;
        private List<String> requirements;

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Integer getMilestoneNumber() { return milestoneNumber; }
        public void setMilestoneNumber(Integer milestoneNumber) { this.milestoneNumber = milestoneNumber; }
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
        public List<String> getLabels() { return labels; }
        public void setLabels(List<String> labels) { this.labels = labels; }
        public Instant getDueDate() { return dueDate; }
        public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }
        public Integer getEstimatedHours() { return estimatedHours; }
        public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
        public Integer getMaxScore() { return maxScore; }
        public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
        public List<String> getRequirements() { return requirements; }
        public void setRequirements(List<String> requirements) { this.requirements = requirements; }
    }

    public static class SubmissionRequest {
        private String description;
        private String branchName;
        private String commitHash;
        private String pullRequestUrl;
        private List<String> files;

        // Getters and setters
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getBranchName() { return branchName; }
        public void setBranchName(String branchName) { this.branchName = branchName; }
        public String getCommitHash() { return commitHash; }
        public void setCommitHash(String commitHash) { this.commitHash = commitHash; }
        public String getPullRequestUrl() { return pullRequestUrl; }
        public void setPullRequestUrl(String pullRequestUrl) { this.pullRequestUrl = pullRequestUrl; }
        public List<String> getFiles() { return files; }
        public void setFiles(List<String> files) { this.files = files; }
    }

    public static class ReviewRequest {
        private String feedback;
        private Integer score;
        private boolean approved;
        private List<String> checkedRequirements;

        // Getters and setters
        public String getFeedback() { return feedback; }
        public void setFeedback(String feedback) { this.feedback = feedback; }
        public Integer getScore() { return score; }
        public void setScore(Integer score) { this.score = score; }
        public boolean isApproved() { return approved; }
        public void setApproved(boolean approved) { this.approved = approved; }
        public List<String> getCheckedRequirements() { return checkedRequirements; }
        public void setCheckedRequirements(List<String> checkedRequirements) { this.checkedRequirements = checkedRequirements; }
    }

    @lombok.Data
    @lombok.Builder
    public static class StudentDashboard {
        private String username;
        private long totalTasks;
        private long completedTasks;
        private long inProgressTasks;
        private long inReviewTasks;
        private long openTasks;
        private int totalEarnedScore;
        private int totalPossibleScore;
        private double scorePercentage;
        private List<MilestoneService.TaskResponse> tasks;
    }
}

