package com.final_project.versioncontrolservice.service;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import com.final_project.versioncontrolservice.repo.InvitationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvitationApplicationService {
    private final InvitationRepository invitationRepository;
    private final VicRepositoryService vicRepositoryService;



    public void create(VicRepositoryDocument meta, String invitedUser, String role) {
        String u = invitedUser.trim().toLowerCase();
        if (u.isEmpty()) {
            throw new BadRequestException("username is required");
        }
        String r = role == null || role.isBlank() ? "write" : role.trim().toLowerCase();
        if (!List.of("read", "write", "admin").contains(r)) {
            throw new BadRequestException("invalid role");
        }
        long pending = invitationRepository.countByRepoOwnerAndRepoNameAndInvitedUserAndStatus(
                meta.getOwner(), meta.getName(), u, "pending");
        if (pending > 0) {
            throw new BadRequestException("pending invitation already exists");
        }
        InvitationDocument inv = new InvitationDocument();
        inv.setRepoOwner(meta.getOwner());
        inv.setRepoName(meta.getName());
        inv.setInvitedUser(u);
        inv.setRole(r);
        inv.setStatus("pending");
        inv.setCreatedAt(Instant.now());
        invitationRepository.save(inv);
    }

    public List<InvitationDocument> listPendingForUser(String username) {
        return invitationRepository.findByInvitedUserAndStatus(username.trim().toLowerCase(), "pending");
    }

    public InvitationDocument findById(String idHex) {
        ObjectId id;
        try {
            id = new ObjectId(idHex.trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("invalid invitation id");
        }
        return invitationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("invitation not found"));
    }

    public void accept(InvitationDocument inv, String username) {
        if (!inv.getInvitedUser().equalsIgnoreCase(username.trim())) {
            throw new ForbiddenException("you cannot accept this invitation");
        }
        if (!"pending".equals(inv.getStatus())) {
            throw new BadRequestException("invitation is not pending");
        }
        vicRepositoryService.addCollaborator(inv.getRepoOwner(), inv.getRepoName(), inv.getInvitedUser(), inv.getRole());
        inv.setStatus("accepted");
        invitationRepository.save(inv);
    }
}
