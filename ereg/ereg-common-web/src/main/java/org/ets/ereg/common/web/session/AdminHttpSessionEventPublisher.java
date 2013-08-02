package org.ets.ereg.common.web.session;

import javax.servlet.http.HttpSessionEvent;

import org.ets.ereg.scheduling.vo.ExtendHoldSeatStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class AdminHttpSessionEventPublisher extends HttpSessionEventPublisher {
	
	private static Logger LOG = LoggerFactory.getLogger(AdminHttpSessionEventPublisher.class);	

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOG.debug("session created");
		super.sessionCreated(event);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOG.debug("session being destroyed");
		ApplicationContext applicationContext = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());

		if(applicationContext != null){
			ExtendHoldSeatStore extendHoldSeatStore = (ExtendHoldSeatStore) applicationContext.getBean("extendHoldSeatStore");
			if(extendHoldSeatStore != null){
				extendHoldSeatStore.removeExtendHoldsForSession(event.getSession().getId());
			}
		}
		super.sessionDestroyed(event);
	}	

}
