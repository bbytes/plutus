package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.model.Subscription;

public class TestSubscriptionController extends PlutusApplicationWebTests {

	

	@Test
	public void testSubscriptionValidity() throws Exception {

		Subscription subscription = subscriptionService.findAll().get(0);
		mockMvc.perform(get("/v1/api/subscription/validate/" + subscription.getSubscriptionKey()))
				.andExpect(status().is2xxSuccessful()).andDo(print());

	}

}
