package org.ets.ereg.commerce.order.service;

import com.broadleafcommerce.vendor.cybersource.service.payment.CybersourceSilentPostCheckoutService;
import org.broadleafcommerce.core.order.domain.Order;

import java.util.Map;

public interface ETSCybersourceHOPCheckoutService extends CybersourceSilentPostCheckoutService {

    public Map<String, String> constructAuthorizeAndDebitFields(Order order, boolean customerFacing, String sessionId);

}
