package org.ets.ereg.security.user;

import java.security.Principal;

import org.ets.ereg.common.exception.ERegRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoggedInUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (parameter.getParameterAnnotation(LoggedInUser.class) != null);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (log.isDebugEnabled()){
			log.debug("resolveArgument parameter name:" + parameter.getParameterName());
		}
		//check if parameter is annotated with @LoggedInUser
		if (!parameter.hasParameterAnnotation(LoggedInUser.class)) {
			log.error("Parameter not Annotated with LoggedInUser");
			throw new ERegRuntimeException("Parameter not Annotated with LoggedInUser");
		}
		//check if the method parameter if of type ERegUser
		if (log.isDebugEnabled()){
			log.debug("Parameter class type: {}", parameter.getParameterType());
		}
		if (!parameter.getParameterType().equals(ERegUser.class)) {
			log.error("Method Parameter is not of Type ERegUser");
			throw new ERegRuntimeException("Method Parameter is not of Type ERegUser");
		}
		Principal principal = webRequest.getUserPrincipal();
		if (principal == null) {
			log.error("Principal Object is null");
			throw new ERegRuntimeException("Principal Object is null");
		}
		return (ERegUser) ((Authentication) principal).getPrincipal();
	}

}
