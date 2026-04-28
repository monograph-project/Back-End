# Version Control Service Code Archive

## Project Structure

```text
version-control-service/
  pom.xml
  src/
    main/
      resources/
        application.yaml
      java/
        com/
          final_project/
            versioncontrolservice/
              VersionControlServiceApplication.java
              client/
                FileServiceClient.java
                UserServiceClient.java
              config/
                MinioConfig.java
                StorageBootstrap.java
              controller/
                AuthController.java
                GlobalExceptionHandler.java
                HealthController.java
                InvitationController.java
                PullRequestController.java
                RepositoryController.java
              dto/
                InvitationResponse.java
                PullRequestResponse.java
              exception/
                BadRequestException.java
                ForbiddenException.java
                NotFoundException.java
              model/
                Collaborator.java
                InvitationDocument.java
                PullRequestDocument.java
                SessionDocument.java
                UserDocument.java
                VicRepositoryDocument.java
              repo/
                InvitationRepository.java
                PullRequestRepository.java
                SessionRepository.java
                UserRepository.java
                VicRepositoryRepository.java
              service/
                AuthService.java
                CommitGraphService.java
                InvitationApplicationService.java
                MinioStorageService.java
                ObjectHash.java
                PullRequestApplicationService.java
                RepoAccessRules.java
                VicObjectFormat.java
                VicRepositoryService.java
    test/
      java/
        com/
          final_project/
            versioncontrolservice/
              VersionControlServiceApplicationTests.java
```

## Source Code

### pom.xml

**Directory Address:** `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.0.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.final_project</groupId>
    <artifactId>version-control-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>version-control-service</name>
    <description>version-control-service</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2025.1.1</spring-cloud.version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.5.7</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-crypto</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <executions>
                    <execution>
                        <id>default-compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <annotationProcessorPaths>
                                <path>
                                    <groupId>org.projectlombok</groupId>
                                    <artifactId>lombok</artifactId>
                                </path>
                            </annotationProcessorPaths>
                        </configuration>
                    </execution>
                    <execution>
                        <id>default-testCompile</id>
                        <phase>test-compile</phase>
                        <goals>
                            <goal>testCompile</goal>
                        </goals>
                        <configuration>
                            <annotationProcessorPaths>
                                <path>
                                    <groupId>org.projectlombok</groupId>
                                    <artifactId>lombok</artifactId>
                                </path>
                            </annotationProcessorPaths>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

### src/main/resources/application.yaml

**Directory Address:** `src/main/resources/application.yaml`

```yaml
spring:
  application:
    name: version-control-service
  config:
    import: optional:configserver:http://localhost:8888
  profiles:
    active: native

```

### src/main/java/com/final_project/versioncontrolservice/VersionControlServiceApplication.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/VersionControlServiceApplication.java`

```java
package com.final_project.versioncontrolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VersionControlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VersionControlServiceApplication.class, args);
    }

}

```

### src/main/java/com/final_project/versioncontrolservice/client/FileServiceClient.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/client/FileServiceClient.java`

```java
package com.final_project.versioncontrolservice.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class FileServiceClient {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean
    public WebClient fileServiceClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://file-service")
                .exchangeStrategies(ExchangeStrategies.builder().build())
                .codecs( configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024) )
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofSeconds(5))
                ))
                .build();
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/client/UserServiceClient.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/client/UserServiceClient.java`

```java
package com.final_project.versioncontrolservice.client;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceClient {


}

```

### src/main/java/com/final_project/versioncontrolservice/config/MinioConfig.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/config/MinioConfig.java`

```java
package com.final_project.versioncontrolservice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(
            @Value("${storage.minio.endpoint}") String endpoint,
            @Value("${storage.minio.access-key}") String accessKey,
            @Value("${storage.minio.secret-key}") String secretKey
    ) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/config/StorageBootstrap.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/config/StorageBootstrap.java`

```java

package  com.final_project.versioncontrolservice.config;
import com.final_project.versioncontrolservice.service.MinioStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StorageBootstrap {

    private final MinioStorageService minioStorageService;

    public StorageBootstrap(MinioStorageService minioStorageService) {
        this.minioStorageService = minioStorageService;
    }

    @PostConstruct
    public void init() {
        minioStorageService.ensureBuckets();
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/AuthController.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/AuthController.java`

```java
package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    public record RegisterBody(String username, String email, String password) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LoginBody(String identifier, String password) {
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/GlobalExceptionHandler.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/GlobalExceptionHandler.java`

```java
package com.final_project.versioncontrolservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.service.AuthService;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException ex) {
        return text(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequest(BadRequestException ex) {
        return text(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AuthService.InvalidCredentialsException.class)
    public ResponseEntity<String> invalidCreds() {
        return text(HttpStatus.UNAUTHORIZED, "invalid credentials");
    }

    @ExceptionHandler(AuthService.UnauthorizedException.class)
    public ResponseEntity<String> unauthorized(AuthService.UnauthorizedException ex) {
        return text(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbiddenPr(ForbiddenException ex) {
        return text(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArg(IllegalArgumentException ex) {
        return text(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> illegalState(IllegalStateException ex) {
        return text(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private static ResponseEntity<String> text(HttpStatus status, String body) {
        return ResponseEntity.status(status).contentType(MediaType.TEXT_PLAIN).body(body);
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/HealthController.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/HealthController.java`

```java
package com.final_project.versioncontrolservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/InvitationController.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/InvitationController.java`

```java
package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.InvitationApplicationService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.service.VicRepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.dto.InvitationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class InvitationController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;
    private final InvitationApplicationService invitationApplicationService;

    public InvitationController(
            AuthService authService,
            RepositoryService vicRepositoryService,
            InvitationApplicationService invitationApplicationService
    ) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
        this.invitationApplicationService = invitationApplicationService;
    }

    @PostMapping(path = "/repos/{owner}/{repo}/invitations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody InviteBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canAdmin(meta, user.getUsername())) {
            throw new ForbiddenException("only repository admins can invite collaborators");
        }
        if (body == null) {
            throw new BadRequestException("invalid json body");
        }
        invitationApplicationService.create(meta, body.username(), body.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "invited"));
    }

    @GetMapping(path = "/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InvitationResponse> listMine(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization
    ) {
        UserDocument user = authService.requireUser(authorization);
        return invitationApplicationService.listPendingForUser(user.getUsername()).stream()
                .map(InvitationResponse::from)
                .toList();
    }

    @PostMapping(path = "/invitations/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> accept(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String id
    ) {
        UserDocument user = authService.requireUser(authorization);
        InvitationDocument inv = invitationApplicationService.findById(id);
        invitationApplicationService.accept(inv, user.getUsername());
        return ResponseEntity.ok(Map.of("status", "accepted"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record InviteBody(String username, String role) {
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/PullRequestController.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/PullRequestController.java`

```java
package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.PullRequestDocument;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.PullRequestApplicationService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.dto.PullRequestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PullRequestController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;
    private final PullRequestApplicationService pullRequestApplicationService;

    public PullRequestController(
            AuthService authService,
            RepositoryService vicRepositoryService,
            PullRequestApplicationService pullRequestApplicationService
    ) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
        this.pullRequestApplicationService = pullRequestApplicationService;
    }

    @PostMapping(path = "/repos/{owner}/{repo}/pulls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PullRequestResponse> create(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestBody CreatePrBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (body == null) {
            throw new BadRequestException("invalid json body");
        }
        PullRequest pr = pullRequestApplicationService.create(
                meta,
                user.getUsername(),
                body.sourceBranch(),
                body.targetBranch(),
                body.title(),
                body.description()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(PullRequestResponse.from(pr));
    }

    @GetMapping(path = "/repos/{owner}/{repo}/pulls", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PullRequestResponse> list(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        return pullRequestApplicationService.list(meta).stream().map(PullRequestResponse::from).toList();
    }

    @GetMapping(path = "/repos/{owner}/{repo}/pulls/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PullRequestResponse get(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        PullRequest pr = pullRequestApplicationService.find(meta, id);
        return PullRequestResponse.from(pr);
    }

    @PostMapping(path = "/repos/{owner}/{repo}/pulls/{id}/merge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> merge(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String id
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        PullRequest pr = pullRequestApplicationService.find(meta, id);
        pullRequestApplicationService.merge(meta, pr, user.getUsername());
        return ResponseEntity.ok(Map.of("status", "merged"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CreatePrBody(
            String sourceBranch,
            String targetBranch,
            String title,
            String description
    ) {
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/controller/RepositoryController.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/controller/RepositoryController.java`

```java
package com.final_project.versioncontrolservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.service.AuthService;
import com.final_project.versioncontrolservice.service.RepoAccessRules;
import com.final_project.versioncontrolservice.service.RepositoryService;
import com.final_project.versioncontrolservice.service.VicRepositoryService;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RepositoryController {

    private final AuthService authService;
    private final RepositoryService vicRepositoryService;

    public RepositoryController(AuthService authService, RepositoryService vicRepositoryService) {
        this.authService = authService;
        this.vicRepositoryService = vicRepositoryService;
    }

    @PostMapping(path = "/repos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createRepo(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @RequestBody CreateRepoBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        if (body == null || body.name() == null || body.name().isBlank()) {
            throw new BadRequestException("repository name is required");
        }
        var doc = vicRepositoryService.createRepo(user.getUsername(), body.name(), body.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "created",
                "owner", doc.getOwner(),
                "name", doc.getName()
        ));
    }

    @GetMapping(path = "/repos/{owner}/{repo}/info/refs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> infoRefs(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        return vicRepositoryService.listRefs(meta);
    }

    @GetMapping(path = "/repos/{owner}/{repo}/objects/{hash}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getObject(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String hash
    ) {
        var meta = vicRepositoryService.loadMeta(owner, repo);
        String username = authService.optionalUser(authorization).map(UserDocument::getUsername).orElse("");
        if (!RepoAccessRules.canRead(meta, username)) {
            throw new ForbiddenException("forbidden");
        }
        byte[] data = vicRepositoryService.readObjectRaw(meta, hash.trim());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    }

    @PostMapping(
            path = "/repos/{owner}/{repo}/objects/{hash}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, String>> uploadObject(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String hash,
            @RequestBody byte[] body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, user.getUsername())) {
            throw new ForbiddenException("forbidden");
        }
        vicRepositoryService.writeObject(meta, hash.trim(), body);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "stored"));
    }

    @PostMapping(
            path = "/repos/{owner}/{repo}/refs/heads/{branch}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UpdateBranchResponse updateBranch(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String branch,
            @RequestBody UpdateBranchBody body
    ) {
        UserDocument user = authService.requireUser(authorization);
        var meta = vicRepositoryService.loadMeta(owner, repo);
        if (!RepoAccessRules.canWrite(meta, user.getUsername())) {
            throw new com.final_project.versioncontrolservice.exception.ForbiddenException("forbidden");
        }
        if (body == null || body.hash() == null || body.hash().isBlank()) {
            throw new BadRequestException("hash is required");
        }
        vicRepositoryService.updateBranchRef(meta, branch, body.hash().trim());
        return new UpdateBranchResponse("updated", branch.trim());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CreateRepoBody(String name, String description) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record UpdateBranchBody(String hash) {
    }

    /** Matches Go: JSON field {@code hash} holds the branch name. */
    public record UpdateBranchResponse(String status, @JsonProperty("hash") String branchName) {
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/dto/InvitationResponse.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/dto/InvitationResponse.java`

```java
package com.final_project.versioncontrolservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.model.InvitationDocument;

import java.time.Instant;

public record InvitationResponse(
        @JsonProperty("id") String id,
        @JsonProperty("repo_owner") String repoOwner,
        @JsonProperty("repo_name") String repoName,
        @JsonProperty("invited_user") String invitedUser,
        String role,
        String status,
        @JsonProperty("created_at") Instant createdAt
) {
    public static InvitationResponse from(InvitationDocument d) {
        return new InvitationResponse(
                d.getId().toHexString(),
                d.getRepoOwner(),
                d.getRepoName(),
                d.getInvitedUser(),
                d.getRole(),
                d.getStatus(),
                d.getCreatedAt()
        );
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/dto/PullRequestResponse.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/dto/PullRequestResponse.java`

```java
package com.final_project.versioncontrolservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.PullRequestDocument;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PullRequestResponse(
        @JsonProperty("id") String id,
        @JsonProperty("repo_owner") String repoOwner,
        @JsonProperty("repo_name") String repoName,
        String author,
        @JsonProperty("source_branch") String sourceBranch,
        @JsonProperty("target_branch") String targetBranch,
        String title,
        String description,
        String status,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("merged_at") Instant mergedAt
) {
    public static PullRequestResponse from(PullRequest d) {
        return new PullRequestResponse(
                d.getId().toHexString(),
                d.getRepoOwner(),
                d.getRepoName(),
                d.getAuthor(),
                d.getSourceBranch(),
                d.getTargetBranch(),
                d.getTitle(),
                d.getDescription(),
                d.getStatus(),
                d.getCreatedAt(),
                d.getMergedAt()
        );
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/exception/BadRequestException.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/exception/BadRequestException.java`

```java
package com.final_project.versioncontrolservice.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}

```

### src/main/java/com/final_project/versioncontrolservice/exception/ForbiddenException.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/exception/ForbiddenException.java`

```java
package com.final_project.versioncontrolservice.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) { super(message); }
}

```

### src/main/java/com/final_project/versioncontrolservice/exception/NotFoundException.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/exception/NotFoundException.java`

```java
package com.final_project.versioncontrolservice.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}

```

### src/main/java/com/final_project/versioncontrolservice/model/Collaborator.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/Collaborator.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator {
    private String username;
    private String role;
}

```

### src/main/java/com/final_project/versioncontrolservice/model/InvitationDocument.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/InvitationDocument.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Document(collection = "invitations")
public class InvitationDocument {

    @Id
    private ObjectId id;

    @Field("repo_owner")
    private String repoOwner;

    @Field("repo_name")
    private String repoName;

    @Field("invited_user")
    private String invitedUser;

    private String role;
    private String status;
    private Instant createdAt;
}

```

### src/main/java/com/final_project/versioncontrolservice/model/PullRequestDocument.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/PullRequestDocument.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Document(collection = "pull_requests")
public class PullRequestDocument {

    @Id
    private ObjectId id;

    @Field("repo_owner")
    private String repoOwner;

    @Field("repo_name")
    private String repoName;

    private String author;

    @Field("source_branch")
    private String sourceBranch;

    @Field("target_branch")
    private String targetBranch;

    private String title;
    private String description;
    private String status;
    private Instant createdAt;

    @Field("merged_at")
    private Instant mergedAt;
}

```

### src/main/java/com/final_project/versioncontrolservice/model/SessionDocument.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/SessionDocument.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "sessions")
public class SessionDocument {

    @Id
    private ObjectId id;

    private ObjectId userId;

    @Indexed(unique = true)
    private String token;

    private Instant createdAt;
    private Instant expiresAt;
}

```

### src/main/java/com/final_project/versioncontrolservice/model/UserDocument.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/UserDocument.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "users")
public class UserDocument {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private Instant createdAt;
}

```

### src/main/java/com/final_project/versioncontrolservice/model/VicRepositoryDocument.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/model/VicRepositoryDocument.java`

```java
package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "vic_repositories")
public class VicRepositoryDocument {

    @Id
    private String id;

    private String owner;
    private String name;
    private String description;
    private String visibility = "private";
    private List<Collaborator> collaborators = new ArrayList<>();
    private Map<String, String> branchHeads = new HashMap<>();
    private String symbolicHead = "refs/heads/main";

    public static String compositeId(String owner, String name) {
        return owner.toLowerCase() + "/" + name.toLowerCase();
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/repo/InvitationRepository.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/repo/InvitationRepository.java`

```java
package com.final_project.versioncontrolservice.repo;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends MongoRepository<InvitationDocument, ObjectId> {

    long countByRepoOwnerAndRepoNameAndInvitedUserAndStatus(
            String repoOwner, String repoName, String invitedUser, String status);

    List<InvitationDocument> findByInvitedUserAndStatus(String invitedUser, String status);
}

```

### src/main/java/com/final_project/versioncontrolservice/repo/PullRequestRepository.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/repo/PullRequestRepository.java`

```java
package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.PullRequestDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PullRequestRepository extends MongoRepository<PullRequest, ObjectId> {

    List<PullRequest> findByRepoOwnerAndRepoName(String repoOwner, String repoName);

    Optional<PullRequest> findByIdAndRepoOwnerAndRepoName(ObjectId id, String repoOwner, String repoName);
}

```

### src/main/java/com/final_project/versioncontrolservice/repo/SessionRepository.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/repo/SessionRepository.java`

```java
package com.final_project.versioncontrolservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<SessionDocument, org.bson.types.ObjectId> {

    Optional<SessionDocument> findByToken(String token);
}

```

### src/main/java/com/final_project/versioncontrolservice/repo/UserRepository.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/repo/UserRepository.java`

```java
package com.final_project.versioncontrolservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, org.bson.types.ObjectId> {

    Optional<UserDocument> findByUsername(String username);

    Optional<UserDocument> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

```

### src/main/java/com/final_project/versioncontrolservice/repo/VicRepositoryRepository.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/repo/VicRepositoryRepository.java`

```java
package com.final_project.versioncontrolservice.repo;

import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VicRepositoryRepository extends MongoRepository<VicRepositoryDocument, String> {

    Optional<VicRepositoryDocument> findByOwnerIgnoreCaseAndNameIgnoreCase(String owner, String name);

    boolean existsByOwnerIgnoreCaseAndNameIgnoreCase(String owner, String name);
}

```

### src/main/java/com/final_project/versioncontrolservice/service/AuthService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/AuthService.java`

```java
package com.final_project.versioncontrolservice.service;

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

    public static class InvalidCredentialsException extends RuntimeException {
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String m) {
            super(m);
        }
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/service/CommitGraphService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/CommitGraphService.java`

```java
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

```

### src/main/java/com/final_project/versioncontrolservice/service/InvitationApplicationService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/InvitationApplicationService.java`

```java
package com.final_project.versioncontrolservice.service;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import com.final_project.versioncontrolservice.repo.InvitationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvitationApplicationService {
    private final InvitationRepository invitationRepository;
    private final RepositoryService vicRepositoryService;


    public void create(VicRepositoryDocument meta, String invitedUser, String role) {
        String u = invitedUser.trim().toLowerCase();
        if (u.isEmpty()) {
            throw new BadRequestException("username is required");
        }
        String r = role == null || role.isBlank() ? "write" : role.trim().toLowerCase();
        if (!List.of("read", "write", "admin").contains(r)) {
            throw new BadRequestException("invalid role");
        }
        long pending = invitationRepository.countByRepoOwnerAndRepoNameAndInvitedUserAndStatus(
                meta.getOwner(), meta.getName(), u, "pending");
        if (pending > 0) {
            throw new BadRequestException("pending invitation already exists");
        }
        InvitationDocument inv = new InvitationDocument();
        inv.setRepoOwner(meta.getOwner());
        inv.setRepoName(meta.getName());
        inv.setInvitedUser(u);
        inv.setRole(r);
        inv.setStatus("pending");
        inv.setCreatedAt(Instant.now());
        invitationRepository.save(inv);
    }

    public List<InvitationDocument> listPendingForUser(String username) {
        return invitationRepository.findByInvitedUserAndStatus(username.trim().toLowerCase(), "pending");
    }

    public InvitationDocument findById(String idHex) {
        ObjectId id;
        try {
            id = new ObjectId(idHex.trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("invalid invitation id");
        }
        return invitationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("invitation not found"));
    }

    public void accept(InvitationDocument inv, String username) {
        if (!inv.getInvitedUser().equalsIgnoreCase(username.trim())) {
            throw new ForbiddenException("you cannot accept this invitation");
        }
        if (!"pending".equals(inv.getStatus())) {
            throw new BadRequestException("invitation is not pending");
        }
        vicRepositoryService.addCollaborator(inv.getRepoOwner(), inv.getRepoName(), inv.getInvitedUser(), inv.getRole());
        inv.setStatus("accepted");
        invitationRepository.save(inv);
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/service/MinioStorageService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/MinioStorageService.java`

```java
package com.final_project.versioncontrolservice.service;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class MinioStorageService {

    private final MinioClient minioClient;
    private final String objectsBucket;
    private final String layoutBucket;

    public MinioStorageService(
            MinioClient minioClient,
            @Value("${storage.minio.bucket-objects}") String objectsBucket,
            @Value("${storage.minio.bucket-layout}") String layoutBucket
    ) {
        this.minioClient = minioClient;
        this.objectsBucket = objectsBucket;
        this.layoutBucket = layoutBucket;
    }

    public void ensureBuckets() {
        ensureBucket(objectsBucket);
        ensureBucket(layoutBucket);
    }

    public String objectsBucket() {
        return objectsBucket;
    }

    public String layoutBucket() {
        return layoutBucket;
    }

    public String objectKey(String owner, String repo, String hash) {
        return owner + "/" + repo + "/objects/" + hash.substring(0, 2) + "/" + hash.substring(2);
    }

    private static String layoutKey(String owner, String repo, String relativePath) {
        return owner + "/" + repo + "/.vic/" + relativePath;
    }

    public void writeLayoutHead(String owner, String repo) {
        byte[] data = "ref: refs/heads/main\n".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        put(layoutBucket, layoutKey(owner, repo, "HEAD"), data, "text/plain; charset=utf-8");
    }

    public void writeLayoutBranchRef(String owner, String repo, String branch, String hash) {
        String content = (hash == null || hash.isEmpty()) ? "" : hash + "\n";
        put(layoutBucket, layoutKey(owner, repo, "refs/heads/" + branch),
                content.getBytes(java.nio.charset.StandardCharsets.UTF_8), "text/plain; charset=utf-8");
    }

    public boolean objectExists(String owner, String repo, String hash) {
        return exists(objectsBucket, objectKey(owner, repo, hash));
    }

    public byte[] getObjectBytes(String owner, String repo, String hash) {
        return get(objectsBucket, objectKey(owner, repo, hash));
    }

    public void putObjectIfAbsent(String owner, String repo, String hash, byte[] data) {
        String key = objectKey(owner, repo, hash);
        if (exists(objectsBucket, key)) {
            return;
        }
        put(objectsBucket, key, data, "application/octet-stream");
    }

    private void put(String bucket, String key, byte[] data, String contentType) {
        try {
            ensureBucket(bucket);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .stream(new ByteArrayInputStream(data), data.length, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException("put failed: " + key, e);
        }
    }

    private byte[] get(String bucket, String key) {
        try (InputStream in = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(key).build())) {
            return in.readAllBytes();
        } catch (Exception e) {
            throw new StorageException("get failed: " + key, e);
        }
    }

    private boolean exists(String bucket, String key) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(key).build());
            return true;
        } catch (ErrorResponseException e) {
            if ("NoSuchKey".equals(e.errorResponse().code())) {
                return false;
            }
            throw new StorageException("stat failed: " + key, e);
        } catch (Exception e) {
            throw new StorageException("stat failed: " + key, e);
        }
    }

    private void ensureBucket(String bucket) {
        try {
            boolean ok = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!ok) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new StorageException("bucket: " + bucket, e);
        }
    }

    public static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/service/ObjectHash.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/ObjectHash.java`

```java
package com.final_project.versioncontrolservice.service;

public final class ObjectHash {
    private ObjectHash() {}

    public static boolean isValidSha1Hex(String hash) {
        if (hash == null || hash.length() != 40) {
            return false;
        }
        for (int i = 0; i < 40; i++) {
            char c = hash.charAt(i);
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'))) {
                return false;
            }
        }
        return true;
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/service/PullRequestApplicationService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/PullRequestApplicationService.java`

```java
package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.repo.PullRequestRepository;
import com.final_project.versioncontrolservice.model.VicRepositoryDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PullRequestApplicationService {

    private final PullRequestRepository pullRequestRepository;
    private final RepositoryService vicRepositoryService;
    private final CommitGraphService commitGraphService;

    public PullRequestApplicationService(
            PullRequestRepository pullRequestRepository,
            RepositoryService vicRepositoryService,
            CommitGraphService commitGraphService
    ) {
        this.pullRequestRepository = pullRequestRepository;
        this.vicRepositoryService = vicRepositoryService;
        this.commitGraphService = commitGraphService;
    }

    public PullRequest create(
            VicRepositoryDocument meta,
            String author,
            String sourceBranch,
            String targetBranch,
            String title,
            String description
    ) {
        if (!RepoAccessRules.canRead(meta, author)) {
            throw new ForbiddenException("forbidden");
        }
        sourceBranch = sourceBranch.trim();
        targetBranch = targetBranch.trim();
        title = title.trim();
        description = description == null ? "" : description.trim();
        if (sourceBranch.isEmpty() || targetBranch.isEmpty() || title.isEmpty()) {
            throw new BadRequestException("source_branch, target_branch and title are required");
        }
        if (sourceBranch.equals(targetBranch)) {
            throw new BadRequestException("source and target branch cannot be the same");
        }
        String headSource = vicRepositoryService.listBranchHash(meta, sourceBranch);
        if (headSource.isEmpty()) {
            throw new BadRequestException("source branch \"" + sourceBranch + "\" does not exist");
        }
        String headTarget = vicRepositoryService.listBranchHash(meta, targetBranch);
        if (headTarget.isEmpty()) {
            throw new BadRequestException("target branch \"" + targetBranch + "\" does not exist");
        }

        PullRequest pr = new PullRequest();
        pr.setRepoOwner(meta.getOwner());
        pr.setRepoName(meta.getName());
        pr.setAuthor(author.trim().toLowerCase());
        pr.setSourceBranch(sourceBranch);
        pr.setTargetBranch(targetBranch);
        pr.setTitle(title);
        pr.setDescription(description);
        pr.setStatus("open");
        pr.setCreatedAt(Instant.now());
        return pullRequestRepository.save(pr);
    }

    public List<PullRequest> list(VicRepositoryDocument meta) {
        return pullRequestRepository.findByRepoOwnerAndRepoName(meta.getOwner(), meta.getName());
    }

    public PullRequest find(VicRepositoryDocument meta, String idHex) {
        ObjectId id;
        try {
            id = new ObjectId(idHex.trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("invalid pull request id");
        }
        return pullRequestRepository
                .findByIdAndRepoOwnerAndRepoName(id, meta.getOwner(), meta.getName())
                .orElseThrow(() -> new NotFoundException("pull request not found"));
    }

    public void merge(VicRepositoryDocument meta, PullRequest pr, String adminUsername) {
        if (!RepoAccessRules.canAdmin(meta, adminUsername)) {
            throw new ForbiddenException("forbidden");
        }
        if (!"open".equals(pr.getStatus())) {
            throw new BadRequestException("pull request is not open");
        }
        String sourceHash = vicRepositoryService.listBranchHash(meta, pr.getSourceBranch());
        if (sourceHash.isEmpty()) {
            throw new BadRequestException("source branch does not exist");
        }
        String targetHash = vicRepositoryService.listBranchHash(meta, pr.getTargetBranch());
        if (targetHash.isEmpty()) {
            throw new BadRequestException("target branch does not exist");
        }

        if (sourceHash.equals(targetHash)) {
            markMerged(pr.getId());
            return;
        }

        boolean canFf;
        try {
            canFf = commitGraphService.isAncestorInRepo(meta.getOwner(), meta.getName(), targetHash, sourceHash);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        if (!canFf) {
            throw new BadRequestException("non-fast-forward merge not supported yet");
        }

        VicRepositoryDocument fresh = vicRepositoryService.loadMeta(meta.getOwner(), meta.getName());
        vicRepositoryService.updateBranchRef(fresh, pr.getTargetBranch(), sourceHash);
        markMerged(pr.getId());
    }

    private void markMerged(ObjectId id) {
        PullRequest pr = pullRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("pull request not found"));
        pr.setStatus("merged");
        pr.setMergedAt(Instant.now());
        pullRequestRepository.save(pr);
    }
}

```

### src/main/java/com/final_project/versioncontrolservice/service/RepoAccessRules.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/RepoAccessRules.java`

```java
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

```

### src/main/java/com/final_project/versioncontrolservice/service/VicObjectFormat.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/VicObjectFormat.java`

```java
package com.final_project.versioncontrolservice.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

public final class VicObjectFormat {

    private VicObjectFormat() {}

    public record ParsedObject(String type, byte[] content) {}

    public static byte[] zlibInflate(byte[] compressed) throws IOException {
        try (InflaterInputStream zin = new InflaterInputStream(new ByteArrayInputStream(compressed))) {
            return zin.readAllBytes();
        }
    }

    public static ParsedObject parseInflated(byte[] raw) {
        int z = indexOfZero(raw);
        if (z < 0) {
            throw new IllegalArgumentException("invalid object: missing header separator");
        }
        byte[] header = new byte[z];
        System.arraycopy(raw, 0, header, 0, z);
        byte[] content = new byte[raw.length - z - 1];
        System.arraycopy(raw, z + 1, content, 0, content.length);

        String headerStr = new String(header, java.nio.charset.StandardCharsets.UTF_8);
        int sp = headerStr.indexOf(' ');
        if (sp < 0) {
            throw new IllegalArgumentException("invalid object header");
        }
        String type = headerStr.substring(0, sp);
        return new ParsedObject(type, content);
    }

    public static ParsedObject parseCompressed(byte[] compressed) throws IOException {
        return parseInflated(zlibInflate(compressed));
    }

    private static int indexOfZero(byte[] raw) {
        for (int i = 0; i < raw.length; i++) {
            if (raw[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static String headerValue(String commitText, String key) {
        List<String> values = headerValues(commitText, key);
        return values.isEmpty() ? "" : values.get(0);
    }

    public static List<String> headerValues(String commitText, String key) {
        String prefix = key + " ";
        List<String> out = new ArrayList<>();
        for (String ln : commitText.split("\n", -1)) {
            if (ln.isBlank()) {
                break;
            }
            if (ln.startsWith(prefix)) {
                out.add(ln.substring(prefix.length()).trim());
            }
        }
        return out;
    }

    public static CommitData parseCommitContent(byte[] content) {
        String text = new String(content, java.nio.charset.StandardCharsets.UTF_8);
        String tree = headerValue(text, "tree");
        List<String> parents = headerValues(text, "parent");
        return new CommitData(tree, parents);
    }

    public record CommitData(String tree, List<String> parents) {}
}

```

### src/main/java/com/final_project/versioncontrolservice/service/VicRepositoryService.java

**Directory Address:** `src/main/java/com/final_project/versioncontrolservice/service/VicRepositoryService.java`

```java
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

```

### src/test/java/com/final_project/versioncontrolservice/VersionControlServiceApplicationTests.java

**Directory Address:** `src/test/java/com/final_project/versioncontrolservice/VersionControlServiceApplicationTests.java`

```java
package com.final_project.versioncontrolservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VersionControlServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

```

