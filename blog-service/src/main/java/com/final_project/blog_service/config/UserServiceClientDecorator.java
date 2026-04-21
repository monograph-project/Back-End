package com.final_project.blog_service.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Slf4j
@Configuration
public class UserServiceClientDecorator {

    @Bean
    public CircuitBreaker userServiceCircuitBreaker(
            CircuitBreakerRegistry registry) {

        CircuitBreaker circuitBreaker = registry.circuitBreaker("user-service");
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.warn("User Service CB state change: {}", event))
                .onError(event -> log.error("User Service CB error: {}", event.getThrowable().getMessage()))
                .onSuccess(event -> log.debug("User Service CB success"));

        return circuitBreaker;
    }

    @Bean
    public Retry userServiceRetry(
           RetryRegistry registry) {

        Retry retry = registry.retry("user-service");
        retry.getEventPublisher()
                .onRetry(event -> log.warn("User Service retry: {}", event.getNumberOfRetryAttempts()))
                .onError(event -> log.error("User Service retry failed after {} attempts",
                        event.getNumberOfRetryAttempts()));

        return retry;
    }
}