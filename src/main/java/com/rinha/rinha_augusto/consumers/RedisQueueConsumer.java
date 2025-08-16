package com.rinha.rinha_augusto.consumers;

import redis.clients.jedis.Jedis;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinha.rinha_augusto.clients.PaymentProcessorDefaultClient;
import com.rinha.rinha_augusto.clients.PaymentProcessorFallbackClient;
import com.rinha.rinha_augusto.clients.DTO.requests.PaymentProcessRequest;
import com.rinha.rinha_augusto.clients.DTO.responses.MessageResponse;
import com.rinha.rinha_augusto.models.Log;
import com.rinha.rinha_augusto.models.Payment;
import com.rinha.rinha_augusto.models.PaymentEvent;
import com.rinha.rinha_augusto.models.enums.ErrorType;
import com.rinha.rinha_augusto.models.enums.Processor;
import com.rinha.rinha_augusto.repositories.LogRepository;
import com.rinha.rinha_augusto.repositories.PaymentRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisQueueConsumer {
    private static final String SUCCESS_MESSAGE = "payment processed successfully";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final int MAX_RETRIES = 3;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.paymentQueue}")
    private String paymentQueue;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private PaymentProcessorDefaultClient paymentProcessorDefaultClient;
    @Autowired
    private PaymentProcessorFallbackClient paymentProcessorFallbackClient;

    @PostConstruct
    public void startConsumer() {
        new Thread(() -> {
            try (Jedis jedis = new Jedis(redisHost, redisPort)) {
                System.out.println("Redis Consumer started on queue: " + paymentQueue);

                while (true) {
                    List<String> result = jedis.blpop(0, paymentQueue);
                    if (result != null && result.size() == 2) {
                        String message = result.get(1);
                        System.out.println("Consumed message: " + message);

                        PaymentEvent paymentEvent = mapper.readValue(message, PaymentEvent.class);

                        try {
                            sendPaymentToProcessor(paymentEvent.generateRequest());
                        } catch (Exception processingException) {
                            treatFailedProcessing(jedis, paymentEvent, processingException);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void treatFailedProcessing(Jedis jedis, PaymentEvent paymentEvent, Exception processingException) throws JsonProcessingException, InterruptedException {
        if (paymentEvent.getRetries() >= MAX_RETRIES) {
            log.error("Processing failed. MAX RETRIES REACHED:" + processingException.getMessage());
            saveError(paymentEvent, ErrorType.MAX_RETRIES_FAILED, processingException.getMessage());
            return;
        }

        log.error("Processing failed, pushing back to queue: " + processingException.getMessage());
        saveError(paymentEvent, ErrorType.REQUEUED, processingException.getMessage());
        paymentEvent.increaseRetry();
        
        String event = mapper.writeValueAsString(paymentEvent);
        jedis.rpush(paymentQueue, event);
        Thread.sleep(1000);
    }

    private void sendPaymentToProcessor(PaymentProcessRequest paymentProcessRequest) throws Exception {
        try {
            MessageResponse response = paymentProcessorDefaultClient.createPayment(paymentProcessRequest);
            if (!response.message().equals(SUCCESS_MESSAGE)) {
                log.error("Default Processor failed. Trying fallback processor: "
                        + response.message());
                throw new Exception(response.message());
            }
            paymentRepository.save(Payment.createPayment(paymentProcessRequest, Processor.DEFAULT));
        } catch (Exception e) {
            MessageResponse fallbackResponse = paymentProcessorFallbackClient.createPayment(paymentProcessRequest);
            paymentRepository.save(Payment.createPayment(paymentProcessRequest, Processor.FALLBACK));
        }
    }

    private void saveError(PaymentEvent paymentEvent, ErrorType errorType, String errorMessage) {
        Log log = Log.builder()
                .correlationId(paymentEvent.getCorrelationId())
                .paymentRequestTime(OffsetDateTime.parse(paymentEvent.getRequestedAt()).toInstant())
                .createdAt(OffsetDateTime.now().toInstant())
                .errorType(errorType.value())
                .errorMessage(errorMessage)
                .retry(paymentEvent.getRetries())
                .build();
        logRepository.save(log);
    }
}
