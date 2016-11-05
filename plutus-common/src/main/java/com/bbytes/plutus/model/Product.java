package com.bbytes.plutus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class Product extends BaseEntity {

	// product desc
	private String desc;

	@Indexed(unique=true)
	private String name;
	
	// all the emails to which  any communication about this product like
	// feature request , issues etc has to be sent
	private List<String> productTeamEmails = new ArrayList<>();

	public void addProductTeamEmails(String... emails) {
		productTeamEmails.addAll(Arrays.asList(emails));
	}

}
