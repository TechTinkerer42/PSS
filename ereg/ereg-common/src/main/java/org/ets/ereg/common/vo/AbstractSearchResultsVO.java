/**
 * 
 */
package org.ets.ereg.common.vo;

import org.ets.ereg.common.vo.AbstractVO;



public class AbstractSearchResultsVO extends AbstractVO {

	private int totalRecordCount;
	private int currentPage;
	private int totalsPages;
	private Integer[] pages;
	private int rowsPerPage;
	
	
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalsPages() {
		return totalsPages;
	}
	public void setTotalsPages(int totalsPages) {
		this.totalsPages = totalsPages;
	}
	public Integer[] getPages() {
		return pages;
	}
	public void setPages(Integer[] pages) {
		if(pages != null)
		{
			this.pages = pages.clone();
		}
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	
	
}
