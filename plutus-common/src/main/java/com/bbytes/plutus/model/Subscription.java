package com.bbytes.plutus.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.mongo.CascadeSave;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = { "validTill" })
@Document
public class Subscription extends BaseEntity {

	private boolean enable;

	private double billingAmount;

	@Indexed(unique = true)
	private String subscriptionKey;

	@Indexed(unique = true)
	private String subscriptionSecret;

	@DBRef
	@CascadeSave
	private Customer customer;

	@DBRef
	@CascadeSave
	private ProductPlan productPlan;

	@JsonIgnore
	private Date validTill;

	private boolean deactivate = false;

	private String deactivateReason;

	@DBRef
	private List<PaymentHistory> paymentHistory;

	private Interval trialPeriod;

	public void setProductPlan(ProductPlan productPlan) {
		this.productPlan = productPlan;
		this.productPlan.setSubscription(this);
	}

	public boolean isExpired() {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		return now.after(validTill);
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public static Subscription fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Subscription.class);
	}

}
