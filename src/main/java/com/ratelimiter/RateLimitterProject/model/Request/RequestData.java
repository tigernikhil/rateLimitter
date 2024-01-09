package com.ratelimiter.RateLimitterProject.model.Request;

import lombok.Data;

@Data
public class RequestData {
    private String clientId;
    private String webService;
    private int counterValue;

    public RequestData() {}

    public RequestData(String clientId, String webService, int i) {
        this.clientId = clientId;
        this.webService = webService;
        this.counterValue = i;
    }
}
