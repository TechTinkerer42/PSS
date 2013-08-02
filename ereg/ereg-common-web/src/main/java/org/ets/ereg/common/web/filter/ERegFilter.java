package org.ets.ereg.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ERegFilter implements Filter{
	private static Logger log = LoggerFactory.getLogger(ERegFilter.class);
	
	@Override
	public void destroy() {
//		ProgramContextHolder.clearProgramType();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//Customer and User Context model implemented for candidate portal. For CSR web will return "HSE"
//		ProgramContextHolder.setProgramCode("HSE");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
