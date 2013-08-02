package org.ets.ereg.scheduling.vo;

import java.util.List;

import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.booking.EventOutComeStatusImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.test.TestImpl;
import org.ets.ereg.domain.test.TestVariationImpl;

public class AppointmentVO {

	private Booking booking;
	private String testDate;
	private String testTimeHour;
	private String testTimeMinute;
	private String testTimeAMPM;
	private Boolean deletedFlag;
	private Long testId;
	private Long baseFormId;
	private Form baseForm;
	private String agency;
	private String testCenter;
	private List<Form> subForms;
	private boolean eligibilityFlag;
	private Boolean editableFlag;
	private String deliveryModeDescription;

	public AppointmentVO() {
		this.booking = new BookingImpl();
		TestVariation testVariation = new TestVariationImpl();
		Test test = new TestImpl();
		// to do later
		booking.setTestVariation(testVariation);
		testVariation.setTest(test);
		booking.setForm(new FormImpl());
		booking.getForm().setTest(test);
		booking.setEvntOutComeStatus(new EventOutComeStatusImpl());
		baseForm=new FormImpl();
		baseForm.setLangCode(new LanguageTypeImpl());
		//booking.setLangCode(baseForm.getLangCode());
		deletedFlag = new Boolean(false);
		eligibilityFlag=false;
		editableFlag = new Boolean(true);
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestTimeHour() {
		return testTimeHour;
	}

	public void setTestTimeHour(String testTimeHour) {
		this.testTimeHour = testTimeHour;
	}

	public String getTestTimeMinute() {
		return testTimeMinute;
	}

	public void setTestTimeMinute(String testTimeMinute) {
		this.testTimeMinute = testTimeMinute;
	}

	public String getTestTimeAMPM() {
		return testTimeAMPM;
	}

	public void setTestTimeAMPM(String testTimeAMPM) {
		this.testTimeAMPM = testTimeAMPM;
	}

	public Boolean getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Boolean deletedFlag) {
		this.deletedFlag = deletedFlag;
	}


	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getBaseFormId() {
		return baseFormId;
	}

	public void setBaseFormId(Long baseFormId) {
		this.baseFormId = baseFormId;
	}

	public Form getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(Form baseForm) {
		this.baseForm = baseForm;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getTestCenter() {
		return testCenter;
	}

	public void setTestCenter(String testCenter) {
		this.testCenter = testCenter;
	}

	public boolean isEligibilityFlag() {
		return eligibilityFlag;
	}

	public void setEligibilityFlag(boolean eligibilityFlag) {
		this.eligibilityFlag = eligibilityFlag;
	}

	public List<Form> getSubForms() {
		return subForms;
	}

	public void setSubForms(List<Form> subForms) {
		this.subForms = subForms;
	}

	public Boolean getEditableFlag() {
		return editableFlag;
	}

	public void setEditableFlag(Boolean editableFlag) {
		this.editableFlag = editableFlag;
	}

	public String getDeliveryModeDescription() {
		return deliveryModeDescription;
	}

	public void setDeliveryModeDescription(String deliveryModeDescription) {
		this.deliveryModeDescription = deliveryModeDescription;
	}

}
