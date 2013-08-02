package org.ets.ereg.commerce.order.vo;

import org.broadleafcommerce.common.money.Money;

public class CancelTestVO {
	private BatteryItemVO batterySubscription;
	private TestItemVO testItem;
	
	
	private int quantity;
	private Money refundAmount;
	
	public BatteryItemVO getBatterySubscription() {
		return batterySubscription;
	}
	public void setBatterySubscription(BatteryItemVO batterySubscription) {
		this.batterySubscription = batterySubscription;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Money getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
	}
	public TestItemVO getTestItem() {
		return testItem;
	}
	public void setTestItem(TestItemVO testItem) {
		this.testItem = testItem;
	}

	
	
}
