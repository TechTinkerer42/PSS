package org.ets.ereg.domain.interfaces.scheduling.request;

import java.util.Calendar;
import java.util.List;

import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;

public interface FindSeatRequest extends SchedulingRequest{

	Calendar getSearchFromDate();
	void setSearchFromDate(Calendar searchFromDate);

	Calendar getSearchToDate();
	void setSearchToDate(Calendar searchToDate);

	boolean isApplyStatckingLogic();
	void setApplyStatckingLogic(boolean applyStatckingLogic);

    @Override
    TestVariation getTestVariation();
    @Override
    void setTestVariation(TestVariation testVariation);

	String getDeliveryModeCode();
	void setDeliveryModeCode(String deliveryModeCode);

	Short getDuration();
	void setDuration(Short duration);

    @Override
    String getAlternateForm();
    @Override
    void setAlternateForm(String alternateForm);

	boolean isHasAccommodations();
	void setHasAccommodations(boolean hasAccommodations);

	List<CustomerAccommodationTest> getCustomerAccommodations();
	void setCustomerAccommodations(
			List<CustomerAccommodationTest> customerAccommodations);

	String getSchedulingTestSiteId();
	void setSchedulingTestSiteId(String schedulingTestSiteId);

	String getSchedulingTestCode();
	void setSchedulingTestCode(String schedulingTestCode);



}
