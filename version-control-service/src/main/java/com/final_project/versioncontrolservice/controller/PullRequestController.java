package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.model.PullRequestDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.PullRequestApplicationService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.dto.PullRequestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PullRequestController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;
    private final PullRequestApplicationService pullRequestApplicationService;

    public PullRequestController(
            AuthService authService,
            RepositoryService vicRepositoryService,
            PullRequestApplicationService pullRequestApplicationService
    ) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
        this.pullRequestApplicationService = pullRequestApplicationService;
    }

    @PostMapping(path = "/repos/{owner}/{repo}/pulls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PullRequestResponse> create(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody CreatePrBody body
    ) {
        ContributorUser user = authService.getContributorUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (body == null) {
            throw new BadRequestException("invalid json body");
        }
        PullRequestDocument pr = pullRequestApplicationService.create(
                meta,
                user.getUsername(),
                body.sourceBranch(),
                body.targetBranch(),
                body.title(),
                body.description()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(PullRequestResponse.from(pr));
    }

    @GetMapping(path = "/repos/{owner}/{repo}/pulls", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PullRequestResponse> list(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.getContributorUser(authorization).getUsername();
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        return pullRequestApplicationService.list(meta).stream().map(PullRequestResponse::from).toList();
    }

    @GetMapping(path = "/repos/{owner}/{repo}/pulls/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PullRequestResponse get(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.getContributorUser(authorization).getUsername();
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        PullRequestDocument pr = pullRequestApplicationService.find(meta, id);
        return PullRequestResponse.from(pr);
    }

    @PostMapping(path = "/repos/{owner}/{repo}/pulls/{id}/merge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> merge(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        ContributorUser user = authService.getContributorUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        PullRequestDocument pr = pullRequestApplicationService.find(meta, id);
        pullRequestApplicationService.merge(meta, pr, user.getUsername());
        return ResponseEntity.ok(Map.of("status", "merged"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CreatePrBody(
            String sourceBranch,
            String targetBranch,
            String title,
            String description
    ) {}
}
