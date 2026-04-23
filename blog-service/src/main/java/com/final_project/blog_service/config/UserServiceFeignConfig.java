package com.final_project.blog_service.config;

import com.final_project.blog_service.exception.UnauthorizedException;
import com.final_project.blog_service.exception.UserNotFoundException;
import com.final_project.blog_service.exception.UserServiceException;
import com.final_project.blog_service.exception.UserServiceUnavailableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UserServiceFeignConfig {
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
}

