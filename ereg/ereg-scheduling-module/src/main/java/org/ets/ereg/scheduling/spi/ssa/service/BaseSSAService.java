/*
 * BaseSSAService.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */
package org.ets.ereg.scheduling.spi.ssa.service;


import java.io.CharArrayReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class BaseSSAService {
	
	private static Logger log = LoggerFactory.getLogger(BaseSSAService.class);
	
	private IHttpPostService httpPostService;
	
	private String protocol; 
	private String serverPath;
	private String source;
	private String programCode;
	private String requestSystemId;
	private String transactionId;

	protected static String CSR_PERSON_ROLE = "csr";
	protected static String CANDIDATE_PERSON_ROLE = "candidate";
	private Boolean prioritize = false;
	
	public Boolean getPrioritize() {
		return prioritize;
	}

	public void setPrioritize(Boolean prioritize) {
		this.prioritize = prioritize;
	}	
	
	public IHttpPostService getHttpPostService() {
		return httpPostService;
	}

	public IHttpPostService getHttpPostService(int priority) {
		return httpPostService;
	}
	
	
	
	public String getProtocol() {
		return protocol;
	}

	public String getServerPath() {
		return serverPath;
	}

	public String getSource() {
		return source;
	}

	public String getProgramCode() {
		return programCode;
	}

	public String getFormattedDate(Date date, String toFormat) {
		if(date == null) {
			return null;
		}
		if(toFormat == null) {
			toFormat = "MM-dd-yyyy";
		}
		String formattedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(toFormat);
		formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public void setHttpPostService(IHttpPostService httpPostService) {
		this.httpPostService = httpPostService;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public boolean getStatusFromResponse(String xmlString) {
		boolean status = false;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			log.debug("response: " + xmlString );
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader=new CharArrayReader(xmlString.toCharArray());
			Document doc = builder.parse(new org.xml.sax.InputSource(reader));
			NodeList nodes = doc.getElementsByTagName("status");
			Node statusNode = nodes.item(0).getChildNodes().item(0); 
			if (statusNode != null) {
				log.debug("statusNode.getNodeValue() :: " + statusNode.getNodeValue() );
				status = Boolean.parseBoolean(statusNode.getNodeValue());
			}
	    } catch (Exception e) {
	    	log.error("Error while parsing the xml response", e);
	    }
	    return status;

	}



	public String getRequestSystemId() {
		return requestSystemId;
	}



	public void setRequestSystemId(String requestSystemId) {
		this.requestSystemId = requestSystemId;
	}
	
	protected void handleError(String serviceName, Exception e){		
		log.error("Error While executing " + serviceName + " : " + e.getMessage());
		if( "Read timed out".equals(e.getMessage())){
			throw new RuntimeException("error.praxis.system.ssa.response.timeout");
		}else{
			throw new RuntimeException("error.praxis.system.ssa.response.error");
		}		
	}



	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	
}
