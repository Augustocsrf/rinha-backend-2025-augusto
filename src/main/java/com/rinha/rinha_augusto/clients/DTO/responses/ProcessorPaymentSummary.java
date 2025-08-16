package com.rinha.rinha_augusto.clients.DTO.responses;

public record ProcessorPaymentSummary(
    double totalAmount,
	double totalRequests,
	double totalFee,
	double feePerTransaction
) {
    
}
