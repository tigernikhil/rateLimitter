package com.ratelimiter.RateLimitterProject.model.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseData {
    private String clientId;
    private String webService;
    private int counterValue;
    private String message;
    private Boolean isIncremented;
    private Long rateLimit;
}
