package com.ratelimiter.RateLimitterProject.config;

import com.ratelimiter.RateLimitterProject.model.ClientLimitConfig;
import com.ratelimiter.RateLimitterProject.model.RateLimitConfig;
import com.ratelimiter.RateLimitterProject.util.RateLimitFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimitConfigConfigurationTest {

    @Mock
    private RateLimitFileIO rateLimitFileIO;

    @InjectMocks
    private RateLimitConfigConfiguration rateLimitConfigConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testConfiguredRateLimit() throws IOException {
        // Mocking the behavior of readConfigFromFile method
        RateLimitConfig mockedRateLimitConfig = new RateLimitConfig();
        List<ClientLimitConfig> clientLimitConfigs = new ArrayList<>();
        ClientLimitConfig clientLimitConfig = new ClientLimitConfig();
        clientLimitConfig.setClientId("client1");
        clientLimitConfigs.add(clientLimitConfig);
        mockedRateLimitConfig.setClientLimits(clientLimitConfigs);

        when(rateLimitFileIO.readConfigFromFile()).thenReturn(mockedRateLimitConfig);

        // Executing the method
        Map<String, ClientLimitConfig> result = rateLimitConfigConfiguration.configuredRateLimit();

        // Asserting that the result is as expected
        assertEquals(1, result.size());
        assertEquals("client1", result.get("client1").getClientId());
    }

    @Test
    void testConvertToMap() {
        // Creating a list of clientLimitConfigs for testing
        List<ClientLimitConfig> clientLimitConfigs = new ArrayList<>();
        ClientLimitConfig client1 = new ClientLimitConfig();
        client1.setClientId("client1");
        clientLimitConfigs.add(client1);

        // Executing the method
        Map<String, ClientLimitConfig> result = rateLimitConfigConfiguration.convertToMap(clientLimitConfigs);

        // Asserting that the result is as expected
        assertEquals(1, result.size());
        assertEquals("client1", result.get("client1").getClientId());
    }

}
