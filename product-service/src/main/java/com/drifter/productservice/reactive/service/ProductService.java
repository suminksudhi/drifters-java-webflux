package com.drifter.productservice.reactive.service;

import com.drifter.productservice.domain.Product;
import com.drifter.productservice.domain.ReviewScore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@Slf4j
public class ProductService {
    private final WebClient productLiveApiClient;

    @Autowired
    ProductReviewService productReviewService;

    @Autowired
    public ProductService(WebClient productLiveApiClient) {
        this.productLiveApiClient = productLiveApiClient;
    }

    public Mono<Product> getProduct(String productId) {
        return productLiveApiClient
                .get()
                .uri("/products/" + productId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Product not found with id:" + productId)))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding" + error.toString())))
                .bodyToMono(Product.class);
    }

    // aggregation of productReview
    public Mono<Product> fetchProductWithReviewScore(String productId) {
        Mono<Product> product = getProduct(productId);
        Mono<ReviewScore> productReviewScore = productReviewService.getProductReviewScore(productId);
         //ProductWithReviewScore::new
        Mono<Tuple2<Product, ReviewScore>> tuple2  = Mono.zip(product, productReviewScore);
        // access merged mono tuple
        return tuple2.map( result -> {
            Product product1 = result.getT1();
            ReviewScore reviewScore = result.getT2();
            log.info("Fetched  product: {}", product1.getId());
            log.info("Fetched  productReviewScore: {}", reviewScore.getProductId());
            // add the obtained productReview to product
            product1.setAverageReviewScore(reviewScore.getAverageReviewScore());
            product1.setNumberOfReviews(reviewScore.getNumberOfReviews());
            return product1;
        });
    }
}
