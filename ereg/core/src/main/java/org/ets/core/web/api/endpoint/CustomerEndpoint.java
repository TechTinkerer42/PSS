package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.profile.core.dao.CustomerDao;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.ets.core.web.api.wrapper.ETSCustomerWrapper;
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

@Component("etsRestCustomerEndpoint")
@Scope("singleton")
@Path("/customer")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerEndpoint implements ApplicationContextAware {

    @Resource(name="blCustomerDao")
    protected CustomerDao customerDao;

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    @Path("/{id}")
    public ETSCustomerWrapper readCustomerById(@Context HttpServletRequest request, @PathParam("id") Long id) {
        Customer customer = customerDao.readCustomerById(id);
        if (customer != null) {
            ETSCustomerWrapper wrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
            wrapper.wrap(customer, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    public List<ETSCustomerWrapper> readCustomersByUsernameOrEmail(@Context HttpServletRequest request,
                                                     @QueryParam("username") String username,
                                                     @QueryParam("email") String email) {
        List<Customer> customers = null;
        if (username != null ) {
            customers = customerDao.readCustomersByUsername(username);
        } else if (email != null) {
            customers = customerDao.readCustomersByEmail(email);
        }

        if (customers != null && !customers.isEmpty()) {
            List<ETSCustomerWrapper> wrappers = new ArrayList<ETSCustomerWrapper>();
            for (Customer customer : customers) {
                ETSCustomerWrapper wrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
                wrapper.wrap(customer, request);
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
    public ETSCustomerWrapper saveCustomer(@Context HttpServletRequest request, ETSCustomerWrapper customerWrapper) {
        Customer customer = customerWrapper.unwrap(request, context);
        customer = customerService.saveCustomer(customer);
        ETSCustomerWrapper wrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
        wrapper.wrap(customer, request);
        return wrapper;
    }
}

