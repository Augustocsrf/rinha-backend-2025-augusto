package com.rinha.rinha_augusto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rinha.rinha_augusto.models.responses.PaymentSummaryResponse;
import com.rinha.rinha_augusto.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments-summary")
@Slf4j
@Validated
public class PaymentSummaryController {
    @Autowired
    private PaymentService paymentService; 

    @GetMapping
    @Operation(summary = "Este endpoint precisa retornar um resumo do que já foi processado em termos de pagamentos.")
    @ResponseStatus(HttpStatus.OK)
    public PaymentSummaryResponse getPaymentSummary(
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to
    ) {
        return paymentService.getPaymentSummary(from, to);
    }
}
