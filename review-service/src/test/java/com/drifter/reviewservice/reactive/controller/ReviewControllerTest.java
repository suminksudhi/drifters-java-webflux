package com.drifter.reviewservice.reactive.controller;

import com.drifter.reviewservice.domain.Review;
import com.drifter.reviewservice.domain.ReviewScore;
import com.drifter.reviewservice.reactive.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
// @ExtendWith(SpringExtension.class)
// @WebFluxTest(ReviewController.class)
class ReviewControllerTest {

  @Autowired
  private WebTestClient webClient;

  @MockBean
  private ReviewService reviewService;

  @Test
  @DisplayName("Should get product reviews only by productId")
  void shouldGetProductsReviewWithProductId() {
    var productId = "EG4959";
    webClient.get().uri("/review/{productId}/list", productId)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBodyList(Review.class)
            .value(reviews ->
                    reviews.forEach(review ->
                            AssertionErrors.assertEquals("productId matches for reviewId"+ review.getId(), productId, review.getProductId())
                    ));
  }

  @Test
  @DisplayName("Should get product reviews score")
  void shouldGetProductsReviewByProductId() {

    var productId = "EG4959";
    var username = "user";
    var token = "password";
    ReviewScore reviewScore = new ReviewScore();
    reviewScore.setProductId(productId);
    Mockito.when(reviewService.getProductReviewScore(productId)).thenReturn(Mono.just(reviewScore));


    webClient.get().uri("/review/{productId}", productId)
            .header("Authorization", "Basic " + Base64Utils
                    .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8)))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBodyList(ReviewScore.class);
  }


  @Test
  @DisplayName("Should prevent review creation for unauthorized userß")
  void shouldPreventReviewCreationForUnauthorizedUser() {
    var productId = "EG4959";
    var username = "user1"; // unauthorized user
    var token = "password";

    webClient.post().uri("/review/", productId)
            .header("Authorization", "Basic " + Base64Utils
                    .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8)))
            .exchange()
            .expectStatus().isUnauthorized();
  }

  @Test
  @DisplayName("Should prevent review update for unauthorized user")
  void shouldPreventReviewUpdationForUnauthorizedUser() {
    var productId = "EG4959";
    var username = "user1"; // unauthorized user
    var token = "password";

    webClient.put().uri("/review/", productId)
            .header("Authorization", "Basic " + Base64Utils
                    .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8)))
            .exchange()
            .expectStatus().isUnauthorized();
  }

  @Test
  @DisplayName("Should handle request validation while saving")
  void shouldHandleRequestValidationWhilingSaving() {
    var username = "user"; // unauthorized user
    var token = "password";
    Map<String, String> r = new HashMap<>();
    webClient.post().uri("/review")
            .body(Mono.just(r), Review.class)
            .header("Authorization", "Basic " + Base64Utils
                    .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8)))
            .exchange()
            .expectStatus().isBadRequest();
  }

  @Test
  @DisplayName("Should allow review creation for authorized user")
  void shouldAllowReviewCreationForAuthorizedUser() {
    var username = "user"; // unauthorized user
    var token = "password";
    Map<String, String> r = new HashMap<>();
    r.put("userNickname", "sumin");
    r.put("title",  "hello");
    r.put("text", "Amazing shoes w.r.t comfort. Being using it for a month with no issues");
    r.put("isRecommended", "true");
    r.put("rating", String.valueOf(1));
    r.put("locale", "en_GB");
    r.put("productId", "EE6999");
    webClient.post().uri("/review")
            .body(Mono.just(r), Review.class)
            .header("Authorization", "Basic " + Base64Utils
                    .encodeToString((username + ":" + token).getBytes(StandardCharsets.UTF_8)))
            .exchange()
            .expectStatus().isOk();
  }
}