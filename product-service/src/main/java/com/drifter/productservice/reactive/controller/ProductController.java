package com.drifter.productservice.reactive.controller;

import com.drifter.productservice.domain.Product;
import com.drifter.productservice.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{productId}")
    public Mono<Product> getProducts(@PathVariable String productId) {
        return productService.fetchProductWithReviewScore(productId);
    }
}