package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.ProductPlan;
import com.bbytes.plutus.repo.ProductPlanRepository;

@Service
public class ProductPlanService extends AbstractService<ProductPlan, String> {

	@Autowired
	public ProductPlanService(ProductPlanRepository productPlanRepository) {
		super(productPlanRepository);
	}

}
