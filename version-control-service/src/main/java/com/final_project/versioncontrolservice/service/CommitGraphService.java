package com.final_project.versioncontrolservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommitGraphService {

    private final MinioStorageService minio;

    public CommitGraphService(MinioStorageService minio) {
        this.minio = minio;
    }


    public boolean isAncestorInRepo(String owner, String repo, String ancestor, String descendant) {
        if (ancestor == null || descendant == null || ancestor.isEmpty() || descendant.isEmpty()) {
            return false;
        }
        if (ancestor.equals(descendant)) {
            return true;
        }

        Set<String> seen = new HashSet<>();
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add(descendant);

        while (!queue.isEmpty()) {
            String cur = queue.removeFirst();
            if (cur.isEmpty() || seen.contains(cur)) {
                continue;
            }
            seen.add(cur);
            if (cur.equals(ancestor)) {
                return true;
            }

            VicObjectFormat.ParsedObject obj;
            try {
                byte[] raw = minio.getObjectBytes(owner, repo, cur);
                obj = VicObjectFormat.parseCompressed(raw);
            } catch (Exception e) {
                throw new IllegalStateException("read commit " + cur + ": " + e.getMessage(), e);
            }
            if (!"commit".equals(obj.type())) {
                throw new IllegalStateException("object " + cur + " is not a commit");
            }
            VicObjectFormat.CommitData data = VicObjectFormat.parseCommitContent(obj.content());
            for (String p : data.parents()) {
                queue.addLast(p);
            }
        }
        return false;
    }
}
