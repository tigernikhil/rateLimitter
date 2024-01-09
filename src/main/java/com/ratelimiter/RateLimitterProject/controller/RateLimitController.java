package com.ratelimiter.RateLimitterProject.controller;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;
import com.ratelimiter.RateLimitterProject.service.RateLimitterWrapper;
import com.ratelimiter.RateLimitterProject.service.SlidingWindowCounters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ratelimiter.RateLimitterProject.model.Request.RequestData;

@RestController
public class RateLimitController {

//    private final SlidingWindowCounters rateLimiter;
    private RateLimitterWrapper rateLimiterWrapper;

    @Autowired
    public RateLimitController(SlidingWindowCounters rateLimiter) {
        //TODO this can be extended
        rateLimiterWrapper = new RateLimitterWrapper(rateLimiter);
    }

    @PostMapping("/allowRequest")
    public ResponseEntity<ResponseData> allowRequest(@RequestBody RequestData requestData) {
        ResponseData responseData = rateLimiterWrapper.allowRequest(requestData.getClientId(), requestData.getWebService());
        if (responseData.getIsIncremented()) {
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseData);
        }
    }

    @PostMapping("/allowRequestByValue")
    public ResponseEntity<ResponseData> allowRequestByValue(@RequestBody RequestData requestData) {
        ResponseData responseData = rateLimiterWrapper.allowRequestByValue(requestData.getClientId(), requestData.getWebService(), requestData.getCounterValue());
        if (responseData.getIsIncremented()) {
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseData);
        }
    }
}
