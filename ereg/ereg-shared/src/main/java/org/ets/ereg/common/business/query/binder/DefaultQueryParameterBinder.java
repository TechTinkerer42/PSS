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
 * Created on Jan 13, 2010, 9:34:56 PM
 */

package org.ets.ereg.common.business.query.binder;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;

/**
 * Default query binder. Binds parameters using indices. This should suffice in
 * most cases.
 * 
 * Use a {@link NameBasedParameterBinder} if parameter binding should be done
 * using names instead
 * 
 * @see NameBasedParameterBinder
 * 
 * @author Mangesh Pardeshi
 * @version 1.0
 * @since CRS Distribution Engine - Version 1.0
 */
public class DefaultQueryParameterBinder implements ParameterBinder {

	/**
	 * Binds parameters using indices..parameter names are ignored
	 */
	public void bind(Query query, String[] paramNames, Object[] paramValues) {

		if (!ArrayUtils.isEmpty(paramValues)) {
			for (int j = 0; j < paramValues.length; j++) {
				if (paramValues[j] instanceof java.sql.Date)
					query.setParameter(j, (java.sql.Date) paramValues[j],
							TemporalType.DATE);
				else if (paramValues[j] instanceof java.sql.Time)
					query.setParameter(j, (java.sql.Time) paramValues[j],
							TemporalType.TIME);
				else if (paramValues[j] instanceof java.sql.Timestamp)
					query.setParameter(j, (java.sql.Timestamp) paramValues[j],
							TemporalType.TIMESTAMP);
				else
					query.setParameter(j, paramValues[j]);

			}
		}
	}
	
	
	@Override
	public void bind(Query query, List<String> paramNames,
			List<Object> paramValues) {

	
			for (int j = 0; j < paramValues.size(); j++) {
				if (paramValues.get(j) instanceof java.sql.Date)
					query.setParameter(j, (java.sql.Date) paramValues.get(j),
							TemporalType.DATE);
				else if (paramValues.get(j) instanceof java.sql.Time)
					query.setParameter(j, (java.sql.Time) paramValues.get(j),
							TemporalType.TIME);
				else if (paramValues.get(j) instanceof java.sql.Timestamp)
					query.setParameter(j, (java.sql.Timestamp) paramValues.get(j),
							TemporalType.TIMESTAMP);
				else
					query.setParameter(j, paramValues.get(j));

			}

	}
}
