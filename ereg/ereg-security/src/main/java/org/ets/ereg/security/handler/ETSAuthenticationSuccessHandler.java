package org.ets.ereg.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("etsAuthenticationSuccessHandler")
public class ETSAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

	private static Logger logger = LoggerFactory.getLogger(ETSAuthenticationSuccessHandler.class);
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();

        String moduleKey = request.getParameter("moduleKey");
        String pageKey = request.getParameter("pageKey");
        if (moduleKey != null && pageKey != null) {
            targetUrl += "#" + "moduleKey=" + moduleKey + "&pageKey=" + pageKey;
        }

        logger.debug("Redirecting to DefaultSavedRequest Url: {} ",targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}



