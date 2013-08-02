package org.ets.ereg.commerce.order.service.workflow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutContext;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.common.business.scheduling.service.SchedulingPersistenceService;
import org.ets.ereg.common.business.scheduling.service.SchedulingService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.booking.HeldBooking;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.ReserveSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.ets.ereg.domain.order.type.ETSFulfillmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ETSCompleteOrderActivity  extends BaseActivity implements ApplicationContextAware{

private static Logger logger = LoggerFactory.getLogger(ETSCompleteOrderActivity.class);

	@Resource(name="schedulingService")
	private SchedulingService schedulingService;

	@Resource(name="schedulingPersistenceService")
	private SchedulingPersistenceService schedulingPersistenceService;

	@Resource(name = "referenceEntityService")
	private ReferenceService referenceService;

	private ApplicationContext applicationContext;

	@Override
	public ProcessContext execute(ProcessContext context){

		CheckoutSeed seed = ((CheckoutContext) context).getSeedData();

	    Order order = seed.getOrder();
	    ETSCustomer customer = (ETSCustomer)order.getCustomer();

	    List<FulfillmentGroup> fulFillmentGroups = order.getFulfillmentGroups();
	    List<FulfillmentGroupItem> fulFillmentGroupItems = Collections.emptyList();
	    List<Booking> bookings = Collections.emptyList();
	    List<Booking> reservedBookings = new ArrayList<Booking>();
	    Map<Long,ReservedSeat> bookToReservedSeat = new HashMap<Long, ReservedSeat>();

	    ReservedSeat reservedSeat=null;
	    ReserveSeatResponse reserveSeatResponse=null;
	    TestDiscreteOrderItem testDiscreteOrderItem=null;

	    for(FulfillmentGroup fulFillmentGroup:fulFillmentGroups){

	    	if(ETSFulfillmentType.TEST.equals(fulFillmentGroup.getType())){

	    		fulFillmentGroupItems = fulFillmentGroup.getFulfillmentGroupItems();

	    		for(FulfillmentGroupItem fulFillmentGroupItem:fulFillmentGroupItems){

	    			testDiscreteOrderItem=((TestDiscreteOrderItem)fulFillmentGroupItem.getOrderItem());
	    			bookings = testDiscreteOrderItem.getAllBookings();

	    			for(Booking booking:bookings){

	    				reserveSeatResponse = reserveSeat(booking,customer);

	    				if(reserveSeatResponse.isSuccessful()){//add to list of reserved bookings

	    					//update status to reserved in booking table
	    					reservedSeat = reserveSeatResponse.getReservedSeat();

	    					reservedBookings.add(booking);
	    					bookToReservedSeat.put(booking.getId(), reservedSeat);

	    					//booking.setEtsApptID(reservedSeat.getEtsReservationID());
	    					booking.setExtrnlAppointmentID(reservedSeat.getEtsReservationID());
	    					booking.setBookingStatusType(
	    			                referenceService.getEntityByPrimaryKey(
	    			                        BookingStatusType.class,
	    			                        BookingStatusType.RESERVED));

	    					schedulingPersistenceService.updateBooking(booking);
	    					logger.info("reserve seat is successfull for customer {}",customer.getId());

	    				}else{
	    					//if atleast one fails.. cancel already reserved seats..
	    					logger.info("reserve seat is failed, rolling back the reserved seats for customer {}",customer.getId());
	    					rollbackReservations(reservedBookings, bookToReservedSeat, customer);
	    					break;
	    				}
	    			}
	    		}

	    	}

	    }
	    seed.getOrder().setStatus(OrderStatus.SUBMITTED);
        seed.getOrder().setOrderNumber(String.valueOf(seed.getOrder().getId()));
        seed.getOrder().setSubmitDate(Calendar.getInstance().getTime());
		return context;
	}

	private void rollbackReservations(List<Booking> reservedBookings, Map<Long,ReservedSeat> bookToReservedSeat,ETSCustomer customer){

		//mark all bookings are pending for cancellation..
		for(Booking booking:reservedBookings){
			schedulingPersistenceService.updateBookingStatus(booking.getId(), BookingStatusType.PENDING_CANCELLATION);
		}
		CancelSeatRequest cancelSeatRequest = null;
		for(Booking booking:reservedBookings){
			cancelSeatRequest = getCancelSeatRequest();
			cancelSeatRequest.setCustomer(customer);
			cancelSeatRequest.setReservedSeat(bookToReservedSeat.get(booking.getId()));
			if(schedulingService.cancelSeat(cancelSeatRequest).isSuccessful()){
				//is this really required. in case of exceptions we can rollback transaction
				schedulingPersistenceService.updateBookingStatus(booking.getId(), BookingStatusType.CANCELED);
			}
	}

	}

	private ReserveSeatResponse reserveSeat(Booking booking,ETSCustomer customer){

		HeldSeat heldSeat = getHeldSeat();
		heldSeat.setDeliveryModeCode(booking.getTestVariation().getId().getDeliveryModeCode());

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

			heldSeat.setEtsAppointmentId(booking.getEtsApptID());
			heldSeat.setStrLocalStartTime(booking.getAppointmentDateString());
		}

		heldSeat.setTestCenter(booking.getTestCenter());
		heldSeat.setTestVariation(booking.getTestVariation());
		ReserveSeatRequest reserveSeatRequest = getReserveSeatRequest();


		reserveSeatRequest.setCustomer(customer);
		reserveSeatRequest.setHeldSeat(heldSeat);

		return schedulingService.reserveSeat(reserveSeatRequest);

	}

	private ReserveSeatRequest getReserveSeatRequest(){
		return this.applicationContext.getBean("reserveSeatRequest",ReserveSeatRequest.class);
	}
	private HeldSeat getHeldSeat(){
		return (HeldSeat)this.applicationContext.getBean("heldSeat");
	}
	private CancelSeatRequest getCancelSeatRequest(){
		return (CancelSeatRequest)this.applicationContext.getBean("cancelSeatRequest");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}
}
