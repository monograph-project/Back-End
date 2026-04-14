package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.model.Collaborator;
import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import org.springframework.util.StringUtils;

public final class RepoAccessRules {

    private RepoAccessRules() {}

    public static boolean canRead(VicRepositoryDocument meta, String usernameOrEmpty) {
        if ("public".equalsIgnoreCase(StringUtils.trimWhitespace(meta.getVisibility()))) {
            return true;
        }
        String u = norm(usernameOrEmpty);
        if (u.isEmpty()) {
            return false;
        }
        if (meta.getOwner() != null && meta.getOwner().equalsIgnoreCase(u)) {
            return true;
        }
        String role = collaboratorRole(meta, u);
        return "read".equals(role) || "write".equals(role) || "admin".equals(role);
    }

    public static boolean canWrite(VicRepositoryDocument meta, String username) {
        String u = norm(username);
        if (u.isEmpty()) {
            return false;
        }
        if (meta.getOwner() != null && meta.getOwner().equalsIgnoreCase(u)) {
            return true;
        }
        String role = collaboratorRole(meta, u);
        return "write".equals(role) || "admin".equals(role);
    }

    public static boolean canAdmin(VicRepositoryDocument meta, String username) {
        String u = norm(username);
        if (u.isEmpty()) {
            return false;
        }
        if (meta.getOwner() != null && meta.getOwner().equalsIgnoreCase(u)) {
            return true;
        }
        return "admin".equals(collaboratorRole(meta, u));
    }

    private static String collaboratorRole(VicRepositoryDocument meta, String usernameLower) {
        if (meta.getCollaborators() == null) {
            return "";
        }
        for (Collaborator c : meta.getCollaborators()) {
            if (c.getUsername() != null && c.getUsername().trim().equalsIgnoreCase(usernameLower)) {
                return c.getRole() == null ? "" : c.getRole().trim().toLowerCase();
            }
        }
        return "";
    }

    private static String norm(String u) {
        return u == null ? "" : u.trim().toLowerCase();
    }
}
