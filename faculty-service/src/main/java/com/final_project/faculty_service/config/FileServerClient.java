package com.final_project.faculty_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class FileServerClient {
    public WebClient webClient (){
        return WebClient
                .builder()
                .baseUrl("http://file-service")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofSeconds(5))
                ))
                .build();
    }
}
