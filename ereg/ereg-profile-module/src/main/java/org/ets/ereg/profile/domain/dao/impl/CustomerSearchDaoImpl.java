package org.ets.ereg.profile.domain.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.ets.ereg.profile.domain.vo.CustomerSearchPaginationResultVO;
import org.ets.ereg.profile.domain.vo.CustomerSearchResultVO;
import org.ets.ereg.profile.model.dao.CustomerSearchDao;
import org.springframework.stereotype.Repository;
@Repository("customerSearchDao")
public class CustomerSearchDaoImpl extends AbstractDaoImpl<Object> implements
		CustomerSearchDao {

	@Override
	public CustomerSearchPaginationResultVO customerSearch(String strQuery,
			String[] paramNames, Object[] paramValues) {
		Object object = executeNativeSQL(strQuery, paramNames, paramValues, CustomerSearchResultVO.class);
		List<CustomerSearchResultVO> listCustomerSearchResultVO = (List<CustomerSearchResultVO>) object;
		CustomerSearchPaginationResultVO custSearchPagResultVo = new CustomerSearchPaginationResultVO();
		custSearchPagResultVo.getResults().addAll(listCustomerSearchResultVO);
		return custSearchPagResultVo;
	}

	@Override
	public int customerRecordCount(String strQuery, String[] paramNames,
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

	@Override
	public Class<Object> getEntityClass() {
		return Object.class;
	}

}
