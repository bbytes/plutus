package com.bbytes.plutus.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.PaymentMode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class PaymentHistory extends BaseEntity {

	private double amount;

	private Date paymentDate;

	private PaymentMode paymentMode;
	
	private Invoice invoice;

}
