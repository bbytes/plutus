package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.Subscription;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

	Subscription findBysubscriptionKey(String subscriptionKey);
}
