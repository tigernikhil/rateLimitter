package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;

public interface RateLimiterStrategy {
    ResponseData allowRequest(String clientId, String webService);
    ResponseData allowRequestByValue(String clientId, String webService, long count);
}
