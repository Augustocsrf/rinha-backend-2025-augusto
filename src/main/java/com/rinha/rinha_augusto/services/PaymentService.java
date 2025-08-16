package com.rinha.rinha_augusto.services;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rinha.rinha_augusto.models.PaymentCount;
import com.rinha.rinha_augusto.models.PaymentEvent;
import com.rinha.rinha_augusto.models.PaymentRequest;
import com.rinha.rinha_augusto.models.responses.PaymentSummaryResponse;
import com.rinha.rinha_augusto.models.responses.PaymentSummaryTotals;
import com.rinha.rinha_augusto.producers.RedisQueueProducer;
import com.rinha.rinha_augusto.repositories.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {
    
    private PaymentRepository paymentRepository;
    private RedisQueueProducer redisQueueProducer;

    public PaymentService(PaymentRepository paymentRepository, 
            RedisQueueProducer redisQueueProducer) {
        this.paymentRepository = paymentRepository;
        this.redisQueueProducer = redisQueueProducer;
    }

    public void sendPaymentToQueue(PaymentRequest paymentRequest) throws JsonProcessingException{
        Instant now = Instant.now();
        
        PaymentEvent paymentProcessRequest = new PaymentEvent(
            paymentRequest.correlationId(),
            paymentRequest.amount(),
            DateTimeFormatter.ISO_INSTANT.format(now),
            0
        );

        redisQueueProducer.sendPaymentEvent(paymentProcessRequest);
    }

    public PaymentSummaryResponse getPaymentSummary(String from, String to) { 
        List<PaymentSummaryTotals> paymentCounts = paymentRepository.getPaymentCounts();
        PaymentSummaryTotals defaultSummary  = paymentCounts.isEmpty()   ? PaymentSummaryTotals.generateEmptySummary() :  paymentCounts.get(0);
        PaymentSummaryTotals fallbackSummary = paymentCounts.size() == 2 ? paymentCounts.get(1) : PaymentSummaryTotals.generateEmptySummary();

        return PaymentSummaryResponse.constructFromClientResponses(defaultSummary, fallbackSummary);
    }
}
