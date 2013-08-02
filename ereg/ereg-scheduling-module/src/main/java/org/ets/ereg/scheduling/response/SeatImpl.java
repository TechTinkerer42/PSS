package org.ets.ereg.scheduling.response;

import java.util.Calendar;
import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.scheduling.response.AvailabilityStatus;
import org.ets.ereg.domain.interfaces.scheduling.response.Distance;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;

public class SeatImpl implements Seat {

	private TestCenter testCenter;
	private Calendar localStartDateTime;
	private Distance approxDistance;
	private TestVariation testVariation;
	private AvailabilityStatus availabilityStatus=new AvailabilityStatusImpl();
	private String deliveryModeCode;
	private final int id;
	private String schedulingTestSiteId;
	private String schedulingTestCode;

	private String siteCode;
	private String labCode;
	private String seatCode;
	private String strLocalStartTime;

	private boolean hasAccommodations;
	private List<CustomerAccommodationTest> customerAccommodations;

	private Short duration;


	public SeatImpl(int id){
		super();
		this.id = id;
	}

	@Override
	public TestCenter getTestCenter() {
		return testCenter;
	}

	@Override
	public void setTestCenter(TestCenter testCenter) {
		this.testCenter = testCenter;
	}

	@Override
	public Calendar getLocalStartDateTime() {
		return localStartDateTime;
	}

	@Override
	public void setLocalStartDateTime(Calendar localStartDateTime) {
		this.localStartDateTime = localStartDateTime;
	}

	@Override
	public Distance getApproxDistance() {
		return approxDistance;
	}

	@Override
	public void setApproxDistance(Distance approxDistance) {
		this.approxDistance = approxDistance;
	}

    @Override
    public TestVariation getTestVariation() {
        return testVariation;
    }

	@Override
    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
	}

	@Override
	public AvailabilityStatus getAvailabilityStatus() {
		return availabilityStatus;
	}

	@Override
	public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
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
	public int getId() {
		return id;
	}

	@Override
	public String getSiteCode() {
		return siteCode;
	}

	@Override
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	@Override
	public String getLabCode() {
		return labCode;
	}

	@Override
	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}

	@Override
	public String getSeatCode() {
		return seatCode;
	}

	@Override
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	@Override
    public String getStrLocalStartTime() {
		return strLocalStartTime;
	}

	@Override
    public void setStrLocalStartTime(String strLocalStartTime) {
		this.strLocalStartTime = strLocalStartTime;
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
	public List<CustomerAccommodationTest> getCustomerAccommodations() {
		return customerAccommodations;
	}

	@Override
	public void setCustomerAccommodations(
			List<CustomerAccommodationTest> customerAccommodations) {
		this.customerAccommodations = customerAccommodations;
	}

	@Override
	public String getSchedulingTestCode() {
		return schedulingTestCode;
	}

	@Override
	public void setSchedulingTestCode(String schedulingTestCode) {
		this.schedulingTestCode = schedulingTestCode;
	}

	@Override
	public Short getDuration() {
		return duration;
	}

	@Override
	public void setDuration(Short duration) {
		this.duration = duration;
	}

}
