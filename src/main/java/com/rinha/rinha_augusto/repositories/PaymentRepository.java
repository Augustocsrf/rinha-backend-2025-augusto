package com.rinha.rinha_augusto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.rinha.rinha_augusto.models.Payment;
import com.rinha.rinha_augusto.models.PaymentCount;
import com.rinha.rinha_augusto.models.responses.PaymentSummaryTotals;

public interface PaymentRepository extends MongoRepository<Payment, UUID> {

    @Aggregation(pipeline = {
        "{'$group': {'_id': '$processor', 'totalRequests': { '$sum': 1 }, 'totalAmount': { '$sum': '$amount' } } }",
        "{'$sort': {'_id': 1 }}"
    })
    List<PaymentSummaryTotals> getPaymentCounts();

}
  