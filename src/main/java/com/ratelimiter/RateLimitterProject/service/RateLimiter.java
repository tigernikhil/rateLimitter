package com.ratelimiter.RateLimitterProject.service;

import com.ratelimiter.RateLimitterProject.config.RateLimitConfigConfiguration;
import com.ratelimiter.RateLimitterProject.exception.RateLimitException;
import com.ratelimiter.RateLimitterProject.model.ClientLimitConfig;
import com.ratelimiter.RateLimitterProject.model.Response.ResponseData;
import com.ratelimiter.RateLimitterProject.util.RateLimitFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RateLimiter {
    private static final Logger LOGGER = Logger.getLogger(RateLimiter.class.getName());
    private final Map<String, Map<String, Long>> requestCounts;
    private final Map<String, Map<String, Map<Long, Integer>>> requestsPerWindow; // New field
    private Map<String, ClientLimitConfig> clientLimitConfigMap;


    @Autowired
    public RateLimiter(RateLimitFileIO rateLimitFileIO, RateLimitConfigConfiguration rateLimitConfigConfiguration) {
        this.requestCounts = new ConcurrentHashMap<>();
        this.requestsPerWindow = new ConcurrentHashMap<>(); // Initialize requestsPerWindow
        this.clientLimitConfigMap = new ConcurrentHashMap<>();

        try {
            this.clientLimitConfigMap = rateLimitConfigConfiguration.configuredRateLimit();
        } catch (IOException e) {
            logError("Error initializing RateLimiter: " + e.getMessage());
        }
    }

    public synchronized ResponseData incrementCounter(String clientId, String webService) {
        try {
            ClientLimitConfig clientConfig = clientLimitConfigMap.get(clientId);
            if (clientConfig == null) {
                logError("Invalid client ID");
                return ResponseData.builder()
                        .clientId(clientId)
                        .webService(webService)
                        .message("WrongInput")
                        .isIncremented(false)
                        .build();
            }

            Long rateLimit = clientConfig.getServiceLimit(webService);
            if (rateLimit == null) {
                logError("Invalid web service");
                return ResponseData.builder()
                        .clientId(clientId)
                        .rateLimit(rateLimit)
                        .webService(webService)
                        .message("WrongInput")
                        .isIncremented(false).build();
            }

//            long timeWindow = clientConfig.getTimeWindow() * 1000;
            long currentTime = System.currentTimeMillis();
            long currentBucket = currentTime / 1000L;

            Map<String, Map<Long, Integer>> serviceWindowCounts = requestsPerWindow.computeIfAbsent(clientId, k -> new ConcurrentHashMap<>());
            Map<Long, Integer> windowCounts = serviceWindowCounts.computeIfAbsent(webService, k -> new ConcurrentHashMap<>());
            cleanUpOldCounters(windowCounts, currentBucket, clientConfig.getTimeWindow());

            int currentCount = windowCounts.values().stream().mapToInt(Integer::intValue).sum();
            if (currentCount >= rateLimit) {
                logWarning("Request blocked due to rate limiting");
                return ResponseData.builder()
                        .message("RequestBlocked")
                        .rateLimit(rateLimit)
                        .isIncremented(false)
                        .build();
            }
            windowCounts.put(currentBucket, windowCounts.getOrDefault(currentBucket, 0) + 1);
            logInfo("Request accepted");
            //windowCounts.forEach((key, value) -> System.out.println("Bucket: " + key + ", Count: " + value));
            return ResponseData.builder().message("Success")
                    .counterValue(currentCount)
                    .clientId(clientId)
                    .rateLimit(rateLimit)
                    .webService(webService)
                    .isIncremented(true)
                    .build();
        } catch (RateLimitException e) {
            logError("RateLimitException occurred: " + e.getMessage());
            throw e; // Re-throw the RateLimitException
        } catch (Exception e) {
            logError("Exception occurred: " + e.getMessage());
            throw new RateLimitException("An error occurred while processing the request");
        }
    }

    public synchronized ResponseData incrementCounterByValue(String clientId, String webService, long count) {
        try {
            ClientLimitConfig clientConfig = clientLimitConfigMap.get(clientId);
            if (clientConfig == null) {
                logError("Invalid client ID");
                return ResponseData.builder()
                        .clientId(clientId)
                        .webService(webService)
                        .message("WrongInput").isIncremented(false).build();
            }

            Long rateLimit = clientConfig.getServiceLimit(webService);
            if (rateLimit == null) {
                logError("Invalid web service");
                return ResponseData.builder().message("WrongInput")
                        .clientId(clientId)
                        .webService(webService)
                        .rateLimit(rateLimit).isIncremented(false).build();
            }

//            long timeWindow = clientConfig.getTimeWindow();
            long currentTime = System.currentTimeMillis();
            long currentBucket = currentTime / 1000L;

            Map<String, Map<Long, Integer>> serviceWindowCounts = requestsPerWindow.computeIfAbsent(clientId, k -> new ConcurrentHashMap<>());
            Map<Long, Integer> windowCounts = serviceWindowCounts.computeIfAbsent(webService, k -> new ConcurrentHashMap<>());
            cleanUpOldCounters(windowCounts, currentBucket, clientConfig.getTimeWindow());

            int currentCount = windowCounts.values().stream().mapToInt(Integer::intValue).sum();
            long newCount  = currentCount + count;
            if (newCount > rateLimit) {
                logWarning("Request blocked due to rate limiting");
                return ResponseData.builder().message("WrongInput")
                        .clientId(clientId)
                        .webService(webService)
                        .rateLimit(rateLimit).isIncremented(false).build();
            }
            windowCounts.put(currentBucket, windowCounts.getOrDefault(currentBucket, 0) + (int) count);
            logInfo("Incremented counter by value");
            //windowCounts.forEach((key, value) -> System.out.println("Bucket: " + key + ", Count: " + value));
            return ResponseData.builder().message("Success")
                    .counterValue((int)newCount)
                    .clientId(clientId)
                    .webService(webService)
                    .rateLimit(rateLimit)
                    .isIncremented(true)
                    .build();
        } catch (RateLimitException e) {
            logError("RateLimitException occurred: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logError("Exception occurred: " + e.getMessage());
            throw new RateLimitException("An error occurred while processing the request");
        }
    }

    private void cleanUpOldCounters(Map<Long, Integer> windowCounts, long currentBucket, long slidingWindowSize) {
        windowCounts.entrySet().removeIf(entry -> currentBucket - entry.getKey() > slidingWindowSize);
    }

    public void setRequestCounts(Map<String, Map<String, Long>> requestCounts) {
        this.requestCounts.clear();
        this.requestCounts.putAll(requestCounts);
    }

    private void logError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    private void logWarning(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    private void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }
}
