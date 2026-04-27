package com.final_project.versioncontrolservice.controller;


import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.model.Task;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.MilestoneService;
import com.final_project.versioncontrolservice.service.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    private final AuthService authService;
    private final TaskService taskService;

    public TaskController(AuthService authService, TaskService taskService) {
        this.authService = authService;
        this.taskService = taskService;
    }

    /**
     * Create a new task
     * POST /repos/{owner}/{repo}/tasks
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> createTask(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody TaskService.TaskRequest request) {

        ContributorUser user = authService.getContributorUser(authorization);
        Task task = taskService.createTask(owner, repo, request, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MilestoneService.TaskResponse.fromDocument(task));
    }

    /**
     * Assign task to user
     * POST /repos/{owner}/{repo}/tasks/{number}/assign
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/assign",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> assignTask(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody Map<String, String> body) {

        ContributorUser user = authService.getContributorUser(authorization);
        String assignee = body.get("assignee");

        Task task = taskService.assignTask(owner, repo, number, assignee, user.getUsername());

        return ResponseEntity.ok(MilestoneService.TaskResponse.fromDocument(task));
    }

    /**
     * Submit work for a task
     * POST /repos/{owner}/{repo}/tasks/{number}/submit
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/submit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> submitTask(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody TaskService.SubmissionRequest request) {

        ContributorUser user = authService.getContributorUser(authorization);
        taskService.submitTask(owner, repo, number, request, user.getUsername());

        return ResponseEntity.ok(Map.of("status", "submitted"));
    }

    /**
     * Review/grading a task
     * POST /repos/{owner}/{repo}/tasks/{number}/review
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/review",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> reviewTask(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody TaskService.ReviewRequest request) {

        ContributorUser user = authService.getContributorUser(authorization);
        Task task = taskService.reviewTask(owner, repo, number, request, user.getUsername());

        return ResponseEntity.ok(MilestoneService.TaskResponse.fromDocument(task));
    }

    /**
     * Get student dashboard
     * GET /repos/{owner}/{repo}/dashboard
     */
    @GetMapping(path = "/repos/{owner}/{repo}/dashboard",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskService.StudentDashboard> getDashboard(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo) {

        ContributorUser user = authService.getContributorUser(authorization);
        TaskService.StudentDashboard dashboard = taskService.getStudentDashboard(owner, repo, user.getUsername());

        return ResponseEntity.ok(dashboard);
    }

    /**
     * List tasks (with filters)
     * GET /repos/{owner}/{repo}/tasks
     */
    @GetMapping(path = "/repos/{owner}/{repo}/tasks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MilestoneService.TaskResponse>> listTasks(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer milestone) {

        // Implementation depends on your filtering needs
        return ResponseEntity.ok(List.of());
    }
}
