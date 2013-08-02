

/**
 * 
 * 
 * @author Devaraj Prasad
 * 
 * @version 1.0
 * 
 */
package org.ets.ereg.common.web.controller.order.form;

import org.ets.ereg.common.web.form.AbstractSearchCriteriaForm;
import org.ets.ereg.common.web.util.Constant;

public class ViewOrderSearchForm extends AbstractSearchCriteriaForm {

	private Long customerId;
	private String viewOrder = Constant.RECENT_ORDER;
	
	
	

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
