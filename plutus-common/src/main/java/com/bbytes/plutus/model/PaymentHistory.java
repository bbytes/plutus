package com.bbytes.plutus.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.PaymentMode;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class PaymentHistory extends BaseEntity {

	// amount paid during a cycle
	private double amount;
	
	@DBRef
	@JsonBackReference
	private Subscription subscription;

	// payment date
	private Date paymentDate;

	// payment mode , possible values PAYMENT_GATEWAY, NEFT, CHEQUE, CASH
	private PaymentMode paymentMode;

	// The invoice copy create for this payment. Contains amount,template
	// to create pdf and data to fill the pdf
	private Invoice invoice;

}
