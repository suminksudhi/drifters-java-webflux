package com.drifter.productservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ProductApplication {

    @Value("${app.reviewServiceUrl:http://localhost:9001/}")
    public static String REVIEW_SERVICE_BASE_URL;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    public WebClient productLiveApiClient() {
        return WebClient.create("https://www.adidas.co.uk/api/" );
    }

    @Bean
    public WebClient reviewApiClient() {
        return WebClient.create("http://localhost:9001/");
    }
}
