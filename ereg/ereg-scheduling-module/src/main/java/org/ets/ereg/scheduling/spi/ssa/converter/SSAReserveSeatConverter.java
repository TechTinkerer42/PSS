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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.scheduling.request.ReserveSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.response.HeldSeat;
import org.ets.ereg.domain.interfaces.scheduling.response.ReservedSeat;
import org.ets.ereg.scheduling.response.ReservedSeatImpl;
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
public class SSAReserveSeatConverter extends SSABaseConverter {

	private static Map<String, String> templates = null;
	public static void init() {
		if( null == templates){
			templates = new HashMap<String, String>();
			templates.put("DEFAULT", readTemplate("SSAReserveSeat.st"));
			templates.put("EMAIL", readTemplate("SSAEmail.st"));
			templates.put("ALTERNATE", readTemplate("SSAReserveSeatAlternateForm.st"));

		}
	}

	/**
	 * Map eReg reserve seat request to SSA reserve seat request
	 * @param reserveSeatRequest
	 * @return
	 */
	public static String getRequestXML(ReserveSeatRequest reserveSeatRequest) {


		 init();

		 String template = templates.get("DEFAULT");
		 if(reserveSeatRequest.getAlternateForm() != null && !reserveSeatRequest.getAlternateForm().equals("")) {
			 template = templates.get("ALTERNATE");
		 }

		 HeldSeat heldSeat = reserveSeatRequest.getHeldSeat();

		 ETSCustomer customer = reserveSeatRequest.getCustomer();
		 ETSAddress primaryAddress = getPrimaryAddress(customer);
		 
		 if(primaryAddress!=null && StringUtils.isNotBlank(primaryAddress.getEmailAddress())){
			 template = updateEmailTemplate(template, templates.get("EMAIL"), primaryAddress);
		 }

		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		 StringTemplate st = new StringTemplate(template, DefaultTemplateLexer.class);
		 st.setAttribute("transactionId", reserveSeatRequest.getTransactionID());
		 st.setAttribute("userRoleType",reserveSeatRequest.getRequesterRoleTypeCode());
		 st.setAttribute("source", reserveSeatRequest.getSource());

		 st.setAttribute("systemId", reserveSeatRequest.getRequestSystemId());
		 st.setAttribute("programCode", heldSeat.getTestVariation().getTest().getProgramType().getCode());
		 st.setAttribute("testCode", heldSeat.getSchedulingTestCode());

		 st.setAttribute("siteCode", heldSeat.getSiteCode());
		 st.setAttribute("labCode", heldSeat.getLabCode());
		 st.setAttribute("seatCode", heldSeat.getSeatCode());
		 st.setAttribute("startTime", heldSeat.getStrLocalStartTime());

		 st.setAttribute("duration",  getExtendedDuration( heldSeat.getCustomerAccommodations()
				 												, heldSeat.getDuration()));
		 st.setAttribute("requestingSystem", reserveSeatRequest.getRequestSystemId());

		 st.setAttribute("testDate", sdf.format(heldSeat.getLocalStartDateTime().getTime()));

		 st.setAttribute("clientCandidateID", customer.getId());
		 st.setAttribute("firstName", customer.getFirstName() );
		 st.setAttribute("middleName", customer.getMiddleInitial() );
		 st.setAttribute("lastName", customer.getLastName() );
		 st.setAttribute("birthDate", sdf.format(customer.getDateOfBirth()) );
		 st.setAttribute("gender", customer.getGender().getCode());

		 if(StringUtils.isNotEmpty(primaryAddress.getAddressLine1())){
			 st.setAttribute("addressLine1",  primaryAddress.getAddressLine1());
		 }
		 if(StringUtils.isNotEmpty(primaryAddress.getAddressLine1())){
			 st.setAttribute("addressLine2",  primaryAddress.getAddressLine2());
		 }
		 if(StringUtils.isNotEmpty(primaryAddress.getAddressLine1())){
			 st.setAttribute("addressLine3",  primaryAddress.getAddressLine3());
		 }

		 st.setAttribute("city",  primaryAddress.getCity() );

		 if( null != primaryAddress.getState()){
			 st.setAttribute("state", primaryAddress.getState().getAbbreviation());
		 }
		 if ( null != primaryAddress.getState().getCountry()){
			 st.setAttribute("country", ((ETSCountry)primaryAddress.getState().getCountry()).getIsO3CountryCode());
		 }

		 st.setAttribute("postalCode", primaryAddress.getPostalCode() );

		 List<CustomerPhone> customerPhones = customer.getCustomerPhones();
		 if(null!=customerPhones && customerPhones.size()>0){
			 	 CustomerPhone customerPhone = customerPhones.get(0);
				 Phone phone = customerPhone.getPhone();
				 if(phone!=null){
					 if("primary".equalsIgnoreCase(customerPhone.getPhoneName())){
					   st.setAttribute("phoneType","Primary");
					 }
					 else if("alternate".equalsIgnoreCase(customerPhone.getPhoneName())){
						 st.setAttribute("phoneType","Secondary");
					 }
					 st.setAttribute("phoneNumber", customerPhone.getPhone().getPhoneNumber());
				 }

		 }

		st.setAttribute("emailAddressType", "Primary" );
		st.setAttribute("emailAddress", primaryAddress.getEmailAddress());

		st.setAttribute("clientAppointmentID", heldSeat.getEtsAppointmentId());
		st.setAttribute("holdCode", heldSeat.getHoldCode() );
		st.setAttribute("holdSource", heldSeat.getHoldSource() );
		st.setAttribute("holdDuration", heldSeat.getHoldDuration() );

		 if( reserveSeatRequest.getAlternateForm() != null && !reserveSeatRequest.getAlternateForm().equals("")){
			 st.setAttribute("alternateForm", reserveSeatRequest.getAlternateForm());
		 }

		 if(heldSeat.isHasAccommodations() && null!=heldSeat.getCustomerAccommodations()) {
			 st.setAttribute("HasAccommodations", "true" );
			 st.setAttribute("accommodations", getAccommodationsString(heldSeat.getCustomerAccommodations()) );
		 }else {
			 st.setAttribute("HasAccommodations", "false" );
			 st.setAttribute("accommodations", "");
		 }

		StringBuffer sb = new StringBuffer();
		StringReader sr = new StringReader(st.toString());
		BufferedReader bfr = new BufferedReader(sr);
		try{
			String str = bfr.readLine();
			while(str != null ){
				if( (! str.contains("></")) || str.contains("additionalParameters")){
					sb.append(str);
					sb.append("\n");
				}
				str = bfr.readLine();
			}
		}catch(Exception e){}

		 return sb.toString();


	 }

	/**
	 * get primary address of a customer
	 * @param customer
	 * @return
	 */
	private static ETSAddress getPrimaryAddress(ETSCustomer customer){
		 List<CustomerAddress> custAddresses = customer.getCustomerAddresses();
		 ETSAddress address=null;
		 for(CustomerAddress custAddress:custAddresses){
			 address =(ETSAddress) custAddress.getAddress();
			// if(address.isActive() &&
				//	 AddressType.HomeAddress.equals(address.getAddressType().getCode())){
				return address;
			// }
		 }
		 return address;
	}


	/**
	 * map SSA reserve seat response to eReg reserve seat response
	 * @param heldSeat
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	   public static ReservedSeat getResponse(HeldSeat heldSeat, String resp) throws Exception {

		    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true);
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(new ByteArrayInputStream(resp.getBytes()));

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();

		    XPathExpression exprClientApptId = xpath.compile("//clientAppointmentID/text()");
		    XPathExpression exprETSApptId = xpath.compile("//etsAppointmentID/text() ");

		    Object resultClientAppt = exprClientApptId.evaluate(doc, XPathConstants.NODESET);
		    Object resultETSApptId = exprETSApptId.evaluate(doc, XPathConstants.NODESET);

		    NodeList nodesClientAppt = (NodeList) resultClientAppt;
		    NodeList nodesETSApptId = (NodeList) resultETSApptId;

		    ReservedSeat reservedSeat= new ReservedSeatImpl(heldSeat);

		    if(nodesClientAppt.getLength() > 0) {
		    	reservedSeat.setEtsAppointmentId(nodesClientAppt.item(0).getNodeValue());
		    }else {
		    	throw new RuntimeException("Reserve Seat Failed");
		    }

		    if(nodesETSApptId.getLength() > 0) {
		    	reservedSeat.setEtsReservationID(nodesETSApptId.item(0).getNodeValue());
		    }else {
		    	throw new RuntimeException("Reserve Seat Failed");
		    }

		    return reservedSeat;

	}





}
