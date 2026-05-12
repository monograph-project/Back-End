package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.dto.DocumentBlameResponse;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.DerivedDocumentIndex;
import com.final_project.versioncontrolservice.repo.DerivedDocumentIndexRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class DocumentBlameService {

    private final MinioStorageService minioStorageService;
    private final DerivedDocumentIndexRepository derivedDocumentIndexRepository;
    private final DocumentExtractionService documentExtractionService;

    public DocumentBlameResponse getDocumentBlame(
            String owner,
            String repo,
            String branch,
            String commitHash,
            String filePath
    ) {
        String normalizedPath = normalizePath(filePath);

        if (!documentExtractionService.supports(normalizedPath)) {
            throw new BadRequestException("Document blame is not supported for file: " + normalizedPath);
        }

        String blobHash = findBlobHashAtCommit(owner, repo, commitHash, normalizedPath);

        if (blobHash == null) {
            throw new NotFoundException("file not found: " + normalizedPath);
        }

        DerivedDocumentIndex target = loadOrCreateIndex(
                owner,
                repo,
                branch,
                commitHash,
                normalizedPath,
                blobHash
        );

        List<DerivedDocumentIndex.DocumentSegment> targetSegments =
                safeSegments(target.getSegments());

        if (targetSegments.isEmpty()) {
            return DocumentBlameResponse.builder()
                    .path(normalizedPath)
                    .ref(branch)
                    .commitSha(commitHash)
                    .mode("document-segment-blame")
                    .fileType(target.getFileType())
                    .segments(List.of())
                    .build();
        }

        CommitMeta targetMeta = readCommitMeta(owner, repo, commitHash);

        List<SegmentState> states = new ArrayList<>();

        for (DerivedDocumentIndex.DocumentSegment segment : targetSegments) {
            states.add(new SegmentState(segment, targetMeta));
        }

        blameWalk(
                owner,
                repo,
                branch,
                commitHash,
                normalizedPath,
                states,
                new HashSet<>()
        );

        List<DocumentBlameResponse.DocumentBlameSegment> responseSegments =
                states.stream()
                        .map(state -> {
                            CommitMeta meta = state.commitMeta;

                            return DocumentBlameResponse.DocumentBlameSegment.builder()
                                    .id(state.segment.getId())
                                    .kind(state.segment.getKind())
                                    .text(state.segment.getText())
                                    .stableHash(state.segment.getStableHash())
                                    .page(state.segment.getPage())
                                    .orderIndex(state.segment.getOrderIndex())
                                    .commitSha(meta != null ? meta.sha : "")
                                    .shortSha(meta != null ? shortSha(meta.sha) : "")
                                    .author(meta != null ? meta.author : "")
                                    .message(meta != null ? meta.message : "")
                                    .timestamp(meta != null ? meta.timestamp : "")
                                    .build();
                        })
                        .toList();

        return DocumentBlameResponse.builder()
                .path(normalizedPath)
                .ref(branch)
                .commitSha(commitHash)
                .mode("document-segment-blame")
                .fileType(target.getFileType())
                .segments(responseSegments)
                .build();
    }

    private void blameWalk(
            String owner,
            String repo,
            String branch,
            String childCommitHash,
            String filePath,
            List<SegmentState> states,
            Set<String> visited
    ) {
        if (childCommitHash == null || childCommitHash.isBlank()) {
            return;
        }

        if (!visited.add(childCommitHash)) {
            return;
        }

        CommitMeta childMeta = readCommitMeta(owner, repo, childCommitHash);

        if (childMeta.parents == null || childMeta.parents.isEmpty()) {
            return;
        }

        /*
         * First-parent document blame.
         *
         * This is correct for the first stable version.
         * Later, for merge commits, you can improve this by checking all parents
         * and selecting the parent with the best segment match.
         */
        String parentHash = childMeta.parents.get(0);

        String parentBlobHash = findBlobHashAtCommit(owner, repo, parentHash, filePath);

        if (parentBlobHash == null) {
            return;
        }

        DerivedDocumentIndex parent = loadOrCreateIndex(
                owner,
                repo,
                branch,
                parentHash,
                filePath,
                parentBlobHash
        );

        CommitMeta parentMeta = readCommitMeta(owner, repo, parentHash);

        List<DerivedDocumentIndex.DocumentSegment> parentSegments =
                safeSegments(parent.getSegments());

        List<String> parentHashes = parentSegments.stream()
                .map(segment -> safeHash(segment.getStableHash()))
                .toList();

        List<String> childHashes = states.stream()
                .map(state -> safeHash(state.segment.getStableHash()))
                .toList();

        boolean[] unchangedChildSegments = findUnchangedChildSegments(parentHashes, childHashes);

        for (int i = 0; i < states.size(); i++) {
            SegmentState state = states.get(i);

            if (
                    unchangedChildSegments[i]
                            && state.commitMeta != null
                            && childCommitHash.equals(state.commitMeta.sha)
            ) {
                state.commitMeta = parentMeta;
            }
        }

        blameWalk(
                owner,
                repo,
                branch,
                parentHash,
                filePath,
                states,
                visited
        );
    }

    private DerivedDocumentIndex loadOrCreateIndex(
            String owner,
            String repo,
            String branch,
            String commitHash,
            String filePath,
            String blobHash
    ) {
        return derivedDocumentIndexRepository
                .findByOwnerUsernameIgnoreCaseAndRepositoryNameIgnoreCaseAndCommitHashAndPathAndBlobHash(
                        owner,
                        repo,
                        commitHash,
                        filePath,
                        blobHash
                )
                .orElseGet(() -> createIndex(
                        owner,
                        repo,
                        branch,
                        commitHash,
                        filePath,
                        blobHash
                ));
    }

    private DerivedDocumentIndex createIndex(
            String owner,
            String repo,
            String branch,
            String commitHash,
            String filePath,
            String blobHash
    ) {
        byte[] bytes = readBlobBytes(owner, repo, blobHash);

        if (bytes.length == 0) {
            throw new NotFoundException("empty or unreadable blob: " + blobHash);
        }

        if (!documentExtractionService.supports(filePath)) {
            throw new BadRequestException("Document blame is not supported for file: " + filePath);
        }

        DocumentExtractionService.ExtractionResult extraction =
                documentExtractionService.extract(filePath, bytes);

        List<DerivedDocumentIndex.DocumentSegment> extractedSegments =
                extraction.getSegments() != null ? extraction.getSegments() : List.of();

        DerivedDocumentIndex index = DerivedDocumentIndex.builder()
                .ownerUsername(owner)
                .repositoryName(repo)
                .branch(branch)
                .path(filePath)
                .fileName(leafName(filePath))
                .blobHash(blobHash)
                .commitHash(commitHash)
                .fileType(extraction.getFileType())
                .indexedAt(Instant.now())
                .segments(extractedSegments)
                .build();

        return derivedDocumentIndexRepository.save(index);
    }

    private String findBlobHashAtCommit(
            String owner,
            String repo,
            String commitHash,
            String path
    ) {
        if (path == null || path.isBlank()) {
            return null;
        }

        try {
            byte[] commitData = minioStorageService.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
            VicObjectFormat.CommitData commitInfo =
                    VicObjectFormat.parseCommitContent(commitObj.content());

            String normalizedPath = normalizePath(path);
            String[] pathParts = normalizedPath.split("/");

            String currentTree = commitInfo.tree();

            for (int i = 0; i < pathParts.length - 1; i++) {
                currentTree = findTreeEntry(owner, repo, currentTree, pathParts[i]);

                if (currentTree == null) {
                    return null;
                }
            }

            return findTreeEntry(owner, repo, currentTree, pathParts[pathParts.length - 1]);

        } catch (Exception e) {
            return null;
        }
    }

    private String findTreeEntry(
            String owner,
            String repo,
            String treeHash,
            String name
    ) {
        if (treeHash == null || treeHash.isBlank()) {
            return null;
        }

        try {
            byte[] treeData = minioStorageService.getObjectBytes(owner, repo, treeHash);
            VicObjectFormat.ParsedObject treeObj = VicObjectFormat.parseCompressed(treeData);

            String[] lines = new String(treeObj.content(), StandardCharsets.UTF_8)
                    .split("\n");

            for (String line : lines) {
                if (line == null || line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\t");

                if (parts.length >= 4 && Objects.equals(parts[3], name)) {
                    return parts[2];
                }
            }

        } catch (Exception ignored) {
            return null;
        }

        return null;
    }

    private byte[] readBlobBytes(
            String owner,
            String repo,
            String blobHash
    ) {
        if (blobHash == null || blobHash.isBlank()) {
            return new byte[0];
        }

        try {
            byte[] blobData = minioStorageService.getObjectBytes(owner, repo, blobHash);
            VicObjectFormat.ParsedObject blobObj = VicObjectFormat.parseCompressed(blobData);

            if (!"blob".equals(blobObj.type())) {
                return new byte[0];
            }

            return blobObj.content();

        } catch (Exception e) {
            return new byte[0];
        }
    }

    private CommitMeta readCommitMeta(
            String owner,
            String repo,
            String commitHash
    ) {
        try {
            byte[] commitData = minioStorageService.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
            VicObjectFormat.CommitData commitInfo =
                    VicObjectFormat.parseCommitContent(commitObj.content());

            String commitContent = new String(commitObj.content(), StandardCharsets.UTF_8);

            String authorHeader = extractHeader(commitContent, "author");
            String committerHeader = extractHeader(commitContent, "committer");

            String author = extractAuthorName(authorHeader);
            String timestamp = extractAuthorTimestamp(authorHeader);

            if (author.isBlank()) {
                author = extractAuthorName(committerHeader);
            }

            if (timestamp.isBlank()) {
                timestamp = extractAuthorTimestamp(committerHeader);
            }

            return CommitMeta.builder()
                    .sha(commitHash)
                    .author(author)
                    .message(extractMessage(commitContent))
                    .timestamp(timestamp)
                    .parents(commitInfo.parents())
                    .build();

        } catch (Exception e) {
            throw new NotFoundException("commit not found: " + commitHash);
        }
    }

    private boolean[] findUnchangedChildSegments(
            List<String> parentHashes,
            List<String> childHashes
    ) {
        boolean[] unchanged = new boolean[childHashes.size()];

        int[][] lcs = buildLcsTable(parentHashes, childHashes);

        int i = 0;
        int j = 0;

        while (i < parentHashes.size() && j < childHashes.size()) {
            if (Objects.equals(parentHashes.get(i), childHashes.get(j))) {
                unchanged[j] = true;
                i++;
                j++;
            } else if (lcs[i + 1][j] >= lcs[i][j + 1]) {
                i++;
            } else {
                j++;
            }
        }

        return unchanged;
    }

    private int[][] buildLcsTable(
            List<String> a,
            List<String> b
    ) {
        int[][] dp = new int[a.size() + 1][b.size() + 1];

        for (int i = a.size() - 1; i >= 0; i--) {
            for (int j = b.size() - 1; j >= 0; j--) {
                if (Objects.equals(a.get(i), b.get(j))) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        return dp;
    }

    private List<DerivedDocumentIndex.DocumentSegment> safeSegments(
            List<DerivedDocumentIndex.DocumentSegment> segments
    ) {
        return segments != null ? segments : List.of();
    }

    private String safeHash(String hash) {
        return hash != null ? hash : "";
    }

    private String normalizePath(String path) {
        if (path == null) {
            return "";
        }

        return path.trim().replace("\\", "/");
    }

    private String extractHeader(
            String content,
            String key
    ) {
        if (content == null || content.isBlank()) {
            return "";
        }

        for (String line : content.split("\n")) {
            if (line.startsWith(key + " ")) {
                return line.substring(key.length() + 1);
            }
        }

        return "";
    }

    private String extractMessage(String content) {
        if (content == null) {
            return "";
        }

        String[] parts = content.split("\n\n", 2);

        return parts.length > 1 ? parts[1].trim() : "";
    }

    private String extractAuthorName(String authorHeader) {
        if (authorHeader == null || authorHeader.isBlank()) {
            return "";
        }

        int emailStart = authorHeader.indexOf('<');

        if (emailStart > 0) {
            return authorHeader.substring(0, emailStart).trim();
        }

        return authorHeader.trim();
    }

    private String extractAuthorTimestamp(String authorHeader) {
        if (authorHeader == null || authorHeader.isBlank()) {
            return "";
        }

        int emailEnd = authorHeader.indexOf('>');

        if (emailEnd < 0 || emailEnd + 1 >= authorHeader.length()) {
            return "";
        }

        String tail = authorHeader.substring(emailEnd + 1).trim();
        String[] parts = tail.split("\\s+");

        if (parts.length < 1) {
            return "";
        }

        try {
            long epochSeconds = Long.parseLong(parts[0]);
            return Instant.ofEpochSecond(epochSeconds).toString();
        } catch (NumberFormatException e) {
            return "";
        }
    }

    private String shortSha(String sha) {
        if (sha == null || sha.isBlank()) {
            return "";
        }

        return sha.length() >= 8 ? sha.substring(0, 8) : sha;
    }

    private String leafName(String path) {
        if (path == null || path.isBlank()) {
            return "";
        }

        String normalized = path.replace("\\", "/");
        int idx = normalized.lastIndexOf('/');

        return idx < 0 ? normalized : normalized.substring(idx + 1);
    }

    private static class SegmentState {
        private final DerivedDocumentIndex.DocumentSegment segment;
        private CommitMeta commitMeta;

        private SegmentState(
                DerivedDocumentIndex.DocumentSegment segment,
                CommitMeta commitMeta
        ) {
            this.segment = segment;
            this.commitMeta = commitMeta;
        }
    }

    @Data
    @Builder
    private static class CommitMeta {
        private String sha;
        private String author;
        private String message;
        private String timestamp;
        private List<String> parents;
    }
}