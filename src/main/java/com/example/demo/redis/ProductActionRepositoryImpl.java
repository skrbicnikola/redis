package com.example.demo.redis;

import com.example.demo.redis.repository.ProductActionRepository;
import com.example.demo.redis.service.ProductAction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.redis.KeyUtil.ACTION;
import static com.example.demo.redis.KeyUtil.PRODUCT_ID;

@Repository
public class ProductActionRepositoryImpl implements ProductActionRepository {
    private final StreamOperations<String, String, String> streamOperations;

    public ProductActionRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.streamOperations = redisTemplate.opsForStream();
    }

    @Override
    public void save(String userId, String productId, ProductAction productAction) {
        Map<String, String> map = new HashMap<>();
        map.put(ACTION, productAction.name());
        map.put(PRODUCT_ID, productId);
        streamOperations.add(KeyUtil.getStreamKeyForUser(userId), map);
    }
}
