package com.bbytes.plutus.model;

import java.io.IOException;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = { "validTill" })
@Document
public class Subscription {

	@Id
	private String id;

	private boolean active;
	
	private boolean ownerKey;

	private String owner;

	private String issuer;

	private Product product;

	private int count;

	@JsonIgnore
	private Date validTill;

	private SubscriptionType type;

	public boolean isExpired() {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		return now.after(validTill);
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public static Subscription fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Subscription.class);
	}

}
