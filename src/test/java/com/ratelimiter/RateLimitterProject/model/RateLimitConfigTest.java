package com.ratelimiter.RateLimitterProject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class RateLimitConfigTest {

    private RateLimitConfig rateLimitConfig;

    @MockBean
    private ClientLimitConfig mockClientLimitConfig;

    @BeforeEach
    void setUp() {
        rateLimitConfig = new RateLimitConfig();
        List<ClientLimitConfig> clientLimits = new ArrayList<>();

        // Add some dummy client limits for testing
        ClientLimitConfig client1 = new ClientLimitConfig();
        client1.setClientId("client1");
        clientLimits.add(client1);

        ClientLimitConfig client2 = new ClientLimitConfig();
        client2.setClientId("client2");
        clientLimits.add(client2);

        rateLimitConfig.setClientLimits(clientLimits);
    }

    @Test
    void testGetClientConfig_ValidClientId() {
        // Mocking getClientId() to return a specific client ID
        when(mockClientLimitConfig.getClientId()).thenReturn("client1");

        // Getting the client configuration for a valid client ID
        ClientLimitConfig clientConfig = rateLimitConfig.getClientConfig(mockClientLimitConfig.getClientId());

        // Asserting that the retrieved client configuration is not null
        assertEquals("client1", clientConfig.getClientId());
    }

    @Test
    void testGetClientConfig_InvalidClientId() {
        // Mocking getClientId() to return a specific client ID
        when(mockClientLimitConfig.getClientId()).thenReturn("nonexistentClient");

        // Getting the client configuration for an invalid client ID
        ClientLimitConfig clientConfig = rateLimitConfig.getClientConfig(mockClientLimitConfig.getClientId());

        // Asserting that the retrieved client configuration is null for an invalid client ID
        assertEquals(null, clientConfig);
    }
}
