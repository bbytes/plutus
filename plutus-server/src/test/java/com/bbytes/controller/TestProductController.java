package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.URLMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class TestProductController extends PlutusApplicationWebTests {

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
	public void testSaveProduct() throws Exception {

		Product product = new Product();
		product.setName(ProductName.Recruiz.toString());
		product.setId(ProductName.Recruiz.toString());
		product.setDesc("recz");
		product.addProductTeamEmails("recruiz@beyondbytes.co.in");
		

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());
		String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
		System.out.println(requestBody);

		mockMvc.perform(post("/" + URLMapping.PRODUCT_URL + "/create").contentType(APPLICATION_JSON_UTF8).content(requestBody)
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void testDeleteProduct() throws Exception {
		mockMvc.perform(delete("/" + URLMapping.PRODUCT_URL + "/" + pricingPlanService.findAll().get(0).getId())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getAllProducts() throws Exception {
		mockMvc.perform(get("/" + URLMapping.PRODUCT_URL + "/all")
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}
}
