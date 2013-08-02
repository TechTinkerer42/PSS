package org.ets.ereg.scheduling.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.ets.ereg.scheduling.vo.TestCenterSearchResultEntry;

public interface TestCenterDao {
	TestCenter readTestCenterById(Long id);
	Long getTestCenterSearchCount(TestCenterSearchCriteria searchCriteria);
	List<TestCenterSearchResultEntry> getTestCenterSearchResult(TestCenterSearchCriteria searchCriteria, int offset, int count);
	List<TestCenter> getAssociatedTestCentersForAdmin(Long adminId);
	List<TestCenter> getTestCentersByAgency(Long agencyId);
}
