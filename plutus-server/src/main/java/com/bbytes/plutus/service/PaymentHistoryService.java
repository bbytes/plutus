package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.PaymentHistory;
import com.bbytes.plutus.repo.PaymentHistoryRepository;

@Service
public class PaymentHistoryService extends AbstractService<PaymentHistory, String> {

	protected PaymentHistoryRepository paymentHistoryRepository;

	@Autowired
	public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository) {
		super(paymentHistoryRepository);
		this.paymentHistoryRepository = paymentHistoryRepository;
	}

}
