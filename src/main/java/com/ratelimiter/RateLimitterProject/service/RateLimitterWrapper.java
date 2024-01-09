package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;

public class RateLimitterWrapper {
    private RateLimiterStrategy rateLimiterStrategy;

    public RateLimitterWrapper(RateLimiterStrategy rateLimiterStrategy) {
        this.rateLimiterStrategy = rateLimiterStrategy;
    }

    public ResponseData allowRequest(String clientId, String webService) {
        return rateLimiterStrategy.allowRequest(clientId, webService);
    }

    public ResponseData allowRequestByValue(String clientId, String webService, long count) {
        return rateLimiterStrategy.allowRequestByValue(clientId, webService, count);
    }

    public void setRateLimiterStrategy(RateLimiterStrategy rateLimiterStrategy) {
        this.rateLimiterStrategy = rateLimiterStrategy;
    }
}

