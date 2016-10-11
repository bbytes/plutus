package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestSubscriptionController extends PlutusApplicationWebTests {

	
	@Before
	public void setup() throws Exception {

		Product product = new Product();
		product.setName(ProductName.statusnap.toString());
		product.setId(ProductName.statusnap.toString());
		product.setDesc("statusnap");
		product.addProductTeamEmails("purple@beyondbytes.co.in");
		productService.save(product);
	}
	
	@Test
	public void testSubscriptionValidity() throws Exception {

		Subscription subscription = subscriptionService.findAll().get(0);
		mockMvc.perform(get("/v1/api/subscription/validate/" + subscription.getSubscriptionKey()))
				.andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void testSubscriptionRegistration() throws Exception {
		SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
		subscriptionInfo.setAppProfile(AppProfile.saas);
		subscriptionInfo.setBillingAddress("none");
		subscriptionInfo.setBillingCycle(BillingCycle.Monthy);
		subscriptionInfo.setContactNo("1234567899");
		subscriptionInfo.setCurrency(Currency.INR);
		subscriptionInfo.setCustomerName("test");
		subscriptionInfo.setEmail("test@test.com");
		subscriptionInfo.setProductName(ProductName.statusnap.toString());
		subscriptionInfo.setTenantId("randomTenantid");

		String requestBody = new ObjectMapper().writeValueAsString(subscriptionInfo);

		mockMvc.perform(post("/v1/api/subscription/register").content(requestBody).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andDo(print());

	}

}
