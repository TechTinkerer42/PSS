package org.ets.ereg.scheduling.testtakertest.dao;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;

public interface TestTakerTestDao extends Dao<TestTakerTest> {
	TestTakerTest saveTestTakerTest(TestTakerTest testTakerTest);
}
