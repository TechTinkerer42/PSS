package org.ets.ereg.commerce.order.type;

import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;

public class ETSPaymentInfoType extends PaymentInfoType {

    private static final long serialVersionUID = 1L;
    public static final PaymentInfoType ZERO_AMOUNT = new PaymentInfoType("ZERO_AMOUNT", "Zero Amount");

}
