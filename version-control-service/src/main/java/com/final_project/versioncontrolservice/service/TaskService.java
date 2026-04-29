package com.final_project.versioncontrolservice.service;


import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.dto.MilestoneTaskUser;
import com.final_project.versioncontrolservice.dto.SubmissionResponse;
import com.final_project.versioncontrolservice.dto.UserDTO;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.*;
import com.final_project.versioncontrolservice.repo.*;
import com.final_project.versioncontrolservice.websocket.WebSocketEvents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskCommentRepository commentRepository;
    private final SubmissionRepository submissionRepository;
    private final RepositoryService vicRepositoryService;
    private final MilestoneService milestoneService;
    private final WebSocketNotificationService notificationService;
    private final AuthService authService;
    /**
     * Create a new task (with optional milestone assignment)
     */
    public MilestoneService.TaskResponse createTask(String owner, String repo, TaskRequest request, String username) {

        UserDTO ownerUser = authService.getUserByUsername(owner);
        if (ownerUser == null) {
            throw new NotFoundException("User Not Found");
        }
        UserDTO repoUser = authService.getUserByUsername(username);
        if (repoUser == null) {
            throw new NotFoundException("Repo User Not Found");
        }



        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        int number = getNextTaskNumber(owner, repo);

        List<ContributorUser> contributorUser = meta.getCollaborators();

        Task task = Task.builder()
                .priority(request.getPriority())
                .status(TaskStatus.OPEN)
                .repoName(meta.getRepositoryName())
                .assignedAt(Instant.now())
                .number(number)
                .repoOwner(MilestoneTaskUser
                        .builder()
                        .email(ownerUser.getEmail())
                        .firstName(ownerUser.getFirstName())
                        .userId(ownerUser.getId())
                        .userName(ownerUser.getUsername())
                        .profile(ownerUser.getProfile())
                        .build())
                .title(request.getTitle())
                .description(request.getDescription())
                .createdBy(repoUser.getUsername())
                .updatedAt(Instant.now())
                .labels(request.getLabels())
                .dueDate(request.getDueDate())
                .estimatedHours(request.getEstimatedHours())
                .maxScore(request.getMaxScore())
                .build();
        if (request.getRequirements() != null) {
            task.setRequirementsChecklist(
                    request.getRequirements()
                            .stream()
                            .map(req -> {
                                Task.RequirementCheck check = new Task.RequirementCheck();
                                check.setRequirement(req);
                                check.setCompleted(false);
                                return check;
                            })
                            .collect(Collectors.toList())
            );
        }
        if (request.getMilestoneNumber() != null) {
            Milestone milestone = milestoneRepository
                    .findByRepoOwner_UserNameAndRepoNameAndNumber(owner, repo, request.getMilestoneNumber())
                    .orElseThrow(() -> new NotFoundException("milestone #" + request.getMilestoneNumber() + " not found"));
            task.setMilestoneId(milestone.getId());
            task.setMilestoneNumber(milestone.getNumber());
            milestoneService.updateMilestoneProgress(owner, repo, milestone.getId());
        }


        Task saved = taskRepository.save(task);

        // Send notification
//        sendTaskNotification(owner, repo, saved, "created", username);

        return MilestoneService.TaskResponse.fromDocument(saved);
    }

    /**
     * Assign task to a user
     */
    public MilestoneService.TaskResponse assignTask(String owner, String repo, int taskNumber,
                                   String assignee, String assignedBy) {
        UserDTO assigneeUser = authService.getUserByUsername(assignee);
        if (assigneeUser == null) {
            throw new NotFoundException("User Not Found");
        }

        UserDTO repoOwner = authService.getUserByUsername(owner);
        if (repoOwner == null) {
            throw new NotFoundException("Repo User Not Found");
        }

        UserDTO assignedByUser =  authService.getUserByUsername(assignedBy);
        if (assignedByUser == null) {
            throw new NotFoundException("User Not Found");
        }

        Task task = getTask(owner, repo, taskNumber);
        RepositoryDocument meta = vicRepositoryService.loadMeta(owner, repo);
        task.setAssignedTo(
                MilestoneTaskUser.builder()
                        .email(assigneeUser.getEmail())
                        .firstName(assigneeUser.getFirstName())
                        .userId(assigneeUser.getId())
                        .userName(assigneeUser.getUsername())
                        .profile(assigneeUser.getProfile())
                        .build()
        );

        task.setAssignedAt(Instant.now());
        task.setStatus(TaskStatus.PROGRESS);
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

        return MilestoneService.TaskResponse.fromDocument(updated);
    }

    /**
     * Submit work for a task
     */
    public SubmissionResponse submitTask(String owner, String repo, int taskNumber,
                                         SubmissionRequest request, String username) {
        UserDTO user = authService.getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }
        UserDTO repoOwner = authService.getUserByUsername(owner);
        if (repoOwner == null) {
            throw new NotFoundException("Repo User Not Found");
        }

        Task task = getTask(owner, repo, taskNumber);
        Submission submission = Submission
                .builder()
                .taskId(task.getId())
                .submittedBy(
                        MilestoneTaskUser
                                .builder()
                                .profile(user.getProfile())
                                .userName(user.getUsername())
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .userId(user.getId())
                                .build()
                )
                .submittedAt(Instant.now())
                .description(request.getDescription())
                .branchName(request.getBranchName())
                .commitHash(request.getCommitHash())
                .pullRequestUrl(request.getPullRequestUrl())
                .files(request.getFiles()).status("submitted")
                .revisionCount(0)
                        .build();
        // Update task status

        task.setStatus(TaskStatus.PROGRESS);
        task.setSubmissionUrl(request.getPullRequestUrl());
        task.setSubmissionBranch(request.getBranchName());
        task.setSubmissionCommit(request.getCommitHash());
        task.setUpdatedAt(Instant.now());
        taskRepository.save(task);
        Submission saved = submissionRepository.save(submission);
        // Notify repo admins
        notifyAdminsAboutSubmission(owner, repo, task, username);
        return SubmissionResponse.from(saved);
    }

    /**
     * Review/grading a task submission
     */
    public MilestoneService.TaskResponse reviewTask(String owner, String repo, int taskNumber,
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
            task.setStatus(TaskStatus.COMPLETED);
            task.setCompletedAt(Instant.now());
        } else {
            task.setStatus(TaskStatus.PROGRESS);  // Back to in_progress for revisions
        }

        task.setUpdatedAt(Instant.now());
        task.setCommentsCount((int) commentRepository.countByTaskId(task.getId()));

        Task updated = taskRepository.save(task);

        // Update milestone progress
        if (task.getMilestoneId() != null) {
            milestoneService.updateMilestoneProgress(owner, repo, task.getMilestoneId());
        }

        // Notify student
        notificationService.sendUserNotification(task.getAssignedTo().getUserName(),
                WebSocketEvents.NotificationEvent.builder()
                        .type("task_reviewed")
                        .title("Task Reviewed")
                        .message("Task #" + taskNumber + " has been reviewed. Score: " + request.getScore())
                        .url("/repos/" + owner + "/" + repo + "/tasks/" + taskNumber)
                        .createdAt(Instant.now())
                        .build()
        );
        return MilestoneService.TaskResponse.fromDocument(updated);
    }

    /**
     * Get student dashboard with all assigned tasks
     */
    public StudentDashboard getStudentDashboard(String owner, String repo, String username) {
        List<Task> tasks = taskRepository
                .findByRepoOwner_UserNameAndRepoNameAndAssignedTo_UserName(owner, repo, username);
        long totalTasks = tasks.size();
        long completedTasks = tasks
                .stream()
                .filter(t -> t.getStatus().equals(TaskStatus.COMPLETED)).count();
        long inProgressTasks = tasks
                .stream()
                .filter(t -> t.getStatus().equals(TaskStatus.PROGRESS)).count();
        long inReviewTasks = tasks
                .stream().filter(t -> t.getStatus().equals(TaskStatus.REVIEW)).count();
        long openTasks = tasks
                .stream()
                .filter(t -> t.getStatus().equals(TaskStatus.OPEN)).count();
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

    private Task getTask(String owner, String repo, int number) {
        return taskRepository.findByRepoOwner_UserNameAndRepoNameAndNumberOrderByNumber(owner, repo, number)
                .orElseThrow(() -> new NotFoundException("task #" + number + " not found"));
    }

    private int getNextTaskNumber(String owner, String repo) {
        return taskRepository
                .findByRepoOwner_UserNameAndRepoNameOrderByNumberDesc(owner, repo)
                .stream()
                .findFirst()
                .map(task -> task.getNumber() + 1)
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
                        "task_id", task.getId()
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

    @Data
    @Builder
    @AllArgsConstructor
    public static class TaskRequest {
        private String title;
        private String description;
        private Integer milestoneNumber;
        private TaskPriority priority;
        private List<Label> labels;
        private Instant dueDate;
        private Integer estimatedHours;
        private Integer maxScore;
        private List<String> requirements;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class SubmissionRequest {
        private String description;
        private String branchName;
        private String commitHash;
        private String pullRequestUrl;
        private List<String> files;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ReviewRequest {
        private String feedback;
        private Integer score;
        private boolean approved;
        private List<String> checkedRequirements;
    }

    @Data
    @Builder
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

