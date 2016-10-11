package com.bbytes.plutus.service;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.util.BillingConstant;
import com.bbytes.plutus.util.DateUtil;

@Service
public class BillingService {

	@Autowired
	private SubscriptionService subscriptionService;

	public void process(ProductPlanStats productPlanStats) {
		Subscription subscription = subscriptionService.findOne(productPlanStats.getSubscriptionKey());

		if (isStatusnap(subscription)) {
			calculateDailyBillingForStatusap(subscription, productPlanStats);
		}

		subscriptionService.save(subscription);
	}

	private void calculateDailyBillingForStatusap(Subscription subscription, ProductPlanStats productPlanStats) {
		if (DateUtil.isNotToday(subscription.getAmountUpdatedTimeStamp())) {
			Double currentBillingAmt = subscription.getBillingAmount();
			Number userCostPerMonth = subscription.getProductPlan().getProductPlanItemToCost()
					.get(BillingConstant.STATUSNAP_USER_COST);
			double costPerUserPerDay = userCostPerMonth.doubleValue() / 30;

			Integer userCountToday = productPlanStats.getStats().get(BillingConstant.STATUSNAP_USER_COUNT);
			Double billAmtForToday = userCountToday * costPerUserPerDay;
			currentBillingAmt = currentBillingAmt + billAmtForToday;

			subscription.setBillingAmount(currentBillingAmt);
			subscription.setAmountUpdatedTimeStamp(DateTime.now());
			subscriptionService.save(subscription);
		}
	}

	private boolean isStatusnap(Subscription subscription) {
		return ProductName.statusnap.toString().equalsIgnoreCase(subscription.getProductPlan().getProduct().getName());
	}

	public Map<String, Number> getProductCostMap(String productName) {
		Map<String, Number> productPlanItemToCost = new HashMap<String, Number>();
		if (ProductName.statusnap.toString().equalsIgnoreCase(productName)) {
			productPlanItemToCost.put(BillingConstant.STATUSNAP_PROJECT_COST, 0);
			productPlanItemToCost.put(BillingConstant.STATUSNAP_USER_COST, 3);
		}
		return productPlanItemToCost;
	}

}
