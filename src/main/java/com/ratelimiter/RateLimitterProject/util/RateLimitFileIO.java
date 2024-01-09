package com.ratelimiter.RateLimitterProject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelimiter.RateLimitterProject.model.RateLimitConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RateLimitFileIO {
    private final ResourceLoader resourceLoader;
    private final String configFilePath;

    public RateLimitFileIO(ResourceLoader resourceLoader, @Value("${config.file.path}") String configFilePath) {
        this.resourceLoader = resourceLoader;
        this.configFilePath = configFilePath;
    }

    public RateLimitConfig readConfigFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Resource resource = resourceLoader.getResource(this.configFilePath);
            try (InputStream inputStream = resource.getInputStream()) {
                return mapper.readValue(inputStream, RateLimitConfig.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read configuration file: " + e.getMessage(), e);
        }
    }
}
