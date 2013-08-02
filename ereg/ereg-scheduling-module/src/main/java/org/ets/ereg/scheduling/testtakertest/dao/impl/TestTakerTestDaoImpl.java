package org.ets.ereg.scheduling.testtakertest.dao.impl;

import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.scheduling.testtakertest.dao.TestTakerTestDao;
import org.springframework.stereotype.Repository;

@Repository("testTakerTestDao")
public class TestTakerTestDaoImpl extends AbstractDaoImpl<TestTakerTest> implements TestTakerTestDao {
	
	@Override
	public TestTakerTest saveTestTakerTest(TestTakerTest testTakerTest) {
		return save(testTakerTest);
	}
	
	@Override
	public Class<TestTakerTestImpl> getEntityClass() {
		return TestTakerTestImpl.class;
	}
}
