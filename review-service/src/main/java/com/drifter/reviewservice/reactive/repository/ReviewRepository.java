package com.drifter.reviewservice.reactive.repository;

import domain.Review;
import domain.ReviewScore;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReviewRepository extends ReactiveMongoRepository<Review, ObjectId> {

   // @Query(value = "{ $or: [ { 'title' : {$regex:?0,$options:'i'} }, { 'description' : {$regex:?0,$options:'i'} } ] }")
   // Page<Review> query(String query, Pageable page);

    Flux<Review[]> findReviewByProductId(String productId);


    @Aggregation({
            "{$match:{ productId:{$eq: ?0 }}}",
            "{$group:{ _id:{ productId: '$productId' },totalRating:{$sum: '$rating'},numberOfReviews:{'$sum':1},'reviews':{$push:'$$ROOT'}}}",
            "{$project:{ _id:0, productId: '$_id.productId' ,avgRating:{ $round:[{ $divide:[ '$totalRating','$numberOfReviews']},2] },numberOfReviews: '$numberOfReviews' }}"
    })
    Flux<ReviewScore> findReviewScoreByProductId(String productId);
}
