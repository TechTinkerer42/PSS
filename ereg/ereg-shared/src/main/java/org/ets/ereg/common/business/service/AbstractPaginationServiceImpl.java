package org.ets.ereg.common.business.service;

import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;
import org.ets.ereg.common.vo.AbstractSearchResultsVO;

/**
 * 
 * 
 */
public abstract class AbstractPaginationServiceImpl<SearchCriteria, PaginationResultVO> implements
		PaginationService<SearchCriteria, PaginationResultVO> {

	protected PaginationResultVO paginationResultVO;
	private static final int PAGE_RANGE = 10;

	private void page(SearchCriteria searchCriteria, boolean defaultSort, String sortFieldAttribute,
			boolean sortAscending) {
		AbstractSearchCriteriaVO abstractSearchCriteriaVO = (AbstractSearchCriteriaVO) searchCriteria;
		int totalRowsPerPage = (abstractSearchCriteriaVO.getRowsperPage() * abstractSearchCriteriaVO.getPageNo());
		int firstRow = ((abstractSearchCriteriaVO.getRowsperPage() * abstractSearchCriteriaVO.getPageNo()) - abstractSearchCriteriaVO
				.getRowsperPage());
		abstractSearchCriteriaVO.setFirstRow(firstRow);
		abstractSearchCriteriaVO.setTotalRowsPerPage(totalRowsPerPage);

		loadDataList(searchCriteria); //
	}

	// abstract method
	public abstract void loadDataList(SearchCriteria searchCriteria);

	public abstract int getTotalRows(SearchCriteria searchCriteria);

	public PaginationResultVO getDataList(SearchCriteria searchCriteria) {

		page(searchCriteria, false, null, false);
		int totalRows = getTotalRows(searchCriteria);
		AbstractSearchCriteriaVO abstractSearchCriteriaVO = (AbstractSearchCriteriaVO) searchCriteria;
		int pageRange = PAGE_RANGE;
		// Set currentPage, totalPages and pages.
		int currentPage = (totalRows / abstractSearchCriteriaVO.getRowsperPage())
				- ((totalRows - abstractSearchCriteriaVO.getFirstRow()) / abstractSearchCriteriaVO.getRowsperPage())
				+ 1;
		int totalPages = (totalRows / abstractSearchCriteriaVO.getRowsperPage())
				+ ((totalRows % abstractSearchCriteriaVO.getRowsperPage() != 0) ? 1 : 0);
		int pagesLength = Math.min(pageRange, totalPages);
		Integer[] pages = new Integer[pagesLength];

		// firstPage must be greater than 0 and lesser than
		// totalPages-pageLength.
		int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

		// Create pages (page numbers for page links).
		for (int i = 0; i < pagesLength; i++) {
			pages[i] = ++firstPage;
		}
		AbstractSearchResultsVO abstractSearchResultsVO = (AbstractSearchResultsVO) paginationResultVO;
		abstractSearchResultsVO.setCurrentPage(currentPage);
		abstractSearchResultsVO.setTotalsPages(totalPages);
		abstractSearchResultsVO.setTotalRecordCount(totalRows);
		abstractSearchResultsVO.setPages(pages);
		abstractSearchResultsVO.setRowsPerPage(abstractSearchCriteriaVO.getRowsperPage());

		return paginationResultVO;
	}

}
