package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.PaymentHistory;

public interface PaymentHistoryRepository extends MongoRepository<PaymentHistory, String> {

}
