package com.final_project.versioncontrolservice.controller;
import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.model.Milestone;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.MilestoneService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MilestoneController {

    private final AuthService authService;
    private final MilestoneService milestoneService;

    public MilestoneController(AuthService authService, MilestoneService milestoneService) {
        this.authService = authService;
        this.milestoneService = milestoneService;
    }

    /**
     * Create a new milestone
     * POST /repos/{owner}/{repo}/milestones
     */
    @PostMapping(path = "/repos/{owner}/{repo}/milestones",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.MilestoneResponse> createMilestone(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody MilestoneService.MilestoneRequest request) {

        ContributorUser user = authService.getContributorUser(authorization);
        Milestone milestone = milestoneService.createMilestone(owner, repo, request, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MilestoneService.MilestoneResponse.fromDocument(milestone));
    }

    /**
     * List all milestones for a repository
     * GET /repos/{owner}/{repo}/milestones
     */
    @GetMapping(path = "/repos/{owner}/{repo}/milestones",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MilestoneService.MilestoneResponse>> listMilestones(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "open") String state) {

        ContributorUser user = authService.getContributorUser(authorization);
        String username = user != null ? user.getUsername() : "";

        List<MilestoneService.MilestoneResponse> milestones = milestoneService.getMilestones(owner, repo);

        // Filter by state if specified
        if (!"all".equals(state)) {
            milestones = milestones.stream()
                    .filter(m -> state.equals(m.getStatus()))
                    .toList();
        }

        return ResponseEntity.ok(milestones);
    }

    /**
     * Get a specific milestone
     * GET /repos/{owner}/{repo}/milestones/{number}
     */
    @GetMapping(path = "/repos/{owner}/{repo}/milestones/{number}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.MilestoneResponse> getMilestone(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number) {

        ContributorUser user = authService.getContributorUser(authorization);
        Milestone milestone = milestoneService.getMilestone(owner, repo, number);

        return ResponseEntity.ok(MilestoneService.MilestoneResponse.fromDocument(milestone));
    }

    /**
     * Close a milestone
     * PATCH /repos/{owner}/{repo}/milestones/{number}
     */
    @PatchMapping(path = "/repos/{owner}/{repo}/milestones/{number}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MilestoneService.MilestoneResponse> updateMilestone(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable int number,
            @RequestBody Map<String, String> body) {

        ContributorUser user = authService.getContributorUser(authorization);
        String action = body.get("action");

        Milestone milestone;
        if ("close".equals(action)) {
            milestone = milestoneService.closeMilestone(owner, repo, number, user.getUsername());
        } else if ("reopen".equals(action)) {
            milestone = milestoneService.reopenMilestone(owner, repo, number, user.getUsername());
        } else {
            throw new IllegalArgumentException("invalid action: " + action);
        }

        return ResponseEntity.ok(MilestoneService.MilestoneResponse.fromDocument(milestone));
    }
}
