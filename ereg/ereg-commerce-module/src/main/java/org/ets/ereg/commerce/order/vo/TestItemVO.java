package org.ets.ereg.commerce.order.vo;


import java.math.BigDecimal;
import java.util.List;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class TestItemVO {
	
	private FulfillmentGroupItem testFulfillmentGroupItem;
	/**
	 * This identifies the battery subscription which was purchased in the past and can be used for this test.
	 */
	private BatterySubscription eligibleBattery;
	
	/**
	 * This identifies the battery product if one exists.
	 */
	private BatteryProduct batteryProduct;
	
	
	
	
	/*private boolean rescheduleEligible = false;
	private boolean changeTestEligible = false;
	private boolean cancelTestEligible = false;*/
	
	
	public TestItemVO(FulfillmentGroupItem testFulfillmentGroupItem, BatterySubscription eligibleBattery) {
		this.testFulfillmentGroupItem = testFulfillmentGroupItem;
		this.eligibleBattery = eligibleBattery;
			
	}
	
	public boolean canPresentBatteryOption(List<BatterySubscription> openBatteries){
		for(BatterySubscription batterySubscription : openBatteries){
			if(batterySubscription.getAgency().getId().equals(getAgency().getId())){
				return false;
			}
			
		}
		return true;	
		
	}
		
	public TestDiscreteOrderItem getTestDiscreteOrderItem() {
		return (TestDiscreteOrderItem) testFulfillmentGroupItem.getOrderItem();
	}

	public Booking getBooking() {
		return ((TestDiscreteOrderItem) testFulfillmentGroupItem.getOrderItem()).getFirstBooking();
	}

	public FulfillmentGroupItem getTestFulfillmentGroupItem() {
		return testFulfillmentGroupItem;
	}

	public void setTestFulfillmentGroupItem(
			FulfillmentGroupItem testFulfillmentGroupItem) {
		this.testFulfillmentGroupItem = testFulfillmentGroupItem;
	}
	
	public Agency getAgency() {			
		return getBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
	}
	
	public BatterySubscription getEligibleBattery() {
		return eligibleBattery;
	}

	public void setEligibleBattery(BatterySubscription eligibleBattery) {
		this.eligibleBattery = eligibleBattery;
	}

	public BatteryProduct getBatteryProduct() {
		return batteryProduct;
	}

	public void setBatteryProduct(BatteryProduct batteryProduct) {
		this.batteryProduct = batteryProduct;
	}
	
	public BigDecimal  getPrice(){
		return this.getTestDiscreteOrderItem().getAveragePrice().getAmount();
	}
}
