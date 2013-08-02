package org.ets.ereg.commerce.order.service.workflow;

import java.util.Date;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.offer.domain.OrderItemAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This CHECKOUT activity is responsible for associating
 * the use of a Test against a particular purchased Battery.
 * This use is recorded in the BatteryScheduledTest table
 * 
 * This activity will also record which offer was applied in order to receive
 * the battery discount for this test is applicable
 * 
 * @see org.ets.ereg.domain.order.BatteryScheduledTestImpl
 * 
 */
public class RecordBatteryScheduledTestActivity  extends BaseActivity {
	
	private static Logger logger = LoggerFactory.getLogger(RecordBatteryScheduledTestActivity.class);
	private static final String TARGET_SYSTEM = "BATTERY";
	
	protected ETSBatterySubscriptionService batterySubscriptionService;
	
    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
    	CheckoutSeed seed = ((CheckoutContext) context).getSeedData();
        Order order = seed.getOrder();
        if (order != null) {
        	for (OrderItem orderItem : order.getOrderItems()) {
        		if (orderItem instanceof TestDiscreteOrderItem) {
        			for (OrderItemPriceDetail priceDetail : orderItem.getOrderItemPriceDetails()) {
        				for (OrderItemPriceDetailAdjustment adjustment : priceDetail.getOrderItemPriceDetailAdjustments()) {
            				if (TARGET_SYSTEM.equals(adjustment.getOffer().getTargetSystem())) {
            					BatterySubscription subscription = 
            							batterySubscriptionService.readEligibleBatteryForTest(order.getCustomer(), 
            									(TestDiscreteOrderItem)orderItem);
            					if (subscription == null) {
            						logger.error("Unable to determine eligible Battery Subscription for Customer: " + 
            								order.getCustomer().getId() + " and order item: " + orderItem.getId() +
            								" given the following adjustment: " + adjustment.getId());
            					} else {    
                					BatteryScheduledTest scheduledTest = batterySubscriptionService.createScheduledTest();
    	        					scheduledTest.setBatterySubscription(subscription);
    	        					scheduledTest.setTestDiscreteOrderItem((TestDiscreteOrderItem) orderItem);
    	        					scheduledTest.setAppliedOffer(adjustment.getOffer());
    	        					scheduledTest.setDateRedeemed(new Date());
    	        					
    	        					batterySubscriptionService.saveScheduledTest(scheduledTest);
            					}
            				}        					
        				}

        			}
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
