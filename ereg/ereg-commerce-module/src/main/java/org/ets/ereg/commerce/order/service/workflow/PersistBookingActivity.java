package org.ets.ereg.commerce.order.service.workflow;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.common.business.scheduling.service.SchedulingPersistenceService;
import org.ets.ereg.domain.interfaces.commerce.order.ETSTestItemRequest;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistBookingActivity extends BaseActivity {

	private static Logger logger = LoggerFactory.getLogger(PersistBookingActivity.class);
	
	@Resource(name="schedulingPersistenceService")
	private SchedulingPersistenceService schedulingPersistenceService;
		
	@Override
	public ProcessContext execute(ProcessContext context){			
		
		logger.debug("inside persist booking activity..");
		
		CartOperationRequest request = ((CartOperationContext) context).getSeedData();
	    OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
	    	    
	   
	    if(orderItemRequestDTO instanceof ETSTestItemRequest){
	    	ETSTestItemRequest etsTestItemRequest = ((ETSTestItemRequest) orderItemRequestDTO);
	    	Seat seat = etsTestItemRequest.getSeat();
	    	
	    	Booking booking = schedulingPersistenceService.createBooking(etsTestItemRequest.getBooking(),seat);
	    	//add persisted booking to test discrete order item
	    	((TestDiscreteOrderItem)request.getAddedOrderItem()).getAllBookings().add(booking);
	    	logger.debug("successfully saved booking for customer {} ",request.getOrder().getCustomer().getId());
	    }
	    
		return context;
	}

}
