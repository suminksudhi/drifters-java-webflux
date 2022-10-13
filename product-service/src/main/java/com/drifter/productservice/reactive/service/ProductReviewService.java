package com.drifter.productservice.reactive.service;

import com.drifter.productservice.domain.ReviewScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class ProductReviewService {
    private final WebClient reviewApiClient;

    @Autowired
    public ProductReviewService(WebClient reviewApiClient) {
        this.reviewApiClient = reviewApiClient;
    }

    public Mono<ReviewScore> getProductReviewScore(String productId) {
        return reviewApiClient
                .get()
                .uri("/review/" + productId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Product not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding"+ error.toString())))
                .bodyToMono(ReviewScore.class);
    }
}
