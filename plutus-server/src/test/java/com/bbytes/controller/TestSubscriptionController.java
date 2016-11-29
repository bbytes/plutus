package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.PaymentMode;
import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.Invoice;
import com.bbytes.plutus.model.PaymentHistory;
import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.URLMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestSubscriptionController extends PlutusApplicationWebTests {

	@Before
	public void setup() throws Exception {

		Product product = new Product();
		product.setName(ProductName.Statusnap.toString());
		product.setId(ProductName.Statusnap.toString());
		product.setDesc("statusnap");
		product.addProductTeamEmails("purple@beyondbytes.co.in");
		product = productService.save(product);

		PricingPlan plan = new PricingPlan();
		plan.setAppProfile(AppProfile.enterprise);
		plan.setBillingCycle(BillingCycle.Monthly);
		plan.setCurrency(Currency.USD);
		plan.setPricingPlanName("test123");
		plan.setProduct(product);
		plan.setDesc("test desc");
		pricingPlanService.save(plan);
	}

	@Test
	public void testSubscriptionValidity() throws Exception {
		Subscription subscription = subscriptionService.findAll().get(0);
		mockMvc.perform(get("/" + URLMapping.SUBSCRIPTION_URL + "/validate")
				.header(GlobalConstant.SUBSCRIPTION_KEY_HEADER, subscription.getSubscriptionKey())
				.header(GlobalConstant.APP_PROFILE_HEADER, AppProfile.saas)
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken(subscription.getSubscriptionSecret())))
				.andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void testSubscriptionRegistration() throws Exception {
		SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
		subscriptionInfo.setAppProfile(AppProfile.saas);
		subscriptionInfo.setBillingAddress("none");
		subscriptionInfo.setBillingCycle(BillingCycle.Monthly);
		subscriptionInfo.setContactNo("1234567899");
		subscriptionInfo.setCurrency(Currency.INR);
		subscriptionInfo.setCustomerName("test");
		subscriptionInfo.setEmail("test@test.com");
		subscriptionInfo.setProductName(ProductName.Statusnap.toString());
		subscriptionInfo.setTenantId("randomTenantid");

		String requestBody = new ObjectMapper().writeValueAsString(subscriptionInfo);

		mockMvc.perform(post("/" + URLMapping.SUBSCRIPTION_URL + "/register").content(requestBody).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(status().is2xxSuccessful()).andDo(print());

	}

	@Test
	public void testDeleteSubscription() throws Exception {
		mockMvc.perform(delete("/" + URLMapping.SUBSCRIPTION_URL + "/" + subscriptionService.findAll().get(0).getId())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getAllSubscription() throws Exception {
		mockMvc.perform(get("/" + URLMapping.SUBSCRIPTION_URL + "/all").header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken()))
				.andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getAllSubscriptionForProduct() throws Exception {
		mockMvc.perform(get("/" + URLMapping.SUBSCRIPTION_URL + "/" + productService.findAll().get(0).getName())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getSubscriptionPaymentHistory() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setBillingAmount(100);
		subscription.setProductName(ProductName.Statusnap.toString());
		subscription.setSubscriptionKey(UUID.randomUUID().toString());
		subscription.setSubscriptionSecret(UUID.randomUUID().toString());
		subscription.setValidTill(DateTime.now().plusDays(100).toDate());
		subscription.setTenantId("randomTenantid");
		subscription.setPricingPlan(pricingPlanService.findByProductName(ProductName.Statusnap.toString()).get(0));

		List<PaymentHistory> paymentHistoryList = new ArrayList<>();
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setAmount(10);
		paymentHistory.setPaymentDate(DateTime.now().toDate());
		paymentHistory.setPaymentMode(PaymentMode.PAYMENT_GATEWAY);
		paymentHistory.setSubscription(subscription);
		// paymentHistory.setInvoice(new Invoice());
		paymentHistoryList.add(paymentHistory);
		subscription = subscriptionService.save(subscription);

		paymentHistoryList = paymentHistoryService.save(paymentHistoryList);
		subscription.setPaymentHistoryList(paymentHistoryList);

		subscription = subscriptionService.save(subscription);

		mockMvc.perform(get("/" + URLMapping.SUBSCRIPTION_URL + "/paymentHistory/" + subscription.getSubscriptionKey())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}
}
