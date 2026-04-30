package com.final_project.versioncontrolservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.final_project.versioncontrolservice.dto.*;
import com.final_project.versioncontrolservice.event.RepositoryOperationEvent;
import com.final_project.versioncontrolservice.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.final_project.versioncontrolservice.model.*;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.repo.InvitationRepository;

import lombok.AllArgsConstructor;

@Slf4j
@Service
@AllArgsConstructor
public class InvitationApplicationService {
    private final InvitationRepository invitationRepository;
    private final AuthService authService;
    private final RepositoryService repositoryService;
    private final KafkaProducer kafkaProducer;
    public InvitationResponse create(InvitationRequest request) {

        ContributorUser guest = authService.getContributorUser(request.getGuest());
        if (guest == null){
            throw new NotFoundException("The Guest User Not Found");
        }
        ContributorUser host = authService.getContributorUser(request.getHost());
        if (host == null){
            throw new NotFoundException("The Host user Not Found");
        }

        RepositoryDTO repo = repositoryService.repositoryByOwnerAndRepoName(host.getUsername(), request.getRepository());
        long pending = invitationRepository
                .countByRepository_UserNameIgnoreCaseAndRepository_RepositoryNameIgnoreCaseAndGuestUser_IdAndStatus(
                repo.getOwner(), repo.getRepositoryName(), guest.getId(), InvitationStatus.PENDING);

        if (pending > 0 ) {

            throw new BadRequestException("pending invitation already exists");
        }

        Invitation invitation = Invitation
                .builder()
                .repository(
                        RepositoryMetadata
                                .builder()
                                .description(repo.getDescription())
                                .type(repo.getVisibility())
                                .userName(repo.getOwner())
                                .repositoryName(repo.getRepositoryName())
                                .build()
                )
                .status(InvitationStatus.PENDING)
                .guestUser(UserDTO.builder()
                        .email(guest.getEmail())
                        .status(guest.getStatus())
                        .username(guest.getUsername())
                        .id(guest.getId())
                        .profile(guest.getProfile())
                        .firstName(guest.getFirstName())
                        .lastName(guest.getLastName())
                        .roles(guest.getRoles())
                        .build())
                .hostUser(UserDTO
                        .builder()
                        .username(host.getUsername())
                        .id(host.getId())
                        .email(host.getEmail())
                        .status(host.getStatus())
                        .lastName(host.getLastName())
                        .firstName(host.getFirstName())
                        .roles(host.getRoles())
                        .profile(host.getProfile())
                        .build())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .build();
       Invitation saved =  invitationRepository.save(invitation);
       kafkaProducer.produce(RepositoryOperationEvent
               .builder()
                       .eventId(UUID.randomUUID().toString())
                       .eventType(RepositoryEventType.REPOSITORY_INVITATION_SENT)
                       .actorEmail(host.getEmail())
                       .actorName(host.getUsername())
                       .actorUserId(host.getId())

                       .invitedUserEmail(guest.getEmail())
                       .invitedUserId(guest.getId())
                       .metadata(Map.of(
                               "operationBy", host.getUsername(),
                               "message", host.getUsername() + " invited to join " + invitation.getRepository().getRepositoryName()
                       ))
                       .invitedUserName(host.getUsername())
                       .ownerUserId(repo.getOwner())
                       .repositoryId(repo.getId())
                       .occurredAt(LocalDateTime.now())
                       .repositoryName(repo.getRepositoryName())
               .build());
        return InvitationResponse.from(saved);
    }



    public List<InvitationResponse> listPendingForUser(String userId) {
        ContributorUser user = authService.getContributorUser(userId);
        List<Invitation> pendingInvitation =  invitationRepository.findByGuestUser_IdAndStatus(user.getId(), InvitationStatus.PENDING);
        if (pendingInvitation.isEmpty()){
            return List.of();
        }
        return pendingInvitation
                .stream()
                .map(InvitationResponse::from).toList();
    }

    public InvitationResponse findById(String id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The Invitation With this not found"));
        return InvitationResponse.from(invitation);
    }


    public InvitationResponse accept(String invitationId, String userId) {

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        log.info(invitation.getStatus().name());
        if (!invitation.getGuestUser().getId().equals(userId)) {
            throw new ForbiddenException("You cannot accept this invitation");
        }

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new BadRequestException("Invitation is not pending");
        }

        if (invitation.getExpiresAt() != null &&
                invitation.getExpiresAt().isBefore(LocalDateTime.now())) {

            invitation.setStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitation);

            throw new BadRequestException("Invitation has expired");
        }

        repositoryService.addCollaborator(
                invitation.getRepository().getUserName(),
                invitation.getRepository().getRepositoryName(),
                ContributorRequest
                        .builder()
                        .email(invitation.getGuestUser().getEmail())
                        .username(invitation.getGuestUser().getUsername())
                        .id(invitation.getGuestUser().getId())
                        .build()
        );
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
        kafkaProducer.produce(RepositoryOperationEvent
                .builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType(RepositoryEventType.REPOSITORY_INVITATION_ACCEPTED)
                        .actorEmail(invitation.getGuestUser().getEmail())
                        .actorName(invitation.getGuestUser().getUsername())
                        .actorUserId(invitation.getGuestUser().getId())

                        .metadata(Map.of(
                                "operationBy", invitation.getHostUser().getUsername(),
                                "message", invitation.getGuestUser().getUsername() + " accepted the invitation to join " + invitation.getRepository().getRepositoryName()
                        ))
                        .ownerUserId(invitation.getHostUser().getId())
                        .ownerEmail(invitation.getHostUser().getEmail())
                        .ownerUserId(invitation.getHostUser().getUsername())

                .build());
        return InvitationResponse.from(invitation);
    }

    public InvitationResponse reject(String invitationId, String userId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        if (!invitation.getGuestUser().getId().equals(userId)) {
            throw new ForbiddenException("You cannot reject this invitation");
        }

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new BadRequestException("Invitation is not pending");
        }

        kafkaProducer.produce(RepositoryOperationEvent
                .builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(RepositoryEventType.REPOSITORY_INVITATION_DECLINED)
                .actorEmail(invitation.getGuestUser().getEmail())
                .actorName(invitation.getGuestUser().getUsername())
                .actorUserId(invitation.getGuestUser().getId())

                .metadata(Map.of(
                        "operationby", invitation.getHostUser().getUsername(),
                        "message", invitation.getGuestUser().getUsername() + " rejected the invitation to join " + invitation.getRepository().getRepositoryName()
                ))
                .ownerUserId(invitation.getHostUser().getId())
                .ownerEmail(invitation.getHostUser().getEmail())
                .ownerUserId(invitation.getHostUser().getUsername())

                .build());
        invitation.setStatus(InvitationStatus.REJECTED);
        Invitation saved =  invitationRepository.save(invitation);
        return InvitationResponse.from(saved);
    }
}
