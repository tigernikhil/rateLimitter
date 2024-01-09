package com.ratelimiter.RateLimitterProject.model.Request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestDataTest {

    @Test
    public void testSetAndGetClientId() {
        String clientId = "123456";
        RequestData requestData = new RequestData();
        requestData.setClientId(clientId);
        assertEquals(clientId, requestData.getClientId());
    }

    @Test
    public void testSetAndGetWebService() {
        String webService = "someWebService";
        RequestData requestData = new RequestData();
        requestData.setWebService(webService);
        assertEquals(webService, requestData.getWebService());
    }

    @Test
    public void testSetAndGetCounterValue() {
        int counterValue = 5;
        RequestData requestData = new RequestData();
        requestData.setCounterValue(counterValue);
        assertEquals(counterValue, requestData.getCounterValue());
    }
}
