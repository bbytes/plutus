package com.bbytes.plutus.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class ProductPlanStats extends BaseEntity {

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date entryDate;

	// plan item like no od users to the current count for that date or hour
	// that is sent from the client. Billing happens based on these nos 
	private Map<String, Integer> stats;

	@Indexed
	private String subscriptionKey;

}
