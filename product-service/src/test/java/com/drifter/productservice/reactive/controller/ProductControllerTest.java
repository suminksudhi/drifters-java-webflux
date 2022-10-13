package com.drifter.productservice.reactive.controller;

import com.drifter.productservice.domain.Product;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProductControllerTest {

  private final WebTestClient webClient;

  @Test
  @DisplayName("Should get product with average review score")
  void shouldGetProductsWithReviews() {
    var productId = "EG4959";
    webClient.get().uri("/product/{productId}", productId)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody(Product.class)
            .value(Product::getId, CoreMatchers.equalTo(productId))
            .value(Product::getNumberOfReviews, CoreMatchers.notNullValue())
            .value(Product::getAverageReviewScore, CoreMatchers.notNullValue());
  }

}