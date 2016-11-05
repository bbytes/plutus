package com.bbytes.plutus.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.service.PlutusException;
import com.bbytes.plutus.service.PricingPlanService;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.PRICING_PLAN_URL)
public class PricingPlanRestController {

	@Autowired
	private PricingPlanService pricingPlanService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	private PlutusRestResponse getAll() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true,pricingPlanService.findAll());
		return status;
	}
	
	@RequestMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.POST)
	private PlutusRestResponse create(@RequestBody PricingPlan pricingPlan) throws PlutusException {

		if (pricingPlan == null)
			throw new PlutusException("Pricing Plan request object is empty or null");

		if (pricingPlanService.exists(pricingPlan.getId())) {
			throw new PlutusException("Pricing Plan object with given id exist in DB");
		}

		pricingPlan = pricingPlanService.save(pricingPlan);

		PlutusRestResponse status = new PlutusRestResponse("Pricing Plan save success", true);
		return status;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	private PlutusRestResponse update(@RequestBody PricingPlan pricingPlan) throws PlutusException {

		if (pricingPlan == null)
			throw new PlutusException("Pricing Plan request object is empty or null");

		if (!pricingPlanService.exists(pricingPlan.getId())) {
			throw new PlutusException("Pricing Plan object cannot be new object");
		}

		pricingPlan = pricingPlanService.save(pricingPlan);

		PlutusRestResponse status = new PlutusRestResponse("Pricing Plan update success", true);
		return status;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	private PlutusRestResponse delete(@PathVariable String id) throws PlutusException {
		if (id == null)
			throw new PlutusException("Pricing Plan request object is empty or null");

		pricingPlanService.delete(id);

		PlutusRestResponse status = new PlutusRestResponse("Pricing Plan deleted", true);
		return status;
	}

	@ExceptionHandler(PlutusException.class)
	public ResponseEntity<PlutusRestResponse> exception(HttpServletRequest req, PlutusException e) {
		PlutusRestResponse status = new PlutusRestResponse(e.getMessage(), false);
		return new ResponseEntity<PlutusRestResponse>(status, HttpStatus.OK);
	}

}