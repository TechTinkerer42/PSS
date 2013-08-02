package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.PaymentInfoImpl;
import org.broadleafcommerce.core.payment.service.PaymentInfoFactory;
import org.ets.ereg.commerce.order.type.ETSPaymentInfoType;
import org.springframework.stereotype.Service;

@Service("etsZeroAmountPaymentInfoFactory")
public class ETSZeroAmountPaymentInfoFactoryImpl implements PaymentInfoFactory {

    /**
     * Constructs a default Zero Amount PaymentInfo object based on the passed in order.
     * Sets the basic information necessary to complete an order.
     *
     * @param order
     * @return PaymentInfo
     */
    @Override
    public PaymentInfo constructPaymentInfo(Order order) {
        PaymentInfoImpl paymentInfo = new PaymentInfoImpl();
        paymentInfo.setOrder(order);
        paymentInfo.setType(ETSPaymentInfoType.ZERO_AMOUNT);
        paymentInfo.setReferenceNumber(String.valueOf(order.getId()));
        paymentInfo.setAmount(order.getRemainingTotal());

        return paymentInfo;
    }

}