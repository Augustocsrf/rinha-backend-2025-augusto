package com.rinha.rinha_augusto.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentCount {
    @Id
    private String id;
    private int totalRequests;
    private double totalAmount;

}
