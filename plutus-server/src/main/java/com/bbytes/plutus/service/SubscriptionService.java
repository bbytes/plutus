package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.repo.SubscriptionRepository;

@Service
public class SubscriptionService extends AbstractService<Subscription, String> {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository) {
		super(subscriptionRepository);
		this.subscriptionRepository = subscriptionRepository;
	}

	public Subscription findBySubscriptionKey(String subscriptionKey) {
		return subscriptionRepository.findBySubscriptionKey(subscriptionKey);
	}

	public Subscription findByProductName(String productName) {
		return subscriptionRepository.findByProductName(productName);
	}

}
