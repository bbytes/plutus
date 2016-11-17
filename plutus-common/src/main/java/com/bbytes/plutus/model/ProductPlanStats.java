package com.bbytes.plutus.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
@Document
public class ProductPlanStats extends BaseEntity {

	// the date on which a stat entry was created in db 
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date entryDate;

	// plan item like no od users to the current count for that date or hour
	// that is sent from the client. Billing happens based on these nos
	private Map<String, Number> stats = new HashMap<String, Number>();

	// the stats for which subscription is refereed here
	@Indexed
	private String subscriptionKey;

	public void addStats(String key, Number value) {
		stats.put(key, value);
	}

}
