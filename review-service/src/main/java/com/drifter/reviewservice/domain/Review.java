package com.drifter.reviewservice.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private ObjectId id;
    private String userNickname;
    private String title;
    private String submissionTime;
    private String text;
    private String isRecommended;
    private Integer rating;
    private String locale;
    private String productId;

}
