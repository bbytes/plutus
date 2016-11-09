package com.bbytes.plutus.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.ProductPlanStats;

public interface ProductPlanStatsRepository extends MongoRepository<ProductPlanStats, String> {

	List<ProductPlanStats> findByEntryDateBetween(Date startDate, Date endDate);
	
	List<ProductPlanStats> findByEntryDateBetweenAndSubscriptionKey(Date startDate, Date endDate,String subscriptionKey);
}
