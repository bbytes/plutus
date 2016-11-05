package com.bbytes.plutus.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.Subscription;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

	Subscription findBySubscriptionKey(String subscriptionKey);
	
	List<Subscription> findByProductName(String productName);

	Subscription findBySubscriptionKeyAndTenantId(String subscriptionKey, String tenantId);
	
	
}
