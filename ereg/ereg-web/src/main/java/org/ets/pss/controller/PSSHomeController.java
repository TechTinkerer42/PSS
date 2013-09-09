package org.ets.pss.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author asampath
 *
 */
/**
 * Sample controller for going to the home page with a message
 */
@Controller("pss-home-controller")
@RequestMapping(value="/pss")
public class PSSHomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(PSSHomeController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/home",  method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		
		//return "forward:/web/task/myPage";
		return "home";
	}

}
