package com.drifter.reviewservice.reactive.service;

import com.drifter.reviewservice.reactive.repository.ReviewRepository;
import com.drifter.reviewservice.domain.Review;
import com.drifter.reviewservice.domain.ReviewScore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public Flux<Review> getProductReviews(String productId) {
        return reviewRepository.findReviewByProductId(productId);
    }

    Mono<ReviewScore> emptyProductReviewScore(String productId) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            var review = new ReviewScore();
            review.setProductId(productId);
            return review;
        }));
    }

    public Mono<ReviewScore> getProductReviewScore(String productId) {
        return reviewRepository
                .findReviewScoreByProductId(productId)
                .switchIfEmpty(emptyProductReviewScore(productId));
                //.switchIfEmpty(Mono.error(new ProductNotFoundException(productId)));
    }

    public Mono<Review> save(Review review) {
            return reviewRepository.save(review)
                .onErrorResume(e -> Mono.error(new RuntimeException("Review for product already submitted by the user " + review.getUserNickname())));

    }

    public Mono<Review> update(String id, Review review) {
        review.setId(id);
        return this.save(review);
    }

    public Mono<Void> delete(String id) {
        return reviewRepository.deleteById(id);
    }
}
