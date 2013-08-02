
/*
 * 
 */

package org.ets.ereg.external.service.eias.client.service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.3.0
 * Fri Dec 14 11:46:20 EST 2012
 * Generated source version: 2.3.0
 * 
 */


@WebServiceClient(name = "EIASWebServiceService", 
                  wsdlLocation = "file:/E:/Ereg_temp/eReg/ereg-eias-client/src/main/resources/eias/wsdl/EIASWebService.wsdl",
                  targetNamespace = "http://webservice.eias.ets.org") 
public class EIASWebServiceService extends Service {

	private static Logger logger = LoggerFactory.getLogger(EIASWebServiceService.class);
	
    public final static QName SERVICE = new QName("http://webservice.eias.ets.org", "EIASWebServiceService");
    public final static QName EIASWebService = new QName("http://webservice.eias.ets.org", "EIASWebService");
    private static EIASWebServiceService eiasWebServiceInstance;
    

    private EIASWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    private EIASWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }
    
    public static EIASWebServiceService getInstance(URL wsdlLocation)
    {
    	if(eiasWebServiceInstance == null) {
    		eiasWebServiceInstance = new EIASWebServiceService(wsdlLocation);
         }
         return eiasWebServiceInstance; 
    }
    
    /**
     * 
     * @return
     *     returns EIASWebService
     */
    @WebEndpoint(name = "EIASWebService")
    public EIASWebService getEIASWebService() {
        return super.getPort(EIASWebService, EIASWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EIASWebService
     */
    @WebEndpoint(name = "EIASWebService")
    public EIASWebService getEIASWebService(WebServiceFeature... features) {
        return super.getPort(EIASWebService, EIASWebService.class, features);
    }

}
