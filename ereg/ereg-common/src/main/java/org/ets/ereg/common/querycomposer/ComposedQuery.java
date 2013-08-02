/**
 * 
 */
package org.ets.ereg.common.querycomposer;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates everything needed to execute a query that is composed based on a dynamic criteria.
 * This includes, the query itself as a string, parameter names and parameter values. 
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class ComposedQuery {

	private String query = "";
	
	private List<String> parameterNames = new ArrayList<String>();
	
	private List<Object> parameterValues = new ArrayList<Object>();

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the parameterNames
	 */
	public List<String> getParameterNames() {
		return parameterNames;
	}

	/**
	 * @param parameterNames the parameterNames to set
	 */
	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}

	/**
	 * @return the parameterValues
	 */
	public List<Object> getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues the parameterValues to set
	 */
	public void setParameterValues(List<Object> parameterValues) {
		this.parameterValues = parameterValues;
	}
	
}
