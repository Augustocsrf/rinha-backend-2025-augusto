package com.rinha.rinha_augusto.models;

import com.rinha.rinha_augusto.clients.DTO.requests.PaymentProcessRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    @Getter
    private String correlationId;
    @Getter
    private double amount;
    @Getter
    private String requestedAt;
    @Getter
    private int retries;

    public PaymentProcessRequest generateRequest() {
        return new PaymentProcessRequest(
            correlationId,
            amount,
            requestedAt
        );
    }

    public void increaseRetry() {
        this.retries++;
    }
}
