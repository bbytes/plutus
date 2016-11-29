package com.bbytes.plutus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.repo.PricingPlanRepository;

@Service
public class PricingPlanService extends AbstractService<PricingPlan, String> {

	private PricingPlanRepository pricingPlanRepository;
	
	@Autowired
	private ProductService productService;

	@Autowired
	public PricingPlanService(PricingPlanRepository pricingPlanRepository) {
		super(pricingPlanRepository);
		this.pricingPlanRepository = pricingPlanRepository;
	}

	public List<PricingPlan> findByProduct(Product product) {
		return pricingPlanRepository.findByProduct(product);
	}
	
	public List<PricingPlan> findByProductName(String productName) {
		return pricingPlanRepository.findByProduct(productService.findByName(productName));
	}
}
