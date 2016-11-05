package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.repo.PricingPlanRepository;

@Service
public class PricingPlanService extends AbstractService<PricingPlan, String> {

	@Autowired
	public PricingPlanService(PricingPlanRepository pricingPlanRepository) {
		super(pricingPlanRepository);
	}

}
