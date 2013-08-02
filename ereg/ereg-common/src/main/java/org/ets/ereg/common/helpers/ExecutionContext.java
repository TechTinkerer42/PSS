/*
 * ExecutionContext.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.common.helpers;

/**
 * ExecutionContext
 *
 * @version	1.0 May 3, 2013
 * @author 	Venkat Shanmugam
 * 
 * @history
 * May 3, 2013 VS Initial Creation
 *
 **/
public class ExecutionContext {
	
	private UserContext user;
	private CustomerContext customer;
	
	
	public UserContext getUser() {
		return user;
	}
	public void setUser(UserContext user) {
		this.user = user;
	}
	public CustomerContext getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerContext customer) {
		this.customer = customer;
	}
	
	
}
