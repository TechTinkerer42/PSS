package org.ets.ereg.web.controller.login;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.web.util.ETSWebConstants;
import org.ets.ereg.web.util.LoginUtil;
import org.ets.ereg.web.util.WebControllerMappingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;


@Controller("etsLoginController")
public class LoginController {

private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MessageSource messageSource;
	
	@Resource(name="applicationConfigurationService")
	protected ApplicationConfigurationService applicationService;

	
	@RequestMapping(value={"/","/public/signin/{tenantCode}"}, method=RequestMethod.GET)
	public String login(@PathVariable("tenantCode") String tenantCode, HttpServletRequest request, HttpServletResponse response,Model model) {

		
		//@TODO Move this from utils to a base class
		LoginUtil.switchCustomerContext(request, tenantCode, null);

		
		return login(request, response, model);
	}	
	
	@RequestMapping(value={"/","/public/signin"}, method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) {
		//@TODO Move this from utils to a base class
		LoginUtil.switchCustomerContext(request, null, null);
		if(null!=request.getParameter("error") && request.getParameter("error").equalsIgnoreCase("true")){
			RequestContext requestContext = new RequestContext(request);
			log.error("in the login method. Error is: {} ", request.getParameter("error"));
			model.addAttribute("error", getMessage("loginpage.notSuccessfulMessage",requestContext.getLocale()));
		}
		//Logic to redirect to OAM or internal
		model=LoginUtil.getAuthAttributes(request,model,applicationService);
		
		// check if already logged in
		return ("anonymousUser".equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()))? ETSWebConstants.LOGIN_PAGE:ETSWebConstants.HOME_PAGE;

	}

	private String getMessage(String code,Locale locale) {
		return messageSource.getMessage(code, null, locale);
	}

	@RequestMapping(value={"/public/authnfailure"}, method=RequestMethod.GET)
	public String authFailure(HttpServletRequest request, HttpServletResponse response,Model model) {

		RequestContext requestContext = new RequestContext(request);
		model.addAttribute("error", getMessage("loginpage.notSuccessfulMessage",requestContext.getLocale()));
		log.info("In the authfailure method");
		//Logic to redirect to OAM or internal
		model=LoginUtil.getAuthAttributes(request,model,applicationService);

		return WebControllerMappingConstants.FORWARD_TO_LOGIN ;

	}
}
