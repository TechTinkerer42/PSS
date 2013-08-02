package org.ets.ereg.common.business.vo;

import java.io.Serializable;

public class SearchParameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numofRows;
	private int pageSize;
	private String sortBy;
	private String des;	
	public int getNumofRows() {
		return numofRows;
	}
	public void setNumofRows(int numofRows) {
		this.numofRows = numofRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

}
