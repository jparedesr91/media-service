package com.newsnow.media.outside.driving.api.util;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Utility class for json parsing operations.
 *
 */
public class JsonUtil {
    public static final ObjectMapper MAPPER;

    static {
        MAPPER = Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(ALWAYS)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }
}
