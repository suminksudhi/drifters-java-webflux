package com.drifter.reviewservice.reactive.controller;

import com.drifter.reviewservice.domain.Review;
import com.drifter.reviewservice.reactive.service.ReviewService;
import com.drifter.reviewservice.domain.ReviewScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/{productId}/list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Review> getProductReviews(@PathVariable String productId) {
        return reviewService.getProductReviews(productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ReviewScore> getProductReviewScore(@PathVariable String productId) {
        return reviewService.getProductReviewScore(productId);
    }

    @PutMapping("{id}")
    public Mono<Review> updateById(@PathVariable("id") final String id, @RequestBody final Review review) {
        return reviewService.update(id, review);
    }
    @PostMapping
    public Mono<Review> save(@RequestBody final Review review) {
        return reviewService.save(review);
    }
    @DeleteMapping("{id}")
    public Mono<Void> deleteById(@PathVariable final String id) {
        return reviewService.delete(id);
    }

}