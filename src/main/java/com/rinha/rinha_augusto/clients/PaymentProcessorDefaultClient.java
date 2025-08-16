package com.rinha.rinha_augusto.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinha.rinha_augusto.clients.DTO.requests.PaymentProcessRequest;
import com.rinha.rinha_augusto.clients.DTO.responses.MessageResponse;
import com.rinha.rinha_augusto.clients.DTO.responses.ProcessorPaymentSummary;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "payment-processor-default", url = "${services.payment.default.basePath}")
public interface PaymentProcessorDefaultClient {
    
    @PostMapping("/payments")
    MessageResponse createPayment(
            @RequestBody PaymentProcessRequest paymentRequest
    );

    @GetMapping("/admin/payments-summary")
    ProcessorPaymentSummary retrieveSummary(
        @RequestHeader("X-Rinha-Token") String token,
        @RequestParam String from,
        @RequestParam String to
    );
}
