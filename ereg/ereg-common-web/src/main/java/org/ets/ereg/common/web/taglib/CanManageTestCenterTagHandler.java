package org.ets.ereg.common.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.scheduling.booking.service.impl.BookingServiceImpl;
import org.ets.ereg.scheduling.service.AgencyService;
import org.ets.ereg.scheduling.service.impl.AgencyServiceImpl;
import org.ets.ereg.scheduling.util.SchedulingTypeEnum;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.ets.ereg.session.facade.profile.service.impl.ETSAdminUserBusinessServiceImpl;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessService;
import org.ets.ereg.session.facade.scheduling.service.BookingBusinessServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CanManageTestCenterTagHandler extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(CanManageTestCenterTagHandler.class);	
	
	private long testCenterId;

	public long getTestCenterId() {
		return testCenterId;
	}

	public void setTestCenterId(long testCenterId) {
		this.testCenterId = testCenterId;
	}


	@Override
	public int doStartTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String username = request.getUserPrincipal().getName();
		
	    ApplicationContext ctx = 
		          WebApplicationContextUtils.
		                getWebApplicationContext(request.getSession().getServletContext());
			
     		
		SecurityContextHolderAwareRequestWrapper wrapper = new SecurityContextHolderAwareRequestWrapper(request, "");
		if(wrapper.isUserInRole("PERMISSION_SCHEDULE_ALL_AGENCIES")){
        	
        	return EVAL_BODY_INCLUDE;
        	
        }else{
        	
        	
        	BookingBusinessService bookingBusinessService = (BookingBusinessService) ctx.getBean("bookingBusinessService");
        	
        	TestCenter testCenter = bookingBusinessService.getTestCenterById(testCenterId);//bookingService.getBookingById(bookingId).getTestCenter();
        	
        	//check if test center has TCA Model
        	if(testCenter.getSchedulingType(ProgramContextHolder.getProgramCode()).getCode().equals( SchedulingTypeEnum.TCA_MODEL.getCode())){
        	
	        	
	        	//check if tca can handle test center	        	
        		ETSAdminUserBusinessService etsAdminUserBusinessService = (ETSAdminUserBusinessService) ctx.getBean("etsAdminUserBusinessService");
				ETSAdminUser adminUser = etsAdminUserBusinessService.readAdminUserByUserName(username);
				for (TestCenterAdmin admin : adminUser.getTestCenters()) {
					if( testCenterId == admin.getTestCenter().getId()){
						return EVAL_BODY_INCLUDE;
					}
				}
        	}    
        	
        }
        
        return SKIP_BODY;
	}

	
	
}