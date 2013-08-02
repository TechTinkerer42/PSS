package org.ets.ereg.common.web.controller.cart;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.cart.BroadleafCartController;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.catalog.service.ETSBatteryProductService;
import org.ets.ereg.commerce.order.dto.BatteryOrderItemRequestDTO;
import org.ets.ereg.commerce.order.service.ETSCybersourceHOPCheckoutService;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.vo.AgencyVO;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.commerce.order.vo.TestItemVO;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.scheduling.service.AgencyService;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.broadleafcommerce.vendor.cybersource.service.payment.type.MessageConstants;

@Controller
@RequestMapping("/secure/cart")
public class ETSCartController extends BroadleafCartController{
	private static Logger log = LoggerFactory
			.getLogger(ETSCartController.class);

	public static final String URL_MAPPING_VIEW = "cart/viewCart";


	@Resource(name = "shoppingCartTransformer")
	private ShoppingCartTransformer shoppingCartTransformer;

	@Resource(name = "etsOrderService")
	private ETSOrderService orderService;

	@Resource(name="blCybersourceSilentPostCheckoutService")
	private ETSCybersourceHOPCheckoutService checkoutService;

    @Resource(name="agencyService")
    private AgencyService agencyService;

    @Resource(name="blCatalogService")
    private CatalogService catalogService;

    @Resource(name="etsBatteryProductService")
    private ETSBatteryProductService batteryProductService;
    
	@RequestMapping(value = "/view", method=RequestMethod.GET)
	public String cart(HttpServletRequest request, HttpServletResponse response, Model model, @LoggedInUser ERegUser loggedInUser) throws PricingException {
		Customer customer = CustomerState.getCustomer();
		Order cart = getCart();
		boolean customerFacing;  
		if(loggedInUser.isCustomer()){
		    customerFacing = true;
		}else{
			 customerFacing = false;
		}
		HttpSession session = request.getSession();
		String submitAction;
	    Map<String, String> hopFields = checkoutService.constructAuthorizeAndDebitFields(cart, customerFacing, session.getId());

	    model.addAttribute("hopFields", hopFields);

		ShoppingCartVO cartVO =shoppingCartTransformer.transform(cart);

		for(AgencyVO agency:cartVO.getAgencies()){
			for(TestItemVO i:agency.getTests()){
				log.info("tstName:{}",i.getBooking().getTestVariation().getTest().getTestName());
				log.debug("tstCenterName:{}",i.getBooking().getTestCenter().getName());
				log.debug("tstTypeName:{}",i.getBooking().getTestVariation().getDeliveryModeType().getDescription());
				log.debug("tstDateNTime:{}",i.getBooking().getAppointmentDateTime());
				log.debug("Language:{}",i.getBooking().getTestVariation().getLanguageType().getDescription());
				log.debug("amount:{}",i.getTestDiscreteOrderItem().getAveragePrice().stringValue());
				log.debug("adjustment :{}",i.getTestDiscreteOrderItem().getAverageAdjustmentValue().stringValue());
				log.debug("after substraction :{}",(i.getTestDiscreteOrderItem().getAveragePrice().subtract(i.getTestDiscreteOrderItem().getAverageAdjustmentValue())).stringValue());
			}

		}



		for(TestItemVO i:cartVO.getTests()){
			log.info("tstName:{}",i.getBooking().getTestVariation().getTest().getTestName());
			log.debug("tstCenterName:{}",i.getBooking().getTestCenter().getName());
			log.debug("tstTypeName:{}",i.getBooking().getTestVariation().getDeliveryModeType().getDescription());
			log.debug("tstDateNTime:{}",i.getBooking().getAppointmentDateTime());
			log.debug("Language:{}",i.getBooking().getTestVariation().getLanguageType().getDescription());
			log.debug("amount:{}",i.getTestDiscreteOrderItem().getAveragePrice().stringValue());
			log.debug("adjustment :{}",i.getTestDiscreteOrderItem().getAverageAdjustmentValue().stringValue());
			log.debug("after substraction :{}",(i.getTestDiscreteOrderItem().getAveragePrice().subtract(i.getTestDiscreteOrderItem().getAverageAdjustmentValue())).stringValue());
		}



		log.debug("Test With Past Battery:{}",cartVO.getTestsWithPastBattery().size());
		//log.debug("Test With charged Battery:{}",cartVO.getTests().size()); //???



		BatteryProduct batteryProduct=(BatteryProduct)catalogService.findProductById(8L);// TODO FIX



		model.addAttribute("hopFields", hopFields);
    	model.addAttribute("cartVO", cartVO);
    	model.addAttribute("customer", customer);
    	model.addAttribute("batteryProduct", batteryProduct);

    	if(!cartVO.getTotalAmount().equals(BigDecimal.ZERO)){
    		submitAction=hopFields.get(MessageConstants.CYBERSOURCE_SERVER_URL);
    		//submitAction=getZeroAmountAction();

    	}else{
    		submitAction=getZeroAmountAction();
    	}

    	// TODO Confirm if cart will always have test
    /*	if(null!=cartVO.getTests() && !cartVO.getTests().isEmpty()){
    		model.addAttribute("testCenterId", cartVO.getTests().get(0).getBooking().getTestCenter().getId());
    	}*/

    	if(!cartVO.getAgencies().isEmpty() && null!=cartVO.getAgencies().get(0) && !cartVO.getAgencies().get(0).getTests().isEmpty()){
    		model.addAttribute("testCenterId", cartVO.getAgencies().get(0).getTests().get(0).getBooking().getTestCenter().getId());
    	}

    	model.addAttribute("action", submitAction);

    	model.addAttribute("address", customer.getCustomerAddresses().get(0).getAddress());


        return URL_MAPPING_VIEW;
    }



	

	@RequestMapping(value = "/add/battery", method = RequestMethod.GET)
    public String processAddBattery(@RequestParam("agencyId") Long agencyId ,

    		HttpServletRequest request, HttpServletResponse response, Model model
    		      ) throws IOException, AddToCartException, PricingException {
        log.info("Method add battery invoked");
        Order cart = CartState.getCart();
        ShoppingCartVO cartVO =shoppingCartTransformer.transform(cart);


        AgencyVO agencyVO=cartVO.getAgency(agencyId);

        //Long batteryProductId=getBatteryProductId((ETSProduct)test.getTestDiscreteOrderItem().getProduct());

        Long batteryProductId=(agencyVO.getBatteryProduct()).getId();

        Product batteryProduct=catalogService.findProductById(batteryProductId);
        Agency agency = agencyService.getAgencyById(agencyId);

        if(null==agencyVO.getBatterySubscription()){ // check if battery not already subscribed
	        BatteryOrderItemRequestDTO itemRequest =new BatteryOrderItemRequestDTO();
	        itemRequest.setCategoryId( batteryProduct.getDefaultCategory().getId());
	        itemRequest.setProductId(batteryProductId);
	        itemRequest.setSkuId(batteryProduct.getDefaultSku().getId());
	        itemRequest.setQuantity(1);
	        itemRequest.setAgency(agency);

	        orderService.addItem(cart.getId(), itemRequest, true);
        }
        return viewCart();
    }

	private String getZeroAmountAction() {

		return "/secure/cybersource/zerocheckout";
	}

	private Order getCart() {
		return CartState.getCart();

	}




    @RequestMapping(value = "/remove/{orderItemId}", method = RequestMethod.GET)
    public String remove(@PathVariable("orderItemId") String orderItemId,HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, PricingException, RemoveFromCartException {
        log.info("Method remove invoked");
        Order cart = CartState.getCart();

        cart = orderService.removeItem(cart.getId(), Long.valueOf(orderItemId), false);
        cart = orderService.save(cart, true);
        CartState.setCart(cart);
        removeBatteryWithNoTestsFromCart();
         return viewCart();

    }

	private void removeBatteryWithNoTestsFromCart() throws RemoveFromCartException, PricingException {
		 Order cart = CartState.getCart();
		 ShoppingCartVO cartVO =shoppingCartTransformer.transform(cart);
		 for(AgencyVO agencyVO:cartVO.getAgencies()){
			 if(agencyVO.getTests().isEmpty() && agencyVO.getBatteryItem()!=null){
				 cart = orderService.removeItem(cart.getId(),  agencyVO.getBatteryItem().getBatteryDiscreteOrderitem().getId(), false);
				 cart = orderService.save(cart, true);
			        CartState.setCart(cart);

			 }


		 }



	}

	private String viewCart() {
		return "redirect:/secure/cart/view";
	}


	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public String emptyCart(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, PricingException, RemoveFromCartException {
		log.info("Method clear cart invoked");
	        Order cart = CartState.getCart();
	        List<OrderItem> orderItemsToBeRemoved = new ArrayList<OrderItem>();

	        orderItemsToBeRemoved.addAll(orderService.getTestsFromCart(cart));// Removing tests will remove the membership as well

            for (OrderItem orderItem : orderItemsToBeRemoved) {
            	cart = orderService.removeItem(cart.getId(), orderItem.getId(), false);
	            cart = orderService.save(cart, true);
            }

	        return viewCart();
	    }


}
