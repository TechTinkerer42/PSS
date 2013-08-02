package org.ets.ereg.commerce.order.service.workflow;

import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.pricing.service.workflow.PricingContext;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;

/**
 * 
 * This activity is responsible for setting up the transient values
 * on the Customer determining if this particular user has any OPEN 
 * Battery Subscriptions Or if there are any BatteryDiscreteOrderItems
 * in the cart.
 * 
 * An "open" battery is defined as one that is not expired and whose tests are not consumed.
 * 
 * IMPORTANT:
 * This activity must run before: org.broadleafcommerce.core.pricing.service.workflow.OfferActivity
 *
 */
public class CustomerBatteryOfferActivity extends BaseActivity {

	protected ETSBatterySubscriptionService batterySubscriptionService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
    	Order order = ((PricingContext)context).getSeedData();
    	List<BatterySubscription> subscriptions = batterySubscriptionService.readAllOpenBatteriesForCustomer(order.getCustomer());
    	
    	if (subscriptions != null && subscriptions.size() > 0) {
    		((ETSCustomer)order.getCustomer()).setHasEligibleBatterySubscription(true);
    	}
    	
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem instanceof BatteryDiscreteOrderItem) {
				((ETSCustomer)order.getCustomer()).setHasBatteryInCart(true);	
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
