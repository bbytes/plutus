package com.bbytes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bbytes.PlutusApplicationWebTests;

public class TestLogin extends PlutusApplicationWebTests {

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(get("/auth/login").param("username", "test4@test.com").param("password", "test"))
				.andExpect(status().is2xxSuccessful()).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
	}
}
