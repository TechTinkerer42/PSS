package org.ets.ereg.profile.accommodation.vo;

import java.util.Collections;
import java.util.List;

public class CustomerTestAccommodationsVO {
	private String testTitle;	
	
	List<CustomerDeliveryMethodAccommodationsVO> customerDeliveryMethodAccommodations=Collections.emptyList();
	
	public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	
	public int getTestAccommodationCount(){
		int count=0;
		for(CustomerDeliveryMethodAccommodationsVO deliveryMethodAccommodation: customerDeliveryMethodAccommodations){
			count+=deliveryMethodAccommodation.getAccommodationCount(); 
			
		}
		return count;
	}
	public List<CustomerDeliveryMethodAccommodationsVO> getCustomerDeliveryMethodAccommodations() {
		return customerDeliveryMethodAccommodations;
	}
	public void setCustomerDeliveryMethodAccommodations(
			List<CustomerDeliveryMethodAccommodationsVO> customerDeliveryMethodAccommodations) {
		this.customerDeliveryMethodAccommodations = customerDeliveryMethodAccommodations;
	}

}
