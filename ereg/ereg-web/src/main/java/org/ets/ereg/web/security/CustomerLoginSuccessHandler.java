package org.ets.ereg.web.security;

import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.service.CustomerLogonHistoryService;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.web.util.ETSWebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("customerLoginSuccessHandler")

public class CustomerLoginSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomerLoginSuccessHandler.class);
	
	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;
	
	@Resource(name="customerLogonHistoryService")
	private CustomerLogonHistoryService custLogonHistService;
	
	@Resource(name="etsCustomerService")
	private ETSCustomerService etsCustomerService;	
	
	@Resource(name="profileDemographicQuestionService")
	private ProfileDemographicQuestionService profileDmgrphService;
	
	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;
	
	@Resource(name = "programService")
	private ProgramService programService;	

	public void onLogin(ERegUser loggedInUser, HttpServletRequest request)
	{
		try
		{
			if (!(null == loggedInUser.getGuId()))
			{
				profileBusinessService.updateGUID(loggedInUser.getGuId(), loggedInUser.getId()); 
			}
			
	        Customer customer=etsCustomerService.readCustomerById(loggedInUser.getId());
	        if(null!=customer){
		        //clear cart on login
		       	logger.info("Clear Cart on Log In");
		       	orderService.clearCart(orderService.findCartForCustomer(customer));
		       	
		       	//Create Login History
		       	createCustomerLogonHistory(customer,request);
		       	
		       	List<String> customerPrograms = etsCustomerService.getCustomerProgramInterests(customer.getId());
		       	if(! customerPrograms.contains(ThreadLocalFacade.getProgramCode())) {

					ProgramType programType = programService.getProgramByPrimaryKey(ThreadLocalFacade.getProgramCode());
					etsCustomerService.addCustomerProgramInterest((ETSCustomer)customer, programType);
		       	}
		       	

	        }
			
		}
		catch(Exception e)
		{
			logger.error("Error in onLogin : {}",e);
		}
	}

	private void createCustomerLogonHistory(Customer customer,
			HttpServletRequest request) throws UnknownHostException {
		CustomerLogonHistory customerLogonHistory;
		customerLogonHistory=custLogonHistService.create();
    	customerLogonHistory.setJavaSessionId(request.getRequestedSessionId());        	
    	customerLogonHistory.setCustomer(customer);
    	logger.info("saving customerLogonHistory for id: {}",customerLogonHistory.getCustomer().getId());
    	customerLogonHistory.setLogonTimestamp(new java.util.Date());
    	customerLogonHistory.setBrowserUserAgent(request.getHeader(ETSWebConstants.USERAGENT));
    	customerLogonHistory.setBrowserName(getBrowserData(request));    	
    	customerLogonHistory.setApplicationServerId(java.net.InetAddress.getLocalHost().getHostName());
    	//customerLogonHistory.setOperatingSystemname(operatingSystemname);
    	custLogonHistService.save(customerLogonHistory);      	
		
	}


	private String getBrowserData(HttpServletRequest request)
	 {
	  String userAgent = request.getHeader("User-Agent");
	  logger.debug("userAgent:{}",userAgent);
	  userAgent = userAgent.toLowerCase();
	  if (userAgent.contains("msie")) {return "IE";}
	  else if (userAgent.contains("opera")) {return "Opera";}
	  else if (userAgent.contains("chrome")) {return "Chrome";}
	  else if (userAgent.contains("firefox")){return "Firefox";}
	  else if (userAgent.contains("safari") && userAgent.contains("version")) {return "Safari";}
	  else {return"UNKnown";}
	 }	
	
	
	
}
