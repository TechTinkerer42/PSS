package org.ets.ereg.security.handler;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.util.StringUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component("etsAuthenticationFailureHandler")
public class ETSAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;

    public ETSAuthenticationFailureHandler() {
        super();
    }

    public ETSAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
        this.defaultFailureUrl = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String failureUrlParam = StringUtil.cleanseUrlString(request.getParameter("failureUrl"));
        String successUrlParam = StringUtil.cleanseUrlString(request.getParameter("successUrl"));
        String failureUrl = failureUrlParam==null?null:failureUrlParam.trim();
        if (StringUtils.isEmpty(failureUrl)) {
            failureUrl = defaultFailureUrl;
        }
        if (failureUrl != null) {
            if (!StringUtils.isEmpty(successUrlParam)) {
                if (!failureUrl.contains("?")) {
                    failureUrl += "?successUrl=" + successUrlParam;
                } else {
                    failureUrl += "&successUrl=" + successUrlParam;
                }
            }
            String moduleKey = request.getParameter("moduleKey");
            String pageKey = request.getParameter("pageKey");
            if (moduleKey != null && pageKey != null) {
                failureUrl += "#" + "moduleKey=" + moduleKey + "&pageKey=" + pageKey;
            }
            saveException(request, exception);
            getRedirectStrategy().sendRedirect(request, response, failureUrl);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}

