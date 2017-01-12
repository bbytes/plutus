package com.bbytes.plutus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.BillingType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document
public class Product {

	@Id
	private String id;

	// product desc
	private String desc;

	@Indexed(unique = true)
	private String name;
	
	private BillingType billingType;

	@CreatedDate
	private DateTime creationDate;

	@LastModifiedDate
	private DateTime lastModified;

	@CreatedBy
	private String createdBy;

	// all the emails to which any communication about this product like
	// feature request , issues etc has to be sent
	private List<String> productTeamEmails = new ArrayList<>();
	
	private List<String>  productFeatureFields = new ArrayList<>();

	public void addProductTeamEmails(String... emails) {
		productTeamEmails.addAll(Arrays.asList(emails));
	}

}
