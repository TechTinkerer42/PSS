package org.ets.ereg.common.web.exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ERegMappingExceptionResolver extends SimpleMappingExceptionResolver {
    @Resource(name="applicationConfigurationService")
	private ApplicationConfigurationService applicationService;
    
    private static final Logger LOG = LoggerFactory.getLogger(ERegMappingExceptionResolver.class);
    
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = super.doResolveException(request,response,handler,ex);
		ERegRuntimeException wrapperException = new ERegRuntimeException(ex);
		LOG.error("exception", wrapperException);
		modelAndView.addObject("wrapperException", wrapperException);
		String helpDeskEmail = applicationService.findApplicationConfigurationValue(ApplicationConfiguration.HELPDESK_EMAIL);
		modelAndView.addObject("helpDeskEmail", helpDeskEmail);
        return modelAndView;
	}
}
