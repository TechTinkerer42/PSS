package org.ets.ereg.common.web.context;

import org.ets.ereg.commerce.order.vo.ViewOrderSearchCriteriaVO;

public class ViewOrderSearchContext {
	
	public static final String VIEW_ORDER_SEARCH_CRITERA = "ViewOrderSearchCriteria";
	
	private ViewOrderSearchCriteriaVO searchCriteriaVO;

	public ViewOrderSearchCriteriaVO getSearchCriteriaVO() {
		return searchCriteriaVO;
	}

	public void setSearchCriteriaVO(ViewOrderSearchCriteriaVO searchCriteriaVO) {
		this.searchCriteriaVO = searchCriteriaVO;
	}
	
	

}
