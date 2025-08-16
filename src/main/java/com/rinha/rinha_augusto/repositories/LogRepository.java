package com.rinha.rinha_augusto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rinha.rinha_augusto.models.Log;

public interface LogRepository extends MongoRepository<Log, String> {
    
}
