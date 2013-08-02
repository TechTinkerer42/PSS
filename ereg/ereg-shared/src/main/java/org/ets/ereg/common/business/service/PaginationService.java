
package org.ets.ereg.common.business.service;


/**
 * 
 * 

 */
public interface PaginationService<SearchCriteria,PaginationResultVO> {

	public PaginationResultVO getDataList(SearchCriteria Results);

}
