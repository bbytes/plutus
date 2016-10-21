package com.bbytes.plutus.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.service.ProductPlanStatsService;
import com.bbytes.plutus.service.ProductStatsException;
import com.bbytes.plutus.util.RequestContextHolder;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.PROD_STATS_URL)
public class ProductPlanStatsRestController {

	@Autowired
	private ProductPlanStatsService productPlanStatsService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	ProductStatsRestResponse validateSubscription(@RequestBody ProductPlanStats productPlanStats) throws ProductStatsException {
		try {
			if (productPlanStats != null) {
				productPlanStats.setSubscriptionKey(RequestContextHolder.getSubscriptionKey());
				productPlanStats = productPlanStatsService.save(productPlanStats);
			} else {
				return new ProductStatsRestResponse("Product Plan Stats cannot be null", false);
			}
		} catch (Throwable e) {
			throw new ProductStatsException(e.getMessage());
		}

		if (productPlanStats == null)
			throw new ProductStatsException("Product Plan Stats create failed");

		ProductStatsRestResponse response = new ProductStatsRestResponse("Created successfully", true);
		return response;
	}

	@ExceptionHandler(ProductStatsException.class)
	public ResponseEntity<ProductStatsRestResponse> exceptionCreate(HttpServletRequest req, Exception e) {
		ProductStatsRestResponse response = new ProductStatsRestResponse(e.getMessage(), false);
		return new ResponseEntity<ProductStatsRestResponse>(response, HttpStatus.OK);
	}

}