package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;

public class LeakyBucketRateLimiter implements RateLimiterStrategy {
    // Implement Leaky Bucket rate limiting strategy
    // ...
    @Override
    public ResponseData allowRequest(String clientId, String webService) {
        // Implement leaky bucket algorithm for allowing requests
        // ...
        return ResponseData.builder().isIncremented(true).build(); // or false based on allowance
    }

    @Override
    public ResponseData allowRequestByValue(String clientId, String webService, long count) {
        // Implement leaky bucket algorithm for allowing requests by value
        // ...
        return ResponseData.builder().isIncremented(true).build(); // or false based on allowance
    }
}