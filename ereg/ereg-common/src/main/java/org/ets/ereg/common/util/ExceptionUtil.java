/*
 * --------------------------------------------------------------------------
 *  ©2011 Educational Testing Service. All Rights Reserved.
 * 
 * CONFIDENTIAL BUSINESS INFORMATION
 * 
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Jan 31, 2011
 */
package org.ets.ereg.common.util;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  
 * 
 * @author zabdul
 * 
 *  History
 *  -------
 *  Jan 31, 2011 zabdul Initial version
 */

public class ExceptionUtil {
    private static final String DATE_FORMAT = "yyyyMMdd-HHmm-ssS";
    private static final DateFormat  df = new SimpleDateFormat(DATE_FORMAT);
    private static String serverId;
    private static boolean serverIdSearched;
    

	private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	  
    public static String buildRefNumber(){
    	StringBuilder sb = new StringBuilder();
    	if(getServerId() != null){
        	sb.append(getServerId()).append('-');
    	}
    	sb.append(df.format(new Date()));
    	
    	return sb.toString();
    }
    
    private static String getServerId(){
    	if(serverId == null && !serverIdSearched){
    		serverIdSearched = true;
    		String serverName = null;
    		try{
        		serverName = java.net.InetAddress.getLocalHost().getHostName();
    		}catch(UnknownHostException uhe){
    			//log.error(uhe);
    		}
    		if(serverName != null){
    			Pattern pattern = Pattern.compile("[0-9]+");
    			Matcher matcher = pattern.matcher(serverName);
    			if(matcher.find()){
    				serverId = matcher.group();	
    			}
    		}
    	}
    	return serverId;
    }
    /*
     * To Check for Serializing JSON data.
     */
    
	  private boolean testIsSerializable(Object obj) {
          try {
        	  ObjectMapper mapper = new ObjectMapper();
                 String str = mapper.writeValueAsString(obj);
                 return (StringUtils.isNotEmpty(str));
          } catch (Exception ioe) {
        	  logger.debug("COULD NOT SERIALIZE: {} ", ioe);
              return false;
          }
   }
    
//    public static void main(String[] args){
//    	logger.debug(ExceptionUtil.buildRefNumber());
//    }

}