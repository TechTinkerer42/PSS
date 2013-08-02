package org.ets.ereg.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.web.filter.AbstractCustomerStateFilter;
import org.springframework.stereotype.Component;


@Component("eRegCustStateFilter")
public class EregCustomerStateFilter extends AbstractCustomerStateFilter {

	@Override
	protected String resolveUserName(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}
	
}