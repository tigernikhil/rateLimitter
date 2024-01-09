package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;

public class TokenBucketRateLimiter implements RateLimiterStrategy {
    // Implement Token Bucket rate limiting strategy
    // ...
    @Override
    public ResponseData allowRequest(String clientId, String webService) {
        // Implement token bucket algorithm for allowing requests
        // ...
        return ResponseData.builder().isIncremented(true).build(); // or false based on allowance
    }

    @Override
    public ResponseData allowRequestByValue(String clientId, String webService, long count) {
        // Implement token bucket algorithm for allowing requests by value
        // ...
        return ResponseData.builder().isIncremented(true).build(); // or false based on allowance
    }
}