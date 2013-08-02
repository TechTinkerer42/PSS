package org.ets.ereg.common.business.vo;

import java.io.Serializable;
import java.util.List;

public class CustomerResultsVO implements Serializable {
	private Long count;
	private List<CustomerVO> customers;
	private boolean errors;
	private List<String> errorList;
	private String link;
	private String idColumnName;
	private boolean moreResults;
	private List<String> warningList;
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<CustomerVO> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerVO> customers) {
		this.customers = customers;
	}
	public boolean isErrors() {
		return errors;
	}
	public void setErrors(boolean errors) {
		this.errors = errors;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIdColumnName() {
		return idColumnName;
	}
	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}
	public boolean isMoreResults() {
		return moreResults;
	}
	public void setMoreResults(boolean moreResults) {
		this.moreResults = moreResults;
	}
	public List<String> getWarningList() {
		return warningList;
	}
	public void setWarningList(List<String> warningList) {
		this.warningList = warningList;
	}

}
