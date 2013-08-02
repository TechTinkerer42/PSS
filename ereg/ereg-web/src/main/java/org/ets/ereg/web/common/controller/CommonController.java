package org.ets.ereg.web.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.StateService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class CommonController {
	@Resource(name = "blStateService")
	private StateService stateService;
	
	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;
	
	@RequestMapping(value="/states", method=RequestMethod.GET)
	@ResponseBody public Map<String, Object> getStates(
			HttpServletResponse response,
			@RequestParam(value="country",required=false, defaultValue="US") String countryAbbreviation){
		Map<String, Object> retValue = new HashMap<String, Object>();
		boolean success = true;
		try{
			List<Map<String, String>> states = new ArrayList<Map<String, String>>();
			retValue.put("states", states);
			for(State state: stateService.findStates(countryAbbreviation.toUpperCase())){
				Map<String, String> st = new HashMap<String, String>();
				st.put("name", state.getName());
				st.put("abbreviation", state.getAbbreviation());
				states.add(st);
			}
		}
		catch(Exception e){
			success = false;
		}
		finally{
			retValue.put("success", success);
			response.setContentType("application/json");
		}
		return retValue;
	}
	
	@RequestMapping(value="/isUsernameAvailable", method=RequestMethod.GET)
	@ResponseBody public Map<String, Object> isUsernameAvailable(
			HttpServletResponse response,
			@RequestParam(value="username",required=false, defaultValue="") String username){
		Map<String, Object> retValue = new HashMap<String, Object>();
		boolean success = true;
		try{
			boolean isAvailable;
			if(username.trim().length() == 0){
				isAvailable = false;
			}
			else{
				isAvailable = profileBusinessService.isUsernameAvailable(username);
			}
			retValue.put("isAvailable", isAvailable);
		}
		catch(Exception e){
			success = false;
		}
		finally{
			retValue.put("success", success);
			response.setContentType("application/json");
		}
		return retValue;
	}
	
	@RequestMapping(value="/exception", method=RequestMethod.GET)
	public  void throwException(
			HttpServletResponse response,
			@RequestParam(value="message",required=false, defaultValue="") String message){
		throw new RuntimeException(message);
	}
}
