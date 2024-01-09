package com.ratelimiter.RateLimitterProject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelimiter.RateLimitterProject.model.RateLimitConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateLimitFileIOTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Value("${config.file.path}")
    private String configFilePath;

    @InjectMocks
    private RateLimitFileIO rateLimitFileIO;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        resourceLoader = mock(ResourceLoader.class);
        rateLimitFileIO = new RateLimitFileIO(resourceLoader, configFilePath);
    }

    @Test
    void testReadConfigFromFile() throws IOException {
        // Mocking resource and resourceLoader behavior
        Resource resourceMock = mock(Resource.class);
        InputStream inputStreamMock = new ByteArrayInputStream("{}".getBytes());

        when(resourceLoader.getResource(configFilePath)).thenReturn(resourceMock);
        when(resourceMock.getInputStream()).thenReturn(inputStreamMock);

        // Creating an instance of ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Creating a sample RateLimitConfig object for comparison
        RateLimitConfig expectedConfig = new RateLimitConfig();
        // Set properties of the expected config if needed

        // Serialize the expectedConfig object to JSON string
        String expectedJson = objectMapper.writeValueAsString(expectedConfig);

        // Invoking the method to be tested
        RateLimitConfig result = rateLimitFileIO.readConfigFromFile();

        // Serialize the actual result object to JSON string
        String actualJson = objectMapper.writeValueAsString(result);

        // Assertions
        assertEquals(expectedJson, actualJson); // Compare JSON strings for equality
    }
}
