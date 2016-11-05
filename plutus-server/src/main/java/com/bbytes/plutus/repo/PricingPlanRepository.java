package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.PricingPlan;

public interface PricingPlanRepository extends MongoRepository<PricingPlan, String> {
  
}
