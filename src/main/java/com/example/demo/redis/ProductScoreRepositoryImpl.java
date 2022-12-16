package com.example.demo.redis;

import com.example.demo.redis.repository.ProductScoreRepository;
import com.example.demo.redis.service.ProductScore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.redis.KeyUtil.PRODUCTS_KEY;

@Repository
public class ProductScoreRepositoryImpl implements ProductScoreRepository {
    private final ZSetOperations<String, String> sortedSetOperations;

    public ProductScoreRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.sortedSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public void incrementScoreForProduct(String productId, int incrementBy) {
        sortedSetOperations.incrementScore(PRODUCTS_KEY, productId, incrementBy);
    }

    @Override
    public Double getScoreForProduct(String productId) {
        return sortedSetOperations.score(PRODUCTS_KEY, productId);
    }

    @Override
    public List<ProductScore> getHighestScoredProducts(int count) {
        Set<ZSetOperations.TypedTuple<String>> productSet = sortedSetOperations.reverseRangeByScoreWithScores(PRODUCTS_KEY, 0, 10000, 0, count);
        if (productSet == null || productSet.isEmpty())
            return Collections.emptyList();
        else
            return productSet.stream()
                    .map(it -> new ProductScore(it.getValue(), it.getScore()))
                    .collect(Collectors.toList());
    }
}
