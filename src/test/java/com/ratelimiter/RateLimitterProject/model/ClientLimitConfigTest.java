package com.ratelimiter.RateLimitterProject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientLimitConfigTest {
    private ClientLimitConfig clientLimitConfig;

    @BeforeEach
    public void setUp() {
        clientLimitConfig = new ClientLimitConfig();
    }

    @Test
    public void testSetAndGetClientId() {
        String clientId = "123456";
        clientLimitConfig.setClientId(clientId);
        assertEquals(clientId, clientLimitConfig.getClientId());
    }

    @Test
    public void testSetAndGetLimits() {
        Map<String, Long> limits = new HashMap<>();
        limits.put("service1", 100L);
        limits.put("service2", 200L);

        clientLimitConfig.setLimits(limits);

        Map<String, Long> retrievedLimits = clientLimitConfig.getLimits();
        assertNotNull(retrievedLimits);
        assertEquals(limits.size(), retrievedLimits.size());
        assertEquals(limits.get("service1"), retrievedLimits.get("service1"));
        assertEquals(limits.get("service2"), retrievedLimits.get("service2"));
    }

    @Test
    public void testSetAndGetAdditionalAllowance() {
        Map<String, Long> additionalAllowance = new HashMap<>();
        additionalAllowance.put("service1", 50L);
        additionalAllowance.put("service2", 75L);

        clientLimitConfig.setAdditionalAllowance(additionalAllowance);

        Map<String, Long> retrievedAdditionalAllowance = clientLimitConfig.getAdditionalAllowance();
        assertNotNull(retrievedAdditionalAllowance);
        assertEquals(additionalAllowance.size(), retrievedAdditionalAllowance.size());
        assertEquals(additionalAllowance.get("service1"), retrievedAdditionalAllowance.get("service1"));
        assertEquals(additionalAllowance.get("service2"), retrievedAdditionalAllowance.get("service2"));
    }

    @Test
    public void testSetAndGetTimeWindow() {
        long timeWindow = 60000L;
        clientLimitConfig.setTimeWindow(timeWindow);
        assertEquals(timeWindow, clientLimitConfig.getTimeWindow());
    }

    @Test
    public void testSetServiceLimit() {
        clientLimitConfig.setLimits(new HashMap<>());
        clientLimitConfig.setServiceLimit("service1", 100L);
        assertEquals(1, clientLimitConfig.getLimits().size());
        assertEquals(100L, clientLimitConfig.getLimits().get("service1"));
    }
}