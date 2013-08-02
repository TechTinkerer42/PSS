package org.ets.ereg.common.web.form;

import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Base class for all search screens that need pagination. 
 * All common search (across all search screens) criteria fields will be defined by this class. 
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */


public class AbstractSearchCriteriaForm extends AbstractForm {
	
	
	private int totalRows;
	private int rowperPage = 5;//default 
	private int pageNo;
	private String action;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	

	public int getRowperPage() {
		return rowperPage;
	}

	public void setRowperPage(int rowperPage) {
		this.rowperPage = rowperPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
	
	
	
}
