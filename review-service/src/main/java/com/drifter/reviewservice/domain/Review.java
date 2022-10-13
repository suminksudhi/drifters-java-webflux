package com.drifter.reviewservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @NotNull(message = "userNickname cannot be empty")
    @Size(message = "userNickname must be between 3 and 25 characters", min = 2, max = 25)
    private String userNickname;

    @NotNull(message = "Title cannot be empty")
    private String title;
    private String submissionTime;

    @NotNull(message = "text cannot be empty")
    private String text;

    @NotNull(message = "isRecommended cannot be empty")
    private String isRecommended;

    @NotNull(message = "rating cannot be empty")
    @Max(5)
    @Min(1)
    private int rating;
    private String locale;

    @NotNull(message = "productId cannot be empty")
    private String productId;

}
