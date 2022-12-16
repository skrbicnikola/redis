package com.example.demo.redis.service;

public enum ProductAction {
    PRODUCT_VIEWED(10),
    PRODUCT_ADDED_TO_WISHLIST(15),
    PRODUCT_ADDED_TO_CART(25),
    PRODUCT_BOUGHT(30);
    private final int score;

    ProductAction(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static ProductAction random() {
        return values()[(int) (Math.random() * values().length)];
    }
}