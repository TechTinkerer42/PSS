package org.ets.ereg.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ERegDecodeURLFilter implements Filter{
	private static Logger log = LoggerFactory.getLogger(ERegDecodeURLFilter.class);

	@Autowired
	private GenerateBase64StringBusinessService generateBase64StringBusinessService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    if (request instanceof HttpServletRequest) {
	        HttpServletRequest httpRequest = (HttpServletRequest)request;
	        HttpServletResponse httpResponse = (HttpServletResponse) response;
	        boolean encodable = httpRequest.getParameter("encodable")!=null ? (httpRequest.getParameter("encodable").equalsIgnoreCase("false") ? Boolean.FALSE :Boolean.TRUE) : Boolean.TRUE;
	        if(!encodable){
	            chain.doFilter(httpRequest, httpResponse);
	        } else{
	            String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
	            if(url.contains(Constant.URL_DECODE_IDENTIFIER)){
	                forwardUrl(url, httpRequest, httpResponse);
	            } else if((httpRequest.getQueryString()!=null && httpRequest.getQueryString().matches(Constant.DECODE_URL_REGEX)) || url.matches(Constant.DECODE_URL_REGEX)) {
	                if(url.contains(Constant.URL_DECODE_IDENTIFIER)){
	                    forwardUrl(url, httpRequest, httpResponse);
	                }
	                else{
	                    forwardSignin(httpRequest, httpResponse);
	                }
	            }
	            else{
	                chain.doFilter(request, response);
	            }
	        }

	    }


	}

    private void forwardSignin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        log.error("Error while decoding url for user: {} " , httpRequest.getUserPrincipal().getName());
        //httpRequest.getSession().invalidate();
        //redirect(httpRequest, httpResponse, Constant.SIGNIN_PAGE);

    }

    private void forwardUrl(String url, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String controllerUrl = url.substring(0, url.lastIndexOf(Constant.FORWARD_SLASH)+1);
        url = url.replaceFirst(controllerUrl, "");
        url = url.replaceFirst(Constant.URL_DECODE_IDENTIFIER, "");
        url = generateBase64StringBusinessService.decodeBase64String(url);
        String username = httpRequest.getUserPrincipal().getName();
        if(url.contains(username)){
            url = StringUtils.remove(url, username+Constant.ENCODING_DELIMITTER);
            url = controllerUrl + url;
            log.debug("Decoded url is : {} ", url);
            forward(httpRequest, httpResponse, url);
        } else {
            forwardSignin(httpRequest, httpResponse);
        }


    }

    private void forward(HttpServletRequest httpRequest,
            HttpServletResponse response, String url) {
        RequestDispatcher rd = httpRequest.getRequestDispatcher(url);
        try {
            rd.forward(httpRequest, response);
        } catch (ServletException e) {
            log.error(" Exception while forwarding to url {} for user {} " + e, url, httpRequest.getUserPrincipal().getName());
        } catch (IOException e) {
            log.error(" Exception while forwarding to url {} for user {} " + e, url, httpRequest.getUserPrincipal().getName());           
        }

    }


    private void redirect(HttpServletRequest request,
            HttpServletResponse response,
            String path) throws ServletException {
      try {
      response.sendRedirect(request.getContextPath() + path);
      }
      catch (java.io.IOException e) {
      throw new ServletException(e);
      }
  }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
