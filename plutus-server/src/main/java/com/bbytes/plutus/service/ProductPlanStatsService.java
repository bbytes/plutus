package com.bbytes.plutus.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.repo.ProductPlanStatsRepository;

@Service
public class ProductPlanStatsService extends AbstractService<ProductPlanStats, String> {

	@Autowired
	public ProductPlanStatsService(ProductPlanStatsRepository productPlanStatsRepository) {
		super(productPlanStatsRepository);
	}

	public <S extends ProductPlanStats> List<S> save(Iterable<S> entites) {
		throw new UnsupportedOperationException();
	}

	/*
	 * Saves a given entity
	 */
	public <S extends ProductPlanStats> S save(S productPlanStats) {
		if (productPlanStats.getId() == null)
			productPlanStats.setId(DateTime.now().toLocalDate() + ":" + productPlanStats.getSubscriptionKey());
		return mongoRepository.save(productPlanStats);
	}

}
