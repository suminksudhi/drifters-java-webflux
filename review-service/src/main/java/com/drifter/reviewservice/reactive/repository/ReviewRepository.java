package com.drifter.reviewservice.reactive.repository;

import com.drifter.reviewservice.domain.Review;
import com.drifter.reviewservice.domain.ReviewScore;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {

    Flux<Review> findReviewByProductId(String productId);

    @Aggregation({
            "{$match:{ productId:{$eq: ?0 }}}",
            "{$group:{ _id:{ productId: '$productId' },totalRating:{$sum: '$rating'},numberOfReviews:{'$sum':1},'reviews':{$push:'$$ROOT'}}}",
            "{$project:{ _id:0, productId: '$_id.productId' ,averageReviewScore:{ $round:[{ $divide:[ '$totalRating','$numberOfReviews']},2] },numberOfReviews: '$numberOfReviews' }}"
    })
    Mono<ReviewScore> findReviewScoreByProductId(String productId);
}
