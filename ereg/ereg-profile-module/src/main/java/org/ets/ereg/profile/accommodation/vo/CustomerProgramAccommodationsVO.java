package org.ets.ereg.profile.accommodation.vo;

import java.util.List;

public class CustomerProgramAccommodationsVO {
	private Long customerId;
	
	private String programCode;
	
	List<CustomerTestAccommodationsVO> customerTestAccommodations;

	

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public List<CustomerTestAccommodationsVO> getCustomerTestAccommodations() {
		return customerTestAccommodations;
	}

	public void setCustomerTestAccommodations(
			List<CustomerTestAccommodationsVO> customerTestAccommodations) {
		this.customerTestAccommodations = customerTestAccommodations;
	}

}
