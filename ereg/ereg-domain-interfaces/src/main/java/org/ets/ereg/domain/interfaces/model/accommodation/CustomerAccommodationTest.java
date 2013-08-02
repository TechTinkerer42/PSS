package org.ets.ereg.domain.interfaces.model.accommodation;

import java.util.Date;

import org.ets.ereg.domain.interfaces.model.accommodation.id.CustomerAccommodationTestId;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface CustomerAccommodationTest {

    CustomerAccommodationTestId getCustomerAccommodationTestId();

    void setCustomerAccommodationTestId(
            CustomerAccommodationTestId customerAccommodationTestId);

    ETSCustomer getCustomer();

    void setCustomer(ETSCustomer customer);

    Test getTest();

    void setTest(Test test);

    AccommodationTypeValue getAccommodationTypeValue();

    void setAccommodationTypeValue(AccommodationTypeValue accommodationTypeValue);

    Date getExpiryDate();

    void setExpiryDate(Date expiryDate);

    Date getApprovedDate();

    void setApprovedDate(Date approvedDate);

    ProgramAccommodationDeliveryMode getProgramAccommodationDeliveryMode();

    void setProgramAccommodationDeliveryMode(
            ProgramAccommodationDeliveryMode programAccommodationDeliveryMode);

    String getOtherAccommodationTypeText();
    void setOtherAccommodationTypeText(String otherAccommodationTypeText);

    AccommodationType getAccommodationType();
}
