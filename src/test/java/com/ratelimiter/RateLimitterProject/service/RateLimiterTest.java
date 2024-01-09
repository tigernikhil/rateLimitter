package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.config.RateLimitConfigConfiguration;
import com.ratelimiter.RateLimitterProject.model.ClientLimitConfig;
import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;
import com.ratelimiter.RateLimitterProject.util.RateLimitFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RateLimiterTest {

    @Mock
    private RateLimitFileIO rateLimitFileIO;

    @Mock
    private RateLimitConfigConfiguration rateLimitConfigConfiguration;

    @InjectMocks
    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIncrementCounter() throws IOException {
        ClientLimitConfig clientLimitConfig = new ClientLimitConfig();
        clientLimitConfig.setClientId("testClient");
        clientLimitConfig.setServiceLimit("testService", 10L);
        clientLimitConfig.setTimeWindow(5L);

        Map<String, ClientLimitConfig> clientLimitConfigMap = Collections.singletonMap("testClient", clientLimitConfig);
        when(rateLimitConfigConfiguration.configuredRateLimit()).thenReturn(clientLimitConfigMap);

        ResponseData responseData = rateLimiter.incrementCounter("testClient", "testService");

        assertEquals("WrongInput", responseData.getMessage());
        assertEquals(0, responseData.getCounterValue());
        assertEquals("testClient", responseData.getClientId());
        assertEquals("testService", responseData.getWebService());
        assertEquals(false, responseData.getIsIncremented());
    }

    @Test
    void testIncrementCounterByValue() throws IOException {
        ClientLimitConfig clientLimitConfig = new ClientLimitConfig();
        clientLimitConfig.setClientId("testClient");
        clientLimitConfig.setServiceLimit("testService", 10L);
        clientLimitConfig.setTimeWindow(5L);

        Map<String, ClientLimitConfig> clientLimitConfigMap = Collections.singletonMap("testClient", clientLimitConfig);
        when(rateLimitConfigConfiguration.configuredRateLimit()).thenReturn(clientLimitConfigMap);

        ResponseData responseData = rateLimiter.incrementCounterByValue("testClient", "testService", 3L);

        assertEquals("WrongInput", responseData.getMessage());
        assertEquals(0, responseData.getCounterValue());
        assertEquals("testClient", responseData.getClientId());
        assertEquals("testService", responseData.getWebService());
        assertEquals(false, responseData.getIsIncremented());
    }

    @Test
    void testIncrementCounter_NoClientConfig() throws IOException {
        when(rateLimitConfigConfiguration.configuredRateLimit()).thenReturn(Collections.emptyMap());

        ResponseData responseData = rateLimiter.incrementCounter("nonExistentClient", "testService");

        assertEquals("WrongInput", responseData.getMessage());
        assertEquals(false, responseData.getIsIncremented());
    }

    @Test
    void testIncrementCounterByValue_NoServiceLimit() throws IOException {
        ClientLimitConfig clientLimitConfig = new ClientLimitConfig();
        clientLimitConfig.setClientId("testClient");
        clientLimitConfig.setTimeWindow(5L);

        Map<String, ClientLimitConfig> clientLimitConfigMap = Collections.singletonMap("testClient", clientLimitConfig);
        when(rateLimitConfigConfiguration.configuredRateLimit()).thenReturn(clientLimitConfigMap);

        ResponseData responseData = rateLimiter.incrementCounterByValue("testClient", "nonExistentService", 3L);

        assertEquals("WrongInput", responseData.getMessage());
        assertEquals(false, responseData.getIsIncremented());
    }


}
