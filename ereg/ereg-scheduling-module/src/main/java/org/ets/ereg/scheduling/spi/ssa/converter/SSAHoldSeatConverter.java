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
import org.ets.ereg.domain.interfaces.scheduling.request.HoldSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.Seat;
import org.ets.ereg.scheduling.response.HeldSeatImpl;
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
public class SSAHoldSeatConverter extends SSABaseConverter {


	private static Logger log = LoggerFactory.getLogger(SSAHoldSeatConverter.class);

	private static Map<String, String> templates = null;
	public static void init() {
		if( null == templates){
			templates = new HashMap<String, String>();
			templates.put("DEFAULT", readTemplate("SSAHoldSeat.st"));
			templates.put("ALTERNATE", readTemplate("SSAHoldSeatAlternateForm.st"));
		}
	}

	 /**
	  * Map eReg hold seat request to SSA hold seat request
	  * @param holdSeatRequest
	  * @return
	  */
	 public static String getRequestXML(HoldSeatRequest holdSeatRequest) {


		 init();

		 String template = templates.get("DEFAULT");
		 if(holdSeatRequest.getAlternateForm() != null ){
			 template = templates.get("ALTERNATE");
		 }

		 Seat seat = holdSeatRequest.getSeat();

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", holdSeatRequest.getTransactionID());
		 st.setAttribute("userRoleType", holdSeatRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", holdSeatRequest.getSource());

		 st.setAttribute("systemId", holdSeatRequest.getRequestSystemId());
		 st.setAttribute("programCode", holdSeatRequest.getSeat().getTestVariation().getTest().getProgramType().getCode());
		 st.setAttribute("testCode", holdSeatRequest.getSeat().getTestVariation().getScheduleTestCode());

		 st.setAttribute("siteCode", seat.getSiteCode());
		 st.setAttribute("labCode", seat.getLabCode());
		 st.setAttribute("seatCode", seat.getSeatCode());
		 st.setAttribute("startTime", seat.getStrLocalStartTime());

		 st.setAttribute("duration",  getExtendedDuration(seat.getCustomerAccommodations(),
				 seat.getDuration() ));
		 st.setAttribute("requestingSystem", holdSeatRequest.getRequestSystemId());

		 if(holdSeatRequest.getAlternateForm() != null && !holdSeatRequest.getAlternateForm().equals("")){
			 st.setAttribute("alternateForm", holdSeatRequest.getAlternateForm());
		 }

		 if(seat.isHasAccommodations() && null!=seat.getCustomerAccommodations()) {
			 st.setAttribute("HasAccommodations", "true" );
			 st.setAttribute("accommodations", getAccommodationsString(seat.getCustomerAccommodations()) );
		 }else{
			 st.setAttribute("HasAccommodations", "false" );
			 st.setAttribute("accommodations", "");
		 }

		 return st.toString();


	 }

	 /**
	  * Map SSA Hold seat response to eReg hold seat response
	  * @param seat
	  * @param resp
	  * @return
	  * @throws Exception
	  */
	 public static HeldSeat getResponse(Seat seat, String resp) throws Exception {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    XPathExpression expr = xpath.compile("//holdCode/source/text() | //holdCode/code/text() | //holdCode/duration/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;

		    HeldSeat heldSeat = new HeldSeatImpl(seat);

		    if( nodes.getLength() > 1 ){
		    	heldSeat.setHoldSource(nodes.item(0).getNodeValue());
		    	heldSeat.setHoldCode(nodes.item(1).getNodeValue());
		    	heldSeat.setHoldDuration(nodes.item(2).getNodeValue());

		    }else{
		    	throw new RuntimeException("Hold Seat Failed");
		    }

		    return heldSeat;
	 }




}
