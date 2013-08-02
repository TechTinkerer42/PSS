package org.ets.ereg.commerce.order.service;

import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.ets.ereg.commerce.order.vo.ShoppingCartVO;
import org.ets.ereg.domain.interfaces.commerce.order.AddTestRequest;
import org.ets.ereg.domain.interfaces.commerce.order.RescheduleTestRequest;

public interface ETSOrderService extends OrderService{

	Order addTest(AddTestRequest addTestRequest) throws AddToCartException;
	Order addRescheduleTest(RescheduleTestRequest rescheduleTestRequest) throws AddToCartException;
	List<Order> bulkAddTest(List<AddTestRequest> addTestRequests) throws AddToCartException;	
	void sendOrderConfirmationMail(ShoppingCartVO cartVO);
	void clearCart(Order cart) throws RemoveFromCartException, PricingException;
	List<OrderItem> getTestsFromCart(Order cart);
	
}
