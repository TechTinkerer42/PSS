package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.service.PaymentContext;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.module.AcceptAndPassthroughModule;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.ets.ereg.commerce.order.type.ETSPaymentInfoType;

public class ETSZeroAmountPaymentModule extends AcceptAndPassthroughModule {

    @Override
    public PaymentResponseItem processAuthorizeAndDebit(PaymentContext paymentContext, Money amountToDebit, PaymentResponseItem responseItem) throws PaymentException {
        if (amountToDebit.isZero()) {
            responseItem.setTransactionSuccess(true);
            responseItem.setTransactionAmount(amountToDebit);
            return responseItem;
       }  else {
            throw new PaymentException("Cannot Complete Zero Amount Checkout - amountToDebit = " + amountToDebit);
        }
    }

    @Override
    public Boolean isValidCandidate(PaymentInfoType paymentType) {
        return ETSPaymentInfoType.ZERO_AMOUNT.equals(paymentType);
    }

}
