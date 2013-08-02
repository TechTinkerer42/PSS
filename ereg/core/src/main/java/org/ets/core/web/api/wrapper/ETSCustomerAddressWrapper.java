package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.*;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsCustomerAddress")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSCustomerAddressWrapper extends BaseWrapper implements APIWrapper<CustomerAddress>, APIUnwrapper<CustomerAddress> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String addressName;

    @XmlElement
    protected ETSCustomerWrapper customer;

    @XmlElement
    protected ETSAddressWrapper address;


    @Override
    public void wrap(CustomerAddress model, HttpServletRequest request) {
        this.id = model.getId();
        this.addressName = model.getAddressName();

        ETSCustomerWrapper customerWrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
        customerWrapper.wrap(model.getCustomer(), request);
        this.customer = customerWrapper;

        ETSAddressWrapper addressWrapper = (ETSAddressWrapper) context.getBean(ETSAddressWrapper.class.getName());
        addressWrapper.wrap(model.getAddress(), request);
        this.address = addressWrapper;
    }

    @Override
    public CustomerAddress unwrap(HttpServletRequest request, ApplicationContext appContext) {
        CustomerAddressService customerAddressService = (CustomerAddressService) appContext.getBean("blCustomerAddressService");
        CustomerAddress customerAddress = customerAddressService.readCustomerAddressById(this.id);
        if (customerAddress == null) {
            customerAddress = customerAddressService.create();
        }

        customerAddress.setAddressName(this.addressName);
        Address address1 = this.address.unwrap(request, appContext);
        customerAddress.setAddress(address1);
        Customer customer1 = this.customer.unwrap(request, appContext);
        customerAddress.setCustomer(customer1);

        return customerAddress;
    }
}
