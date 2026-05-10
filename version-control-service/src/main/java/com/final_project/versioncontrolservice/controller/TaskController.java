package com.final_project.versioncontrolservice.controller;


import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.dto.SubmissionResponse;
import com.final_project.versioncontrolservice.model.Task;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.MilestoneService;
import com.final_project.versioncontrolservice.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/task")
@RestController
@AllArgsConstructor
public class TaskController {
    private final AuthService authService;
    private final TaskService taskService;
    /**
     * Create a new task
     * POST /repos/{owner}/{repo}/tasks
     */

    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> createTask(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody TaskService.TaskRequest request,
            @PathVariable String username,
            @AuthenticationPrincipal Jwt jwt

    ) {
        ContributorUser user = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.createTask(owner, repo, request, username));
    }

    /**
     * Assign task to user
     * POST /repos/{owner}/{repo}/tasks/{number}/assign
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/assign/{user}/{assignee}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> assignTask(
            @PathVariable String user,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @PathVariable String assignee,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ContributorUser contributor = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.assignTask(owner, repo, number, assignee, contributor.getUsername()));
    }

    /**
     * Submit work for a task
     * POST /repos/{owner}/{repo}/tasks/{number}/submit
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/submit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmissionResponse> submitTask(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody TaskService.SubmissionRequest request,
            @AuthenticationPrincipal Jwt jwt
            ) {

        ContributorUser user = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.submitTask(owner, repo, number, request, user.getUsername()));
    }

    /**
     * Review/grading a task
     * POST /repos/{owner}/{repo}/tasks/{number}/review
     */
    @PostMapping(path = "/repos/{owner}/{repo}/tasks/{number}/review",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.TaskResponse> reviewTask(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody TaskService.ReviewRequest request,
            @AuthenticationPrincipal Jwt jwt
            ) {
        ContributorUser user = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.reviewTask(owner, repo, number, request, user.getUsername()));
    }

    /**
     * Get student dashboard
     * GET /repos/{owner}/{repo}/dashboard
     */
    @GetMapping(path = "/repos/{owner}/{repo}/dashboard",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskService.StudentDashboard> getDashboard(
            @PathVariable String owner,
            @PathVariable String repo,
            @AuthenticationPrincipal Jwt jwt
            ) {
        ContributorUser user = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.getStudentDashboard(owner, repo, user.getUsername()));
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
            @RequestParam(required = false) Integer milestone,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(taskService.listTasks(owner, repo, assignee, status, milestone, search));
    }

    @GetMapping(path = "/repos/{owner}/{repo}/tasks/{number}/eligible-pulls",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskService.TaskPullRequestCandidateResponse>> listEligiblePullRequests(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ContributorUser user = authService.getContributorUser(jwt.getSubject());
        return ResponseEntity.ok(taskService.listEligiblePullRequests(owner, repo, number, user.getUsername()));
    }
}
