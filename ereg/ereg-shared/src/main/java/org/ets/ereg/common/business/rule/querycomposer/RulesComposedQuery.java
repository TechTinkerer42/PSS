/**
 * 
 */
package org.ets.ereg.common.business.rule.querycomposer;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates everything needed to execute a query that is composed based on a
 * dynamic criteria. This includes, the query itself as a string, parameter
 * names and parameter values.
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class RulesComposedQuery {

	private String query = "SELECT DISTINCT   D " ;
			

	private String selectQuery ="";
	
	private String dateSelectQuery = " FROM ( " + " SELECT TO_DATE(:STARTDATE1, 'MM/DD/YYYY') - 1 + ROWNUM AS D "
			+ " FROM ALL_OBJECTS  "
			+ " WHERE TO_DATE(:STARTDATE2, 'MM/DD/YYYY') - 1 + ROWNUM <= TO_DATE(:ENDDATE, 'MM/DD/YYYY')  "
			+ " ) DATES ";
	
	private String ruleQuery = "";

	private String orderBy = " ORDER BY DATES.D ";
	
	

	private List<String> parameterNames = new ArrayList<String>();

	private List<Object> parameterValues = new ArrayList<Object>();

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query.concat(getSelectQuery() + getDateSelectQuery() + getRuleQuery()) ;
	}

	/**
	 * @param query
	 *            the query to set
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
	 * @param parameterNames
	 *            the parameterNames to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterNames.add(parameterName);
	}

	/**
	 * @param parameterNames
	 *            the parameterNames to set
	 */
	public void setParameterNamesAll(List<String> parameterNames) {
		this.parameterNames.addAll(parameterNames);
	}

	/**
	 * @return the parameterValues
	 */
	public List<Object> getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues
	 *            the parameterValues to set
	 */
	public void setParameterValue(Object parameterValue) {
		this.parameterValues.add(parameterValue);
	}

	/**
	 * @param parameterValues
	 *            the parameterValues to set
	 */
	public void setParameterValuesAll(List<Object> parameterValues) {
		this.parameterValues.addAll(parameterValues);
	}

	public void setParameterNameVlue(String parameterName, Object parameterValue) {
		this.parameterNames.add(parameterName);
		this.parameterValues.add(parameterValue);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	

	public String getDateSelectQuery() {
		return dateSelectQuery;
	}

	public void setDateSelectQuery(String dateSelectQuery) {
		this.dateSelectQuery = dateSelectQuery;
	}

	public String getSelectQuery() {
		return selectQuery;
	}

	public void setSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
	}

	public String getRuleQuery() {
		return ruleQuery;
	}

	public void setRuleQuery(String ruleQuery) {
		this.ruleQuery = ruleQuery;
	}
	
	
	
	

}
