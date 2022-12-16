package com.example.demo.redis;

public class KeyUtil {
    public static final String PRODUCT_ID = "productId";
    public static final String ACTION = "action";
    private static final String USER_KEY_PREFIX = "user";
    public static final String DEFAULT_USER_ID = "123";

    public static final String PRODUCTS_KEY = "products";


    public static String getStreamKeyForDefaultUser() {
        return String.format("%s:%s", USER_KEY_PREFIX, DEFAULT_USER_ID);
    }

    public static String getStreamKeyForUser(String userId) {
        return String.format("%s:%s", USER_KEY_PREFIX, userId);
    }
}
