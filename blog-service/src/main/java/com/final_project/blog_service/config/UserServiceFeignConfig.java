package com.final_project.blog_service.config;

import com.final_project.blog_service.exception.UnauthorizedException;
import com.final_project.blog_service.exception.UserNotFoundException;
import com.final_project.blog_service.exception.UserServiceException;
import com.final_project.blog_service.exception.UserServiceUnavailableException;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UserServiceFeignConfig {

    /**
     * Custom error decoder
     * Converts HTTP errors to meaningful exceptions
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            log.error("User Service error: {} {}", response.status(), response.reason());

            switch (response.status()) {
                case 400:
                    return new UserServiceException("Bad request to User Service");
                case 401:
                case 403:
                    return new UnauthorizedException("Unauthorized access to User Service");
                case 404:
                    return new UserNotFoundException("User not found in User Service");
                case 500:
                case 502:
                case 503:
                case 504:
                    return new UserServiceUnavailableException("User Service is unavailable");
                default:
                    return new UserServiceException("User Service error: " + response.reason());
            }
        };
    }

    /**
     * Request interceptor for service-to-service authentication
     * Adds API key or service token to requests
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Add API key if configured
            String apiKey = System.getenv("USER_SERVICE_API_KEY");
            if (apiKey != null && !apiKey.isBlank()) {
                template.header("X-API-Key", apiKey);
            }

            // Add service identification
            template.header("X-Service-Name", "article-service");
            template.header("X-Request-ID", generateRequestId());

            log.debug("Request prepared for User Service: {}", template.request().getUrl());
        };
    }

    /**
     * Request/response timeout configuration
     */
    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(
                // Connect timeout
                // Read timeout
        );
    }

    private String generateRequestId() {
        return java.util.UUID.randomUUID().toString();
    }
}

