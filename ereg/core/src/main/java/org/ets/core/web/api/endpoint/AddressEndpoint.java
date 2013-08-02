package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.core.web.api.wrapper.AddressWrapper;
import org.broadleafcommerce.profile.core.dao.AddressDao;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.ets.core.web.api.wrapper.ETSAddressWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component("etsRestAddressEndpoint")
@Scope("singleton")
@Path("/address")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AddressEndpoint implements ApplicationContextAware {

    @Resource(name="blAddressDao")
    protected AddressDao addressDao;

    @Resource(name="blAddressService")
    protected AddressService addressService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    @Path("/{id}")
    public ETSAddressWrapper readAddressById(@Context HttpServletRequest request, @PathParam("id") Long id) {
        Address address = addressDao.readAddressById(id);
        if (address != null) {
            ETSAddressWrapper wrapper = (ETSAddressWrapper) context.getBean(ETSAddressWrapper.class.getName());
            wrapper.wrap(address, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    /**
     * WRITE OPERATIONS BELOW
     * Transactions marked at the service layer.
     * Pointcuts are defined in bl-profile-applicationContext-persistence.xml
     */

    @PUT
    public ETSAddressWrapper saveAddress(@Context HttpServletRequest request, AddressWrapper addressWrapper) {
        Address address = addressWrapper.unwrap(request, context);
        address = addressService.saveAddress(address);
        ETSAddressWrapper wrapper = (ETSAddressWrapper) context.getBean(ETSAddressWrapper.class.getName());
        wrapper.wrap(address, request);
        return wrapper;
    }

}
