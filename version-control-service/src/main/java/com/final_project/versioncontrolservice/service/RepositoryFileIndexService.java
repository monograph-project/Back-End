package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.model.RepositoryDocument;
import com.final_project.versioncontrolservice.model.RepositoryFileIndex;
import com.final_project.versioncontrolservice.repo.RepositoryFileIndexRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
@AllArgsConstructor
public class RepositoryFileIndexService {

    private final RepositoryFileIndexRepository fileIndexRepository;
    private final MinioStorageService minioStorageService;

    public void rebuildIndex(RepositoryDocument repo, String branch, String commitHash) throws IOException {
        fileIndexRepository.deleteByOwnerUsernameIgnoreCaseAndRepositoryNameIgnoreCaseAndBranch(
                repo.getOwner().getUsername(),
                repo.getRepositoryName(),
                branch
        );

        byte[] commitData = minioStorageService.getObjectBytes(
                repo.getOwner().getUsername(),
                repo.getRepositoryName(),
                commitHash
        );

        VicObjectFormat.ParsedObject commitObj =
                VicObjectFormat.parseCompressed(commitData);

        VicObjectFormat.CommitData commit =
                VicObjectFormat.parseCommitContent(commitObj.content());

        walkTree(repo, branch, commitHash, commit.tree(), "");
    }

    private void walkTree(
            RepositoryDocument repo,
            String branch,
            String commitHash,
            String treeHash,
            String currentPath
    ) throws IOException {
        byte[] treeData = minioStorageService.getObjectBytes(
                repo.getOwner().getUsername(),
                repo.getRepositoryName(),
                treeHash
        );

        VicObjectFormat.ParsedObject treeObj =
                VicObjectFormat.parseCompressed(treeData);

        String treeText = new String(treeObj.content(), StandardCharsets.UTF_8);

        for (String line : treeText.split("\n")) {
            if (line.isBlank()) continue;

            String[] parts = line.split("\t");
            if (parts.length < 4) continue;

            String mode = parts[0];
            String type = parts[1];
            String hash = parts[2];
            String name = parts[3];

            String path = currentPath.isEmpty() ? name : currentPath + "/" + name;

            if ("tree".equals(type)) {
                walkTree(repo, branch, commitHash, hash, path);
            }

            if ("blob".equals(type)) {
                byte[] blobData = minioStorageService.getObjectBytes(
                        repo.getOwner().getUsername(),
                        repo.getRepositoryName(),
                        hash
                );

                VicObjectFormat.ParsedObject blobObj =
                        VicObjectFormat.parseCompressed(blobData);

                RepositoryFileIndex index = RepositoryFileIndex.builder()
                        .repositoryId(repo.getId())
                        .ownerUsername(repo.getOwner().getUsername())
                        .repositoryName(repo.getRepositoryName())
                        .branch(branch)
                        .path(path)
                        .fileName(name)
                        .blobHash(hash)
                        .commitHash(commitHash)
                        .size((long) blobObj.content().length)
                        .language(detectLanguage(name))
                        .deleted(false)
                        .indexedAt(Instant.now())
                        .build();

                fileIndexRepository.save(index);
            }
        }
    }

    private String detectLanguage(String fileName) {
        if (fileName.endsWith(".java")) return "java";
        if (fileName.endsWith(".go")) return "go";
        if (fileName.endsWith(".js")) return "javascript";
        if (fileName.endsWith(".ts")) return "typescript";
        if (fileName.endsWith(".json")) return "json";
        if (fileName.endsWith(".md")) return "markdown";
        return "text";
    }
}