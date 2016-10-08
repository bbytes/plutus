package com.bbytes.plutus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class Customer extends BaseEntity {

	private String primaryContactNo;

	private String secondaryContactNo;

	private String primaryEmail;

	private String secondaryEmail;

	private String website;

	private String billingAddress;

	private List<String> billingEmails = new ArrayList<>();

	@DBRef
	private List<Subscription> subscriptions;

	public void addBillingEmails(String... emails) {
		billingEmails.addAll(Arrays.asList(emails));
	}

}
