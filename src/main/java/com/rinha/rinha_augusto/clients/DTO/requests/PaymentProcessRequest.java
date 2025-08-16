package com.rinha.rinha_augusto.clients.DTO.requests;

public record PaymentProcessRequest(
    String correlationId,
    double amount,
    String requestedAt
) {
}
