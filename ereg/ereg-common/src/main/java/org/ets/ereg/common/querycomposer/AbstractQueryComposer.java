package org.ets.ereg.common.querycomposer;

import java.sql.Date;
import java.sql.Timestamp;

import org.ets.ereg.common.vo.AbstractSearchCriteriaVO;


/**
 * Query Composer to compose a query based on dynamic criteria. Implementations 
 * must provide the actual composition logic. This class only has any common reusable logic
 * to compose such queries.
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public abstract class AbstractQueryComposer {

	private ComposedQuery composedQuery;
	
	private AbstractSearchCriteriaVO criteria;
	
	/**
	 * Must create composer with a criteria
	 */
	public AbstractQueryComposer(AbstractSearchCriteriaVO criteria) {
		setCriteria(criteria);
		setComposedQuery(new ComposedQuery());
	}

 	/**
 	 * Compose a query based on a dynamic search criteria
 	 * 
 	 * @param criteria
 	 * @return
 	 */
	public abstract ComposedQuery compose(AbstractSearchCriteriaVO criteria);
	
	/**
	 * Compose a count query 
	 * 
	 * @param criteria
	 * @return
	 */
	public abstract ComposedQuery composeCount(AbstractSearchCriteriaVO criteria);
	
	
	protected void addParameterName(String name) {
		
		getComposedQuery().getParameterNames().add(name);
	}
	
	protected void addParameterValue(Object value) {
		
		getComposedQuery().getParameterValues().add(value);
	}
	
	protected void addParameter(String name, Object value) {
		
		addParameterName(name);
		addParameterValue(value);
	}
	protected void appendGreaterThanEqual(StringBuffer where,String sqlName, String name, Date value) {
		
	     where.append(sqlName + " >= :" + name);
	     addParameter(name, value);
	}
	
	protected void appendGreaterThanEqual(StringBuffer where,String sqlName, String name, Timestamp value) {
		
	     where.append(sqlName + " >= :" + name);
	     addParameter(name, value);
	}
	
    protected void appendLessThanEqual(StringBuffer where,String sqlName,String name, Date value) {
		
	     where.append(sqlName + " <= :" + name);
	     addParameter(name, value);
	}
    
    protected void appendLessThanEqual(StringBuffer where,String sqlName,String name, Timestamp value) {
		
	     where.append(sqlName + " <= :" + name);
	     addParameter(name, value);
	}
	
	protected void appendLike(StringBuffer where,String sqlName, String name, String value){
		where.append(sqlName + " LIKE :" + name);
		addParameter(name, value+"%");
	}
	
	/**
	 * @param string
	 * @param appointmentId
	 */
	protected void appendEquals(StringBuffer where, String name, Object value) {

		where.append(name + " = :" + name);
		addParameter(name, value);
	}
	
	
	
	protected void appendEquals(StringBuffer where,String sqlName, String name, String value){
		where.append(sqlName + " =  :" + name);
		addParameter(name, value);
	}
	
	
	protected void appendEquals(StringBuffer where, String sqlName, String name, Object value) {

		where.append(sqlName + " = :" + name);
		addParameter(name, value);
	}
	


	/**
	 * @return the criteria
	 */
	public AbstractSearchCriteriaVO getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(AbstractSearchCriteriaVO criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the composedQuery
	 */
	public ComposedQuery getComposedQuery() {
		return composedQuery;
	}

	/**
	 * @param composedQuery the composedQuery to set
	 */
	public void setComposedQuery(ComposedQuery composedQuery) {
		this.composedQuery = composedQuery;
	}
	
	
}
