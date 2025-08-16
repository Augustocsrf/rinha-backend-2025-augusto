package com.rinha.rinha_augusto.models;

import java.time.Instant;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rinha.rinha_augusto.clients.DTO.requests.PaymentProcessRequest;
import com.rinha.rinha_augusto.models.enums.Processor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    private String correlationId;
    private Double amount;
    private Instant requestedAt;
    private String processor;

    public static Payment createPayment(PaymentProcessRequest paymentProcessRequest, Processor processor) {
        return new Payment(
            paymentProcessRequest.correlationId(), 
            paymentProcessRequest.amount(), 
            OffsetDateTime.parse(paymentProcessRequest.requestedAt()).toInstant(),
            processor.value()
        );
    }
}
