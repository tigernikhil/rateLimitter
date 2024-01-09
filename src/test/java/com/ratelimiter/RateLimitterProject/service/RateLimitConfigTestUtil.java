package com.ratelimiter.RateLimitterProject.service;
import com.ratelimiter.RateLimitterProject.model.ClientLimitConfig;
import com.ratelimiter.RateLimitterProject.model.RateLimitConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimitConfigTestUtil {

    public static RateLimitConfig createSampleRateLimitConfig() {
        RateLimitConfig rateLimitConfig = new RateLimitConfig();
        List<ClientLimitConfig> clientLimitConfigs = new ArrayList<>();

        // Create sample ClientLimitConfig instances
        ClientLimitConfig client1 = new ClientLimitConfig();
        client1.setClientId("client1");
        Map<String, Long> limits1 = new HashMap<>();
        limits1.put("webServiceA", 20L);
        limits1.put("webServiceB", 15L);
        client1.setLimits(limits1);
        client1.setAdditionalAllowance(new HashMap<>());
        client1.setTimeWindow(60);

        ClientLimitConfig client2 = new ClientLimitConfig();
        client2.setClientId("client2");
        Map<String, Long> limits2 = new HashMap<>();
        limits2.put("webServiceA", 30L);
        limits2.put("webServiceC", 25L);
        client2.setLimits(limits2);
        client2.setAdditionalAllowance(new HashMap<>());
        client2.setTimeWindow(45);

        // Add sample ClientLimitConfig instances to the list
        clientLimitConfigs.add(client1);
        clientLimitConfigs.add(client2);

        rateLimitConfig.setClientLimits(clientLimitConfigs);
        return rateLimitConfig;
    }
}
