package com.rinha.rinha_augusto.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    private String id;
    private String correlationId;
    private Instant paymentRequestTime;
    private Instant createdAt;
    private String errorType; 
    private String errorMessage;
    private Integer retry;
}
