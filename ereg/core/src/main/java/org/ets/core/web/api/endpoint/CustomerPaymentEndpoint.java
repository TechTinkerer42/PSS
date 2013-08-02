package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component("etsRestCustomerPaymentEndpoint")
@Scope("singleton")
@Path("/customerPayment")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerPaymentEndpoint implements ApplicationContextAware {

    //TODO CustomerPayment is in BLC 2.1
    //@Resource(name="blCustomerPaymentDao")
    //protected CustomerPaymentDao customerPaymentDao;

    @Resource(name="blCustomerAddressService")
    protected CustomerAddressService customerAddressService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
