package org.ets.ereg.security.util;

import org.ets.ereg.common.exception.ERegRuntimeException;
import org.ets.ereg.security.user.ERegUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ERegThreadContext {
	protected static Logger log = LoggerFactory.getLogger(ERegThreadContext.class);

	public static ERegUser getLoggedInERegUser() {
		log.info("getLoggedInERegUser");
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object == null) {
			log.error("Principal object is null");
			throw new ERegRuntimeException("Principal object is null");
		}
		if (!(object instanceof UserDetails)) {
			log.error("Principal object is invalid");
			throw new ERegRuntimeException("Principal object is invalid");
		}
		return (ERegUser) object;
	}

	public static boolean isUserInAnyRoles(String... roles) {
		log.info("isUserInAnyRoles");
		return getLoggedInERegUser().isUserInAnyRoles(roles);
	}

	public static boolean isUserInRole(String role) {
		log.info("isUserInRole");
		return getLoggedInERegUser().isUserInRole(role);
	}
}
