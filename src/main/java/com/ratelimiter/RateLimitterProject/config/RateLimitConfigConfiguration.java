package com.ratelimiter.RateLimitterProject.config;

import com.ratelimiter.RateLimitterProject.model.ClientLimitConfig;
import com.ratelimiter.RateLimitterProject.model.RateLimitConfig;
import com.ratelimiter.RateLimitterProject.util.RateLimitFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RateLimitConfigConfiguration {

    @Autowired
    RateLimitFileIO rateLimitFileIO;

    @Bean
    public Map<String, ClientLimitConfig> configuredRateLimit() throws IOException {
        RateLimitConfig rateLimitConfig = rateLimitFileIO.readConfigFromFile();
        return convertToMap(rateLimitConfig.getClientLimits());
    }

    Map<String, ClientLimitConfig> convertToMap(List<ClientLimitConfig> clientLimitConfigs) {
        Map<String, ClientLimitConfig> clientLimitConfigMap = new HashMap<>();
        for (ClientLimitConfig clientLimitConfig : clientLimitConfigs) {
            clientLimitConfigMap.put(clientLimitConfig.getClientId(), clientLimitConfig);
        }
        return clientLimitConfigMap;
    }
}
