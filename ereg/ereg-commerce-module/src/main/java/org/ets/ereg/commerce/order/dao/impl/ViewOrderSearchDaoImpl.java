package org.ets.ereg.commerce.order.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.ets.ereg.commerce.order.dao.ViewOrderSearchDao;
import org.ets.ereg.commerce.order.vo.ViewOrderPaginationResultVO;
import org.ets.ereg.commerce.order.vo.ViewOrderSearchResultVO;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.springframework.stereotype.Repository;

@Repository("viewOrderSearchDao")
public class ViewOrderSearchDaoImpl extends AbstractDaoImpl<Object>
		implements ViewOrderSearchDao {

	@Override
	public Class<Object> getEntityClass() {
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ViewOrderPaginationResultVO viewOrderSearch(String strQuery,
			String[] paramNames, Object[] paramValues) {

		ViewOrderPaginationResultVO viewOrderPaginationResultVO = new ViewOrderPaginationResultVO();

		Object object = executeNativeSQL(strQuery, paramNames, paramValues,
				ViewOrderSearchResultVO.class);
		List<ViewOrderSearchResultVO> lisViewOrderSearchResultVO = (List<ViewOrderSearchResultVO>) object;
		viewOrderPaginationResultVO.getResults().addAll(
				lisViewOrderSearchResultVO);

		return viewOrderPaginationResultVO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int viewOrderRecordCount(String strQuery, String[] paramNames,
			Object[] paramValues) {
		int count = 0;
		ParameterBinder parameterBinder = new NameBasedParameterBinder();
		Object obj = executeNativeSQL(strQuery, paramNames, paramValues,
				parameterBinder);
		List list = (List) obj;
		if (list != null && list.size() > 0) {
			BigDecimal recCount = (BigDecimal) list.get(0);
			count = recCount.intValue();
		}

		return count;
	}
}
