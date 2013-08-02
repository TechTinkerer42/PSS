/*
 * HttpPostService.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.scheduling.spi.ssa.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * HttpPostService
 *
 * @version	1.0 Dec 14, 2010
 * @author 	Rukmaiah Bommakanti
 * 
 * @history
 * Dec 14, 2010 RB Initial Creation
 *
 **/

//@Service("httpPostService")
public class HttpPostService implements IHttpPostService,InitializingBean {
	private static Logger log = LoggerFactory.getLogger(HttpPostService.class);
	
	
	private String loginPwd;
	private String loginUserId;
	private int authPort;
	private String authHost;
	//acc to documentation Thread Safe
	private DefaultHttpClient httpClient = null;
	private Integer connectionTimeout;
	private Integer socketTimeout;
	private String acceptAllCerts;	
	
	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService; 

    @Override
    public void afterPropertiesSet() throws Exception {
    	loginPwd = getApplicationConfigurationvalue(Constant.SSA_LOGIN_PWD);
    	loginUserId = getApplicationConfigurationvalue(Constant.SSA_LOGIN_USERID);
    	authPort = Integer.valueOf(getApplicationConfigurationvalue(Constant.SSA_AUTH_PORT));
    	authHost = getApplicationConfigurationvalue(Constant.SSA_AUTH_HOST);
    	connectionTimeout = Integer.valueOf(getApplicationConfigurationvalue(Constant.SSA_CONN_TIMEOUT));
    	socketTimeout = Integer.valueOf(getApplicationConfigurationvalue(Constant.SSA_SOCKET_TIMEOUT));
    	acceptAllCerts = getApplicationConfigurationvalue(Constant.SSA_ACCEPT_ALL_CERTS);    	
    }

    private String getApplicationConfigurationvalue(String key) {
        return applicationConfigurationService.findApplicationConfigurationValue(key);
    }
	    
	public String getAcceptAllCerts() {
		return acceptAllCerts;
	}

	public void setAcceptAllCerts(String acceptAllCerts) {
		this.acceptAllCerts = acceptAllCerts;
	}

	public HttpPostService() {
	}

	/* (non-Javadoc)
	 * @see org.ets.iser.common.service.HttpPostService#doPost(String protocol, String serverPath, Map<String, String> params, Map<String, String> headers, boolean reqParamNamesInclude ) throws Exception {
	 */	
	public String doPost(String protocol, String serverPath,
			Map<String, String> params, Map<String, String> headers,
			boolean reqParamNamesInclude) throws Exception {
		log.debug("In doPost protocol:{0} :: serverPath:  {1} ",  protocol, serverPath);
		try {
			if (httpClient == null) {
				httpClient = new DefaultHttpClient();

				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), authPort));
				schemeRegistry.register(new Scheme("https", SSLSocketFactory
						.getSocketFactory(), 443));

				ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
						httpClient.getParams(), schemeRegistry);

				httpClient = new DefaultHttpClient(cm, httpClient.getParams());

				if ("YES".equalsIgnoreCase(acceptAllCerts)
						&& protocol.startsWith("https")) {
					try {
						SSLContext ctx = SSLContext.getInstance("TLS");
						X509TrustManager tm = new X509TrustManager() {

							public void checkClientTrusted(
									X509Certificate[] xcs, String string)
									throws CertificateException {
							}

							public void checkServerTrusted(
									X509Certificate[] xcs, String string)
									throws CertificateException {
							}

							public X509Certificate[] getAcceptedIssuers() {
								return null;
							}
						};
						ctx.init(null, new TrustManager[] { tm }, null);
						SSLSocketFactory ssf = new SSLSocketFactory(ctx);
						ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
						ClientConnectionManager ccm = httpClient
								.getConnectionManager();
						SchemeRegistry sr = ccm.getSchemeRegistry();
						sr.register(new Scheme("https", ssf, 443));
					} catch (Exception ex) {
						log.error("Error connecting to SSA", ex);
						return null;
					}
				}
				// Check if auth required.
				if (null != authHost)
				{
					httpClient.getCredentialsProvider().setCredentials(
							new AuthScope(authHost, authPort),
							new UsernamePasswordCredentials(loginUserId,
									loginPwd));
				}
			}
			HttpPost httpPost = new HttpPost(protocol + "://" + serverPath);

			HttpParams httpParams = httpPost.getParams();
			
			if (connectionTimeout != null)
			{
				HttpConnectionParams.setConnectionTimeout(httpParams,
						connectionTimeout);
			}
			
			if (socketTimeout != null)
			{
				HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
			}

			HttpEntity entity;
			String queryString = getQueryString(params, reqParamNamesInclude);
			// log.debug("queryString: ",  queryString );

			entity = new StringEntity(queryString, "UTF-8");
			httpPost.setEntity(entity);
			StringBuffer response = null;
			HttpResponse httpResponse = null;
			// httpClient.
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (Exception e) {
				httpPost.abort();
				throw e;
			}
			String line;
			response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			try{
				br.close();
			}
			catch(Exception e)
			{
				log.error("",e);
			}
			if (null != httpResponse.getEntity()) {
				try {
					httpResponse.getEntity().consumeContent();
				} catch (Exception e) {
					log.error("",e);
				} finally {
					httpPost.abort();
				}
			}

			// log.debug("response :: ", response );
			return response.toString();
		} catch (Exception e) {
			log.error("",e);
			throw e;
		}

	}

	/* (non-Javadoc)
	 * @see org.ets.iser.common.service.HttpPostService#doPost(String protocol, String serverPath, Map<String, String> params, Map<String, String> headers) throws Exception {
	 */	
	public String doPost(String protocol, String serverPath, Map<String, String> params, Map<String, String> headers) throws Exception {
		return doPost(protocol, serverPath, params, headers, true);
	}
	
	/**
	 * Prepares http header or params query.
	 * 
	 * @param params Map<String, String>
	 * @param keyNeeded boolean checks whether the key name is needed for a param or not
	 * 
	 * @return String concatenated param values
	 */
	protected static String getQueryString(Map<String, String> params, boolean keyNeeded){
	    //Request Parameters
	    StringBuffer buffer = new StringBuffer();
	    if( null != params ){
	        Iterator<String> iter = params.keySet().iterator();
	        while( iter.hasNext()){
	            String key = iter.next();
	            if( null != key ){
	            	if(keyNeeded) {
		                buffer.append(key);
		                buffer.append("=");
	            	}
	                buffer.append(params.get(key).toString());
	                if(iter.hasNext())
	                {
	                    buffer.append("&");
	                }
	            }
	
	        }
	    }
	    return buffer.toString();
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public int getAuthPort() {
		return authPort;
	}

	public void setAuthPort(int authPort) {
		this.authPort = authPort;
	}

	public String getAuthHost() {
		return authHost;
	}

	public void setAuthHost(String authHost) {
		this.authHost = authHost;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
}
