package com.example.demo.redis.repository;

import com.example.demo.redis.service.ProductScore;

import java.util.List;

public interface ProductScoreRepository {
    void incrementScoreForProduct(String productKey, int incrementBy);

    Double getScoreForProduct(String productKey);

    List<ProductScore> getHighestScoredProducts(int count);
}
