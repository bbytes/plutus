package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.SubscriptionAuthKey;

public interface SubscriptionAuthKeyRepository extends MongoRepository<SubscriptionAuthKey, String> {

}


