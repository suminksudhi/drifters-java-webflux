package com.drifter.productservice.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewScore {
    private float averageReviewScore;
    private long numberOfReviews;
    private String productId;
}


