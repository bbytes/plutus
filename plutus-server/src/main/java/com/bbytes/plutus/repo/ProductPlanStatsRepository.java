package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.ProductPlanStats;

public interface ProductPlanStatsRepository extends MongoRepository<ProductPlanStats, String> {

}
