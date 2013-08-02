package org.ets.ereg.common.web.controller.cart;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.ets.ereg.commerce.order.service.ETSCancellationService;
import org.ets.ereg.common.web.form.CancelOrderForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.broadleafcommerce.oms.cancellation.service.CancellationException;
import com.broadleafcommerce.oms.cancellation.service.CancellationService;

@Controller
@RequestMapping("/secure/order")
public class ETSOrderController extends BroadleafCartController{
	private static Logger log = LoggerFactory
			.getLogger(ETSOrderController.class);

	public static final String URL_CONFIRM_CANCEL = "order/canceltest";

	private static final String VIEW_FORM_ATTRIBUTE = "CancelOrderForm";

	private static final String QUANTITY = "quantity";

	private static final String URL_TEST_CANCELLED = "order/testCancelled";
	
    
    @Resource(name="etsCancellationService")
    private ETSCancellationService cancellationService;    
    
    @Resource(name="blCancellationService")
    private CancellationService CancellingService;

	@RequestMapping(value = "/canceltest", method = RequestMethod.GET)
	public String cancelTest(@RequestParam("fulfillmentGroupItemId") Long fulfillmentGroupItemId,	
			@ModelAttribute(ETSOrderController.VIEW_FORM_ATTRIBUTE) CancelOrderForm cancelOrderForm,
			HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, PricingException, RemoveFromCartException, CancellationException {
		
		int quantity=1;		
		log.info("Method cancel test invoked");
		if(null!=request.getParameter(QUANTITY)){
			quantity=Integer.parseInt(request.getParameter(QUANTITY));
		}
		
		cancelOrderForm.setCancelTest(cancellationService.generateCancellationDetails(fulfillmentGroupItemId, quantity));
		
		return URL_CONFIRM_CANCEL;
	 }
	


	@RequestMapping(value = "/canceltest", method = RequestMethod.POST)
	public String cancelTestConfirmed(
			@RequestParam("fulfillmentGroupItemId") Long fulfillmentGroupItemId,
			@ModelAttribute(ETSOrderController.VIEW_FORM_ATTRIBUTE) CancelOrderForm cancelOrderForm,
			HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, PricingException, RemoveFromCartException, CancellationException {
		
		log.info("Method cancelTestConfirmed invoked");
		int quantity=1;	
		if(null!=request.getParameter(QUANTITY)){
			quantity=Integer.parseInt(request.getParameter(QUANTITY));
		}
		
		cancelOrderForm.setCancelTest(cancellationService.generateCancellationDetails(fulfillmentGroupItemId, quantity));
		
	//	CancellingService.cancel(cancelOrderForm.getCancelTest().getTestItem().getTestFulfillmentGroupItem(), cancelOrderForm.getCancelTest().getQuantity());
		
		  return URL_TEST_CANCELLED;
	}
	


}
