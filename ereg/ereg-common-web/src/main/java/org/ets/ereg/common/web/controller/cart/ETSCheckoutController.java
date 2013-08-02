package org.ets.ereg.common.web.controller.cart;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.PaymentInfoFactory;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.order.CartState;
import org.ets.ereg.commerce.order.type.ETSPaymentInfoType;
import org.ets.ereg.common.web.util.Constant;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.broadleafcommerce.vendor.cybersource.web.controller.BroadleafCybersourceSilentPostController;

@Controller
@RequestMapping("/secure/cybersource")
public class ETSCheckoutController extends BroadleafCybersourceSilentPostController {

    private static Logger log = LoggerFactory
            .getLogger(ETSCheckoutController.class);
    public static final String URL_MAPPING_VIEW = "cart/viewCart";
    
    @Resource(name = "generateBase64StringBusinessService")
	private GenerateBase64StringBusinessService generateBase64StringBusinessService;

    @Resource(name = "etsZeroAmountPaymentInfoFactory")
    protected PaymentInfoFactory zeroAmountPaymentInfoFactory;

    @RequestMapping(value = "/zerocheckout", method = RequestMethod.POST)
    public String processZeroCheckout(Model model, HttpServletRequest request,
                                      HttpServletResponse response) throws CheckoutException, PricingException {

        Order cart = CartState.getCart();
        if (cart != null) {
            Map<PaymentInfo, Referenced> payments = new HashMap<PaymentInfo, Referenced>();

            orderService.removePaymentsFromOrder(cart, ETSPaymentInfoType.ZERO_AMOUNT);

            PaymentInfo paymentInfo = zeroAmountPaymentInfoFactory.constructPaymentInfo(cart);
            cart.getPaymentInfos().add(paymentInfo);
            payments.put(paymentInfo, paymentInfo.createEmptyReferenced());

            CheckoutResponse checkoutResponse = checkoutService.performCheckout(cart, payments);

            if (!checkoutResponse.getPaymentResponse().getResponseItems().get(paymentInfo).getTransactionSuccess()){
                //TODO handle putting error message on model
                return getCheckoutView();
            }

            return getConfirmationView(cart.getOrderNumber());
        }

        return getCartPageRedirect();
    }
  

    @Override
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String processCybersourceSilentPostAuthorizeAndDebitSuccess(Model model, HttpServletRequest request,
            HttpServletResponse response) throws CheckoutException, PricingException {
        return super.processCybersourceSilentPostAuthorizeAndDebitSuccess(model, request, response);
    }

    @Override
    @RequestMapping(value = "/decline", method = RequestMethod.POST)
    public String processCybersourceSilentPostAuthorizeAndDebitDecline(Model model, HttpServletRequest request,
            HttpServletResponse response) throws CheckoutException, PricingException {
        return super.processCybersourceSilentPostAuthorizeAndDebitDecline(model, request, response);
    }

    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String processCybersourceSilentPostAuthorizeAndDebitError(Model model, HttpServletRequest request,
            HttpServletResponse response) throws CheckoutException, PricingException {
        return super.processCybersourceSilentPostAuthorizeAndDebitDecline(model, request, response);
    }

    @Override
    public String getBaseConfirmationView() {
        return "redirect:/secure/orderConfirmation";
    }
    
    
    @Override
    public String getCartPageRedirect() {
    	return "redirect:/secure/cart/view";
    }
    
    @Override
    public String getCheckoutView() {
    	return "redirect:/secure/cart/view"; //errpr page
    }
    
    @Override
    protected String getConfirmationView(String orderNumber) {

    	String username = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		String encodeOrderNumber = Constant.URL_DECODE_IDENTIFIER+generateBase64StringBusinessService.encodeBase64String(username+Constant.ENCODING_DELIMITTER + orderNumber);
        return getBaseConfirmationView() + "/" + encodeOrderNumber;
    }
   

}
