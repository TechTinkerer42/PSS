package org.ets.ereg.profile.model.dao;

import org.ets.ereg.profile.domain.vo.TestTakerRosterPaginationResultVO;

public interface TestTakerRosterSearchDao {

	public TestTakerRosterPaginationResultVO testTakerRosterSearch(String strQuery,
			String[] paramNames, Object[] paramValues);
	
	public int testTakerRosterRecordCount(String strQuery, String[] paramNames, Object[] paramValues);

}
