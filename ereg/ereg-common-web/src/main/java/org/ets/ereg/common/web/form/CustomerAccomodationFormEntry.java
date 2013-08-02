package org.ets.ereg.common.web.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;

public class CustomerAccomodationFormEntry {
	private Long testId;
	private String deliveryMethod;
	private String accommodationTypeCode;
	private String accommodationTypeValue;
	private String originalAccommodationTypeValue;
	private String expirationDay;
	private String expirationMonth;
	private String expirationYear;
	private Date expirationDate;
	private Date originalExpirationDate;
	private Date approvalDate;
	private String accommodationStatus;
	private AccommodationType accommodation;
	private boolean existing;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATE_FIELD_SEPARATOR = "-";
	private static final int MIN_YEAR = 0;
	private static final int MAX_YEAR = 9999;

	static{
		DATE_FORMAT.setLenient(false);
	}

	public String getAccommodationTypeCode() {
		return accommodationTypeCode;
	}

	public void setAccommodationTypeCode(String accommodationTypeCode) {
		this.accommodationTypeCode = accommodationTypeCode;
	}

	public String getAccommodationTypeValue() {
		return accommodationTypeValue;
	}

	public void setAccommodationTypeValue(String accommodationTypeValue) {
		this.accommodationTypeValue = accommodationTypeValue;
	}

	public String getExpirationDay() {
		return expirationDay;
	}

	public void setExpirationDay(String expirationDay) {
		this.expirationDay = expirationDay;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getAccommodationStatus() {
		return accommodationStatus;
	}

	public void setAccommodationStatus(String accommodationStatus) {
		this.accommodationStatus = accommodationStatus;
	}

	public AccommodationType getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(AccommodationType accommodation) {
		this.accommodation = accommodation;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getOriginalAccommodationTypeValue() {
		return originalAccommodationTypeValue;
	}

	public void setOriginalAccommodationTypeValue(
			String originalAccommodationTypeValue) {
		this.originalAccommodationTypeValue = originalAccommodationTypeValue;
	}

	public Date getOriginalExpirationDate() {
		return originalExpirationDate;
	}

	public void setOriginalExpirationDate(Date originalExpirationDate) {
		this.originalExpirationDate = originalExpirationDate;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

	public void transfereFromFieldsToDate(){
		expirationDate = null;
		if(	null != expirationYear && expirationYear.trim().length() > 0 &&
			null != expirationMonth && expirationMonth.trim().length() > 0 &&
			null != expirationDay && expirationDay.trim().length() > 0
		){
			int year = Integer.parseInt(expirationYear);
			if(year > CustomerAccomodationFormEntry.MIN_YEAR && year <= CustomerAccomodationFormEntry.MAX_YEAR){
				try {
					expirationDate = DATE_FORMAT.parse(expirationYear + CustomerAccomodationFormEntry.DATE_FIELD_SEPARATOR + expirationMonth + CustomerAccomodationFormEntry.DATE_FIELD_SEPARATOR + expirationDay);
				}
				catch(ParseException e){

				}
			}
		}
	}

	public void transfereDateToFields(){
		if(null == expirationDate){
			expirationDay = "";
			expirationMonth = "";
			expirationYear = "";
		}
		else{
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.setTime(expirationDate);
			expirationDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			expirationMonth = Integer.toString(cal.get(Calendar.MONTH)+1);
			expirationYear = Integer.toString(cal.get(Calendar.YEAR));
		}
	}
}
