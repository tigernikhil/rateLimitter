package com.ratelimiter.RateLimitterProject.controller;

import com.ratelimiter.RateLimitterProject.model.Request.RequestData;
import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;
import com.ratelimiter.RateLimitterProject.service.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RateLimitControllerTest {

    private RateLimiter rateLimiter;
    private RateLimitController rateLimitController;

    @BeforeEach
    void setUp() {
        rateLimiter = mock(RateLimiter.class);
        rateLimitController = new RateLimitController(rateLimiter);
    }

    @Test
    void incrementCounter_Success() {
        RequestData requestData = new RequestData("client_id", "web_service", 1);

        ResponseData responseData = ResponseData.builder().message("Success").isIncremented(true).build();
        when(rateLimiter.incrementCounter("client_id", "web_service")).thenReturn(responseData);

        ResponseEntity<ResponseData> responseEntity = rateLimitController.incrementCounter(requestData);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
    }

    @Test
    void incrementCounter_TooManyRequests() {
        RequestData requestData = new RequestData("client_id", "web_service", 1);

        ResponseData responseData = ResponseData.builder().message("RequestBlocked").isIncremented(false).build();
        when(rateLimiter.incrementCounter("client_id", "web_service")).thenReturn(responseData);

        ResponseEntity<ResponseData> responseEntity = rateLimitController.incrementCounter(requestData);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
    }
}
