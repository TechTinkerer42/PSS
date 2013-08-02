package org.ets.ereg.profile.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.AbstractPaginationServiceImpl;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.ets.ereg.profile.csr.domain.querycomposer.TestTakerRosterSearchQueryComposer;
import org.ets.ereg.profile.domain.vo.TestTakerRosterPaginationResultVO;
import org.ets.ereg.profile.domain.vo.TestTakerRosterSearchCriteriaVO;
import org.ets.ereg.profile.model.dao.TestTakerRosterSearchDao;
import org.ets.ereg.profile.service.TestTakerRosterSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("testTakerRosterSearchService")
public class TestTakerRosterSearchServiceImpl
		extends
		AbstractPaginationServiceImpl<TestTakerRosterSearchCriteriaVO, TestTakerRosterPaginationResultVO>
		implements TestTakerRosterSearchService {
	private static Logger logger = LoggerFactory
			.getLogger(TestTakerRosterSearchServiceImpl.class);

	@Resource(name = "testTakerRosterSearchDao")
	protected TestTakerRosterSearchDao testTakerRosterSearchDao;

	@Override
	public void loadDataList(TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo){
		TestTakerRosterSearchQueryComposer queryComposer = new TestTakerRosterSearchQueryComposer(
				testTakerRosterSearchCriteriaVo);
		ComposedQuery cs = queryComposer
				.compose(testTakerRosterSearchCriteriaVo);
	
		logger.debug("Query : {}" , cs.getQuery());
		paginationResultVO = testTakerRosterSearchDao.testTakerRosterSearch(
				cs.getQuery(),
				(String[]) cs.getParameterNames().toArray(
						new String[cs.getParameterNames().size()]), cs
						.getParameterValues().toArray());

	}

	@Override
	public int getTotalRows(
			TestTakerRosterSearchCriteriaVO testTakerRosterSearchCriteriaVo) {
		TestTakerRosterSearchQueryComposer queryComposerCount = new TestTakerRosterSearchQueryComposer(
				testTakerRosterSearchCriteriaVo);
		ComposedQuery countQuery = queryComposerCount
				.composeCount(testTakerRosterSearchCriteriaVo);
		return testTakerRosterSearchDao.testTakerRosterRecordCount(
				countQuery.getQuery(),
				(String[]) countQuery.getParameterNames().toArray(
						new String[countQuery.getParameterNames().size()]),
				countQuery.getParameterValues().toArray());
	}

}
