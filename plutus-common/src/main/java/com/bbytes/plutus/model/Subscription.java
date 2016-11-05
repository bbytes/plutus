package com.bbytes.plutus.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.mongo.CascadeSave;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = { "validTill" })
@Document
public class Subscription extends BaseEntity {

	// The flag to enable to disable the Subscription account
	private boolean enable = true;

	// The amount pending to be paid in currency mentioned in @ProductPlan
	// object
	private double billingAmount;

	// The time stamp to indicate when billingAmount value was updated
	private DateTime amountUpdatedTimeStamp;

	// Key to identify the Subscription object
	@Indexed(unique = true)
	private String subscriptionKey;

	// secret for Subscription object used during api auth access
	@Indexed(unique = true)
	private String subscriptionSecret;

	// tenant id if the product is saas based
	@Indexed
	private String tenantId;
	
	// store product Name
	private String productName;

	// The customer object hold all information about a customer
	@DBRef
	@CascadeSave
	private Customer customer;

	// The Project plan object hold all plan info like cost ,duration and other
	// restrictions for a plan
	@DBRef
	@CascadeSave
	private PricingPlan pricingPlan;

	// the date till which the Subscription is valid after which the billing
	// stops and user access denied
	@JsonIgnore
	private Date validTill;

	// deactivate the account used for temporary suspension of account
	private boolean deactivate = false;

	// deactivate the account reason to be stored to indicate end user the
	// reason
	private String deactivateReason;

	// all the payment made by the customer for this product Subscription
	@DBRef
	private List<PaymentHistory> paymentHistory;

	// string form stored in db but converted during runtime
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String trialPeriod;

	// trial period stored as date interval like 10/01/2016 to 10/02/2016
	@Transient
	private Interval trialPeriodInterval;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String supportPeriod;

	// period stored as date interval like 10/01/2016 to 10/02/2016 for paid
	// support or free suppor commited to customer
	@Transient
	private Interval supportPeriodInterval;

	public void setProductPlan(PricingPlan productPlan) {
		this.pricingPlan = productPlan;
		this.pricingPlan.setSubscription(this);
	}

	public void setTrialPeriodInterval(Interval trialPeriodInterval) {
		this.trialPeriodInterval = trialPeriodInterval;
		this.trialPeriod = this.trialPeriodInterval.toString();
	}

	public Interval getTrialPeriodInterval() {
		return new Interval(trialPeriod);
	}

	public Interval getSupportPeriodInterval() {
		return new Interval(supportPeriod);
	}

	public void setSupportPeriodInterval(Interval supportPeriodInterval) {
		this.supportPeriodInterval = supportPeriodInterval;
		this.supportPeriod = this.supportPeriodInterval.toString();
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
