package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.model.SessionDocument;
import com.final_project.versioncontrolservice.repo.SessionRepository;
import com.final_project.versioncontrolservice.model.UserDocument;
import com.final_project.versioncontrolservice.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private static final int SESSION_HOURS = 24;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserDocument register(String username, String email, String password) {
        String u = username.trim().toLowerCase();
        String em = email.trim().toLowerCase();
        if (u.isEmpty() || em.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("username, email and password are required");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("password must be at least 8 characters");
        }
        if (userRepository.existsByUsername(u)) {
            throw new IllegalArgumentException("username already exists");
        }
        if (userRepository.existsByEmail(em)) {
            throw new IllegalArgumentException("email already exists");
        }
        UserDocument doc = new UserDocument();
        doc.setUsername(u);
        doc.setEmail(em);
        doc.setPasswordHash(bcrypt.encode(password));
        doc.setCreatedAt(Instant.now());
        return userRepository.save(doc);
    }

    public String login(String identifier, String password) {
        String id = identifier.trim().toLowerCase();
        if (id.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("identifier and password are required");
        }
        UserDocument user = userRepository.findByUsername(id)
                .or(() -> userRepository.findByEmail(id))
                .orElseThrow(() -> new InvalidCredentialsException());
        if (!bcrypt.matches(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        String token = UUID.randomUUID().toString();
        SessionDocument s = new SessionDocument();
        s.setUserId(user.getId());
        s.setToken(token);
        s.setCreatedAt(Instant.now());
        s.setExpiresAt(Instant.now().plus(SESSION_HOURS, ChronoUnit.HOURS));
        sessionRepository.save(s);
        return token;
    }

    public Optional<UserDocument> optionalUser(String authorizationHeader) {
        String token = bearerToken(authorizationHeader);
        if (token == null) {
            return Optional.empty();
        }
        Optional<SessionDocument> s = sessionRepository.findByToken(token);
        if (s.isEmpty()) {
            return Optional.empty();
        }
        SessionDocument session = s.get();
        if (Instant.now().isAfter(session.getExpiresAt())) {
            sessionRepository.delete(session);
            return Optional.empty();
        }
        return userRepository.findById(session.getUserId());
    }

    public UserDocument requireUser(String authorizationHeader) {
        return optionalUser(authorizationHeader).orElseThrow(() -> new UnauthorizedException("unauthorized"));
    }

    private static String bearerToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return null;
        }
        String h = authorizationHeader.trim();
        final String p = "Bearer ";
        if (!h.startsWith(p)) {
            return null;
        }
        String t = h.substring(p.length()).trim();
        return t.isEmpty() ? null : t;
    }

    public static class InvalidCredentialsException extends RuntimeException {}

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String m) {
            super(m);
        }
    }
}
