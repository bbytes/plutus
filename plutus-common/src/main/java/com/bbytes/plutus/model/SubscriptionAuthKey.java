package com.bbytes.plutus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Document
public class SubscriptionAuthKey {

	@Id
	private String subscriptionKey;

	private String subscriptionSecret;

	

}
