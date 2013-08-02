package org.ets.ereg.profile.accommodation.vo;

import java.util.Collections;
import java.util.List;

public class CustomerDeliveryMethodAccommodationsVO {
	private String deliveryMethod;
	
	List<CustomerAccommodationVO> customerAccommodations=Collections.emptyList();
	
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	
	public int getAccommodationCount(){
		return 	customerAccommodations.size();
	}
	public List<CustomerAccommodationVO> getCustomerAccommodations() {
		return customerAccommodations;
	}
	public void setCustomerAccommodations(
			List<CustomerAccommodationVO> customerAccommodations) {
		this.customerAccommodations = customerAccommodations;
	}
	
	public String getDeliveryMethodCode(){
		
		if(!customerAccommodations.isEmpty())
		{
			return customerAccommodations.get(0).getDeliveryMethodCode();
		}
		
		return null;
	}
	

}
