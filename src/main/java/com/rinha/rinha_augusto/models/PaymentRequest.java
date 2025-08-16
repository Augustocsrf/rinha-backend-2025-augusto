package com.rinha.rinha_augusto.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
    @NotEmpty String correlationId,
    @NotNull Double amount
) {
}
