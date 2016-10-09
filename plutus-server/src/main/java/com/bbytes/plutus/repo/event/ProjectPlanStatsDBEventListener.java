package com.bbytes.plutus.repo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.service.BillingService;

@Component
public class ProjectPlanStatsDBEventListener extends AbstractMongoEventListener<ProductPlanStats> {

	@Autowired
	private BillingService billingService;
	

	/**
	 * Update pricing after saving comment.
	 */
	@Override
	public void onAfterSave(AfterSaveEvent<ProductPlanStats> event) {
		billingService.process(event.getSource());
	}

}
