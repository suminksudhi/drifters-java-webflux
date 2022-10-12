package com.drifter.productservice.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient localApiClient;

    @Autowired
    public ProductService(WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    public Mono<Object> getProduct(String id) {
        return localApiClient
                .get()
                .uri("/products/" + id)
                .retrieve()
                .bodyToMono(Object.class);
    }
}
