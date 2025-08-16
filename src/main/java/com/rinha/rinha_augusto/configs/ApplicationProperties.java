package com.rinha.rinha_augusto.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {
    @Value("${services.payment.default.token}")
    public String defaultToken;
    
    @Value("${services.payment.fallback.token}")
    public String fallbackToken;

    @Value("${spring.redis.port}")
    private int redisPort;
}
