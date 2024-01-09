package com.ratelimiter.RateLimitterProject.controller;

import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;
import com.ratelimiter.RateLimitterProject.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ratelimiter.RateLimitterProject.model.Request.RequestData;

@RestController
public class RateLimitController {

    private final RateLimiter rateLimiter;

    @Autowired
    public RateLimitController(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/incrementCounter")
    public ResponseEntity<ResponseData> incrementCounter(@RequestBody RequestData requestData) {
        ResponseData responseData = rateLimiter.incrementCounter(requestData.getClientId(), requestData.getWebService());
        if (responseData.getIsIncremented()) {
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseData);
        }
    }

    @PostMapping("/incrementCounterByValue")
    public ResponseEntity<ResponseData> incrementCounterByValue(@RequestBody RequestData requestData) {
        ResponseData responseData = rateLimiter.incrementCounterByValue(requestData.getClientId(), requestData.getWebService(), requestData.getCounterValue());
        if (responseData.getIsIncremented()) {
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseData);
        }
    }
}
