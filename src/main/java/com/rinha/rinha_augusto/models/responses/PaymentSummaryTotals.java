package com.rinha.rinha_augusto.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public record PaymentSummaryTotals(
    @JsonInclude(JsonInclude.Include.ALWAYS)
    int totalRequests,
    @JsonInclude(JsonInclude.Include.ALWAYS)
    double totalAmount
) {

    public static PaymentSummaryTotals generateEmptySummary() {
        return new PaymentSummaryTotals(0, 0.0);
    }
}
