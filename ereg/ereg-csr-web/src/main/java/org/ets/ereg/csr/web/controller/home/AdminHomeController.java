package org.ets.ereg.csr.web.controller.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.csr.web.controller.login.AdminLoginController;
import org.ets.ereg.csr.web.util.Constant;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("etsAdminHomeController")
public class AdminHomeController {
	  private static final String ATTR_TEST_CENTER_NAMES = "testCenterNames";

		private static final String ATTR_ROLE_NAME = "roleNames";
		private static final String ATTR_FIRST_NAME = "firstName";
		private static final String ATTR_LAST_NAME = "lastName";

		private static Logger log = LoggerFactory.getLogger(AdminLoginController.class);

	    @Resource(name="blAdminSecurityService")
	    private AdminSecurityService adminSecurityService;   

	    @Resource(name="applicationConfigurationService")
	    private ApplicationConfigurationService applicationService;

	    /**
	     * Method to redirect to the home page
	     *
	     * @param model
	     * @param request
	     * @param principal
	     * @return
	     */
	    @RequestMapping(value=Constant.HOME, method=RequestMethod.GET)
	    public String home(ModelMap model, HttpServletRequest request,Principal principal,
	    		@LoggedInUser ERegUser loggedInUser ) {
	    	log.debug("In the Home method");
	        ETSAdminUser adminUser = (ETSAdminUser) adminSecurityService.readAdminUserById(loggedInUser.getId());
	    	String programCode = ProgramContextHolder.getProgramCode();
	    	String testCenterSearchURL = applicationService.findApplicationConfigurationValue(ApplicationConfiguration.TESTCENTER_SEARCH_URL);
	    	model.addAttribute("testCenterSearchAction", testCenterSearchURL+"/"+programCode);
	        model.addAttribute(ATTR_FIRST_NAME,loggedInUser.getFirstName());
        	model.addAttribute(ATTR_LAST_NAME,loggedInUser.getLastName());
        	model.addAttribute(ATTR_ROLE_NAME,getRoles(loggedInUser));
	        if (loggedInUser.hasTCARole()){
	        	Map<String,List<String>> tca_testCenters=getTCATestCenter(adminUser);
	        	model.addAttribute(ATTR_TEST_CENTER_NAMES,tca_testCenters);
	        	log.debug("TCA user, redirecting to TCA home");
	        	return applicationService.findApplicationConfigurationValue(ApplicationConfiguration.TCA_USR_HOME_PAGE);
	        } else {        	
	        	log.debug("CSR user, redirecting to CSR home");	        	
	        	return applicationService.findApplicationConfigurationValue(ApplicationConfiguration.CSR_USR_HOME_PAGE);
	        }
	    }
	    
	    private List<String> getRoles(ERegUser loggedInUser) {
	    	List<String> roleNames=  new ArrayList<String>();
	    	for(RoleEnum role : loggedInUser.getEregUserRoles()){
	    		roleNames.add(role.getDescription());
	    	}
	    	return roleNames;
	    	
		}

		private Map<String, List<String>> getTCATestCenter(ETSAdminUser adminUser) {
	    	Map<String, List<String>> testCenterMap=new HashMap<String, List<String>>();
	    	
	    	List<TestCenter> testCenters = new ArrayList<TestCenter>();
	    	List<TestCenterAdmin> testCenterAdmins = adminUser.getTestCenters();
	    	List<String> testCenterNames=new ArrayList<String>();
	    	
			for (TestCenterAdmin admin : testCenterAdmins) {
				testCenters.add(admin.getTestCenter());
			}
			
			for(TestCenter t:testCenters){
				String agencyName = t.getAgency(ProgramContextHolder.getProgramCode()).getName();
				if(null==testCenterMap.get(agencyName)){
					testCenterMap.put(agencyName, new ArrayList<String>(Arrays.asList(t.getName())));
				}else{
					testCenterMap.get(agencyName).add(t.getName());
				}
				log.info("Agency :{}",agencyName);
				log.info("Test Center :{}",t.getName());
				testCenterNames.add(t.getName());
			}
			
			return testCenterMap;
			
		}

		/**
	     * Method to redirect to customer error page if user don't have specific access
	     *
	     * @param request
	     * @param response
	     * @param model
	     * @return
	     */
	    @RequestMapping(value=Constant.ACCESS_ERROR, method=RequestMethod.GET)
	    public String redirectToErrorPage(HttpServletRequest request, HttpServletResponse response,Model model) {
	        return Constant.ACCESS_ERROR_PAGE;

	    }
}
