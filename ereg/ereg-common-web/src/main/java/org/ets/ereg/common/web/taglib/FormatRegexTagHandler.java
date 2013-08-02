/**
 * 
 */
package org.ets.ereg.common.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.ets.ereg.common.web.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * @author SCHANANI
   @time_created May 21, 2013 11:27:44 AM
 *
 */
public class FormatRegexTagHandler extends RequestContextAwareTag  {

    /**
    *
    */
   private static final long serialVersionUID = 1L;
   
   private static final Logger log = LoggerFactory.getLogger(FormatRegexTagHandler.class);
   
   private String out;
   private String regexInputString;
   private String regexOutputString;   
	
		/**
	 * @return the out
	 */
	public String getOut() {
		return out;
	}
	
	/**
	 * @param out the out to set
	 */
	public void setOut(String out) {
		this.out = out;
	}

	/**
	 * @return the regexInputString
	 */
	public String getRegexInputString() {
		return regexInputString;
	}

	/**
	 * @param regexInputString the regexInputString to set
	 */
	public void setRegexInputString(String regexInputString) {
		this.regexInputString = regexInputString;
	}

	/**
	 * @return the regexOutputString
	 */
	public String getRegexOutputString() {
		return regexOutputString;
	}

	/**
	 * @param regexOutputString the regexOutputString to set
	 */
	public void setRegexOutputString(String regexOutputString) {
		this.regexOutputString = regexOutputString;
	}	


@Override
   protected int doStartTagInternal() throws Exception {
	   
	   StringBuilder sb = new StringBuilder();
	   
	   log.info("Data String - " + out);
	   
	   //sb.append(phoneNumber.replaceAll("(\\d\\d\\d)(\\d\\d\\d)(\\d\\d\\d\\d)", "$1-$2-$3"));
	   sb.append(out.replaceAll(regexInputString, regexOutputString));
	   
		try {
			pageContext.getOut().write(sb.toString());
			HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();  
			HttpSession session = httpRequest.getSession(true);  
			session.setAttribute("outputExpression", sb.toString()); 
		} catch (IOException e) {
			log.error("Error outputing the resultant string: {}", e.toString());
		}
	   
	   return EVAL_BODY_INCLUDE;
   }
	
}
