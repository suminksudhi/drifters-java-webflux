package com.drifter.reviewservice.reactive.controller;

import com.drifter.reviewservice.reactive.service.ReviewService;
import domain.ReviewScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/{productId}/list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getProductReviews(@PathVariable String productId) {
        return reviewService.getProductReviews(productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ReviewScore> getProductReviewScore(@PathVariable String productId) {
        return reviewService.getProductReviewScore(productId);
    }


}