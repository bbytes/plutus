package com.bbytes.plutus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
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

	// main mobile contact info for this customer
	private String contactNo;

	// main email contact info for this customer
	@Indexed(unique = true)
	private String email;

	// customer website if any ..not very important information
	private String website;

	// customer billing address to send invoice hard copy if required
	private String billingAddress;

	// the list of email address to which the bill amount and invoice soft copy
	// has to be sent.
	private List<String> billingEmails = new ArrayList<>();

	// cross link the customer to their list of subscriptions if they have
	// signed up for more than one proeduct with same contant info. For eg they
	// might be customer of statusnap , recruiz , errZero etc
	@DBRef
	private List<Subscription> subscriptions;

	public void addBillingEmails(String... emails) {
		billingEmails.addAll(Arrays.asList(emails));
	}

}
