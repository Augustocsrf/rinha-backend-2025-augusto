package com.rinha.rinha_augusto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rinha.rinha_augusto.models.PaymentRequest;
import com.rinha.rinha_augusto.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments")
@Slf4j
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService; 

    @PostMapping
    @Operation(summary = "Principal endpoint que recebe requisições de pagamentos a serem processados.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void performPayment(
       @RequestBody PaymentRequest paymentRequest
    ) throws JsonProcessingException {
        paymentService.sendPaymentToQueue(paymentRequest);
    }
}
