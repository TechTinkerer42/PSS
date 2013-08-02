package org.ets.ereg.common.business.dao;

import org.ets.ereg.domain.interfaces.model.test.Test;

public interface TestDao extends Dao<Test> {
	public Test getTest(Long testId);
}
