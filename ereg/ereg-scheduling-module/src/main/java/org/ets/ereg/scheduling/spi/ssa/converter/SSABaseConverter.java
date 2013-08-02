/*
 * SSABaseConverter.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.scheduling.spi.ssa.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.broadleafcommerce.profile.core.domain.Address;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.scheduling.request.FindSeatRequest;
import org.ets.ereg.domain.interfaces.scheduling.request.SchedulingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SSABaseConverter
 *
 * @version	1.0 Mar 10, 2011
 * @author 	Venkat Shanmugam
 * 
 * @history
 * Mar 10, 2011 VS Initial Creation
 *
 **/
public abstract class SSABaseConverter {
	
	private static Logger log = LoggerFactory.getLogger(SSABaseConverter.class);
	
	
	/**
	 * @param templateName
	 * @return
	 */
	protected static String readTemplate(String templateName){
		InputStream is = SSABaseConverter.class.getResourceAsStream("/SSARequestTemplates/"+templateName);

		 BufferedReader br = new BufferedReader(new InputStreamReader(is));
		 StringBuffer sb = new StringBuffer();
		 try{
			 String str = br.readLine();
			 while( str != null){
				 sb.append(str +"\n");
				 str = br.readLine();
			 }
		 }catch(Exception e){
			 log.error("",e);
		 }
		 finally
		 {
			 try {
				br.close();
			} catch (IOException e) {
				log.error("",e);
			}
		 }
		 return sb.toString();
	}
	
	protected static String getExtendedDuration(List<CustomerAccommodationTest> customerAccommodations, Short duration ){
		if(null!=customerAccommodations && customerAccommodations.size()>0){
			 for(CustomerAccommodationTest customerAccommodation : customerAccommodations) {			 
				 if(customerAccommodation.getAccommodationTypeValue()!=null){
					 if("84".equals(customerAccommodation.getAccommodationType().getCode())){
							Short newDuration = (short) (duration * ( Float.valueOf(
									customerAccommodation.getAccommodationTypeValue().getValue())));
							if ( newDuration > duration) {
								duration = newDuration;
							}
					 }
					 else if( "32".equals(customerAccommodation.getAccommodationType().getCode())){
							Short newDuration = (short) (duration + ( Float.valueOf(
											customerAccommodation.getAccommodationTypeValue().getValue())));
							if ( newDuration > duration) {
								duration = newDuration;
							}
					 }
				 }			
			}	
		}		
		log.debug("New Duration : " + duration);
		return "" + duration;
		
	}
	
	
	
	 public static String getAccommodationsString(List<CustomerAccommodationTest> customerAccommodations) {
		 StringBuffer accommodationsSB = new StringBuffer();		
		 for(CustomerAccommodationTest customerAccommodation : customerAccommodations) {			
			accommodationsSB.append("<accommodation>");
			accommodationsSB.append("\n");
			accommodationsSB.append("<accommodationCode>");
			accommodationsSB.append(customerAccommodation.getAccommodationType().getCode());
			accommodationsSB.append("</accommodationCode>");
			accommodationsSB.append("\n");
			
			if(customerAccommodation.getAccommodationTypeValue()!=null){
				accommodationsSB.append("<additionalParameters>");
				accommodationsSB.append("<name>");
				accommodationsSB.append("AccommodationValue"); // Need to know this value
				accommodationsSB.append("</name>");
				accommodationsSB.append("<data><string>");
				
				//.getSchedulingServiceAccommodationValue() - do we need to use scheduling service accommodation?
				accommodationsSB.append(customerAccommodation
								.getAccommodationTypeValue().getValue());
				accommodationsSB.append("</string></data>");
				accommodationsSB.append("</additionalParameters>");
			}			
			accommodationsSB.append("</accommodation>");
			accommodationsSB.append("\n");
		 }		 
		 log.debug("accommodationsSB.toString() :: " + accommodationsSB.toString() );
		 return accommodationsSB.toString();
	 }

	 public static String updateEmailTemplate(String template, String emailTemplate, Address adr){

		 if (null != emailTemplate && null != adr && adr.getEmailAddress() != null 
				 && adr.getEmailAddress().length() > 0 && adr.getEmailAddress().length() < 51){
			 template = template.replace("$emailAddress$", emailTemplate);
		 }else{
			 template = template.replace("$emailAddress$", "");
		 }
		 
		 return template;
	 } 

}
