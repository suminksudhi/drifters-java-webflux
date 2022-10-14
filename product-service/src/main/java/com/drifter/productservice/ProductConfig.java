package com.drifter.productservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class ProductConfig {

    @Value("${drifter.review.url:http://localhost:9001/}")
    public String reviewServiceUrl;

    @Bean
    public WebClient productLiveApiClient() {
        return WebClient.create("https://www.adidas.co.uk/api/" );
    }

    @Bean
    public WebClient reviewApiClient() {
        log.info("Review service url: {}", this.reviewServiceUrl);
        return WebClient.create(this.reviewServiceUrl);
    }
}
