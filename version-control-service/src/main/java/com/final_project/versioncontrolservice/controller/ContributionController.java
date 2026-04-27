package com.final_project.versioncontrolservice.controller;
import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.ContributionService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ContributionController {
    private final AuthService authService;
    private final ContributionService contributionService;
    private final RepositoryService vicRepositoryService;

    /**
     * Get contribution statistics for a repository
     * GET /repos/{owner}/{repo}/contributors
     */
    @GetMapping(value = "/repos/{owner}/{repo}/contributors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContributionService.ContributionStats> getContributors(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        ContributorUser user = authService.getContributorUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String username = user != null ? user.getUsername(): "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }

        ContributionService.ContributionStats stats = contributionService.getContributionStats(owner, repo);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get contribution graph for a user
     * GET /repos/{owner}/{repo}/contributors/{username}/graph
     */
    @GetMapping(value = "/repos/{owner}/{repo}/contributors/{username}/graph", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContributionService.ContributionGraph> getContributionGraph(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String username
    ) {
        ContributorUser user = authService.getContributorUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String currentUser = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, currentUser)) {
            throw new ForbiddenException("forbidden");
        }

        ContributionService.ContributionGraph graph = contributionService.getContributionGraph(owner, repo, username);
        return ResponseEntity.ok(graph);
    }

    /**
     * Get user activity feed
     * GET /users/{username}/activity
     */
    @GetMapping(value = "/users/{username}/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContributionService.ActivityEvent>> getUserActivity(
            @PathVariable String username,
            @RequestParam(defaultValue = "20") int limit
    ) {
        List<ContributionService.ActivityEvent> events = contributionService.getUserActivity(username, limit);
        return ResponseEntity.ok(events);
    }
}
