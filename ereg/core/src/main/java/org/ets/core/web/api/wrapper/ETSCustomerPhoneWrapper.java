package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper;
import org.broadleafcommerce.core.web.api.wrapper.APIWrapper;
import org.broadleafcommerce.core.web.api.wrapper.BaseWrapper;
import org.broadleafcommerce.core.web.api.wrapper.PhoneWrapper;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsCustomerPhone")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSCustomerPhoneWrapper extends BaseWrapper implements APIWrapper<CustomerPhone>, APIUnwrapper<CustomerPhone> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String phoneName;

    @XmlElement
    protected ETSCustomerWrapper customer;

    @XmlElement
    protected PhoneWrapper phone;

    @Override
    public void wrap(CustomerPhone model, HttpServletRequest request) {
        this.id = model.getId();
        this.phoneName = model.getPhoneName();

        ETSCustomerWrapper customerWrapper = (ETSCustomerWrapper) context.getBean(ETSCustomerWrapper.class.getName());
        customerWrapper.wrap(model.getCustomer(), request);
        this.customer = customerWrapper;

        PhoneWrapper phoneWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
        phoneWrapper.wrap(model.getPhone(), request);
        this.phone = phoneWrapper;
    }

    @Override
    public CustomerPhone unwrap(HttpServletRequest request, ApplicationContext appContext) {
        CustomerPhoneService customerPhoneService = (CustomerPhoneService) appContext.getBean("blCustomerPhoneService");
        CustomerPhone customerPhone = customerPhoneService.readCustomerPhoneById(this.id);
        if (customerPhone == null) {
            customerPhone = customerPhoneService.create();
        }

        customerPhone.setPhoneName(this.phoneName);
        Phone phone1 = this.phone.unwrap(request, appContext);
        customerPhone.setPhone(phone1);
        Customer customer1 = this.customer.unwrap(request, appContext);
        customerPhone.setCustomer(customer1);

        return customerPhone;
    }

}
