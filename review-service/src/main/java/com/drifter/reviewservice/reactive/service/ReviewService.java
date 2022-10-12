package com.drifter.reviewservice.reactive.service;

import com.drifter.reviewservice.reactive.repository.ReviewRepository;
import com.drifter.reviewservice.domain.Review;
import com.drifter.reviewservice.domain.ReviewScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    public Flux<Review> getProductReviews(String productId) {
        return reviewRepository.findReviewByProductId(productId);
    }

    public Mono<ReviewScore> getProductReviewScore(String productId) {
        return reviewRepository.findReviewScoreByProductId(productId);
    }

    public Mono<Review> save(Review review) {
        return reviewRepository.save(review);
    }

    public Mono<Review> update(String id, Review review) {
        return reviewRepository.save(review);
    }

    public Mono<Void> delete(String id) {
        return reviewRepository.deleteById(id);
    }
}
