package org.ets.ereg.csr.web.form.profile;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.common.web.form.AbstractSearchCriteriaForm;



public class CustomerSearchForm extends AbstractSearchCriteriaForm{
	
	private String testTakerId;
	private Long appointmentNumber;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String ssn;
	private Date dateOfBirth;
	private String email;
	private String country;
	private String city;
	private String state;
	private String postalCode;
	private String phone;
	private String formType;
	private Long adminId;
	public String getTestTakerId() {
		return testTakerId;
	}
	public void setTestTakerId(String testTakerId) {
		if(!StringUtils.isEmpty(testTakerId))
		{
			this.testTakerId = testTakerId.trim();
		}
	}
	public Long getAppointmentNumber() {
		return appointmentNumber;
	}
	public void setAppointmentNumber(Long appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if(!StringUtils.isEmpty(firstName))
		{
			this.firstName = firstName.trim();
		}
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if(!StringUtils.isEmpty(lastName))
		{
			this.lastName = lastName.trim();
		}
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		if(!StringUtils.isEmpty(middleInitial))
		{
			this.middleInitial = middleInitial.trim();
		}
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		if(!StringUtils.isEmpty(ssn))
		{
			this.ssn = ssn.trim();
		}
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(!StringUtils.isEmpty(email))
		{
			this.email = email.trim();
		}
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		if(!StringUtils.isEmpty(country))
		{
			this.country = country.trim();
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if(!StringUtils.isEmpty(city))
		{
			this.city = city.trim();
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(!StringUtils.isEmpty(state))
		{
			this.state = state;
		}
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		if(!StringUtils.isEmpty(postalCode))
		{
			this.postalCode = postalCode.trim();
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if(!StringUtils.isEmpty(phone))
		{
			this.phone = phone.trim();
		}
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		if(!StringUtils.isEmpty(formType))
		{
			this.formType = formType.trim();
		}
	}
	


}
