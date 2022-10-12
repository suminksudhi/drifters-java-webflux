package domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewScore {
    private float avgRating;
    private long numberOfReviews;
    private String productId;
}


