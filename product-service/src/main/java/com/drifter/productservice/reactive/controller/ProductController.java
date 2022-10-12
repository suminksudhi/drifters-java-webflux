package com.drifter.productservice.reactive.controller;

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
    public Object getProducts(@PathVariable String productId) {
        return productService.getProduct(productId);
    }
}