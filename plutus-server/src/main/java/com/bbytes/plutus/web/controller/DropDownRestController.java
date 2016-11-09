package com.bbytes.plutus.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.BillingType;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.PaymentMode;
import com.bbytes.plutus.enums.SubscriptionType;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.service.PlutusException;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.DROP_DOWN_URL)
public class DropDownRestController {

	@RequestMapping(value = "/billingCycle", method = RequestMethod.GET)
	private PlutusRestResponse getBillingCycle() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, BillingCycle.values());
		return status;
	}

	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	private PlutusRestResponse getCurrency() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, Currency.values());
		return status;
	}

	@RequestMapping(value = "/paymentMode", method = RequestMethod.GET)
	private PlutusRestResponse getPaymentMode() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, PaymentMode.values());
		return status;
	}

	@RequestMapping(value = "/subscriptionType", method = RequestMethod.GET)
	private PlutusRestResponse getSubscriptionType() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, SubscriptionType.values());
		return status;
	}
	
	@RequestMapping(value = "/billingType", method = RequestMethod.GET)
	private PlutusRestResponse getBillingType() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, BillingType.values());
		return status;
	}

}