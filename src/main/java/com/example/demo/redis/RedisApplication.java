package com.example.demo.redis;

import com.example.demo.redis.service.ProductAction;
import com.example.demo.redis.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class RedisApplication {

    private final ProductService productService;

    public RedisApplication(ProductService productService) {
        this.productService = productService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

//    @Scheduled(fixedDelay = 3000)
    public void simulateProductAction() {
        productService.processProductAction(String.format("PD%d", (int) (Math.random() * 10)), ProductAction.random());
    }
}
