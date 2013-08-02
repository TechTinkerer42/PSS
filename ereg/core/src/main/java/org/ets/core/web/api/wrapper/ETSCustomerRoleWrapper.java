package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper;
import org.broadleafcommerce.core.web.api.wrapper.APIWrapper;
import org.broadleafcommerce.core.web.api.wrapper.BaseWrapper;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.domain.CustomerRoleImpl;
import org.broadleafcommerce.profile.core.domain.Role;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsCustomerRole")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSCustomerRoleWrapper extends BaseWrapper implements APIWrapper<CustomerRole>, APIUnwrapper<CustomerRole> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected ETSCustomerWrapper customer;

    @XmlElement
    protected ETSRoleWrapper role;

    @Override
    public void wrap(CustomerRole model, HttpServletRequest request) {
        this.id = model.getId();

        ETSCustomerWrapper customerWrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
        customerWrapper.wrap(model.getCustomer(), request);
        this.customer = customerWrapper;

        ETSRoleWrapper roleWrapper = (ETSRoleWrapper) context.getBean(ETSRoleWrapper.class.getName());
        roleWrapper.wrap(model.getRole(), request);
        this.role = roleWrapper;
    }

    @Override
    public CustomerRole unwrap(HttpServletRequest request, ApplicationContext appContext) {
        CustomerRole customerRole = new CustomerRoleImpl();
        customerRole.setId(this.id);

        Customer customer1 = this.customer.unwrap(request, appContext);
        customerRole.setCustomer(customer1);

        Role role1 = this.role.unwrap(request, appContext);
        customerRole.setRole(role1);

        return customerRole;
    }

}
