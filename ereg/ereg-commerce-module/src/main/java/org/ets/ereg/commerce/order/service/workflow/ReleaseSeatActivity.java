package org.ets.ereg.commerce.order.service.workflow;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.common.business.scheduling.service.SchedulingPersistenceService;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.ReleaseSeatResponse;
import org.ets.ereg.domain.order.type.ETSFulfillmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ReleaseSeatActivity extends BaseActivity implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(ReleaseSeatActivity.class);

	private ApplicationContext applicationContext;

	@Resource(name="schedulingService")
	private SchedulingService schedulingService;

	@Resource(name="schedulingPersistenceService")
	private SchedulingPersistenceService schedulingPersistenceService;

	@Override
	public ProcessContext execute(ProcessContext context){

		CartOperationRequest request = ((CartOperationContext) context).getSeedData();

	    Order order = request.getOrder();
	    ETSCustomer customer = (ETSCustomer)order.getCustomer();

	    List<FulfillmentGroup> fulFillmentGroups = order.getFulfillmentGroups();
	    List<FulfillmentGroupItem> fulFillmentGroupItems = Collections.emptyList();
	    List<Booking> bookings = Collections.emptyList();

	    ReleaseSeatResponse releaseSeatResponse=null;
	    TestDiscreteOrderItem testDiscreteOrderItem=null;

	    Long orderItemIdToRemove = request.getItemRequest().getOrderItemId();

	    if(fulFillmentGroups!=null && !fulFillmentGroups.isEmpty()){
		    for(FulfillmentGroup fulFillmentGroup:fulFillmentGroups){

		    	if(ETSFulfillmentType.TEST.equals(fulFillmentGroup.getType())){

		    		fulFillmentGroupItems = fulFillmentGroup.getFulfillmentGroupItems();
		    		if(fulFillmentGroupItems!=null && !fulFillmentGroupItems.isEmpty()){
			    		for(FulfillmentGroupItem fulFillmentGroupItem:fulFillmentGroupItems){
			    			testDiscreteOrderItem=((TestDiscreteOrderItem)fulFillmentGroupItem.getOrderItem());

			    			//if the order item equals to the order item id to be removed
			    			if(orderItemIdToRemove.equals(testDiscreteOrderItem.getId())){			    				
			    				bookings = testDiscreteOrderItem.getAllBookings();				    			
				    			for(Booking booking:bookings){

				    				//if the booking is in held state
				    				if(booking.getBookingStatusType()!=null && booking.getBookingStatusType().getCode().equalsIgnoreCase(BookingStatusType.HELD)){
				    					try{
					    				releaseSeatResponse = releaseSeat(booking,customer);	    				
					    				if(releaseSeatResponse.isSuccessful()){	    					
					    					schedulingPersistenceService.updateBookingStatus(booking.getId(), BookingStatusType.RELEASED);	    					
					    					logger.info("successfully released seat in release seat activity for customer {}",customer.getId());  			    	
					    				}else{	    					
					    					logger.info("failed to release seat in release seat activity for customer {}",customer.getId());	   					    				
										}
				    					}catch(Exception e){
				    						logger.error("Release seat call failed", e);
				    					}
				    				}
				    			}
			    			}
			    			
			    		}
		    		}

		    	}

	    }
	    }

		return context;
	}

	private ReleaseSeatResponse releaseSeat(Booking booking,ETSCustomer customer){

		HeldSeat heldSeat = getHeldSeat();

		heldSeat = getHeldSeat();
		heldSeat.setDeliveryModeCode(booking.getTestVariation().getScheduleTestCode());
		//need to read from held seat entity..

		HeldBooking heldBooking = booking.getHeldBooking();

		if(heldBooking!=null){
			heldSeat.setHoldCode(heldBooking.getHoldSeatId());
			heldSeat.setHoldSource(heldBooking.getHoldSourceDesc());
			heldSeat.setSiteCode(heldBooking.getSiteCode());
			heldSeat.setSeatCode(heldBooking.getSeatCode());
			heldSeat.setLabCode(heldBooking.getLabCode());
			heldSeat.setHoldDuration(heldBooking.getHoldDuration());
			
			// TODO reconsider data type of LocalStartDateTime
			Calendar calLocalStartDateTime = Calendar.getInstance();
			calLocalStartDateTime.setTime(booking.getAppointmentDateTime());
			heldSeat.setLocalStartDateTime(calLocalStartDateTime);
			
			heldSeat.setStrLocalStartTime(booking.getAppointmentDateString());
		}

		heldSeat.setTestCenter(booking.getTestCenter());
		heldSeat.setTestVariation(booking.getTestVariation());

		ReleaseSeatRequest releaseSeatRequest = getReleaseSeatRequest();

		releaseSeatRequest = getReleaseSeatRequest();
		releaseSeatRequest.setCustomer(customer);
		releaseSeatRequest.setHeldSeat(heldSeat);


		return schedulingService.releaseSeat(releaseSeatRequest);

	}

	protected HeldSeat getHeldSeat(){

		return (HeldSeat)this.applicationContext.getBean("heldSeat");
	}

	protected ReleaseSeatRequest getReleaseSeatRequest(){

		return (ReleaseSeatRequest)this.applicationContext.getBean("releaseSeatRequest");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		 this.applicationContext = applicationContext;
	}

}
