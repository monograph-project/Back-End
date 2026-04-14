package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import com.final_project.versioncontrolservice.model.UserDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.InvitationApplicationService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.VicRepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.dto.InvitationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class InvitationController {

    private final AuthService authService;
    private final VicRepositoryService vicRepositoryService;
    private final InvitationApplicationService invitationApplicationService;

    public InvitationController(
            AuthService authService,
            VicRepositoryService vicRepositoryService,
            InvitationApplicationService invitationApplicationService
    ) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
        this.invitationApplicationService = invitationApplicationService;
    }

    @PostMapping(path = "/repos/{owner}/{repo}/invitations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody InviteBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, user.getUsername())) {
            throw new ForbiddenException("only repository admins can invite collaborators");
        }
        if (body == null) {
            throw new BadRequestException("invalid json body");
        }
        invitationApplicationService.create(meta, body.username(), body.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "invited"));
    }

    @GetMapping(path = "/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InvitationResponse> listMine(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization
    ) {
        UserDocument user = authService.requireUser(authorization);
        return invitationApplicationService.listPendingForUser(user.getUsername()).stream()
                .map(InvitationResponse::from)
                .toList();
    }

    @PostMapping(path = "/invitations/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> accept(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String id
    ) {
        UserDocument user = authService.requireUser(authorization);
        InvitationDocument inv = invitationApplicationService.findById(id);
        invitationApplicationService.accept(inv, user.getUsername());
        return ResponseEntity.ok(Map.of("status", "accepted"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record InviteBody(String username, String role) {}
}
