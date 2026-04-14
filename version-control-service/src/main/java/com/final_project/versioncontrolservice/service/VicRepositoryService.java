package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.Collaborator;
import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import com.final_project.versioncontrolservice.repo.VicRepositoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class VicRepositoryService {

    private final VicRepositoryRepository repositoryRepository;
    private final MinioStorageService minio;

    public VicRepositoryService(VicRepositoryRepository repositoryRepository, MinioStorageService minio) {
        this.repositoryRepository = repositoryRepository;
        this.minio = minio;
    }

    public VicRepositoryDocument loadMeta(String owner, String repo) {
        return repositoryRepository
            .findByOwnerIgnoreCaseAndNameIgnoreCase(owner.trim(), repo.trim())
            .orElseThrow(() -> new NotFoundException("read repo metadata"));
    }

    public VicRepositoryDocument createRepo(String ownerUsername, String name, String description) {
        String owner = ownerUsername.trim().toLowerCase();
        String repoName = name.trim();
        if (repoName.isEmpty()) {
            throw new BadRequestException("owner and repository name are required");
        }
        if (repositoryRepository.existsByOwnerIgnoreCaseAndNameIgnoreCase(owner, repoName)) {
            throw new BadRequestException("repo already exists");
        }
        VicRepositoryDocument doc = new VicRepositoryDocument();
        doc.setId(VicRepositoryDocument.compositeId(owner, repoName));
        doc.setOwner(owner);
        doc.setName(repoName);
        doc.setDescription(description == null ? "" : description.trim());
        doc.setVisibility("private");
        doc.setSymbolicHead("refs/heads/main");
        doc.getBranchHeads().put("main", "");
        VicRepositoryDocument saved = repositoryRepository.save(doc);
        minio.ensureBuckets();
        minio.writeLayoutHead(saved.getOwner(), saved.getName());
        minio.writeLayoutBranchRef(saved.getOwner(), saved.getName(), "main", "");
        return saved;
    }

    public Map<String, Object> listRefs(VicRepositoryDocument meta) {
        Map<String, String> refs = new LinkedHashMap<>();
        for (var e : meta.getBranchHeads().entrySet()) {
            refs.put("refs/heads/" + e.getKey(), e.getValue() == null ? "" : e.getValue().trim());
        }
        return Map.of("HEAD", meta.getSymbolicHead(), "refs", refs);
    }

    public byte[] readObjectRaw(VicRepositoryDocument meta, String hash) {
        if (!ObjectHash.isValidSha1Hex(hash)) {
            throw new BadRequestException("invalid object hash");
        }
        try {
            return minio.getObjectBytes(meta.getOwner(), meta.getName(), hash.trim());
        } catch (MinioStorageService.StorageException e) {
            throw new NotFoundException("object not found");
        }
    }

    public void writeObject(VicRepositoryDocument meta, String hash, byte[] data) {
        if (data == null || data.length == 0) {
            throw new BadRequestException("empty object body");
        }
        if (!ObjectHash.isValidSha1Hex(hash)) {
            throw new BadRequestException("invalid object hash");
        }
        minio.ensureBuckets();
        minio.putObjectIfAbsent(meta.getOwner(), meta.getName(), hash.trim(), data);
    }

    public void updateBranchRef(VicRepositoryDocument meta, String branch, String hash) {
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
        if (!minio.objectExists(meta.getOwner(), meta.getName(), hash.trim())) {
            throw new BadRequestException("object does not exist on server");
        }
        meta.getBranchHeads().put(branch, hash.trim());
        repositoryRepository.save(meta);
        minio.writeLayoutBranchRef(meta.getOwner(), meta.getName(), branch, hash.trim());
    }

    public String listBranchHash(VicRepositoryDocument meta, String branch) {
        String h = meta.getBranchHeads().get(branch.trim());
        if (h == null) {
            return "";
        }
        return h.trim();
    }

    public void addCollaborator(String owner, String repo, String username, String role) {
        VicRepositoryDocument meta = loadMeta(owner, repo);
        String u = username.trim().toLowerCase();
        String r = role == null || role.isBlank() ? "write" : role.trim().toLowerCase();
        if (!List.of("read", "write", "admin").contains(r)) {
            throw new BadRequestException("invalid role");
        }
        if (meta.getCollaborators() == null) {
            meta.setCollaborators(new java.util.ArrayList<>());
        }
        for (Collaborator c : meta.getCollaborators()) {
            if (c.getUsername() != null && c.getUsername().equalsIgnoreCase(u)) {
                throw new BadRequestException("user is already a collaborator");
            }
        }
        meta.getCollaborators().add(new Collaborator(u, r));
        repositoryRepository.save(meta);
    }
    
}
