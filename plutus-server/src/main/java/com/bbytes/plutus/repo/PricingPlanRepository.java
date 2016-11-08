package com.bbytes.plutus.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.model.Product;

public interface PricingPlanRepository extends MongoRepository<PricingPlan, String> {
  
	List<PricingPlan> findByProduct(Product product);
}
