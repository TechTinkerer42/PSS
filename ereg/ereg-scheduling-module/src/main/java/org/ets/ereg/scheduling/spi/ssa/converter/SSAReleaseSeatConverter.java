/*
 * SSAReleaseSeatConverter.java
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
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.ets.ereg.domain.interfaces.scheduling.request.ReleaseSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



/**
 * SSAReleaseSeatConverter
 *
 * @version	1.0 Mar 16, 2011
 * @author 	Venkat Shanmugam
 *
 * @history
 * Mar 16, 2011 VS Initial Creation
 *
 **/
public class SSAReleaseSeatConverter extends SSABaseConverter {


	private static Logger log = LoggerFactory.getLogger(SSAReleaseSeatConverter.class);

	private static Map<String, String> templates = null;
	public static void init() {
		if( null == templates){
			templates = new HashMap<String, String>();
			templates.put("DEFAULT", readTemplate("SSAReleaseSeat.st"));
			templates.put("ALTERNATE", readTemplate("SSAReleaseSeatAlternateForm.st"));
		}
	}


	 /**
	  * Map eReg release seat request to SSA release seat request
	  * @param releaseSeatRequest
	  * @return
	  */
	 public static String getRequestXML(ReleaseSeatRequest releaseSeatRequest) {


		 init();

		 String template = templates.get("DEFAULT");

		 if(releaseSeatRequest.getAlternateForm() != null
				 && !releaseSeatRequest.getAlternateForm().equals("")) {
			 template = templates.get("ALTERNATE");
		 }

		 HeldSeat heldSeat = releaseSeatRequest.getHeldSeat();

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", releaseSeatRequest.getTransactionID());
		 st.setAttribute("userRoleType", releaseSeatRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", releaseSeatRequest.getSource());


		 st.setAttribute("systemId", releaseSeatRequest.getRequestSystemId());
		 st.setAttribute("programCode", heldSeat.getTestVariation().getTest().getProgramType().getCode());
		 st.setAttribute("testCode", heldSeat.getSchedulingTestCode());

		 st.setAttribute("siteCode",heldSeat.getSiteCode());
		 st.setAttribute("duration", heldSeat.getDuration() );
		 st.setAttribute("requestingSystem", releaseSeatRequest.getRequestSystemId());

		 if(releaseSeatRequest.getAlternateForm() != null && !releaseSeatRequest.getAlternateForm().equals("")){
			 st.setAttribute("alternateForm", releaseSeatRequest.getAlternateForm());
		 }

		st.setAttribute("holdCode", heldSeat.getHoldCode() );
		st.setAttribute("holdSource", heldSeat.getHoldSource() );

		 return st.toString();


	 }

	 /**
	  * Map SSA release seat response to eReg release seat response
	  * @param heldSeat
	  * @param resp
	  * @return
	  * @throws Exception
	  */
	 public static boolean getResponse(HeldSeat heldSeat, String resp) throws Exception {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));

			NamespaceContext ctx = new NamespaceContext() {

				@Override
                public String getNamespaceURI(String prefix) {
					String uri;
					if (prefix.equals("ns2"))
						uri = "http://rr.ets.org/v200609/schemas";
					else if (prefix.equals("ns1"))
						uri = "http://www.w3.org/2001/XMLSchema-instance";
					else
						uri = "";
					return uri;
				}

				@Override
                public Iterator getPrefixes(String val) {
					return null;
				}

				@Override
                public String getPrefix(String uri) {
					return null;
				}
			};

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    xpath.setNamespaceContext(ctx);
		    XPathExpression expr = xpath.compile("//ns2:status/text()");

		    Object result = expr.evaluate(doc, XPathConstants.NODESET);

		    NodeList nodes = (NodeList) result;

		    if( nodes.getLength() > 0 ){

		    	log.debug("Release seat status {}"+nodes.item(0).getNodeValue());

		    	if(! "true".equals(nodes.item(0).getNodeValue())){
		    		throw new RuntimeException("Release Seat Failed");
		    	}

		    }else{
		    	throw new RuntimeException("Release Seat Failed");
		    }

		    return true;

	 }

}
