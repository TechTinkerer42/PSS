/*
 * SSAFindSeatConverter.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of
 * Educational Testing Service. ("Confidential Information").
 *
 */
package org.ets.ereg.scheduling.spi.ssa.converter;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.ets.ereg.domain.interfaces.scheduling.request.ExtendHoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.ExtendHoldSeatResponse;
import org.ets.ereg.scheduling.response.ExtendHoldSeatResponseImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 * SSAFindSeatConverter
 *
 * @version	1.0 Mar 1, 2011
 * @author 	Venkat Shanmugam
 *
 * @history
 * Mar 1, 2011 VS Initial Creation
 *
 **/
public class SSAExtendHoldSeatConverter extends SSABaseConverter {

	private static Map<String, String> templates = null;
	public static void init() {
		if( null == templates){
			templates = new HashMap<String, String>();
			templates.put("DEFAULT", readTemplate("SSAEntendHoldSeat.st"));
			templates.put("ALTERNATE", readTemplate("SSAExtendHoldSeatAlternateForm.st"));
		}
	}

 public static String getRequestXML(ExtendHoldSeatRequest extendHoldRequest) {

		 init();

		 String template = templates.get("DEFAULT");

		 if(extendHoldRequest.getAlternateForm() != null ){
			 template = templates.get("ALTERNATE");
		 }

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", extendHoldRequest.getTransactionID());
		 st.setAttribute("userRoleType", extendHoldRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", extendHoldRequest.getSource());

		 st.setAttribute("systemId", extendHoldRequest.getRequestSystemId());
		 st.setAttribute("programCode", extendHoldRequest.getTestVariation().getTest().getProgramType().getCode());
		 st.setAttribute("testId", extendHoldRequest.getTestVariation().getId().getTestId());

		 st.setAttribute("siteId", extendHoldRequest.getSiteCode() );
		 st.setAttribute("holdCode", extendHoldRequest.getHoldCode() );
		 st.setAttribute("holdSource", extendHoldRequest.getHoldSource() );
		 st.setAttribute("holdDuration", extendHoldRequest.getHoldDuration() );

		 st.setAttribute("requestingSystem", extendHoldRequest.getRequestSystemId());

		 if( extendHoldRequest.getAlternateForm() != null && !extendHoldRequest.getAlternateForm().equals("")){
			 st.setAttribute("alternateForm", extendHoldRequest.getAlternateForm());
		 }

		 return st.toString();


	 }



	 public static ExtendHoldSeatResponse getResponse(ExtendHoldSeatRequest extendHoldSeatRequest, String resp) throws Exception {

		 	ExtendHoldSeatResponse extendHoldSeatResponse = new ExtendHoldSeatResponseImpl();
		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr = xpath.compile("//holdCode/source/text() | //holdCode/code/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);

		    NodeList nodes = (NodeList) result;

		    if( nodes.getLength() > 1 ){
		    	extendHoldSeatResponse.setSuccessful(true);
		    	extendHoldSeatResponse.setHoldSource(nodes.item(0).getNodeValue());
		    	extendHoldSeatResponse.setHoldCode(nodes.item(1).getNodeValue());

		    	extendHoldSeatResponse.setEtsAppointmentId(extendHoldSeatRequest.getEtsAppointmentId());
		    	extendHoldSeatResponse.setHoldDuration(extendHoldSeatRequest.getHoldDuration());
		    	//extendHoldSeatResponse.setTestId(extendHoldSeatRequest.getTestVariation().getTest().getTestId());
		    	extendHoldSeatResponse.setSiteCode(extendHoldSeatRequest.getSiteCode());

		    }else{
		    	throw new RuntimeException("Extend Hold Seat Failed");
		    }

		    return extendHoldSeatResponse;
	 }

}
