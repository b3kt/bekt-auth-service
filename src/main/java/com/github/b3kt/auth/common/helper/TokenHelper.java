package com.github.b3kt.auth.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.b3kt.auth.common.model.TokenMetadata;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@ApplicationScoped
public class TokenHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public static TokenMetadata extractTokenMetadata(String accessToken) {
        return Optional.ofNullable(accessToken)
                .map(token -> new String(Base64.getDecoder().decode(token.split("\\.")[1].translateEscapes())))
                .map(jsonString -> {
                    try {
                        return objectMapper.readValue(jsonString.getBytes(StandardCharsets.UTF_8), TokenMetadata.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);
    }
}
