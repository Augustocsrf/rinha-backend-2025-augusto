package com.rinha.rinha_augusto.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentSummaryResponse {
    @JsonProperty("default")
    private PaymentSummaryTotals defaultSummary;

    @JsonProperty("fallback")
    private PaymentSummaryTotals fallback;

    public static PaymentSummaryResponse constructFromClientResponses(PaymentSummaryTotals defaultDataSummary, PaymentSummaryTotals fallbackDataSummary) {
        PaymentSummaryTotals defaultSummary = new PaymentSummaryTotals(defaultDataSummary.totalRequests(), defaultDataSummary.totalAmount());
        PaymentSummaryTotals fallbackSummary = new PaymentSummaryTotals(fallbackDataSummary.totalRequests(), fallbackDataSummary.totalAmount());

        return new PaymentSummaryResponse(defaultSummary, fallbackSummary);
    }
}
