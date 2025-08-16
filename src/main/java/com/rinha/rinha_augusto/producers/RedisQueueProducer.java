package com.rinha.rinha_augusto.producers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinha.rinha_augusto.models.PaymentEvent;

import redis.clients.jedis.Jedis;

@Service
public class RedisQueueProducer {
    
    @Value("${spring.redis.host}")
    private String redisHost;
    
    @Value("${spring.redis.paymentQueue}")
    private String paymentQueue;

    @Value("${spring.redis.port}")
    private int redisPort;

    private ObjectMapper mapper = new ObjectMapper();

    public void sendPaymentEvent(PaymentEvent paymentEvent) throws JsonProcessingException {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            String event = mapper.writeValueAsString(paymentEvent);

            jedis.rpush(paymentQueue, event);

            System.out.println("Event pushed to Redis queue: " + event);
        }
    }
}
