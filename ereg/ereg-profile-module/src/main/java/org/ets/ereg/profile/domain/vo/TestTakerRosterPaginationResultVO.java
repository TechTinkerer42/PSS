/**
 * 
 */
package org.ets.ereg.profile.domain.vo;



import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.vo.AbstractSearchResultsVO;


public class TestTakerRosterPaginationResultVO extends
		AbstractSearchResultsVO {
  
	private List<TestTakerRosterSearchResultVO> results = new ArrayList<TestTakerRosterSearchResultVO>();

	public List<TestTakerRosterSearchResultVO> getResults() {
		return results;
	}

	
}
