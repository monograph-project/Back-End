package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.model.UserDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterBody body) {
        if (body == null) {
            throw new IllegalArgumentException("invalid json body");
        }
        UserDocument u = authService.register(body.username(), body.email(), body.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "registered",
                "username", u.getUsername(),
                "email", u.getEmail()
        ));
    }

    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginBody body) {
        if (body == null) {
            throw new IllegalArgumentException("invalid json body");
        }
        String token = authService.login(body.identifier(), body.password());
        return ResponseEntity.ok(Map.of("status", "authenticated", "token", token));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RegisterBody(String username, String email, String password) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LoginBody(String identifier, String password) {}
}
