package org.ets.ereg.csr.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.web.filter.AbstractCustomerStateFilter;
import org.ets.ereg.csr.web.util.Constant;
import org.springframework.stereotype.Component;

@Component("eRegCustStateFilter")
public class EregAdminCustomerStateFilter extends AbstractCustomerStateFilter {

	@Override
	protected String resolveUserName(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(Constant.CUSTOMER_USER_NAME);
	}

}
