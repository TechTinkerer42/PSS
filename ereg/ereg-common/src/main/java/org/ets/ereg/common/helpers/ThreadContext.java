/*
 * ThreadContext.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.common.helpers;

/**
 * ThreadContext
 *
 * @version	1.0 May 3, 2013
 * @author 	Venkat Shanmugam
 * 
 * @history
 * May 3, 2013 VS Initial Creation
 *
 **/
public class ThreadContext {
	
	private static final ThreadLocal<ExecutionContext> threadLocal = new ThreadLocal<ExecutionContext>();

	private static ExecutionContext getExecutionContext() {
		ExecutionContext ecxt = threadLocal.get();
		if( null == ecxt ) {
			ecxt = new ExecutionContext();
			threadLocal.set(ecxt);
		}
		return ecxt;
	}
	
	public static CustomerContext getCustomerContext() {
		CustomerContext cctx = getExecutionContext().getCustomer();
		if( null == cctx ) {
			getExecutionContext().setCustomer(new CustomerContext());
		}
		
		return getExecutionContext().getCustomer();
	}

	public static UserContext getUserContext() {
		UserContext uctx = getExecutionContext().getUser();
		if( null == uctx ) {
			getExecutionContext().setUser(new UserContext());
		}
		
		return getExecutionContext().getUser();
	}
	
	
	
	public static void setCustomerContext(CustomerContext cctx) {
		getExecutionContext().setCustomer(cctx);
	}
	
	public static void setUserContext(UserContext uctx) {
		getExecutionContext().setUser(uctx);
	}

}
