package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.profile.core.dao.CustomerAddressDao;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.ets.core.web.api.wrapper.ETSCustomerAddressWrapper;
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
import java.util.ArrayList;
import java.util.List;

@Component("etsRestCustomerAddressEndpoint")
@Scope("singleton")
@Path("/customerAddress")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerAddressEndpoint implements ApplicationContextAware {

    @Resource(name="blCustomerAddressDao")
    protected CustomerAddressDao customerAddressDao;

    @Resource(name="blCustomerAddressService")
    protected CustomerAddressService customerAddressService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    @Path("/{id}")
    public ETSCustomerAddressWrapper readCustomerAddressById(@Context HttpServletRequest request, @PathParam("id") Long id) {
        CustomerAddress customerAddress = customerAddressDao.readCustomerAddressById(id);
        if (customerAddress != null) {
            ETSCustomerAddressWrapper wrapper = (ETSCustomerAddressWrapper) context.getBean(ETSCustomerAddressWrapper.class.getName());
            wrapper.wrap(customerAddress, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/customer/{id}")
    public List<ETSCustomerAddressWrapper> readActiveCustomerAddressesByCustomerId(@Context HttpServletRequest request, @PathParam("id") Long id) {
        List<CustomerAddress> customerAddresses = customerAddressDao.readActiveCustomerAddressesByCustomerId(id);
        if (customerAddresses != null && !customerAddresses.isEmpty()) {
            List<ETSCustomerAddressWrapper> wrappers = new ArrayList<ETSCustomerAddressWrapper>();
            for (CustomerAddress customerAddress : customerAddresses) {
                ETSCustomerAddressWrapper wrapper = (ETSCustomerAddressWrapper) context.getBean(ETSCustomerAddressWrapper.class.getName());
                wrapper.wrap(customerAddress, request);
                wrappers.add(wrapper);
            }
            return wrappers;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    /**
     * WRITE OPERATIONS BELOW
     * Transactions marked at the service layer.
     * Pointcuts are defined in bl-profile-applicationContext-persistence.xml
     */

    @PUT
    public ETSCustomerAddressWrapper saveCustomerAddress(@Context HttpServletRequest request, ETSCustomerAddressWrapper customerAddressWrapper) {
        CustomerAddress customerAddress = customerAddressWrapper.unwrap(request, context);
        customerAddress = customerAddressService.saveCustomerAddress(customerAddress);
        ETSCustomerAddressWrapper wrapper = (ETSCustomerAddressWrapper) context.getBean(ETSCustomerAddressWrapper.class.getName());
        wrapper.wrap(customerAddress, request);
        return wrapper;
    }

    @DELETE
    public void deleteCustomerAddress(@Context HttpServletRequest request, ETSCustomerAddressWrapper customerAddressWrapper) {
        CustomerAddress customerAddress = customerAddressWrapper.unwrap(request, context);
        customerAddressService.deleteCustomerAddressById(customerAddress.getId());
    }

    @POST
    @Path("/customer/{id}")
    public void makeCustomerAddressDefault(@Context HttpServletRequest request,
                                           @PathParam("id") Long id,
                                           ETSCustomerAddressWrapper customerAddressWrapper) {
        CustomerAddress customerAddress = customerAddressWrapper.unwrap(request, context);
        customerAddressService.makeCustomerAddressDefault(customerAddress.getId(), id);
    }
}
