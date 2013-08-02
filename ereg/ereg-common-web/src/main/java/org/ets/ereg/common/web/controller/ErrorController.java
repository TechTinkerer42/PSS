package org.ets.ereg.common.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.web.exception.JspException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/errorcontroller")
public class ErrorController {
	private final static Logger logger = LoggerFactory.getLogger(ErrorController.class);	

	@RequestMapping(value="/error", method = RequestMethod.GET)
	protected String logGetErrorMessage(HttpServletRequest request, HttpServletResponse response) throws JspException {		
		logger.info("ErrorController invoked from GET...");	
		throw new JspException(response.toString());	
	}
	
	@RequestMapping(value="/error", method = RequestMethod.POST)
	protected String logPostErrorMessage(HttpServletRequest request, HttpServletResponse response) throws JspException, IOException {
		logger.info("ErrorController invoked from POST...");	
		logger.info("ErrorController capturing the error...");		
		String exceptionToLog = request.getParameter("data").replaceAll("newline", "\n").replaceAll("newtab","\r");
		//logger.info(exceptionToLog);
		throw new JspException(exceptionToLog);	
	}	
}