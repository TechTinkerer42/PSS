package org.ets.ereg.scheduling.response;

import java.util.Calendar;

import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("heldSeat")
@Scope("prototype")
public class HeldSeatImpl extends SeatImpl implements HeldSeat {

	private String holdCode;
	private String holdSource;
	private Calendar holdExpiration;
	private String holdDuration;
	private String etsAppointmentId;


	public HeldSeatImpl(){
		super(0);
	}

	public HeldSeatImpl(Seat seat){
		super(seat.getId());
		setApproxDistance(seat.getApproxDistance());
		setAvailabilityStatus(seat.getAvailabilityStatus());
		setDeliveryModeCode(seat.getDeliveryModeCode());
		setLocalStartDateTime(seat.getLocalStartDateTime());
		setTestCenter(seat.getTestCenter());
		setTestVariation(seat.getTestVariation());
		setSiteCode(seat.getSiteCode());
		setLabCode(seat.getLabCode());
		setSeatCode(seat.getSeatCode());
		setStrLocalStartTime(seat.getStrLocalStartTime());
		setHasAccommodations(seat.isHasAccommodations());
		setCustomerAccommodations(seat.getCustomerAccommodations());
	}

	@Override
	public String getHoldCode() {
		return holdCode;
	}

	@Override
	public void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	@Override
	public String getHoldSource() {
		return holdSource;
	}

	@Override
	public void setHoldSource(String holdSource) {
		this.holdSource = holdSource;
	}

	@Override
	public Calendar getHoldExpiration() {
		return holdExpiration;
	}

	@Override
	public void setHoldExpiration(Calendar holdExpiration) {
		this.holdExpiration = holdExpiration;
	}


	@Override
	public String getHoldDuration() {
		return holdDuration;
	}

	@Override
	public void setHoldDuration(String holdDuration) {
		this.holdDuration = holdDuration;
	}

	@Override
    public String getEtsAppointmentId() {
		return etsAppointmentId;
	}

	@Override
    public void setEtsAppointmentId(String etsAppointmentId) {
		this.etsAppointmentId = etsAppointmentId;
	}

}
