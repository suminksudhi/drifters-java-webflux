package com.drifter.productservice.reactive.controller;

import com.drifter.productservice.domain.Product;
import com.drifter.productservice.reactive.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{productId}")
    public Mono<Product> getProducts(@PathVariable String productId) {
        log.info("Fetching product with score for productId:" + productId);
        return productService.fetchProductWithReviewScore(productId);
    }
}