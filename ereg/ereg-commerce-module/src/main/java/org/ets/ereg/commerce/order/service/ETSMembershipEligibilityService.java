package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

public interface ETSMembershipEligibilityService {

    MembershipDiscreteOrderItem readActiveMembershipByCustomerAndAgency(Customer customer, Agency agency);

}
