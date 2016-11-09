package com.bbytes.plutus.service;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.repo.ProductPlanStatsRepository;

@Service
public class ProductPlanStatsService extends AbstractService<ProductPlanStats, String> {
	private ProductPlanStatsRepository productPlanStatsRepository;

	@Autowired
	public ProductPlanStatsService(ProductPlanStatsRepository productPlanStatsRepository) {
		super(productPlanStatsRepository);
		this.productPlanStatsRepository = productPlanStatsRepository;
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
		return productPlanStatsRepository.save(productPlanStats);
	}

	public List<ProductPlanStats> findByEntryDateBetween(Date startDate, Date endDate) {
		return productPlanStatsRepository.findByEntryDateBetween(startDate, endDate);
	}

	public List<ProductPlanStats> findByEntryDateBetweenAndSubscriptionKey(Date startDate, Date endDate,String subscriptionKey){
		return productPlanStatsRepository.findByEntryDateBetweenAndSubscriptionKey(startDate, endDate, subscriptionKey);
	}
}
