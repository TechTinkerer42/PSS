package org.ets.ereg.commerce.order.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.service.type.ETSFulfillmentGroupStatusType;
import org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem;



/**
 * 
 * This CHECKOUT activity is responsible for finding any reschedules in the cart
 * and marking any FulfillmentGroupItem associated with the rescheduled test
 * to ETSFulfillmentGroupStatusType.RESCHEDULED as well as releasing any bookings 
 * related to the original appointments.
 * 
 * This activity should be run after the activity that reserves the new seats.
 *
 */
public class CompleteRescheduleActivity extends BaseActivity {

    @Resource(name = "blFulfillmentGroupItemDao")
    protected FulfillmentGroupItemDao fulfillmentGroupItemDao;
    
   @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
    	CheckoutSeed seed = ((CheckoutContext) context).getSeedData();
        Order order = seed.getOrder();
        if (order != null) {
        	for (OrderItem orderItem : order.getOrderItems()) {
        		if (orderItem instanceof RescheduleTestDiscreteOrderItem) {
        			FulfillmentGroupItem original = ((RescheduleTestDiscreteOrderItem) orderItem).getOriginalTestItem();
        			original.setStatus(ETSFulfillmentGroupStatusType.RESCHEDULED);
        			fulfillmentGroupItemDao.save(original);
        			
        			//TODO release any seats related to original appointment
        		}
        	}
        }
        return context;
    }
	   
}
