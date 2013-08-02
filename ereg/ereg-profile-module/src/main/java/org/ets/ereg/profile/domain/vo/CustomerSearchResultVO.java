package org.ets.ereg.profile.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.ets.ereg.common.business.annotation.SelectColumn;

public class CustomerSearchResultVO {
	@SelectColumn(order = 0)
	private BigDecimal candidateId;
	@SelectColumn(order = 1)
	private Long deactivated;
	@SelectColumn(order = 2)
	private String firstName;
	@SelectColumn(order = 3)
	private String lastName;
	@SelectColumn(order = 4)
	private String socialSecurity;
	@SelectColumn(order = 5)
	private Date dateOfBirth;
	@SelectColumn(order = 6)
	private String middleIntial;

	@SelectColumn(order = 7)
	private String addressLine1;
	@SelectColumn(order = 8)
	private String city;
	@SelectColumn(order = 9)
	private String state;
	@SelectColumn(order = 10)
	private String postalCode;
	public BigDecimal getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(BigDecimal candidateId) {
		this.candidateId = candidateId;
	}
	public Long getDeactivated() {
		return deactivated;
	}
	public void setDeactivated(Long deactivated) {
		this.deactivated = deactivated;
	}
	public String getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMiddleIntial() {
		return middleIntial;
	}
	public void setMiddleIntial(String middleIntial) {
		this.middleIntial = middleIntial;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
