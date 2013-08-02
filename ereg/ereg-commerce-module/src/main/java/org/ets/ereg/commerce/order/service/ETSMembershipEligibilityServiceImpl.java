package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("etsMembershipEligibilityService")
public class ETSMembershipEligibilityServiceImpl implements ETSMembershipEligibilityService {

    @Resource(name="blOrderService")
    private OrderService orderService;

    @Override
    public MembershipDiscreteOrderItem readActiveMembershipByCustomerAndAgency(Customer customer, Agency agency) {
        //TODO optimize to use a SQL query instead of this.
        List<Order> orders = orderService.findOrdersForCustomer(customer, OrderStatus.SUBMITTED);

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem instanceof MembershipDiscreteOrderItem) {
                    MembershipDiscreteOrderItem oi = (MembershipDiscreteOrderItem) orderItem;
                    if (oi.getAgency() != null && agency != null &&
                            oi.getAgency().getId().equals(agency.getId())) {
                        if (oi.getStartDate() == null && oi.getEndDate() == null) {
                            return oi;
                        } else {
                            DateTime now = new DateTime();
                            DateTime start = new DateTime(oi.getStartDate());
                            DateTime end = new DateTime(oi.getEndDate());
                            Interval interval = new Interval(start, end);
                            if (interval.contains(now)) {
                                return oi;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

}
