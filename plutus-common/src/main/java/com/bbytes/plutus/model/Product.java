package com.bbytes.plutus.model;

import java.util.List;

import lombok.Data;

@Data
public class Product {

	private String name;

	private String displayName;
	
	private String desc;
	
	private List<String> notificationEmail;
	
}
