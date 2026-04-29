package com.final_project.versioncontrolservice.service;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@Service
@AllArgsConstructor
public class PullRequestMergeService {

    private final MinioStorageService minio;

    public MergeAnalysis analyze(
            String owner,
            String repo,
            String baseHash,
            String targetHash,
            String sourceHash
    ) {
        CommitInfo baseCommit = readCommit(owner, repo, baseHash);
        CommitInfo targetCommit = readCommit(owner, repo, targetHash);
        CommitInfo sourceCommit = readCommit(owner, repo, sourceHash);

        Map<String, TreeEntry> baseTree = readTreeRecursive(owner, repo, baseCommit.getTreeHash());
        Map<String, TreeEntry> targetTree = readTreeRecursive(owner, repo, targetCommit.getTreeHash());
        Map<String, TreeEntry> sourceTree = readTreeRecursive(owner, repo, sourceCommit.getTreeHash());

        Map<String, TreeEntry> merged = new TreeMap<>();
        List<ConflictFile> conflicts = new ArrayList<>();

        Set<String> allPaths = new TreeSet<>();
        allPaths.addAll(baseTree.keySet());
        allPaths.addAll(targetTree.keySet());
        allPaths.addAll(sourceTree.keySet());

        for (String path : allPaths) {
            TreeEntry base = baseTree.get(path);
            TreeEntry target = targetTree.get(path);
            TreeEntry source = sourceTree.get(path);

            String baseBlob = hashOf(base);
            String targetBlob = hashOf(target);
            String sourceBlob = hashOf(source);

            // Same result on both branches
            if (Objects.equals(targetBlob, sourceBlob)) {
                if (source != null) {
                    merged.put(path, source);
                }
                continue;
            }

            // Target did not change, source changed
            if (Objects.equals(targetBlob, baseBlob)) {
                if (source != null) {
                    merged.put(path, source);
                }
                continue;
            }

            // Source did not change, target changed
            if (Objects.equals(sourceBlob, baseBlob)) {
                if (target != null) {
                    merged.put(path, target);
                }
                continue;
            }

            // Both changed differently
            conflicts.add(ConflictFile.builder()
                    .path(path)
                    .baseHash(baseBlob)
                    .targetHash(targetBlob)
                    .sourceHash(sourceBlob)
                    .baseContent(readBlobAsStringOrEmpty(owner, repo, baseBlob))
                    .targetContent(readBlobAsStringOrEmpty(owner, repo, targetBlob))
                    .sourceContent(readBlobAsStringOrEmpty(owner, repo, sourceBlob))
                    .build());
        }

        if (!conflicts.isEmpty()) {
            return MergeAnalysis.builder()
                    .hasConflicts(true)
                    .conflicts(conflicts)
                    .mergedEntries(merged)
                    .build();
        }

        String mergedTreeHash = writeTree(owner, repo, merged);

        return MergeAnalysis.builder()
                .hasConflicts(false)
                .conflicts(List.of())
                .mergedEntries(merged)
                .mergedTreeHash(mergedTreeHash)
                .build();
    }

    public String createMergeCommit(
            String owner,
            String repo,
            String mergedTreeHash,
            String targetParentHash,
            String sourceParentHash,
            String message,
            String authorUsername
    ) {
        String author = authorUsername == null || authorUsername.isBlank()
                ? "system"
                : authorUsername.trim();

        long timestamp = Instant.now().getEpochSecond();

        StringBuilder content = new StringBuilder();
        content.append("tree ").append(mergedTreeHash).append("\n");
        content.append("parent ").append(targetParentHash).append("\n");
        content.append("parent ").append(sourceParentHash).append("\n");
        content.append("author ").append(author).append(" <").append(author).append("@vic.local> ")
                .append(timestamp).append(" +0000\n");
        content.append("committer ").append(author).append(" <").append(author).append("@vic.local> ")
                .append(timestamp).append(" +0000\n");
        content.append("\n");
        content.append(message == null || message.isBlank() ? "Merge pull request" : message.trim());
        content.append("\n");

        return writeObject(owner, repo, "commit", content.toString().getBytes(StandardCharsets.UTF_8));
    }

    public String writeMergedTreeFromResolvedFiles(
            String owner,
            String repo,
            Map<String, TreeEntry> cleanMergedEntries,
            Map<String, String> resolvedContentByPath
    ) {
        Map<String, TreeEntry> finalEntries = new TreeMap<>(cleanMergedEntries);

        for (Map.Entry<String, String> resolved : resolvedContentByPath.entrySet()) {
            String path = normalizePath(resolved.getKey());
            String content = resolved.getValue() == null ? "" : resolved.getValue();

            String blobHash = writeObject(
                    owner,
                    repo,
                    "blob",
                    content.getBytes(StandardCharsets.UTF_8)
            );

            finalEntries.put(path, TreeEntry.builder()
                    .path(path)
                    .name(leafName(path))
                    .mode("100644")
                    .type("blob")
                    .hash(blobHash)
                    .build());
        }

        return writeTree(owner, repo, finalEntries);
    }

    private CommitInfo readCommit(String owner, String repo, String commitHash) {
        try {
            byte[] raw = minio.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject obj = VicObjectFormat.parseCompressed(raw);

            if (!"commit".equals(obj.type())) {
                throw new BadRequestException("Object is not a commit: " + commitHash);
            }

            VicObjectFormat.CommitData data = VicObjectFormat.parseCommitContent(obj.content());

            return CommitInfo.builder()
                    .hash(commitHash)
                    .treeHash(data.tree())
                    .parents(data.parents())
                    .build();

        } catch (Exception e) {
            throw new BadRequestException("Cannot read commit " + commitHash + ": " + e.getMessage());
        }
    }

    private Map<String, TreeEntry> readTreeRecursive(String owner, String repo, String treeHash) {
        Map<String, TreeEntry> result = new TreeMap<>();
        readTreeRecursive(owner, repo, treeHash, "", result);
        return result;
    }

    private void readTreeRecursive(
            String owner,
            String repo,
            String treeHash,
            String prefix,
            Map<String, TreeEntry> result
    ) {
        try {
            byte[] raw = minio.getObjectBytes(owner, repo, treeHash);
            VicObjectFormat.ParsedObject obj = VicObjectFormat.parseCompressed(raw);

            if (!"tree".equals(obj.type())) {
                throw new BadRequestException("Object is not a tree: " + treeHash);
            }

            String content = new String(obj.content(), StandardCharsets.UTF_8);

            if (content.isBlank()) {
                return;
            }

            for (String line : content.split("\n")) {
                if (line == null || line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\t", 4);
                if (parts.length != 4) {
                    throw new BadRequestException("Invalid tree line: " + line);
                }

                String mode = parts[0];
                String type = parts[1];
                String hash = parts[2];
                String name = parts[3];

                String fullPath = prefix.isBlank() ? name : prefix + "/" + name;

                if ("tree".equals(type)) {
                    readTreeRecursive(owner, repo, hash, fullPath, result);
                } else if ("blob".equals(type)) {
                    result.put(fullPath, TreeEntry.builder()
                            .path(fullPath)
                            .name(name)
                            .mode(mode)
                            .type(type)
                            .hash(hash)
                            .build());
                }
            }

        } catch (Exception e) {
            throw new BadRequestException("Cannot read tree " + treeHash + ": " + e.getMessage());
        }
    }

    private String writeTree(String owner, String repo, Map<String, TreeEntry> flatEntries) {
        TreeNode root = new TreeNode();

        for (Map.Entry<String, TreeEntry> entry : flatEntries.entrySet()) {
            String path = normalizePath(entry.getKey());
            TreeEntry treeEntry = entry.getValue();

            if (treeEntry == null || treeEntry.getHash() == null || treeEntry.getHash().isBlank()) {
                continue;
            }

            root.insert(path.split("/"), treeEntry.getMode(), treeEntry.getHash());
        }

        return root.write(owner, repo);
    }

    private String writeObject(String owner, String repo, String type, byte[] content) {
        try {
            String header = type + " " + content.length + "\0";
            byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);

            byte[] full = new byte[headerBytes.length + content.length];
            System.arraycopy(headerBytes, 0, full, 0, headerBytes.length);
            System.arraycopy(content, 0, full, headerBytes.length, content.length);

            String hash = sha1Hex(full);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try (DeflaterOutputStream dos = new DeflaterOutputStream(baos, new Deflater())) {
                dos.write(full);
            }

            minio.putObjectIfAbsent(owner, repo, hash, baos.toByteArray());

            return hash;

        } catch (Exception e) {
            throw new BadRequestException("Cannot write " + type + " object: " + e.getMessage());
        }
    }

    private String readBlobAsStringOrEmpty(String owner, String repo, String blobHash) {
        if (blobHash == null || blobHash.isBlank()) {
            return "";
        }

        try {
            byte[] raw = minio.getObjectBytes(owner, repo, blobHash);
            VicObjectFormat.ParsedObject obj = VicObjectFormat.parseCompressed(raw);

            if (!"blob".equals(obj.type())) {
                return "";
            }

            return new String(obj.content(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            return "";
        }
    }

    private String hashOf(TreeEntry entry) {
        return entry == null ? null : entry.getHash();
    }

    private String normalizePath(String path) {
        return path == null ? "" : path.trim().replace("\\", "/");
    }

    private String leafName(String path) {
        String normalized = normalizePath(path);
        int idx = normalized.lastIndexOf('/');
        return idx < 0 ? normalized : normalized.substring(idx + 1);
    }

    private String sha1Hex(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(data);

        StringBuilder sb = new StringBuilder();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    @Data
    @Builder
    public static class MergeAnalysis {
        private boolean hasConflicts;
        private List<ConflictFile> conflicts;
        private Map<String, TreeEntry> mergedEntries;
        private String mergedTreeHash;
    }

    @Data
    @Builder
    public static class ConflictFile {
        private String path;

        private String baseHash;
        private String targetHash;
        private String sourceHash;

        private String baseContent;
        private String targetContent;
        private String sourceContent;
    }

    @Data
    @Builder
    public static class CommitInfo {
        private String hash;
        private String treeHash;
        private List<String> parents;
    }

    @Data
    @Builder
    public static class TreeEntry {
        private String path;
        private String name;
        private String mode;
        private String type;
        private String hash;
    }

    private class TreeNode {
        private String mode;
        private String blobHash;
        private final Map<String, TreeNode> children = new TreeMap<>();

        void insert(String[] parts, String mode, String hash) {
            if (parts.length == 0) {
                return;
            }

            if (parts.length == 1) {
                TreeNode leaf = new TreeNode();
                leaf.mode = mode == null || mode.isBlank() ? "100644" : mode;
                leaf.blobHash = hash;
                children.put(parts[0], leaf);
                return;
            }

            TreeNode child = children.computeIfAbsent(parts[0], key -> new TreeNode());
            child.insert(Arrays.copyOfRange(parts, 1, parts.length), mode, hash);
        }

        String write(String owner, String repo) {
            if (blobHash != null && !blobHash.isBlank()) {
                return blobHash;
            }

            List<String> lines = new ArrayList<>();

            for (Map.Entry<String, TreeNode> entry : children.entrySet()) {
                String name = entry.getKey();
                TreeNode child = entry.getValue();

                String mode;
                String type;
                String hash;

                if (child.blobHash != null && !child.blobHash.isBlank()) {
                    mode = child.mode == null || child.mode.isBlank() ? "100644" : child.mode;
                    type = "blob";
                    hash = child.blobHash;
                } else {
                    mode = "040000";
                    type = "tree";
                    hash = child.write(owner, repo);
                }

                lines.add(mode + "\t" + type + "\t" + hash + "\t" + name);
            }

            String content = String.join("\n", lines);

            return writeObject(owner, repo, "tree", content.getBytes(StandardCharsets.UTF_8));
        }
    }
}