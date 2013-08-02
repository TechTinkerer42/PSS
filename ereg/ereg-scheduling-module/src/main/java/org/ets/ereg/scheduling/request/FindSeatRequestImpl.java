package org.ets.ereg.scheduling.request;

import java.util.Calendar;
import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingOperation;


public class FindSeatRequestImpl extends AbstractSchedulingRequest implements
		FindSeatRequest {

	private Calendar searchFromDate;
	private Calendar searchToDate;
	private boolean applyStatckingLogic;
	private String deliveryModeCode;

	private Short duration;
	private String schedulingTestSiteId;
	private String schedulingTestCode;

	private boolean hasAccommodations;
	private List<CustomerAccommodationTest> customerAccommodations;



	@Override
	public List<CustomerAccommodationTest> getCustomerAccommodations() {
		return customerAccommodations;
	}

	@Override
	public void setCustomerAccommodations(
			List<CustomerAccommodationTest> customerAccommodations) {
		this.customerAccommodations = customerAccommodations;
	}

	protected FindSeatRequestImpl(){

	}

	@Override
	public Calendar getSearchFromDate() {
		return this.searchFromDate;
	}

	@Override
	public void setSearchFromDate(Calendar searchFromDate) {
		this.searchFromDate = searchFromDate;

	}

	@Override
	public Calendar getSearchToDate() {
		return searchToDate;
	}

	@Override
	public void setSearchToDate(Calendar searchToDate) {
		this.searchToDate = searchToDate;
	}

	@Override
	public boolean isApplyStatckingLogic() {
		return applyStatckingLogic;
	}

	@Override
	public void setApplyStatckingLogic(boolean applyStatckingLogic) {
		this.applyStatckingLogic = applyStatckingLogic;
	}


	@Override
	public SchedulingOperation getOperation() {
		return SchedulingOperation.FIND_SEAT;
	}

	@Override
	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}

	@Override
	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}


	@Override
	public boolean isHasAccommodations() {
		return hasAccommodations;
	}

	@Override
	public void setHasAccommodations(boolean hasAccommodations) {
		this.hasAccommodations = hasAccommodations;
	}

	@Override
	public Short getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Short duration) {
		this.duration = duration;
	}

	@Override
	public String getSchedulingTestSiteId() {
		return schedulingTestSiteId;
	}

	@Override
	public void setSchedulingTestSiteId(String schedulingTestSiteId) {
		this.schedulingTestSiteId = schedulingTestSiteId;
	}

	@Override
	public String getSchedulingTestCode() {
		return schedulingTestCode;
	}

	@Override
	public void setSchedulingTestCode(String schedulingTestCode) {
		this.schedulingTestCode = schedulingTestCode;
	}

}
