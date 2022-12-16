package com.example.demo.redis.service;

public class ProductScore {
    String value;
    Double score;

    public ProductScore(String value, Double score) {
        this.value = value;
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public Double getScore() {
        return score;
    }
}
