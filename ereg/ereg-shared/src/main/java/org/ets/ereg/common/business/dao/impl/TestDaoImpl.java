package org.ets.ereg.common.business.dao.impl;

import org.ets.ereg.common.business.dao.TestDao;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.test.TestImpl;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDaoImpl extends AbstractDaoImpl<Test> implements TestDao  {

	@Override
	public Class<TestImpl> getEntityClass() {
		// TODO Auto-generated method stub
		return TestImpl.class;
	}

	@Override
	public Test getTest(Long testId){

		return findByPrimaryKey(testId);
	}

}
