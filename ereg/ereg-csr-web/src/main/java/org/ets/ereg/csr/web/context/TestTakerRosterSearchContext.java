package org.ets.ereg.csr.web.context;

import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchCriteriaVO;

public class TestTakerRosterSearchContext {
	
	public static final String TEST_TAKER_ROSTER_SEARCH_CRITERA = "TestTakerSearchCriteria";
	
	private TestTakerRosterSearchCriteriaVO searchCriteriaVO;

	public TestTakerRosterSearchCriteriaVO getSearchCriteriaVO() {
		return searchCriteriaVO;
	}

	public void setSearchCriteriaVO(TestTakerRosterSearchCriteriaVO searchCriteriaVO) {
		this.searchCriteriaVO = searchCriteriaVO;
	}
	
	

}
