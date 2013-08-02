
/**
 * 
 * 
 * @author Devaraj Prasad
 * 
 * @version 1.0
 * 
 */
package org.ets.ereg.commerce.order.vo;





import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.vo.AbstractSearchResultsVO;


public class ViewOrderPaginationResultVO extends
		AbstractSearchResultsVO {
  
	private List<ViewOrderSearchResultVO> results = new ArrayList<ViewOrderSearchResultVO>();

	public List<ViewOrderSearchResultVO> getResults() {
		return results;
	}

	public void setResults(List<ViewOrderSearchResultVO> results) {
		this.results = results;
	}

	
	
}
