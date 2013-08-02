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
import java.util.Calendar;
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
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.FindSeatResponse;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.response.FindSeatResponseImpl;
import org.ets.ereg.scheduling.response.SeatImpl;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SSAFindSeatConverter extends SSABaseConverter {

	private static Logger log = LoggerFactory.getLogger(SSAFindSeatConverter.class);

	private static Map<String, String> templates = new HashMap<String, String>();
	private static boolean init = false;
	private static void init() {
		if( init == false ){
			synchronized (SSAFindSeatConverter.class) {
				if( init == false ) {
					templates = new HashMap<String, String>();
					templates.put("ALTERNATE", readTemplate("SSAFindSeatAlternateForm.st"));
					templates.put("DEFAULT", readTemplate("SSAFindSeat.st"));
					init = true;
				}	
			}
		}
	}





	 /**
	  * Map eReg find seat request to SSA find seat request
	  * @param findSeatRequest
	  * @return
	  */
     public static String getRequestXML(FindSeatRequest findSeatRequest) {

		 init();

		 String template = templates.get("DEFAULT");

		 if(findSeatRequest.getAlternateForm() != null ){
			 template = templates.get("ALTERNATE");
		 }

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", findSeatRequest.getTransactionID());
		 st.setAttribute("userRoleType", findSeatRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", findSeatRequest.getSource());

		 st.setAttribute("siteId", findSeatRequest.getSchedulingTestSiteId());

		 st.setAttribute("startDate", DateHandler.convertDateToStringyyyyMMdd(
				 		findSeatRequest.getSearchFromDate().getTime()));
		 st.setAttribute("endDate", DateHandler.convertDateToStringyyyyMMdd(
				 						findSeatRequest.getSearchToDate().getTime()));

		 st.setAttribute("testCode",findSeatRequest.getSchedulingTestCode());

		 st.setAttribute("duration", "PT" + getExtendedDuration(findSeatRequest.getCustomerAccommodations(),
				 															findSeatRequest.getDuration())  + "M");
		 st.setAttribute("systemId", findSeatRequest.getRequestSystemId());

		 st.setAttribute("programCode", findSeatRequest.getTestVariation().getTest().getProgramType().getCode());
		 if( findSeatRequest.getAlternateForm() != null ){
			 st.setAttribute("alternateForm", findSeatRequest.getAlternateForm());
		 }

		 if(findSeatRequest.isHasAccommodations() && null!=findSeatRequest.getCustomerAccommodations()){
			 st.setAttribute("HasAccommodations", "true" );
			 st.setAttribute("accommodations", getAccommodationsString(findSeatRequest.getCustomerAccommodations()));
		 }else {
			 st.setAttribute("HasAccommodations", "false" );
			 st.setAttribute("accommodations", "");
		 }

		 return st.toString();


	 }

	 /**
	  * Map SSA find seat response to eReg find seat response
	  * @param resp
	  * @param siteCode
	  * @return
	  * @throws Exception
	  */
	 public static FindSeatResponse getFindSeatResponse(String resp,FindSeatRequest findSeatRequest,TestCenter testCenter) throws Exception {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression statusExpr = xpath.compile("//responseHeader/status/text()");

		    Object status = statusExpr.evaluate(doc, XPathConstants.NODESET);

		    NodeList statusnodes = (NodeList) status;
		    for (int i = 0; i < statusnodes.getLength(); i++ ) {
		    	log.debug( statusnodes.item(i).getNodeValue());
		    }
		    XPathExpression expr = xpath.compile("//slot/*[position()<5]/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);

		    NodeList nodes = (NodeList) result;
		    DateTimeFormatter parser = ISODateTimeFormat.dateTime();

		    Seat seat=null;
		    FindSeatResponse findSeatResponse = new FindSeatResponseImpl();


		    for (int i = 0, j = 0; i < nodes.getLength(); i+= 4) {

		    	seat = new SeatImpl(j++);

		    	seat.setDeliveryModeCode(findSeatRequest.getDeliveryModeCode());
		    	seat.setTestVariation(findSeatRequest.getTestVariation());
		    	seat.setTestCenter(testCenter);
		    	seat.setSiteCode(testCenter.getExternalTestCenterId());
		    	seat.setLabCode(nodes.item(i + 1).getNodeValue());
		    	seat.setSeatCode(nodes.item(i + 2).getNodeValue());
		    	if(findSeatRequest.isHasAccommodations()){
		    	  seat.setHasAccommodations(true);
		    	  seat.setCustomerAccommodations(findSeatRequest.getCustomerAccommodations());
		    	}

	    		String strLocalStartTime = nodes.item(i + 3).getNodeValue();
	    		try{
	    			Calendar cal = Calendar.getInstance();
	    			cal.setTimeInMillis(parser.parseDateTime(strLocalStartTime).getMillis());
	    			seat.setLocalStartDateTime(cal);
	    			seat.setStrLocalStartTime(strLocalStartTime);
	    		}catch( Exception e ){
	    			log.error(e.getMessage());
	    			//Ignore that slot.
	    			continue;
	    		}

		    	findSeatResponse.addSeat(seat);
		    }

	    	return findSeatResponse;

	 }


}

