package com.bbytes.plutus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.response.PingResponse;
import com.mongodb.CommandResult;

@RestController
public class PingController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/status")
	PingResponse ping() {
		String message = "";
		String status = "Server running fine";
		try {
			CommandResult result = this.mongoTemplate.executeCommand("{ buildInfo: 1 }");
			message = "DB running with version " + result.get("version");
		} catch (Throwable e) {
			message = "Cannot connect to DB";
			status = "Server Error";
		}

		PingResponse response = new PingResponse(status, message, "200 OK");
		return response;
	}

}