package com.bbytes.plutus.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BillingInfo {

	private String contactNo;

	private String email;
	
	private String name;

	private String website;

	private String billingAddress;

	private List<String> billingEmails = new ArrayList<>();

}
