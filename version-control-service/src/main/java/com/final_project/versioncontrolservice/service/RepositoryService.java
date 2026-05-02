package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.dto.*;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.ContributorStatus;
import com.final_project.versioncontrolservice.model.Invitation;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import com.final_project.versioncontrolservice.model.RepositoryFileIndex;
import com.final_project.versioncontrolservice.repo.InvitationRepository;
import com.final_project.versioncontrolservice.repo.RepositoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final MinioStorageService minio;
    private final AuthService authService;
    private final InvitationRepository invitationRepository;
    private final RepositoryFileIndexService fileIndex;
    public RepositoryDocument loadMeta(String userName, String repo) {
        return repositoryRepository
            .findByOwner_UsernameIgnoreCaseAndRepositoryNameIgnoreCase(userName.trim(), repo.trim())
            .orElseThrow(() -> new NotFoundException("read repo metadata"));
    }

    public RepositoryResponse createRepo(CreateRepositoryRequest request) {
        UserDTO user = authService.getUserByUsername(request.getUserName());
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (repositoryRepository.existsByOwner_UsernameIgnoreCaseAndRepositoryNameIgnoreCase(user.getUsername(), request.getRepositoryName().trim())) {
            throw new BadRequestException("repo already exists");
        }
       RepositoryDocument repo =  RepositoryDocument
                .builder()
                .repositoryName(request.getRepositoryName())
                .cloneUrl("http://localhost:8000/api/v1/repo/..")
                .description(request.getDescription())
                .owner(
                        UserDTO.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .emailVerified(user.getEmailVerified())
                                .username(user.getUsername())
                                .status(user.getStatus())
                                .build()
                )
                .visibility(request.getVisibility())
                .symbolicHead("refs/heads/main")
                .branchHeads(Map.of("main", ""))
                                                                .build();
       RepositoryDocument saved = repositoryRepository.save(repo);


        minio.ensureBuckets();
        minio.writeLayoutHead(saved.getOwner().getUsername(), saved.getRepositoryName());
        minio.writeLayoutBranchRef(saved.getOwner().getUsername(), saved.getRepositoryName(), "main", "");

        return RepositoryResponse
                .builder()
                .id(saved.getId())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .branchHeads(saved.getBranchHeads())
                .repositoryName(saved.getRepositoryName())
                .cloneUrl(saved.getCloneUrl())
                .collaborators(saved.getCollaborators())
                .description(saved.getDescription())
                .owner(
                        UserDTO.builder()
                                .id(saved.getOwner().getId())
                                .email(saved.getOwner().getEmail())
                                .emailVerified(saved.getOwner().getEmailVerified())
                                .username(saved.getOwner().getUsername())
                                .status(saved.getOwner().getStatus())
                                .build()
                )
                .build()
                ;
    }

    public Map<String, Object> listRefs(RepositoryDocument meta) {
        Map<String, String> refs = new LinkedHashMap<>();
        for (var e : meta.getBranchHeads().entrySet()) {
            refs.put("refs/heads/" + e.getKey(), e.getValue() == null ? "" : e.getValue().trim());
        }
        return Map.of("HEAD", meta.getSymbolicHead(), "refs", refs);
    }

    public byte[] readObjectRaw(RepositoryDocument meta, String hash) {
        if (!ObjectHash.isValidSha1Hex(hash)) {
            throw new BadRequestException("invalid object hash");
        }
        try {
            return minio.getObjectBytes(meta.getOwner().getUsername(), meta.getRepositoryName(), hash.trim());
        } catch (MinioStorageService.StorageException e) {
            throw new NotFoundException("object not found");
        }
    }

    public void writeObject(RepositoryDocument meta, String hash, byte[] data) {
        if (data == null || data.length == 0) {
            throw new BadRequestException("empty object body");
        }
        if (!ObjectHash.isValidSha1Hex(hash)) {
            throw new BadRequestException("invalid object hash");
        }
        minio.ensureBuckets();
        minio.putObjectIfAbsent(meta.getOwner().getUsername(), meta.getRepositoryName(), hash.trim(), data);
    }

    public void updateBranchRef(RepositoryDocument meta, String branch, String hash) throws IOException {
        branch = branch.trim();
        if (branch.isEmpty()) {
            throw new BadRequestException("owner, repo, branch and hash are required");
        }
        for (char c : branch.toCharArray()) {
            if ("\\/:*?\"<>|".indexOf(c) >= 0) {
                throw new BadRequestException("invalid branch name");
            }
        }
        if (!ObjectHash.isValidSha1Hex(hash)) {
            throw new BadRequestException("invalid object hash");
        }
        if (!minio.objectExists(meta.getOwner().getUsername(), meta.getRepositoryName(), hash.trim())) {
            throw new BadRequestException("object does not exist on server");
        }
        meta.getBranchHeads().put(branch, hash.trim());
        repositoryRepository.save(meta);
        minio.writeLayoutBranchRef(meta.getOwner().getUsername(), meta.getRepositoryName(), branch, hash.trim());
        fileIndex.rebuildIndex(meta, branch, hash.trim());
    }

//    public String listBranchHash(RepositoryDocument meta, String branch) {
//        String h = meta.getBranchHeads().get(branch.trim());
//        if (h == null) {
//            return "";
//        }
//        return h.trim();
//    }
    public String listBranchHash(RepositoryDocument meta, String branch) {

        if (meta == null) {
            throw new NotFoundException("Repository not found");
        }

        if (branch == null || branch.trim().isEmpty()) {
            throw new BadRequestException("Branch is required");
        }

        String branchName = branch.trim();

        if (meta.getBranchHeads() == null || !meta.getBranchHeads().containsKey(branchName)) {
            throw new BadRequestException("Branch does not exist: " + branchName);
        }

        String hash = meta.getBranchHeads().get(branchName);

        if (hash == null || hash.trim().isEmpty()) {
            throw new BadRequestException("Branch has no commits yet: " + branchName);
        }

        return hash.trim();
    }

    @Transactional
    public void addCollaborator(String owner, String repo, ContributorRequest request) {
        RepositoryDocument meta = loadMeta(owner, repo);
        ContributorUser contributorUser = authService.getContributorUser(request.getId());
        if (contributorUser == null){
            throw new BadRequestException("There is No such User");
        }

        if (meta.getCollaborators() == null) {
            meta.setCollaborators(new java.util.ArrayList<>());
        }
        if (meta.getCollaborators().stream()
                .anyMatch(col ->
                        col.getId().
                                equals(contributorUser.getId()))

        ) {
            throw new BadRequestException("User Already Contribute to this repository");
        }
        contributorUser.setContributorStatus(ContributorStatus.ACCEPTED);
        meta.getCollaborators().add(contributorUser);
        repositoryRepository.save(meta);
    }

    public RepositoryDTO getRepositoryById(String id){
        RepositoryDocument repo = repositoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Repository doesn't exist"));
        return RepositoryDTO
                .builder()
                .owner(repo.getOwner().getUsername())
                .visibility(repo.getVisibility())
                .collaborators(repo.getCollaborators())
                .description(repo.getDescription())
                .repositoryName(repo.getRepositoryName())
                .cloneUrl(repo.getCloneUrl())
                .build();
    }

    public RepositoryDTO repositoryByOwnerAndRepoName(String ownerName, String repositoryName){

        RepositoryDocument repo = repositoryRepository.findByOwner_UsernameIgnoreCaseAndRepositoryNameIgnoreCase(ownerName, repositoryName)
                .orElseThrow(() -> new NotFoundException("with this detail, there is no such user"));
        return RepositoryDTO
                .builder()
                .owner(repo.getOwner().getUsername())
                .repositoryName(repo.getRepositoryName())
                .cloneUrl(repo.getCloneUrl())
                .visibility(repo.getVisibility())
                .build();
    }

    public RepositoryDTO removeBlockUserFromRepository(String ownerId, String gustUser, String repositoryName){
        ContributorUser owner = authService.getContributorUser(ownerId);
        if (owner == null){
            throw new NotFoundException("The Usr owner not found");
        }

        RepositoryDocument repo = repositoryRepository.findByOwner_UsernameIgnoreCaseAndRepositoryNameIgnoreCase(owner.getUsername(),repositoryName)
                .orElseThrow(() -> new NotFoundException("There is no such repo"));
        List<ContributorUser> currentContributor =  repo.getCollaborators().stream().filter((cont) -> !cont.getId().equals(gustUser)).toList();
        repo.setCollaborators(currentContributor);
        RepositoryDocument saved =  repositoryRepository.save(repo);
        removeInvitation(owner.getUsername(), gustUser,repositoryName);
        return RepositoryDTO
                .builder()
                .owner(saved.getOwner().getUsername())
                .cloneUrl(saved.getCloneUrl())
                .visibility(saved.getVisibility())
                .repositoryName(saved.getRepositoryName())
                .id(saved.getId())
                .collaborators(saved.getCollaborators())
                .description(saved.getDescription())
                .build();
    }
    public void removeInvitation(String owner, String guestUser, String repo){

        Invitation invitation =  invitationRepository.findByHostUser_UsernameIgnoreCaseAndRepository_UserNameIgnoreCaseAndRepository_RepositoryNameIgnoreCase(owner,guestUser,repo)
                .orElseThrow(() -> new NotFoundException("Not Found"));
        invitationRepository.delete(invitation);
    }
    
}
