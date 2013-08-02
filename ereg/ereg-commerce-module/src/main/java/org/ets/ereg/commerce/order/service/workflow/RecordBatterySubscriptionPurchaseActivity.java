package org.ets.ereg.commerce.order.service.workflow;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;

/**
 * 
 * This CHECKOUT activity is responsible for recording the purchase of a 
 * Battery Subscription in the BatterySubscription table.
 * @see org.ets.ereg.domain.order.BatterySubscriptionImpl
 *
 */
public class RecordBatterySubscriptionPurchaseActivity extends BaseActivity {
	
	protected ETSBatterySubscriptionService batterySubscriptionService;
	
    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
    	CheckoutSeed seed = ((CheckoutContext) context).getSeedData();
        Order order = seed.getOrder();
        if (order != null) {
        	for (OrderItem orderItem : order.getOrderItems()) {
        		if (orderItem instanceof BatteryDiscreteOrderItem) {
        			BatteryDiscreteOrderItem bdi = (BatteryDiscreteOrderItem)orderItem;
        			BatterySubscription batterySubscription = batterySubscriptionService.createSubscription();
        			batterySubscription.setBatteryDiscreteOrderItem(bdi);
        			batterySubscription.setCustomer(order.getCustomer());
        			batterySubscription = batterySubscriptionService.saveSubscription(batterySubscription);
        		}
        	}
        }
        return context;
    }

	public ETSBatterySubscriptionService getBatterySubscriptionService() {
		return batterySubscriptionService;
	}

	public void setBatterySubscriptionService(
			ETSBatterySubscriptionService batterySubscriptionService) {
		this.batterySubscriptionService = batterySubscriptionService;
	}

}
