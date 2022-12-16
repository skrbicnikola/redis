package com.example.demo.redis.service;

import com.example.demo.redis.KeyUtil;
import com.example.demo.redis.RedisIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static com.example.demo.redis.KeyUtil.*;

public class ProductServiceTest extends RedisIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void givenProductViewed_whenGettingScoreForProduct_thenProductExistsAndHasSameScore() {
        String productId = "testProduct1";
        productService.processProductAction(productId, ProductAction.PRODUCT_VIEWED);

        MapRecord<String, Object, Object> lastEntry = redisTemplate.opsForStream()
                .reverseRange(KeyUtil.getStreamKeyForDefaultUser(), Range.unbounded(), RedisZSetCommands.Limit.limit().count(1)).get(0);

        Assertions.assertEquals(productService.getScoreForProduct(productId), ProductAction.PRODUCT_VIEWED.getScore());
        Assertions.assertEquals(ProductAction.valueOf(lastEntry.getValue().get(ACTION).toString()), ProductAction.PRODUCT_VIEWED);
        Assertions.assertEquals(lastEntry.getValue().get(PRODUCT_ID), productId);
    }

    @Test
    void givenNoActionForProduct_whenGettingScoreForProduct_thenNullIsReturned() {
        String productId = "testProductX";
        Assertions.assertNull(productService.getScoreForProduct(productId));
    }

    @Test
    void givenProductAddedToCartAndBought_whenGettingScoreForProduct_thenProductExistsAndHasExpectedScore() {
        String productId = "testProduct1";
        productService.processProductAction(productId, ProductAction.PRODUCT_ADDED_TO_CART);
        productService.processProductAction(productId, ProductAction.PRODUCT_BOUGHT);
        Assertions.assertEquals(productService.getScoreForProduct(productId),
                ProductAction.PRODUCT_ADDED_TO_CART.getScore() + ProductAction.PRODUCT_BOUGHT.getScore());
    }

    @Test
    void givenProductBought_whenGettingScoreForProduct_thenProductExistsAndHasSameScore222() {
        productService.processProductAction("testProduct1", ProductAction.PRODUCT_VIEWED);
        productService.processProductAction("testProduct2", ProductAction.PRODUCT_ADDED_TO_WISHLIST);
        productService.processProductAction("testProduct3", ProductAction.PRODUCT_ADDED_TO_CART);
        productService.processProductAction("testProduct4", ProductAction.PRODUCT_BOUGHT);

        List<MapRecord<String, Object, Object>> streamEntries = redisTemplate.opsForStream().reverseRange(
                KeyUtil.getStreamKeyForDefaultUser(), Range.unbounded());
        MapRecord<String, Object, Object> lastEntry = streamEntries.get(0);

        Assertions.assertEquals(productService.getHighestScoredProducts(1).get(0).getValue(), "testProduct4");
        Assertions.assertEquals(4, streamEntries.size());
        Assertions.assertEquals(lastEntry.getValue().get(PRODUCT_ID), "testProduct4");
        Assertions.assertEquals(ProductAction.valueOf(lastEntry.getValue().get(ACTION).toString()), ProductAction.PRODUCT_BOUGHT);
    }


    @AfterEach
    public void flush() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}