package org.ets.ereg.security.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.enums.EIASHeadersEnum;
import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public abstract class AbstractERegUserDetailsOAMPreAuthService extends AbstractERegUserDetailsService implements
AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationService;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		if (!ApplicationConfiguration.AUTH_MECHANISM_OAM.equals(applicationService
				.findApplicationConfigurationValue(ApplicationConfiguration.AUTH_MECHANISM))) {
			log.error("Authentication mechanism is not configured as OAM");
			throw new UsernameNotFoundException("Authentication mechanism is not configured as OAM");
		}
		validateEIASHeaders();
		String userName = token.getPrincipal().toString();
		log.info("Username :" + userName);
		return findUserAndPopulateAuthorities(userName);
	}

	private void validateEIASHeaders() throws UsernameNotFoundException {
		log.info("Validating EIAS Headers");
		HttpServletRequest request = getCurrentRequest();
		@SuppressWarnings("unchecked")
		List<String> headerNamesFromEIAS = Collections.list(request.getHeaderNames());
		if (CollectionUtils.isEmpty(headerNamesFromEIAS)) {
			// probably need to change this
			throw new UsernameNotFoundException("No headers received from EIAS");
		}
		// change to debug after testing in Dev
		if (log.isInfoEnabled()) {
			logHeaders(headerNamesFromEIAS);
		}
		for (EIASHeadersEnum header : EIASHeadersEnum.getMandatoryHeaders()) {
			String headerName = header.getCode();
			if (!headerNamesFromEIAS.contains(headerName)) {
				// need to change this
				log.error("Required header(" + headerName + ") not received from EIAS");
				throw new UsernameNotFoundException("Required header(" + headerName + ") not received from EIAS");
			}
			if (StringUtils.isEmpty(request.getHeader(headerName))) {
				log.error("Header received is empty:" + headerName);
				throw new UsernameNotFoundException("Header received is empty:" + headerName);
			}
		}
		String eiasUserTypeHeaderValue = getEIASUserTypeHeaderValue();
		if (!isEIASUserTypeHeaderValid(eiasUserTypeHeaderValue)) {
			log.error("Value(" + eiasUserTypeHeaderValue + ") for "
					+ EIASHeadersEnum.EIASUSERTYPE + " received is invalid");
			throw new UsernameNotFoundException("Value(" + eiasUserTypeHeaderValue + ") for "
					+ EIASHeadersEnum.EIASUSERTYPE + " received is invalid");
		}
		log.info("EIAS Headers Validation successful");
	}

	private void logHeaders(List<String> headerNamesFromEIAS) {
		log.info("No of Headers received :" + headerNamesFromEIAS.size());
		for (String headerName : headerNamesFromEIAS) {
			log.info(headerName + ":" + getHeaderValue(headerName));
		}
	}

	private boolean isEIASUserTypeHeaderValid(String eiasUserTypeHeaderValue) {
		return StringUtils.equalsIgnoreCase(eiasUserTypeHeaderValue, INTERNAL_USER_IDENTIFICATION_PATTERN)
				|| StringUtils.equalsIgnoreCase(eiasUserTypeHeaderValue, EXTERNAL_USER_IDENTIFICATION_PATTERN);
	}

	protected boolean isInternalUser(String eiasUserTypeHeaderValue) {
		return StringUtils.equalsIgnoreCase(eiasUserTypeHeaderValue, INTERNAL_USER_IDENTIFICATION_PATTERN);
	}
	
	protected String getEIASUserTypeHeaderValue(){
		return getHeaderValue(EIASHeadersEnum.EIASUSERTYPE.getCode());
	}

}
