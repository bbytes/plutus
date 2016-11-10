package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.util.BillingConstant;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.URLMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class TestPricePlanController extends PlutusApplicationWebTests {

	@Before
	public void setup() throws Exception {

		Product product = new Product();
		product.setName(ProductName.Statusnap.toString());
		product.setId(ProductName.Statusnap.toString());
		product.setDesc("statusnap");
		product.addProductTeamEmails("purple@beyondbytes.co.in");
		productService.save(product);
	}

	@Test
	public void testSavePricingPlan() throws Exception {

		PricingPlan pricingPlan = new PricingPlan();
		pricingPlan.setName("TEST");
		pricingPlan.setBillingCycle(BillingCycle.Monthy);
		pricingPlan.setCurrency(Currency.INR);
		pricingPlan.setAppProfile(AppProfile.saas);
		pricingPlan.setId("TEST2");
		pricingPlan.setProduct(productService.findAll().get(0));

		Map<String, Number> productPlanItemToCost = new HashMap<>();
		productPlanItemToCost.put(BillingConstant.STATUSNAP_USER_COST, 120);
		productPlanItemToCost.put(BillingConstant.STATUSNAP_PROJECT_COST, 100);
		pricingPlan.setProductPlanItemToCost(productPlanItemToCost);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());
		String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pricingPlan);
		System.out.println(requestBody);

		mockMvc.perform(post("/" + URLMapping.PRICING_PLAN_URL + "/create").contentType(APPLICATION_JSON_UTF8).content(requestBody)
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void testDeletePricingPlan() throws Exception {
		mockMvc.perform(delete("/" + URLMapping.PRICING_PLAN_URL + "/" + pricingPlanService.findAll().get(0).getId())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getAllPricingPlan() throws Exception {
		mockMvc.perform(get("/" + URLMapping.PRICING_PLAN_URL + "/all")
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}
}
