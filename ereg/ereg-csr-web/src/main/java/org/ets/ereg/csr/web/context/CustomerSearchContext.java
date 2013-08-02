package org.ets.ereg.csr.web.context;

import org.ets.ereg.profile.domain.vo.CustomerSearchCriteriaVO;

public class CustomerSearchContext {
	
	public static final String CUSTOMER_SEARCH_CRITERA = "CustomerSearchCriteria";
	
	private CustomerSearchCriteriaVO searchCriteriaVO;

	public CustomerSearchCriteriaVO getSearchCriteriaVO() {
		return searchCriteriaVO;
	}

	public void CustomerSearchCriteriaVO(CustomerSearchCriteriaVO searchCriteriaVO) {
		this.searchCriteriaVO = searchCriteriaVO;
	}
	
	

}
