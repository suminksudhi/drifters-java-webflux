package com.drifter.reviewservice.reactive.service;

import com.drifter.reviewservice.reactive.repository.ReviewRepository;
import domain.Review;
import domain.ReviewScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    public Flux<Review[]> getProductReviews(String productId) {
        return reviewRepository.findReviewByProductId(productId);
    }

    public Flux<ReviewScore> getProductReviewScore(String productId) {
        return reviewRepository.findReviewScoreByProductId(productId);
    }
}
