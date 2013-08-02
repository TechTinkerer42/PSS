/**
 * 
 */
package org.ets.ereg.common.vo;

import org.ets.ereg.common.vo.AbstractVO;


/**
 * 
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class AbstractSearchCriteriaVO extends AbstractVO {

	private int firstRow;
	private int totalRowsPerPage;
	private int pageNo;
	private int rowsperPage;
	
	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getTotalRowsPerPage() {
		return totalRowsPerPage;
	}

	public void setTotalRowsPerPage(int totalRowsPerPage) {
		this.totalRowsPerPage = totalRowsPerPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRowsperPage() {
		return rowsperPage;
	}

	public void setRowsperPage(int rowsperPage) {
		this.rowsperPage = rowsperPage;
	}

	
	

	
}
