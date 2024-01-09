package com.ratelimiter.RateLimitterProject.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RateLimitConfig {
    private List<ClientLimitConfig> clientLimits = new ArrayList<>();

    public RateLimitConfig() {
    }

    public List<ClientLimitConfig> getClientLimits() {
        return clientLimits;
    }

    public void setClientLimits(List<ClientLimitConfig> clientLimits) {
        this.clientLimits = clientLimits;
    }

    public ClientLimitConfig getClientConfig(String clientId) {
        for (ClientLimitConfig clientLimitConfig : clientLimits) {
            if (clientLimitConfig.getClientId().equals(clientId)) {
                return clientLimitConfig;
            }
        }
        return null;
    }
}
