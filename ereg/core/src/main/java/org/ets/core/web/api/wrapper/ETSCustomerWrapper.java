package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper;
import org.broadleafcommerce.core.web.api.wrapper.CustomerWrapper;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsCustomer")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSCustomerWrapper extends CustomerWrapper implements APIUnwrapper<Customer> {
    //TODO create an ETSCustomer

    @XmlElement
    private String etsProperty;

    @Override
    public void wrap(Customer model, HttpServletRequest request) {
        //First, call the super method to get the default behavior
        super.wrap(model, request);
        //Next, cast the customer passed in to ETSCustomer and use it to set the appropriate properties
        this.etsProperty = (model).getUsername();
    }

    @Override
    //TODO this method should return an ETSCustomer
    public Customer unwrap(HttpServletRequest request, ApplicationContext applicationContext) {
        CustomerService customerService = (CustomerService) applicationContext.getBean("blCustomerService");
        Customer customer = customerService.readCustomerById(this.id);
        if (customer == null) {
            customer = customerService.createCustomer();
        }
        //TODO implement rest of properties
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmailAddress(this.emailAddress);

        return customer;
    }
}
