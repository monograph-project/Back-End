package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.model.UserDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.VicRepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RepositoryController {

    private final AuthService authService;
    private final VicRepositoryService vicRepositoryService;

    public RepositoryController(AuthService authService, VicRepositoryService vicRepositoryService) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
    }

    @PostMapping(path = "/repos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createRepo(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody CreateRepoBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        if (body == null || body.name() == null || body.name().isBlank()) {
            throw new BadRequestException("repository name is required");
        }
        var doc = vicRepositoryService.createRepo(user.getUsername(), body.name(), body.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "created",
                "owner", doc.getOwner(),
                "name", doc.getName()
        ));
    }

    @GetMapping(path = "/repos/{owner}/{repo}/info/refs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> infoRefs(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        return vicRepositoryService.listRefs(meta);
    }

    @GetMapping(path = "/repos/{owner}/{repo}/objects/{hash}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getObject(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String hash
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        byte[] data = vicRepositoryService.readObjectRaw(meta, hash.trim());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    }

    @PostMapping(
            path = "/repos/{owner}/{repo}/objects/{hash}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, String>> uploadObject(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String hash,
            @RequestBody byte[] body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, user.getUsername())) {
            throw new ForbiddenException("forbidden");
        }
        vicRepositoryService.writeObject(meta, hash.trim(), body);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "stored"));
    }

    @PostMapping(
            path = "/repos/{owner}/{repo}/refs/heads/{branch}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UpdateBranchResponse updateBranch(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String branch,
            @RequestBody UpdateBranchBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, user.getUsername())) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }
        if (body == null || body.hash() == null || body.hash().isBlank()) {
            throw new BadRequestException("hash is required");
        }
        vicRepositoryService.updateBranchRef(meta, branch, body.hash().trim());
        return new UpdateBranchResponse("updated", branch.trim());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CreateRepoBody(String name, String description) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record UpdateBranchBody(String hash) {}

    /** Matches Go: JSON field {@code hash} holds the branch name. */
    public record UpdateBranchResponse(String status, @JsonProperty("hash") String branchName) {}
}
