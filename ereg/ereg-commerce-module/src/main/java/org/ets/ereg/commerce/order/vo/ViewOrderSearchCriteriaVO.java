/**
 * 
 */
package org.ets.ereg.commerce.order.vo;

import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;


/**
 * 
 * 
 * @author Devaraj Prasad
 * 
 * @version 1.0
 * 
 */
public class ViewOrderSearchCriteriaVO extends
		AbstractSearchCriteriaVO {
	
	private Long customerId;
	private String viewOrder;
	
	

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(String viewOrder) {
		this.viewOrder = viewOrder;
	}
	
	

	
}
