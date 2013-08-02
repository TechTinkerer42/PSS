package org.ets.ereg.commerce.order.service.discount;

import org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactoryImpl;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

/**
 * This class extends org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactoryImpl
 * in order to add some transient calculated values to the ETS Specific Order Item.
 * See - org.broadleafcommerce.core.offer.service.processor.AbstractBaseProcessor
 * See - org.broadleafcommerce.core.offer.service.processor.OrderOfferProcessorImpl
 * See - org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemImpl
 *
 * If the order item is of a specific ETS type, for example "TestDiscreteOrderItem",
 * additional transient fields, such as "EligibleForBatteryInCart" will be populated for the evaluation context,
 * so that the MVEL processor can properly evaluate the expression.
 */
public class ETSPromotableItemFactoryImpl extends PromotableItemFactoryImpl implements PromotableItemFactory {
	
	private ETSBatterySubscriptionService batterySubscriptionService;

    @Override
    public PromotableOrderItem createPromotableOrderItem(OrderItem orderItem, PromotableOrder order,
                                                         boolean includeAdjustments) {
    	
        if (orderItem instanceof TestDiscreteOrderItem) {
            TestDiscreteOrderItem testItem = (TestDiscreteOrderItem) orderItem;
            testItem.setEligibleForBatteryInCart(batterySubscriptionService.isTestEligibleForAnyBatteryInCart(testItem));
            
            Customer customer = testItem.getOrder().getCustomer();
            Boolean hasEligibleBatteryForTest = batterySubscriptionService.readEligibleBatteryForTest(customer, testItem) != null;
            testItem.setEligibleForPurchasedBattery(hasEligibleBatteryForTest);
        }
        
        return super.createPromotableOrderItem(orderItem, order, includeAdjustments);
    }

	public ETSBatterySubscriptionService getBatterySubscriptionService() {
		return batterySubscriptionService;
	}

	public void setBatterySubscriptionService(
			ETSBatterySubscriptionService batterySubscriptionService) {
		this.batterySubscriptionService = batterySubscriptionService;
	}
    
    
}
