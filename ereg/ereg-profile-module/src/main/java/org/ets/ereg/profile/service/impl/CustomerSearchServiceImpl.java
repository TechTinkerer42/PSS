package org.ets.ereg.profile.service.impl;

import javax.annotation.Resource;

import org.ets.ereg.common.business.service.AbstractPaginationServiceImpl;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.ets.ereg.profile.csr.domain.querycomposer.CustomerSearchQueryComposer;
import org.ets.ereg.profile.domain.vo.CustomerSearchCriteriaVO;
import org.ets.ereg.profile.domain.vo.CustomerSearchPaginationResultVO;
import org.ets.ereg.profile.model.dao.CustomerSearchDao;
import org.ets.ereg.profile.service.CustomerSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("customerSearchService")
public class CustomerSearchServiceImpl extends AbstractPaginationServiceImpl<CustomerSearchCriteriaVO, CustomerSearchPaginationResultVO> implements CustomerSearchService {
	private static Logger logger = LoggerFactory.getLogger(CustomerSearchServiceImpl.class);

	@Resource(name = "customerSearchDao")
	protected CustomerSearchDao customerSearchDao;


	@Override
	public void loadDataList(CustomerSearchCriteriaVO searchCriteria) {
		
		CustomerSearchQueryComposer queryComposer = new CustomerSearchQueryComposer(searchCriteria);
		ComposedQuery cs = queryComposer.compose(searchCriteria);
		logger.debug("Query : {}" , cs.getQuery());
		// TODO Auto-generated method stub
		paginationResultVO =customerSearchDao.customerSearch(cs.getQuery(),(String[]) cs.getParameterNames().toArray(new String[cs.getParameterNames().size()]), cs.getParameterValues().toArray());
		
	}

	@Override
	public int getTotalRows(CustomerSearchCriteriaVO searchCriteria) {
		CustomerSearchQueryComposer queryComposerCount = new CustomerSearchQueryComposer(searchCriteria);
		ComposedQuery countQuery = queryComposerCount.composeCount(searchCriteria);
		return customerSearchDao.customerRecordCount(countQuery.getQuery(),(String[]) countQuery.getParameterNames().toArray(new String[countQuery.getParameterNames().size()]),countQuery.getParameterValues().toArray()); 
	}

}
