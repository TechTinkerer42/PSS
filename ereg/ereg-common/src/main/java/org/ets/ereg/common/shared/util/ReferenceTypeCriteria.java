package org.ets.ereg.common.shared.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ReferenceTypeCriteria {

	public static final String ORDER_BY_CODE_COLUMN = "code";
	public static final String ORDER_BY_DESCRIPTION_COLUMN = "description";
	public static final String ORDER_BY_SEQUENCE_COLUMN = "displaySequence";
	private String nativeSql;
	private String[] nativeSqlParamNames;
	private Object[] nativeSqlParamValues;

	/**
	 * Customized column ordering (allows specifying column name and ordering -
	 * asc or desc)
	 */
	private List<ReferenceTypeOrderBy> orderbyList;

	public static class ReferenceTypeOrderBy {

		private String orderByAttribute;
		private boolean ascending;

		/**
		 * @return the orderByAttribute
		 */
		public String getOrderByAttribute() {
			return orderByAttribute;
		}

		/**
		 * @param orderByAttribute
		 *            the orderByAttribute to set
		 */
		public void setOrderByAttribute(String orderByAttribute) {
			this.orderByAttribute = orderByAttribute;
		}

		/**
		 * @return the ascending
		 */
		public boolean isAscending() {
			return ascending;
		}

		/**
		 * @param ascending
		 *            the ascending to set
		 */
		public void setAscending(boolean ascending) {
			this.ascending = ascending;
		}
	}

	/**
	 *
	 */
	public ReferenceTypeCriteria() {
		ReferenceTypeOrderBy defaultOrdering = new ReferenceTypeOrderBy();
		defaultOrdering.setAscending(true);
		defaultOrdering.setOrderByAttribute(ORDER_BY_DESCRIPTION_COLUMN);
		List<ReferenceTypeOrderBy> defaultOrderByList = new ArrayList<ReferenceTypeOrderBy>();
		defaultOrderByList.add(defaultOrdering);
		setOrderbyList(defaultOrderByList);
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		ReferenceTypeOrderBy ordering = new ReferenceTypeOrderBy();
		ordering.setAscending(true);
		ordering.setOrderByAttribute(orderBy);
		List<ReferenceTypeOrderBy> orderByList = new ArrayList<ReferenceTypeOrderBy>();
		orderByList.add(ordering);
		setOrderbyList(orderByList);
	}

	/**
	 * @return the orderbyList
	 */
	public List<ReferenceTypeOrderBy> getOrderbyList() {
		return orderbyList;
	}

	/**
	 * @param orderbyList
	 *            the orderbyList to set
	 */
	public void setOrderbyList(List<ReferenceTypeOrderBy> orderbyList) {
		this.orderbyList = orderbyList;
	}

	/**
	 * @return the nativeSql
	 */
	public String getNativeSql() {
		return nativeSql;
	}

	/**
	 * @param nativeSql
	 *            the nativeSql to set
	 */
	public void setNativeSql(String nativeSql) {
		this.nativeSql = nativeSql;
	}

	/**
	 * @return the nativeSqlParamNames
	 */
	public String[] getNativeSqlParamNames() {
		return nativeSqlParamNames;
	}

	/**
	 * @param nativeSqlParamNames
	 *            the nativeSqlParamNames to set
	 */
	public void setNativeSqlParamNames(String[] nativeSqlParamNames) {
		if(nativeSqlParamNames != null)
		{
			this.nativeSqlParamNames = nativeSqlParamNames.clone();
		}
	}

	/**
	 * @return the nativeSqlParamValues
	 */
	public Object[] getNativeSqlParamValues() {
		return nativeSqlParamValues;
	}

	/**
	 * @param nativeSqlParamValues
	 *            the nativeSqlParamValues to set
	 */
	public void setNativeSqlParamValues(Object[] nativeSqlParamValues) {
		if(nativeSqlParamValues != null)
		{
			this.nativeSqlParamValues = nativeSqlParamValues.clone();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (!StringUtils.isEmpty(getNativeSql())) {
			return getNativeSql();
		} else {
			StringBuffer hashString = new StringBuffer();
			// ordering on more than one attribute
			if (this.getOrderbyList() != null) {
				for (ReferenceTypeOrderBy orderBy : this.getOrderbyList()) {
					hashString.append(orderBy.getOrderByAttribute());
					hashString.append("-");
					hashString.append(orderBy.isAscending() ? "ASC" : "DESC");
				}
			}
			return hashString.toString();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
