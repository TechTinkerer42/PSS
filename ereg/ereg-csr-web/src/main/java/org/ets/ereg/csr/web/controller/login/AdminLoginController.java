package org.ets.ereg.csr.web.controller.login;

import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.csr.web.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;

@Controller("etsAdminLoginController")
public class AdminLoginController {

	private static Logger log = LoggerFactory.getLogger(AdminLoginController.class);

    @Resource(name="applicationConfigurationService")
    private ApplicationConfigurationService applicationService;
	
    @Autowired
    private MessageSource messageSource;

    /**
     * Method to sign-in the user to the application
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value=Constant.SIGNIN, method=RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response,Model model) {
    	
       if(null!=request.getParameter(Constant.ERROR) && request.getParameter(Constant.ERROR).equalsIgnoreCase(Constant.TRUE)){
           RequestContext requestContext = new RequestContext(request);
           log.error("in the login method. Error is: {} ", request.getParameter(Constant.ERROR));
           model.addAttribute(Constant.ERROR, getMessage(Constant.LOGIN_INVALID, requestContext.getLocale()));
        }
        model=AdminLoginUtil.setAuthAttributes(request, model, applicationService);
        return (SecurityContextHolder.getContext().getAuthentication() == null || Constant.ANONYMOUS_USER.equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())) ? Constant.LOGIN_PAGE : Constant.HOME;

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
		model=AdminLoginUtil.setAuthAttributes(request,model,applicationService);
		// call the login action with error attribute
		return Constant.LOGIN_PAGE; 

	}
	
	@RequestMapping(value={"/public/logout"}, method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response,Model model) {

		return "public/authn/logout";

	}	
 }
