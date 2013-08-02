package org.ets.ereg.scheduling.service;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.vo.SearchResult;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.ets.ereg.scheduling.vo.TestCenterSearchResultEntry;

public interface TestCenterService {
	SearchResult<TestCenterSearchResultEntry> findTestCenters(TestCenterSearchCriteria searchCriteria, int offset, int count);
	SearchResult<TestCenterSearchResultEntry> findTestCenters(TestCenterSearchCriteria searchCriteria);
	TestCenter readTestCenterById(Long id);
	List<TestCenter> getAssociatedTestCentersForAdmin(Long adminId);
	String getAddress(ETSAddress address);
	List<TestCenter> getTestCentersByAgency(Long agencyId);
}
