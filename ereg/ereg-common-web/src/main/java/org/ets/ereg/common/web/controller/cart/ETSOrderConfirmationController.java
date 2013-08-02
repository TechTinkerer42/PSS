package org.ets.ereg.common.web.controller.cart;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.web.controller.checkout.BroadleafOrderConfirmationController;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/secure/orderConfirmation")

public class ETSOrderConfirmationController  {

    private static Logger log = LoggerFactory
            .getLogger(ETSOrderConfirmationController.class);
    
    private static final String URL_CHECKOUT_CONFIRMATION = "cart/checkout/checkoutConfirmation";

    @Resource(name = "shoppingCartTransformer")
	private ShoppingCartTransformer shoppingCartTransformer;	
	
	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;
	
	@Resource(name = "profileBusinessService")
	private ProfileBusinessService profileBusinessService;

   
    @RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET)
    public String displayOrderConfirmationByOrderNumber(@PathVariable("orderNumber") String orderNumber,
            Model model, HttpServletRequest request, HttpServletResponse response) {
    	
    	Customer customer = CustomerState.getCustomer();
    	
    	log.info("orderNumber - " + orderNumber);
		 
		if (customer != null) {
        	Order order = getOrder(customer.getId(),orderNumber); 
        	
        	if (order != null && customer.equals(order.getCustomer())) {
        		ProfileVO profile = profileBusinessService.readProfileById(customer.getId());
        		
        		ShoppingCartVO cartVO =shoppingCartTransformer.transform(order);
        		
        		model.addAttribute("bookings", cartVO.getAllBookings());
        		model.addAttribute("profile", profile);
        		model.addAttribute("mode", "SELF");  
        		
				
        		return URL_CHECKOUT_CONFIRMATION;
        	}
        }
        	
		return null;
        
    }
    
    private Order getOrder(Long customerId, String orderNumber) {		
		return  orderService.findOrderByOrderNumber(orderNumber);
    	/*Order order1=orderService.createNewCartForCustomer(CustomerState.getCustomer());		
		order1=shoppingCartTransformer.getTempOrder(order1);
		return order1;*/
    }
    
}


