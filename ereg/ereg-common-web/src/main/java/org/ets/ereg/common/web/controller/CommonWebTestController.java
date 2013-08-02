package org.ets.ereg.common.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cmnwebtestcontroller")
@RequestMapping("/testcommonweb")
public class CommonWebTestController {
	
	@RequestMapping(value="/testcommonweb", method=RequestMethod.GET)
	public String testCommonWebJSPRedirect(HttpServletRequest request, HttpServletResponse response,Model model) {
		return "commonwebtest";
	}

}
