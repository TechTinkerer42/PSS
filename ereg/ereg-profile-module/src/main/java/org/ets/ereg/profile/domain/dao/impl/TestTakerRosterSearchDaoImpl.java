package org.ets.ereg.profile.domain.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.ets.ereg.profile.domain.vo.TestTakerRosterPaginationResultVO;
import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchResultVO;
import org.ets.ereg.profile.model.dao.TestTakerRosterSearchDao;
import org.springframework.stereotype.Repository;

@Repository("testTakerRosterSearchDao")
public class TestTakerRosterSearchDaoImpl extends AbstractDaoImpl<Object>
		implements TestTakerRosterSearchDao {

	@Override
	public Class<Object> getEntityClass() {
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TestTakerRosterPaginationResultVO testTakerRosterSearch(
			String strQuery, String[] paramNames, Object[] paramValues) {

	
		TestTakerRosterPaginationResultVO testTakerRosterPaginationResultVO = new TestTakerRosterPaginationResultVO();

		Object object = executeNativeSQL(strQuery, paramNames, paramValues, TestTakerRosterSearchResultVO.class);
		List<TestTakerRosterSearchResultVO> listAppointmentSearchResultVO = (List<TestTakerRosterSearchResultVO>) object;
		testTakerRosterPaginationResultVO.getResults().addAll(
				listAppointmentSearchResultVO);

		return testTakerRosterPaginationResultVO;
	}

	

	@SuppressWarnings("rawtypes")
	@Override
	public int testTakerRosterRecordCount(String strQuery, String[] paramNames,
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
