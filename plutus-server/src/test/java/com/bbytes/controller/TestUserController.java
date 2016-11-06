package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;

import com.bbytes.PlutusApplicationWebTests;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.URLMapping;

public class TestUserController extends PlutusApplicationWebTests {

	@Test
	public void testSaveUser() throws Exception {

		
		JSONObject user = new JSONObject();
		user.put("email", "test5@test.com");
		user.put("name", "test");
		user.put("userRole", "test");
		user.put("password", "test");
		
		String requestBody = user.toString();

		
		mockMvc.perform(
				post("/" + URLMapping.USER_URL + "/create").contentType(APPLICATION_JSON_UTF8).content(requestBody)
						.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken()))
				.andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/" + URLMapping.USER_URL + "/" + userService.findAll().get(0).getEmail())
				.header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken())).andExpect(status().is2xxSuccessful()).andDo(print());
	}

	@Test
	public void getAllUser() throws Exception {
		mockMvc.perform(get("/" + URLMapping.USER_URL + "/all").header(GlobalConstant.AUTH_TOKEN_HEADER, getAuthToken()))
				.andExpect(status().is2xxSuccessful()).andDo(print());
	}
}
