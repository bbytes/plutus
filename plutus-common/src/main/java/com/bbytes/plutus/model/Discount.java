package com.bbytes.plutus.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.DiscountType;

import lombok.Data;

@Data
@Document
public class Discount {

	private double discount;
	
	private DiscountType discountType;
}
