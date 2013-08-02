package org.ets.ereg.profile.domain.vo;

import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.vo.AbstractSearchResultsVO;

public class CustomerSearchPaginationResultVO extends AbstractSearchResultsVO {
	private List<CustomerSearchResultVO> results = new ArrayList<CustomerSearchResultVO>();

	public List<CustomerSearchResultVO> getResults() {
		return results;
	}

}
