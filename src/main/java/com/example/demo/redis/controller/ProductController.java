package com.example.demo.redis.controller;

import com.example.demo.redis.service.ProductScore;
import com.example.demo.redis.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/score")
    public List<ProductScore> getHighestScoredProducts(@RequestParam(required = false, defaultValue = "3") int count) {
        return productService.getHighestScoredProducts(count);
    }

    @GetMapping("/{productId}/score")
    public Double getScoreForProduct(@PathVariable String productId) {
        return productService.getScoreForProduct(productId);
    }
}