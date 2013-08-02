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
 * Created on Jan 13, 2010, 9:49:03 PM
 */

package org.ets.ereg.common.business.query.binder;

import java.util.List;

import javax.persistence.Query;




/**
 * This interface defines how parameters will be bound to the query.
 * 
 * @see DefaultQueryParameterBinder
 * @see NameBasedParameterBinder
 * 
 * @author Mangesh Pardeshi
 * @version 1.0
 * @since CRS Distribution Engine - Version 1.0
 */
public abstract interface ParameterBinder {

	/**
	 * Implementation can define how parameters are bound when query is formed
	 * 
	 * @param query
	 * @param paramNames
	 * @param paramValues
	 */
	public void bind(Query query, String[] paramNames, Object[] paramValues);
	
	
	public void bind(Query query, List<String> paramNames, List<Object> paramValues);
}
