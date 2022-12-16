package com.example.demo.redis;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
@ContextConfiguration(initializers = RedisIntegrationTest.Initializer.class)
public class RedisIntegrationTest {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static GenericContainer redis = new GenericContainer<>("redis:6-alpine").withExposedPorts(6379);

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            redis.start();
            String redisContainerIP = "spring.redis.host=" + redis.getHost();
            String redisContainerPort = "spring.redis.port=" + redis.getMappedPort(6379);
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, redisContainerIP, redisContainerPort);
        }
    }
}