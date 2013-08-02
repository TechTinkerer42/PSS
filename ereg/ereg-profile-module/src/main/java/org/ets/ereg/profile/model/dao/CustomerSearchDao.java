package org.ets.ereg.profile.model.dao;

import org.ets.ereg.profile.domain.vo.CustomerSearchPaginationResultVO;

public interface CustomerSearchDao {
	
	public CustomerSearchPaginationResultVO customerSearch(String strQuery,String[] paramNames, Object[] paramValues);
	public int customerRecordCount(String strQuery, String[] paramNames,Object[] paramValues);

}
