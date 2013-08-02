/*
 * SSAReserveSeatConverter.java
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
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.interfaces.scheduling.request.CancelSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 * SSAReserveSeatConverter
 *
 * @version	1.0 Mar 15, 2011
 * @author 	Rukmaiah Bommakanti
 *
 * @history
 * Mar 15, 2011 RB Initial Creation
 *
 **/
public class SSACancelSeatConverter extends SSABaseConverter {

	private static Map<String, String> templates = null;
	public static void init() {
		if( null == templates){
			templates = new HashMap<String, String>();
			templates.put("DEFAULT", readTemplate("SSACancelSeat.st"));
		}
	}


	 /**
	  * Map eReg cancel seat request to SSA cancel seat request
	  * @param cancelSeatRequest
	  * @return
	  */
	 public static String getRequestXML(CancelSeatRequest cancelSeatRequest) {

		 init();

		 String template = templates.get("DEFAULT");

		 ReservedSeat reservedSeat = cancelSeatRequest.getReservedSeat();

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", cancelSeatRequest.getTransactionID());
		 st.setAttribute("userRoleType", cancelSeatRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", cancelSeatRequest.getSource());

		 st.setAttribute("systemId", cancelSeatRequest.getRequestSystemId());
		 st.setAttribute("programCode", cancelSeatRequest.getTestVariation().getTest().getProgramType().getCode());
		 st.setAttribute("testId", reservedSeat.getSchedulingTestCode());

		 st.setAttribute("siteId",reservedSeat.getTestCenter().getExternalTestCenterId());

		 st.setAttribute("labCode", reservedSeat.getLabCode());
		 st.setAttribute("seatCode", reservedSeat.getSeatCode());
		 st.setAttribute("startTime", reservedSeat.getStrLocalStartTime());

		 st.setAttribute("requestingSystem", cancelSeatRequest.getRequestSystemId());

		 st.setAttribute("testDate", DateHandler.convertDateToStringyyyyMMdd(reservedSeat
				 .getLocalStartDateTime().getTime()));

		 if( null != cancelSeatRequest.getCancelReasonCode()){
			 st.setAttribute("cancelCode", cancelSeatRequest.getCancelReasonCode());
		 }
		 else{
			 st.setAttribute("cancelCode", "CandCancelNoReasonGiven");
		 }

		 st.setAttribute("clientCandidateID", cancelSeatRequest.getCustomer().getId());
		 st.setAttribute("clientAppointmentID", reservedSeat.getEtsAppointmentId());

		 return st.toString();


	 }

	 /**
	  * Map SSA cancel seat response to eReg cancel seat response
	  * @param reservedSeat
	  * @param resp
	  * @return
	  * @throws Exception
	  */
	 public static boolean getResponse(ReservedSeat reservedSeat, String resp) throws Exception {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(false);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));


		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr = xpath.compile("//status/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);

		    NodeList nodes = (NodeList) result;

		    if(!(null!=nodes && nodes.getLength() > 0 && nodes.item(0).getNodeValue().equalsIgnoreCase("true") )){
		    	throw new RuntimeException("Cancel Seat Failed");
		    }

		    return true;

	 }


}
