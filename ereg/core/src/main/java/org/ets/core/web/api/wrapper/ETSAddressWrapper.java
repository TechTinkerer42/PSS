package org.ets.core.web.api.wrapper;

import org.broadleafcommerce.core.web.api.wrapper.AddressWrapper;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etsAddress")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ETSAddressWrapper extends AddressWrapper {
    //TODO create an ETSAddress

    @XmlElement
    private String etsProperty;

    @Override
    public void wrap(Address model, HttpServletRequest request) {
        super.wrap(model, request);
        //Next, cast the address passed in to ETSAddress and use it to set the appropriate properties
        this.etsProperty = (model).getAddressLine1();
    }

    @Override
    //TODO this method should return an ETSAddress
    public Address unwrap(HttpServletRequest request, ApplicationContext appContext) {
        AddressService addressService = (AddressService) appContext.getBean("blAddressService");

        Address address = addressService.readAddressById(this.id);
        if (address == null) {
            address = addressService.create();
        }

        //TODO implement rest of properties
        address.setFirstName(this.firstName);
        address.setLastName(this.lastName);
        address.setAddressLine1(this.addressLine1);
        address.setAddressLine2(this.addressLine2);
        address.setCity(this.city);

        if (this.state != null) {
            address.setState(this.state.unwrap(request, appContext));
        }

        if (this.country != null) {
            address.setCountry(this.country.unwrap(request, appContext));
        }

        address.setPostalCode(this.postalCode);
        address.setPrimaryPhone(this.primaryPhone);
        address.setSecondaryPhone(this.secondaryPhone);
        address.setCompanyName(this.companyName);
        address.setBusiness(this.isBusiness);
        address.setDefault(this.isDefault);

        return address;
    }
}
