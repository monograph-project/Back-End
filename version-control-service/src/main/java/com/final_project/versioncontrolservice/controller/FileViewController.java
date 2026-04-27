package com.final_project.versioncontrolservice.controller;

import com.final_project.versioncontrolservice.dto.ContributorUser;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import com.final_project.versioncontrolservice.service.*;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/repos/{owner}/{repo}")
public class FileViewController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;
    private final MinioStorageService minioStorageService;
    private final CommitGraphService commitGraphService;

    public FileViewController(
            AuthService authService,
            RepositoryService vicRepositoryService,
            MinioStorageService minioStorageService,
            CommitGraphService commitGraphService
    ) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
        this.minioStorageService = minioStorageService;
        this.commitGraphService = commitGraphService;
    }

    /**
     * Get file content at a specific commit/branch
     * GET /repos/{owner}/{repo}/contents/{path}?ref=main
     */
    @GetMapping(value = "/contents/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileContentResponse> getFileContent(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "main") String ref,
            HttpServletRequest request
    ) {
        ContributorUser user = authService.getContributorUser(authorization)
                ;
        var meta = vicRepositoryService.loadMeta(owner, repo);

        // Check permissions
        String username = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }

        // Extract file path from URL
        String fullPath = request.getRequestURI();
        String prefix = "/repos/" + owner + "/" + repo + "/contents/";
        String filePath = fullPath.substring(fullPath.indexOf(prefix) + prefix.length());

        // Resolve ref to commit hash
        String commitHash = resolveRef(meta, ref);
        if (commitHash.isEmpty()) {
            throw new NotFoundException("ref not found: " + ref);
        }

        // Get file content from commit
        FileContentResponse response = getFileAtCommit(owner, repo, commitHash, filePath);
        return ResponseEntity.ok(response);
    }

    /**
     * Get file history (blame)
     * GET /repos/{owner}/{repo}/blame/{path}?ref=main
     */
    @GetMapping(value = "/blame/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BlameEntry>> getBlame(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "main") String ref,
            HttpServletRequest request
    ) {
        ContributorUser user = authService.getContributorUser(authorization)
                ;
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String username = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }

        String fullPath = request.getRequestURI();
        String prefix = "/repos/" + owner + "/" + repo + "/blame/";
        String filePath = fullPath.substring(fullPath.indexOf(prefix) + prefix.length());

        String commitHash = resolveRef(meta, ref);
        if (commitHash.isEmpty()) {
            throw new NotFoundException("ref not found: " + ref);
        }

        List<BlameEntry> blame = calculateBlame(owner, repo, commitHash, filePath);
        return ResponseEntity.ok(blame);
    }

    /**
     * Get file history (commits that modified this file)
     * GET /repos/{owner}/{repo}/commits?path=src/main.java&ref=main
     */
    @GetMapping(value = "/commits", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommitResponse>> getFileHistory(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(required = false) String path,
            @RequestParam(defaultValue = "main") String ref,
            @RequestParam(defaultValue = "20") int limit
    ) {
        ContributorUser user = authService.getContributorUser(authorization)
                ;
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String username = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }

        String commitHash = resolveRef(meta, ref);
        if (commitHash.isEmpty()) {
            throw new NotFoundException("ref not found: " + ref);
        }

        List<CommitResponse> history = getCommitHistory(owner, repo, commitHash, path, limit);
        return ResponseEntity.ok(history);
    }

    /**
     * Get directory listing (tree view)
     * GET /repos/{owner}/{repo}/tree?ref=main&path=src
     */
    @GetMapping(value = "/tree", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TreeEntry>> getTree(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "main") String ref,
            @RequestParam(defaultValue = "") String path
    ) {
        ContributorUser user = authService.getContributorUser(authorization)
                ;
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String username = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }

        String commitHash = resolveRef(meta, ref);
        if (commitHash.isEmpty()) {
            throw new NotFoundException("ref not found: " + ref);
        }

        List<TreeEntry> tree = getTreeEntries(owner, repo, commitHash, path);
        return ResponseEntity.ok(tree);
    }

    /**
     * Compare two commits/branches
     * GET /repos/{owner}/{repo}/compare/base...head
     */
    @GetMapping(value = "/compare/{base}...{head}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompareResponse> compareCommits(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String base,
            @PathVariable String head
    ) {
        ContributorUser user = authService.getContributorUser(authorization)
                ;
        var meta = vicRepositoryService.loadMeta(owner, repo);

        String username = user != null ? user.getUsername() : "";
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }

        String baseHash = resolveRef(meta, base);
        String headHash = resolveRef(meta, head);

        if (baseHash.isEmpty() || headHash.isEmpty()) {
            throw new NotFoundException("ref not found");
        }

        CompareResponse comparison = compareTrees(owner, repo, baseHash, headHash);
        return ResponseEntity.ok(comparison);
    }

    // ─── Helper Methods ───────────────────────────────────────────────────

    private String resolveRef(RepositoryDocument meta, String ref) {
        // Try as branch name
        String hash = meta.getBranchHeads().get(ref);
        if (hash != null && !hash.isEmpty()) {
            return hash;
        }

        // Try as full SHA
        if (ref.length() == 40 && ref.matches("[0-9a-f]{40}")) {
            return ref;
        }

        return "";
    }

    private FileContentResponse getFileAtCommit(String owner, String repo, String commitHash, String filePath) {
        try {
            // Read commit to get tree hash
            byte[] commitData = minioStorageService.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
            VicObjectFormat.CommitData commitInfo = VicObjectFormat.parseCommitContent(commitObj.content());

            // Find file in tree
            String[] pathParts = filePath.split("/");
            String currentTree = commitInfo.tree();

            for (int i = 0; i < pathParts.length - 1; i++) {
                currentTree = findTreeEntry(owner, repo, currentTree, pathParts[i]);
                if (currentTree == null) {
                    throw new NotFoundException("path not found: " + filePath);
                }
            }

            // Get the file blob
            String fileName = pathParts[pathParts.length - 1];
            String blobHash = findBlobEntry(owner, repo, currentTree, fileName);
            if (blobHash == null) {
                throw new NotFoundException("file not found: " + filePath);
            }

            byte[] blobData = minioStorageService.getObjectBytes(owner, repo, blobHash);
            VicObjectFormat.ParsedObject blobObj = VicObjectFormat.parseCompressed(blobData);

            String content = new String(blobObj.content(), StandardCharsets.UTF_8);
            long size = blobObj.content().length;

            // Detect language for syntax highlighting
            String language = detectLanguage(fileName);

            return new FileContentResponse(
                    fileName,
                    filePath,
                    blobHash,
                    content,
                    size,
                    language,
                    commitHash
            );
        } catch (Exception e) {
            throw new NotFoundException("file not found: " + e.getMessage());
        }
    }

    private String findTreeEntry(String owner, String repo, String treeHash, String name) {
        try {
            byte[] treeData = minioStorageService.getObjectBytes(owner, repo, treeHash);
            VicObjectFormat.ParsedObject treeObj = VicObjectFormat.parseCompressed(treeData);
            String[] lines = new String(treeObj.content(), StandardCharsets.UTF_8).split("\n");

            for (String line : lines) {
                String[] parts = line.split("\t");
                if (parts.length >= 4 && parts[3].equals(name)) {
                    return parts[2]; // return hash
                }
            }
        } catch (Exception e) {
            // Entry not found
        }
        return null;
    }

    private String findBlobEntry(String owner, String repo, String treeHash, String name) {
        return findTreeEntry(owner, repo, treeHash, name);
    }

    private List<BlameEntry> calculateBlame(String owner, String repo, String commitHash, String filePath) {
        Map<Integer, BlameEntry> lineMap = new HashMap<>();

        // Walk commit history for this file
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(commitHash);

        while (!queue.isEmpty()) {
            String currentHash = queue.poll();
            if (visited.contains(currentHash)) continue;
            visited.add(currentHash);

            try {
                // Get commit info
                byte[] commitData = minioStorageService.getObjectBytes(owner, repo, currentHash);
                VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
                VicObjectFormat.CommitData commitInfo = VicObjectFormat.parseCommitContent(commitObj.content());

                // Get author and timestamp
                String commitContent = new String(commitObj.content(), StandardCharsets.UTF_8);
                String author = extractHeader(commitContent, "author");
                String[] authorParts = author.split(" ");
                String authorName = String.join(" ", Arrays.copyOf(authorParts, authorParts.length - 1));

                // Get file content at this commit
                try {
                    FileContentResponse fileContent = getFileAtCommit(owner, repo, currentHash, filePath);
                    String[] lines = fileContent.getContent().split("\n", -1);

                    for (int i = 0; i < lines.length; i++) {
                        if (!lineMap.containsKey(i)) {
                            BlameEntry entry = new BlameEntry(
                                    currentHash.substring(0, 8),
                                    authorName,
                                    i + 1,
                                    lines[i]
                            );
                            lineMap.put(i, entry);
                        }
                    }
                } catch (NotFoundException e) {
                    // File didn't exist at this commit
                }

                // Add parents to queue
                queue.addAll(commitInfo.parents());
            } catch (Exception e) {
                // Skip problematic commits
            }
        }

        // Convert map to sorted list
        List<BlameEntry> blame = new ArrayList<>(lineMap.values());
        blame.sort(Comparator.comparingInt(BlameEntry::getLineNumber));

        return blame;
    }

    private List<CommitResponse> getCommitHistory(String owner, String repo, String commitHash, String path, int limit) {
        List<CommitResponse> history = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(commitHash);

        while (!queue.isEmpty() && history.size() < limit) {
            String currentHash = queue.poll();
            if (visited.contains(currentHash)) continue;
            visited.add(currentHash);

            try {
                byte[] commitData = minioStorageService.getObjectBytes(owner, repo, currentHash);
                VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
                VicObjectFormat.CommitData commitInfo = VicObjectFormat.parseCommitContent(commitObj.content());

                String commitContent = new String(commitObj.content(), StandardCharsets.UTF_8);

                // Check if this commit affects the specified path
                if (path != null && !path.isEmpty()) {
                    boolean affectsPath = doesCommitAffectPath(owner, repo, currentHash, path);
                    if (!affectsPath) {
                        queue.addAll(commitInfo.parents());
                        continue;
                    }
                }

                CommitResponse response = new CommitResponse(
                        currentHash,
                        currentHash.substring(0, 8),
                        extractHeader(commitContent, "author"),
                        extractHeader(commitContent, "committer"),
                        extractMessage(commitContent),
                        commitInfo.parents()
                );

                history.add(response);

                queue.addAll(commitInfo.parents());
            } catch (Exception e) {
                // Skip
            }
        }

        return history;
    }

    private boolean doesCommitAffectPath(String owner, String repo, String commitHash, String path) {
        try {
            byte[] commitData = minioStorageService.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
            VicObjectFormat.CommitData commitInfo = VicObjectFormat.parseCommitContent(commitObj.content());

            // Try to find the file in this commit's tree
            String blobHash = findBlobEntry(owner, repo, commitInfo.tree(), path);
            return blobHash != null;
        } catch (Exception e) {
            return false;
        }
    }

    private List<TreeEntry> getTreeEntries(String owner, String repo, String commitHash, String path) {
        List<TreeEntry> entries = new ArrayList<>();

        try {
            byte[] commitData = minioStorageService.getObjectBytes(owner, repo, commitHash);
            VicObjectFormat.ParsedObject commitObj = VicObjectFormat.parseCompressed(commitData);
            VicObjectFormat.CommitData commitInfo = VicObjectFormat.parseCommitContent(commitObj.content());

            String currentTree = commitInfo.tree();

            // Navigate to subdirectory if path specified
            if (!path.isEmpty()) {
                String[] parts = path.split("/");
                for (String part : parts) {
                    currentTree = findTreeEntry(owner, repo, currentTree, part);
                    if (currentTree == null) {
                        throw new NotFoundException("directory not found: " + path);
                    }
                }
            }

            // Read tree entries
            byte[] treeData = minioStorageService.getObjectBytes(owner, repo, currentTree);
            VicObjectFormat.ParsedObject treeObj = VicObjectFormat.parseCompressed(treeData);
            String[] lines = new String(treeObj.content(), StandardCharsets.UTF_8).split("\n");

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    TreeEntry entry = new TreeEntry(
                            parts[3], // name
                            parts[1], // type (blob/tree)
                            parts[0], // mode
                            parts[2], // hash
                            path.isEmpty() ? parts[3] : path + "/" + parts[3]
                    );
                    entries.add(entry);
                }
            }
        } catch (Exception e) {
            throw new NotFoundException("tree not found: " + e.getMessage());
        }

        return entries;
    }

    private CompareResponse compareTrees(String owner, String repo, String baseHash, String headHash) {
        List<FileDiff> diffs = new ArrayList<>();

        try {
            // Get base tree
            byte[] baseCommitData = minioStorageService.getObjectBytes(owner, repo, baseHash);
            VicObjectFormat.ParsedObject baseCommitObj = VicObjectFormat.parseCompressed(baseCommitData);
            VicObjectFormat.CommitData baseCommitInfo = VicObjectFormat.parseCommitContent(baseCommitObj.content());

            // Get head tree
            byte[] headCommitData = minioStorageService.getObjectBytes(owner, repo, headHash);
            VicObjectFormat.ParsedObject headCommitObj = VicObjectFormat.parseCompressed(headCommitData);
            VicObjectFormat.CommitData headCommitInfo = VicObjectFormat.parseCommitContent(headCommitObj.content());

            // Get all files in base
            Map<String, String> baseFiles = getAllFiles(owner, repo, baseCommitInfo.tree(), "");

            // Get all files in head
            Map<String, String> headFiles = getAllFiles(owner, repo, headCommitInfo.tree(), "");

            // Compare files
            Set<String> allPaths = new HashSet<>();
            allPaths.addAll(baseFiles.keySet());
            allPaths.addAll(headFiles.keySet());

            for (String path : allPaths) {
                String baseFileHash = baseFiles.get(path);
                String headFileHash = headFiles.get(path);

                if (baseFileHash == null && headFileHash != null) {
                    // Added
                    diffs.add(new FileDiff(path, "added", null, headFileHash));
                } else if (baseFileHash != null && headFileHash == null) {
                    // Deleted
                    diffs.add(new FileDiff(path, "deleted", baseFileHash, null));
                } else if (!baseFileHash.equals(headFileHash)) {
                    // Modified
                    diffs.add(new FileDiff(path, "modified", baseFileHash, headFileHash));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("compare failed: " + e.getMessage());
        }

        return new CompareResponse(baseHash, headHash, diffs);
    }

    private Map<String, String> getAllFiles(String owner, String repo, String treeHash, String prefix) {
        Map<String, String> files = new HashMap<>();

        try {
            byte[] treeData = minioStorageService.getObjectBytes(owner, repo, treeHash);
            VicObjectFormat.ParsedObject treeObj = VicObjectFormat.parseCompressed(treeData);
            String[] lines = new String(treeObj.content(), StandardCharsets.UTF_8).split("\n");

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    String name = parts[3];
                    String type = parts[1];
                    String hash = parts[2];
                    String fullPath = prefix.isEmpty() ? name : prefix + "/" + name;

                    if ("tree".equals(type)) {
                        files.putAll(getAllFiles(owner, repo, hash, fullPath));
                    } else {
                        files.put(fullPath, hash);
                    }
                }
            }
        } catch (Exception e) {
            // Skip
        }

        return files;
    }

    private String extractHeader(String content, String key) {
        for (String line : content.split("\n")) {
            if (line.startsWith(key + " ")) {
                return line.substring(key.length() + 1);
            }
        }
        return "";
    }

    private String extractMessage(String content) {
        String[] parts = content.split("\n\n", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    private String detectLanguage(String fileName) {
        if (fileName.endsWith(".java")) return "java";
        if (fileName.endsWith(".py")) return "python";
        if (fileName.endsWith(".js")) return "javascript";
        if (fileName.endsWith(".ts")) return "typescript";
        if (fileName.endsWith(".go")) return "go";
        if (fileName.endsWith(".xml")) return "xml";
        if (fileName.endsWith(".json")) return "json";
        if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) return "yaml";
        if (fileName.endsWith(".md")) return "markdown";
        if (fileName.endsWith(".html")) return "html";
        if (fileName.endsWith(".css")) return "css";
        if (fileName.endsWith(".sql")) return "sql";
        return "text";
    }

    // ─── Response DTOs ────────────────────────────────────────────────────

    @Data
    @AllArgsConstructor
    public static class FileContentResponse {
        private String name;
        private String path;
        private String sha;
        private String content;
        private long size;
        private String language;
        private String commitSha;

    }

    @Data
    @AllArgsConstructor
    public static class BlameEntry {
        private String commitSha;
        private String author;
        private int lineNumber;
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class CommitResponse {
        private String sha;
        private String shortSha;
        private String author;
        private String committer;
        private String message;
        private List<String> parents;


    }

    @AllArgsConstructor
    @Data
    public static class TreeEntry {
        private String name;
        private String type;
        private String mode;
        private String sha;
        private String path;

    }

    @Data
    @AllArgsConstructor
    public static class FileDiff {
        private String path;
        private String status;
        private String baseSha;
        private String headSha;


    }

    @Data
    @AllArgsConstructor
    public static class CompareResponse {
        private String baseCommit;
        private String headCommit;
        private List<FileDiff> files;
    }
}
