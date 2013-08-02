package org.ets.ereg.commerce.order.service;

import javax.annotation.Resource;

import org.ets.ereg.commerce.domain.querycomposer.ViewOrderSearchQueryComposer;
import org.ets.ereg.commerce.order.dao.ViewOrderSearchDao;
import org.ets.ereg.commerce.order.vo.ViewOrderPaginationResultVO;
import org.ets.ereg.commerce.order.vo.ViewOrderSearchCriteriaVO;
import org.ets.ereg.common.business.service.AbstractPaginationServiceImpl;
import org.ets.ereg.common.querycomposer.ComposedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("etsViewOrderSearchService")
public class ETSViewOrderSearchServiceImpl
		extends
		AbstractPaginationServiceImpl<ViewOrderSearchCriteriaVO, ViewOrderPaginationResultVO>
		implements ETSViewOrderSearchService {
	private static Logger logger = LoggerFactory
			.getLogger(ETSViewOrderSearchServiceImpl.class);

	@Resource(name = "viewOrderSearchDao")
	protected ViewOrderSearchDao viewOrderSearchDao;

	@Override
	public void loadDataList(ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo){
		ViewOrderSearchQueryComposer queryComposer = new ViewOrderSearchQueryComposer(
				viewOrderSearchCriteriaVo);
		ComposedQuery cs = queryComposer
				.compose(viewOrderSearchCriteriaVo);
	
		logger.debug("Query : {}" , cs.getQuery());
		paginationResultVO = viewOrderSearchDao.viewOrderSearch(
				cs.getQuery(),
				(String[]) cs.getParameterNames().toArray(
						new String[cs.getParameterNames().size()]), cs
						.getParameterValues().toArray());

	}

	@Override
	public int getTotalRows(
			ViewOrderSearchCriteriaVO viewOrderSearchCriteriaVo) {
		/*ViewOrderSearchQueryComposer queryComposerCount = new ViewOrderSearchQueryComposer(
				viewOrderSearchCriteriaVo);
		ComposedQuery countQuery = queryComposerCount
				.composeCount(viewOrderSearchCriteriaVo);
		return viewOrderSearchDao.viewOrderRecordCount(
				countQuery.getQuery(),
				(String[]) countQuery.getParameterNames().toArray(
						new String[countQuery.getParameterNames().size()]),
				countQuery.getParameterValues().toArray());*/
		return 0;
	}

}
