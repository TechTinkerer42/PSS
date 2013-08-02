package org.ets.ereg.commerce.order.dao;

import org.ets.ereg.commerce.order.vo.ViewOrderPaginationResultVO;

public interface ViewOrderSearchDao {

	public ViewOrderPaginationResultVO viewOrderSearch(String strQuery,
			String[] paramNames, Object[] paramValues);
	
	public int viewOrderRecordCount(String strQuery, String[] paramNames, Object[] paramValues);

}
