/*
 * CustomerContext.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.common.helpers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomerContext
 *
 * @version	1.0 May 3, 2013
 * @author 	Venkat Shanmugam
 * 
 * @history
 * May 3, 2013 VS Initial Creation
 *
 **/
public class CustomerContext {
	
	private String currentProgramCode;
	private String customerId;
	// Program Code, Customer Id
	private Map<String, String> availablePrograms = new HashMap<String, String>();
	private BigDecimal accountBalance;
	private String accountStatus;
	//@TODO description is not customer specific move to a global area.
	private Map<String, String> siteDescriptions = new HashMap<String, String>();
	private String currentProgramShortDescription;
	
	
	public String getCurrentProgramCode() {
		return currentProgramCode;
	}
	public void setCurrentProgramCode(String currentProgramCode) {
		this.currentProgramCode = currentProgramCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Map<String, String> getAvailablePrograms() {
		return availablePrograms;
	}
	public void setAvailablePrograms(Map<String, String> availablePrograms) {
		this.availablePrograms = availablePrograms;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Map<String, String> getSiteDescriptions() {
		return siteDescriptions;
	}
	public void setSiteDescriptions(Map<String, String> siteDescriptions) {
		this.siteDescriptions = siteDescriptions;
	}
	public String getCurrentProgramShortDescription() {
		return currentProgramShortDescription;
	}
	public void setCurrentProgramShortDescription(
			String currentProgramShortDescription) {
		this.currentProgramShortDescription = currentProgramShortDescription;
	}
	
	
	
	
	
}
