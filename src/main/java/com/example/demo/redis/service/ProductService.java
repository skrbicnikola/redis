package com.example.demo.redis.service;

import com.example.demo.redis.KeyUtil;
import com.example.demo.redis.repository.ProductActionRepository;
import com.example.demo.redis.repository.ProductScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductScoreRepository productScoreRepository;
    private final ProductActionRepository productActionRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductScoreRepository productScoreRepository, ProductActionRepository productActionRepository) {
        this.productScoreRepository = productScoreRepository;
        this.productActionRepository = productActionRepository;
    }

    public void processProductAction(String productId, ProductAction productAction) {
        logger.info("Processing action {} for product {}", productAction, productId);
        productScoreRepository.incrementScoreForProduct(productId, productAction.getScore());
        productActionRepository.save(KeyUtil.DEFAULT_USER_ID, productId, productAction);
    }

    public List<ProductScore> getHighestScoredProducts(int count) {
        return productScoreRepository.getHighestScoredProducts(count);
    }

    public Double getScoreForProduct(String productId) {
        return productScoreRepository.getScoreForProduct(productId);
    }
}