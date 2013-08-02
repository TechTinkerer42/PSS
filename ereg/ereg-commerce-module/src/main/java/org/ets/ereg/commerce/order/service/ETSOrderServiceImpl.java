package org.ets.ereg.commerce.order.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.common.business.util.Constant;
import org.ets.ereg.common.business.util.GenerateBase64String;
import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.ets.ereg.domain.interfaces.commerce.order.RescheduleTestRequest;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("etsOrderService")
public class ETSOrderServiceImpl implements ETSOrderService {

	@Resource(name="etsCustomOrderService")
	private OrderService orderService;
	
	@Resource(name="blEmailService")
    protected EmailService emailService;
	
	@Resource(name="orderConfirmationEmailInfo")
    protected EmailInfo orderConfirmationEmailInfo;
	
	@Resource(name = "shoppingCartTransformer")
	private ShoppingCartTransformer shoppingCartTransformer;	
	
    @Resource(name = "generateBase64String")
    private GenerateBase64String generateBase64String;

	@Override
	public Order addTest(AddTestRequest addTestRequest) throws AddToCartException {	
		
		return addItem(
				addTestRequest.getOrderId(), addTestRequest.getTestItemRequest(), addTestRequest.isPriceOrder());
		
	}
	
	@Override
	public Order addRescheduleTest(RescheduleTestRequest rescheduleTestRequest)
			throws AddToCartException {

		return addItemWithPriceOverrides(
				rescheduleTestRequest.getOrderId(), rescheduleTestRequest.getRescheduleTestItemRequest(), rescheduleTestRequest.isPriceOrder());
	}

	@Override
	public List<Order> bulkAddTest(List<AddTestRequest> addTestRequests) throws AddToCartException {
		
		Order order=null;
		List<Order> orders = Collections.emptyList();
		
		if(addTestRequests!=null && !addTestRequests.isEmpty()){
			orders = new ArrayList<Order>();			
			for(AddTestRequest addTestRequest:addTestRequests){
				order=addTest(addTestRequest);
				orders.add(order);
			}
		}
		
		return orders;
	}

	@Override
	public Order removeItem(Long orderId, Long orderItemId, boolean priceOrder) throws RemoveFromCartException {
		
		return orderService.removeItem(orderId, orderItemId, priceOrder);
	}

	@Override
	public Order createNewCartForCustomer(Customer customer) {
		return orderService.createNewCartForCustomer(customer);
	}

	@Override
	public Order createNamedOrderForCustomer(String name, Customer customer) {
		return orderService.createNamedOrderForCustomer(name, customer);
	}

	@Override
	public Order findNamedOrderForCustomer(String name, Customer customer) {
		return orderService.findNamedOrderForCustomer(name, customer);
	}

	@Override
	public Order findOrderById(Long orderId) {
		return orderService.findOrderById(orderId);
	}

	@Override
	public Order findCartForCustomer(Customer customer) {
		return orderService.findCartForCustomer(customer);
	}

	@Override
	public List<Order> findOrdersForCustomer(Customer customer) {
		return orderService.findOrdersForCustomer(customer);
	}

	@Override
	public List<Order> findOrdersForCustomer(Customer customer,
			OrderStatus status) {		
		return orderService.findOrdersForCustomer(customer, status);
	}

	@Override
	public Order findOrderByOrderNumber(String orderNumber) {		
		return orderService.findOrderByOrderNumber(orderNumber);
	}

	@Override
	public List<PaymentInfo> findPaymentInfosForOrder(Order order) {		
		return orderService.findPaymentInfosForOrder(order);
	}

	@Override
	public PaymentInfo addPaymentToOrder(Order order, PaymentInfo payment,
			Referenced securePaymentInfo) {		
		return orderService.addPaymentToOrder(order, payment, securePaymentInfo);
	}

	@Override
	public Order save(Order order, Boolean priceOrder) throws PricingException {		
		return orderService.save(order, priceOrder);
	}

	@Override
	public void cancelOrder(Order order) {
			orderService.cancelOrder(order);
		
	}

	@Override
	public Order addOfferCode(Order order, OfferCode offerCode,
			boolean priceOrder) throws PricingException,
			OfferMaxUseExceededException {
			return orderService.addOfferCode(order, offerCode, priceOrder);
	}

	@Override
	public Order removeOfferCode(Order order, OfferCode offerCode,
			boolean priceOrder) throws PricingException {		
		return orderService.removeOfferCode(order, offerCode, priceOrder);
	}

	@Override
	public Order removeAllOfferCodes(Order order, boolean priceOrder)
			throws PricingException {	
		return orderService.removeAllOfferCodes(order, priceOrder);
	}

	@Override
	public Order getNullOrder() {		
		return orderService.getNullOrder();
	}

	@Override
	public boolean getAutomaticallyMergeLikeItems() {		
		return orderService.getAutomaticallyMergeLikeItems();
	}

	@Override
	public void setAutomaticallyMergeLikeItems(
			boolean automaticallyMergeLikeItems) {
		orderService.setAutomaticallyMergeLikeItems(automaticallyMergeLikeItems);
	}

	@Override
	public Order confirmOrder(Order order) {		
		return orderService.confirmOrder(order);
	}

	@Override
	public OrderItem findLastMatchingItem(Order order, Long skuId,
			Long productId) {
		return orderService.findLastMatchingItem(order, skuId, productId);
	}

	@Override
	public OrderItem addGiftWrapItemToOrder(Order order,
			GiftWrapOrderItemRequest itemRequest, boolean priceOrder)
			throws PricingException {
		return orderService.addGiftWrapItemToOrder(order, itemRequest, priceOrder);
	}

	@Override
	public Order addItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO,
			boolean priceOrder) throws AddToCartException {		
		return orderService.addItem(orderId, orderItemRequestDTO, priceOrder);
	}

	@Override
	public Order updateItemQuantity(Long orderId,
			OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
			throws UpdateCartException, RemoveFromCartException {
		return orderService.updateItemQuantity(orderId, orderItemRequestDTO, priceOrder);
	}

	@Override
	public boolean isMoveNamedOrderItems() {		
		return orderService.isMoveNamedOrderItems();
	}

	@Override
	public void setMoveNamedOrderItems(boolean moveNamedOrderItems) {
		
		orderService.setMoveNamedOrderItems(moveNamedOrderItems);
	}

	@Override
	public boolean isDeleteEmptyNamedOrders() {
		
		return orderService.isDeleteEmptyNamedOrders();
	}

	@Override
	public void setDeleteEmptyNamedOrders(boolean deleteEmptyNamedOrders) {
		orderService.setDeleteEmptyNamedOrders(deleteEmptyNamedOrders);
	}

	@Override
	public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem,
			boolean priceOrder) throws RemoveFromCartException,
			AddToCartException {
		return orderService.addItemFromNamedOrder(namedOrder, orderItem, priceOrder);
	}

	@Override
	public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem,
			int quantity, boolean priceOrder) throws RemoveFromCartException,
			AddToCartException, UpdateCartException {
		return orderService.addItemFromNamedOrder(namedOrder, orderItem, priceOrder);
	}

	@Override
	public Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder)
			throws RemoveFromCartException, AddToCartException {
		return orderService.addAllItemsFromNamedOrder(namedOrder, priceOrder);
	}

	@Override
	public void removeAllPaymentsFromOrder(Order order) {
		orderService.removeAllPaymentsFromOrder(order);
		
	}

	@Override
	public void removePaymentsFromOrder(Order order,
			PaymentInfoType paymentInfoType) {
		orderService.removePaymentsFromOrder(order, paymentInfoType);
	}

	@Override
	public void removePaymentFromOrder(Order order, PaymentInfo paymentInfo) {
		orderService.removePaymentFromOrder(order, paymentInfo);
	}

	@Override
	public void deleteOrder(Order cart) {
		orderService.deleteOrder(cart);
		
	}

	@Override
	public Order addItemWithPriceOverrides(Long arg0, OrderItemRequestDTO arg1,
			boolean arg2) throws AddToCartException {		
		return orderService.addItemWithPriceOverrides(arg0, arg1, arg2);
	}
	
	@Override
	public void sendOrderConfirmationMail(ShoppingCartVO cartVO) {
		 
		 ETSCustomer customer = cartVO.getCustomer();
         String encodeUrl = Constant.URL_DECODE_IDENTIFIER + generateBase64String.encodeBase64String(customer.getUsername() + Constant.ENCODING_DELIMITTER
                + "?orderNumber=" + cartVO.getOrder().getOrderNumber()  + "&customerId=");
        
    	HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("cart", cartVO);
        props.put("customer", customer);
        props.put("encodeUrl", encodeUrl);
                
        emailService.sendTemplateEmail(customer.getEmailAddress(),orderConfirmationEmailInfo , props);
	}
	
	
	/*Removes all the non dependent items from the cart*/
	
	@Override
	@Transactional
	public  void clearCart(
			 Order cart) throws RemoveFromCartException, PricingException {
			if(null!=cart){
				List<OrderItem> orderItemsToBeRemoved=getTestsFromCart(cart);
				for (OrderItem orderItem : orderItemsToBeRemoved) {  
						cart = removeItem(cart.getId(), orderItem.getId(), false);	
						cart=save(cart, true);
						
				}
				cancelOrder(cart);
				
			}
	}
	
	
	@Override
	@Transactional
	public  List<OrderItem> getTestsFromCart(Order cart) {
		List<OrderItem> testItemsInCart = new ArrayList<OrderItem>();
		if(null!=cart && !cart.getOrderItems().isEmpty()){ 
			 for (OrderItem oi : cart.getOrderItems()) {
	             if (oi instanceof ETSDiscreteOrderItem) {
	                 ETSDiscreteOrderItem eOrderItem = (ETSDiscreteOrderItem) oi;
	                 if(null==eOrderItem.getRefOrderItemId()){
	                     testItemsInCart.add(eOrderItem);
	                 }
	             }
	         }
		}
		 return testItemsInCart;
	}
}
