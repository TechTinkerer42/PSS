package org.ets.ereg.commerce.order.vo;

import java.util.Date;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public class BatteryItemVO {

    FulfillmentGroupItem batteryFulfillmentGroupItem;

    public BatteryItemVO(FulfillmentGroupItem batteryFulfillmentGroupItem) {
        this.batteryFulfillmentGroupItem = batteryFulfillmentGroupItem;
    }

    public BatteryDiscreteOrderItem getBatteryDiscreteOrderitem() {
        return (BatteryDiscreteOrderItem) batteryFulfillmentGroupItem.getOrderItem();
    }

    public FulfillmentGroupItem getBatteryFulfillmentGroupItem() {
        return batteryFulfillmentGroupItem;
    }

    public void setBatteryFulfillmentGroupItem(FulfillmentGroupItem batteryFulfillmentGroupItem) {
        this.batteryFulfillmentGroupItem = batteryFulfillmentGroupItem;
    }

	public Agency getAgency() {
		return this.getBatteryDiscreteOrderitem().getAgency();
	}
	
	public boolean isTestWithinBatteryDuration(Date testTime) {		
		return (testTime.after(this.getBatteryDiscreteOrderitem().getStartDate()))  && (testTime.before(this.getBatteryDiscreteOrderitem().getEndDate()));
	}
}
