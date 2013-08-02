package org.ets.core.web.api.endpoint;

import org.broadleafcommerce.profile.core.dao.RoleDao;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.domain.Role;
import org.ets.core.web.api.wrapper.ETSCustomerRoleWrapper;
import org.ets.core.web.api.wrapper.ETSRoleWrapper;
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

@Component("etsRestRoleEndpoint")
@Scope("singleton")
@Path("/role")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RoleEndpoint implements ApplicationContextAware {

    @Resource(name="blRoleDao")
    protected RoleDao roleDao;

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @GET
    @Path("/{name}")
    public ETSRoleWrapper readRoleByName(@Context HttpServletRequest request, @PathParam("name") String name) {
        Role role = roleDao.readRoleByName(name);
        if (role != null) {
            ETSRoleWrapper wrapper = (ETSRoleWrapper) context.getBean(ETSRoleWrapper.class.getName());
            wrapper.wrap(role, request);
            return wrapper;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/customer/{id}")
    public List<ETSCustomerRoleWrapper> readCustomerRolesByCustomerId(@Context HttpServletRequest request, @PathParam("id") Long id) {
        List<CustomerRole> customerRoles = roleDao.readCustomerRolesByCustomerId(id);
        if (customerRoles != null && !customerRoles.isEmpty()) {
            List<ETSCustomerRoleWrapper> wrappers = new ArrayList<ETSCustomerRoleWrapper>();
            for (CustomerRole customerRole : customerRoles) {
                ETSCustomerRoleWrapper wrapper = (ETSCustomerRoleWrapper) context.getBean(ETSCustomerRoleWrapper.class.getName());
                wrapper.wrap(customerRole, request);
                wrappers.add(wrapper);
            }
            return wrappers;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }


    /**
     * WRITE OPERATIONS BELOW
     * TODO add transaction pointcut for RoleDao
     * Transaction is currently marked at (org.broadleafcommerce.profile.core.service.CustomerService.register*(..))
     * in /bl-profile-applicationContext-persistence.xml
     */

    @POST
    public void addRoleToCustomer(@Context HttpServletRequest request, ETSCustomerRoleWrapper customerRoleWrapper) {
        CustomerRole customerRole = customerRoleWrapper.unwrap(request, context);
        roleDao.addRoleToCustomer(customerRole);
    }
}
