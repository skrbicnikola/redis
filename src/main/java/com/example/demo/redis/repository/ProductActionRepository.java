package com.example.demo.redis.repository;

import com.example.demo.redis.service.ProductAction;

public interface ProductActionRepository {
    void save(String userId, String productId, ProductAction productAction);
}
