/*
 * IHttpPostService.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.scheduling.spi.ssa.service;

import java.util.Map;

/**
 * IHttpPostService
 *
 * @version	1.0 Dec 14, 2010
 * @author 	Rukmaiah Bommakanti
 * 
 * @history
 * Dec 14, 2010 RB Initial Creation
 *
 **/
public interface IHttpPostService {

	/**
	 * Sends request to the server as a post method.
	 * 
	 * @param protocol String http/https
	 * @param serverPath String url to send the request 
	 * @param params Map<String, String> request param name and values
	 * @param headers Map<String, String> request header names and values
	 * @param reqParamNamesInclude boolean flag for including the param name in the request
	 * 
	 * @return response String response from the server
	 * @throws Exception exception if any error occurs
	 */
	public String doPost(String protocol, String serverPath, Map<String, String> params, Map<String, String> headers, boolean reqParamNamesInclude) throws Exception ;

	/**
	 * Sends request to the server as a post method.
	 * 
	 * @param protocol String http/https
	 * @param serverPath String url to send the request 
	 * @param params Map<String, String> request param name and values
	 * @param headers Map<String, String> request header names and values
	 * 
	 * @return response String response from the server
	 * @throws Exception exception if any error occurs
	 */
	public String doPost(String protocol, String serverPath, Map<String, String> params, Map<String, String> headers) throws Exception;

}
