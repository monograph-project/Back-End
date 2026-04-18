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
