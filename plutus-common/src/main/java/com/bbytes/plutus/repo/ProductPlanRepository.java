package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.ProductPlan;

public interface ProductPlanRepository extends MongoRepository<ProductPlan, String> {
  
}
