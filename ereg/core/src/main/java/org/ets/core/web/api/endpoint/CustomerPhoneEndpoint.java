package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.profile.core.dao.CustomerPhoneDao;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.ets.core.web.api.wrapper.ETSCustomerPhoneWrapper;
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

@Component("etsRestCustomerPhoneEndpoint")
@Scope("singleton")
@Path("/customerPhone")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerPhoneEndpoint implements ApplicationContextAware {

    @Resource(name="blCustomerPhoneDao")
    protected CustomerPhoneDao customerPhoneDao;

    @Resource(name="blCustomerPhoneService")
    protected CustomerPhoneService customerPhoneService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    @Path("/{id}")
    public ETSCustomerPhoneWrapper readCustomerPhoneById(@Context HttpServletRequest request, @PathParam("id") Long id) {
        CustomerPhone customerPhone = customerPhoneDao.readCustomerPhoneById(id);
        if (customerPhone != null) {
            ETSCustomerPhoneWrapper wrapper = (ETSCustomerPhoneWrapper) context.getBean(ETSCustomerPhoneWrapper.class.getName());
            wrapper.wrap(customerPhone, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/customer/{id}")
    public List<ETSCustomerPhoneWrapper> readActiveCustomerPhonesByCustomerId(@Context HttpServletRequest request, @PathParam("id") Long id) {
        List<CustomerPhone> customerPhones = customerPhoneDao.readActiveCustomerPhonesByCustomerId(id);
        if (customerPhones != null && !customerPhones.isEmpty()) {
            List<ETSCustomerPhoneWrapper> wrappers = new ArrayList<ETSCustomerPhoneWrapper>();
            for (CustomerPhone customerPhone : customerPhones) {
                ETSCustomerPhoneWrapper wrapper = (ETSCustomerPhoneWrapper) context.getBean(ETSCustomerPhoneWrapper.class.getName());
                wrapper.wrap(customerPhone, request);
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
    public ETSCustomerPhoneWrapper saveCustomerPhone(@Context HttpServletRequest request, ETSCustomerPhoneWrapper customerPhoneWrapper) {
        CustomerPhone customerPhone = customerPhoneWrapper.unwrap(request, context);
        customerPhone = customerPhoneService.saveCustomerPhone(customerPhone);
        ETSCustomerPhoneWrapper wrapper = (ETSCustomerPhoneWrapper) context.getBean(ETSCustomerPhoneWrapper.class.getName());
        wrapper.wrap(customerPhone, request);
        return wrapper;
    }

    @DELETE
    public void deleteCustomerPhone(@Context HttpServletRequest request, ETSCustomerPhoneWrapper customerPhoneWrapper) {
        CustomerPhone customerPhone = customerPhoneWrapper.unwrap(request, context);
        customerPhoneService.deleteCustomerPhoneById(customerPhone.getId());
    }

    @POST
    @Path("/customer/{id}")
    public void makeCustomerPhoneDefault(@Context HttpServletRequest request,
                                           @PathParam("id") Long id,
                                           ETSCustomerPhoneWrapper customerPhoneWrapper) {
        CustomerPhone customerPhone = customerPhoneWrapper.unwrap(request, context);
        customerPhoneService.makeCustomerPhoneDefault(customerPhone.getId(), id);
    }
}
