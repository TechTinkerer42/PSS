/*--------------------------------------------------------------------------
 * �2009 Educational Testing Service. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Mar 12, 2010, 11:41:55 AM
 */

package org.ets.ereg.common.business.query.binder;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Name based binding for parameters.
 * 
 * Use a {@link DefaultQueryParameterBinder} if you prefer binding based on
 * indices.
 * 
 * @see DefaultQueryParameterBinder
 * 
 * @author Mangesh Pardeshi
 * @version 1.0
 * @since CRS Distribution Engine - Version 1.0
 */
public class NameBasedParameterBinder implements ParameterBinder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ets.crs.common.dao.hibernate.ParameterBinder#bind(org.hibernate.Query
	 * , java.lang.String[], java.lang.Object[])
	 */
	@Override
	public void bind(Query query, String[] paramNames, Object[] paramValues) {

		if (!ArrayUtils.isEmpty(paramValues) && !ArrayUtils.isEmpty(paramNames)
				&& paramNames.length != paramValues.length)
		{
			throw new IllegalStateException(
					"NameBasedParameterBinder failed due to invalid combination of parameter names and values. Name Array: "
							+ ReflectionToStringBuilder.toString(paramNames) + " Value Array: " + ReflectionToStringBuilder.toString(paramValues));
		}
		if (!ArrayUtils.isEmpty(paramNames) && !ArrayUtils.isEmpty(paramValues)) {
			for (int j = 0; j < paramNames.length; j++) {
				if (paramValues[j] instanceof java.sql.Date)
					query.setParameter(paramNames[j],
							(java.sql.Date) paramValues[j], TemporalType.DATE);
				else if (paramValues[j] instanceof java.sql.Time)
					query.setParameter(paramNames[j],
							(java.sql.Time) paramValues[j], TemporalType.TIME);
				else if (paramValues[j] instanceof java.sql.Timestamp)
					query.setParameter(paramNames[j],
							(java.sql.Timestamp) paramValues[j],
							TemporalType.TIMESTAMP);
				else
					query.setParameter(paramNames[j], paramValues[j]);

			}
		}

	}
	
	
	@Override
	public void bind(Query query, List<String> paramNames,
			List<Object> paramValues) {

		if (paramNames != null && paramValues != null
				&& paramNames.size() != paramValues.size())
		{
			throw new IllegalStateException(
					"NameBasedParameterBinder failed due to invalid combination of parameter names and values. Name Array: "
							+ ReflectionToStringBuilder.toString(paramNames) + " Value Array: " + ReflectionToStringBuilder.toString(paramValues));
		}
	
			for (int j = 0; j < paramNames.size(); j++) {
				if (paramValues.get(j) instanceof java.sql.Date)
					query.setParameter(paramNames.get(j),
							(java.sql.Date) paramValues.get(j), TemporalType.DATE);
				else if (paramValues.get(j) instanceof java.sql.Time)
					query.setParameter(paramNames.get(j),
							(java.sql.Time) paramValues.get(j), TemporalType.TIME);
				else if (paramValues.get(j) instanceof java.sql.Timestamp)
					query.setParameter(paramNames.get(j),
							(java.sql.Timestamp) paramValues.get(j),
							TemporalType.TIMESTAMP);
				else
					query.setParameter(paramNames.get(j), paramValues.get(j));

			}
		

	}

}
