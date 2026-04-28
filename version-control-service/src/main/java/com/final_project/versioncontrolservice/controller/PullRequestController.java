package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.dto.CreatePullRequest;
import com.final_project.versioncontrolservice.dto.MergeResponse;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.PullRequestApplicationService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.dto.PullRequestResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/repos")
public class PullRequestController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;
    private final PullRequestApplicationService pullRequestApplicationService;

    @PostMapping(path = "/{owner}/{repo}/pulls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PullRequestResponse> create(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam CreatePullRequest request
            ) {
        return ResponseEntity.ok(pullRequestApplicationService.create(
                owner,
                repo,
                request
        ));
    }

    @GetMapping(path = "/{owner}/{repo}/pulls", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PullRequestResponse>> list(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        return ResponseEntity.ok(pullRequestApplicationService.list(owner, repo));
    }

    @GetMapping(path = "/{owner}/{repo}/pulls/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PullRequestResponse> get(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        return ResponseEntity.ok(pullRequestApplicationService.find(id, owner, repo));
    }

    @PostMapping(path = "/{owner}/{repo}/pulls/{id}/merge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MergeResponse> merge(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        return ResponseEntity.ok(pullRequestApplicationService.merge(id, owner, repo));
    }
}
