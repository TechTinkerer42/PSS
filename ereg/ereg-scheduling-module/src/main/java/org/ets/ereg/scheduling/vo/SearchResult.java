package org.ets.ereg.scheduling.vo;

import java.util.List;

public class SearchResult<T> {
	private Long count;
	private boolean hasMoreResults;
	private boolean hasErrors;
	private List<String> errors;
	private boolean hasWarnings;
	private List<String> warnings;
	private List<T> searchedElements;
	private String link;
	private String idColumnName;
	
	public SearchResult(){
		count = 0l;
		hasMoreResults = false;
		hasErrors = false;
		hasWarnings = false;
		errors = null;
		warnings = null;
		searchedElements = null;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public boolean isHasMoreResults() {
		return hasMoreResults;
	}
	
	public void setHasMoreResults(boolean hasMoreResults) {
		this.hasMoreResults = hasMoreResults;
	}
	
	public boolean isHasErrors() {
		return hasErrors;
	}
	
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public boolean isHasWarnings() {
		return hasWarnings;
	}
	
	public void setHasWarnings(boolean hasWarnings) {
		this.hasWarnings = hasWarnings;
	}
	
	public List<String> getWarnings() {
		return warnings;
	}
	
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}
	
	public List<T> getSearchedElements() {
		return searchedElements;
	}
	
	public void setSearchedElements(List<T> searchedElements) {
		this.searchedElements = searchedElements;
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
}
