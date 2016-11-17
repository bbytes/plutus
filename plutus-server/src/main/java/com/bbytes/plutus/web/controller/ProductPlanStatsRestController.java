package com.bbytes.plutus.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.enums.TimePeriod;
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
	public ProductStatsRestResponse validateSubscription(@RequestBody ProductPlanStats productPlanStats) throws ProductStatsException {
		try {
			if (productPlanStats != null) {
				productPlanStats.setSubscriptionKey(RequestContextHolder.getSubscriptionKey());
				productPlanStats.setEntryDate(DateTime.now().toDate());
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

	@RequestMapping(value = "/{timePeriod}/{subscriptionKey}", method = RequestMethod.GET)
	public ProductStatsRestResponse validateSubscription(@PathVariable String timePeriod, @PathVariable String subscriptionKey)
			throws ProductStatsException {

		TimePeriod timePeriodEnum = TimePeriod.valueOf(timePeriod);
		if (timePeriodEnum == null)
			timePeriodEnum = TimePeriod.Monthly;

		List<ProductPlanStats> stats = productPlanStatsService.findByEntryDateBetweenAndSubscriptionKey(
				DateTime.now().minusDays(timePeriodEnum.getDays()).toDate(), DateTime.now().toDate(), subscriptionKey);

		ProductStatsRestResponse response = new ProductStatsRestResponse(true, stats);
		return response;
	}

	@ExceptionHandler(ProductStatsException.class)
	public ResponseEntity<ProductStatsRestResponse> exceptionCreate(HttpServletRequest req, Exception e) {
		ProductStatsRestResponse response = new ProductStatsRestResponse(e.getMessage(), false);
		return new ResponseEntity<ProductStatsRestResponse>(response, HttpStatus.OK);
	}

}