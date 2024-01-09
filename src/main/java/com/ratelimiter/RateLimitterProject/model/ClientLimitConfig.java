package com.ratelimiter.RateLimitterProject.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientLimitConfig {
    private String clientId;
    private Map<String, Long> limits;
    private Map<String, Long> additional_allowance;
    private long time_window;

    public Long getServiceLimit(String service) {
        return limits.get(service);
    }

    public Long getAdditionalAllowance(String service) {
        return additional_allowance.getOrDefault(service, 0L);
    }

    public long getTimeWindow() {
        return time_window;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map<String, Long> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Long> limits) {
        this.limits = limits;
    }

    public Map<String, Long> getAdditionalAllowance() {
        return additional_allowance;
    }

    public void setAdditionalAllowance(Map<String, Long> additionalAllowance) {
        if (this.additional_allowance == null || this.additional_allowance.isEmpty()) this.additional_allowance = new HashMap<>();
        this.additional_allowance = additionalAllowance;
    }

    public void setTimeWindow(long timeWindow) {
        this.time_window = timeWindow;
    }

    public void setServiceLimit(String service, Long limit) {
        if (limits == null || limits.isEmpty()) limits = new HashMap<>();
        limits.put(service, limit);
    }
}
