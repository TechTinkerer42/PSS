package org.ets.ereg.web.taglib;

import javax.annotation.Resource;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.ets.ereg.commerce.order.service.ETSOrderService;
import org.ets.ereg.commerce.order.util.OrderUtil;
import org.ets.ereg.commerce.order.vo.ShoppingCartTransformer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.http.HttpServletRequest;


public class ETSOrderLookupTag extends BodyTagSupport  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private String orderVar;
	//private String orderName;
	
	private String totalQuantityVar;
	
	public int doStartTag() throws JspException {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		//CustomerState customerState = (CustomerState) applicationContext.getBean("blCustomerState");
        Customer customer = CustomerState.getCustomer((HttpServletRequest) pageContext.getRequest());
        ETSOrderService orderService = (ETSOrderService) applicationContext.getBean("etsOrderService");
      
        
        
        
       Order order = null;
        if (orderId != null) {
            order = orderService.findOrderById(orderId);
        } else if (customer != null){
            order = orderService.findCartForCustomer(customer);
        }
        
        if (orderVar != null) {
        pageContext.setAttribute(orderVar, order);
        }
        
        if (null!=totalQuantityVar) {
        	int orderItemsCount = OrderUtil.getTotalQuantity(order);
        	 pageContext.setAttribute(totalQuantityVar, orderItemsCount);
        }else{
        	 pageContext.setAttribute(totalQuantityVar, 0);
        }
        
		return EVAL_PAGE;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTotalQuantityVar() {
		return totalQuantityVar;
	}

	public void setTotalQuantityVar(String totalQuantityVar) {
		this.totalQuantityVar = totalQuantityVar;
	}

	public String getOrderVar() {
		return orderVar;
	}

	public void setOrderVar(String orderVar) {
		this.orderVar = orderVar;
	}
	
	
	/*private Order getCart(OrderService orderService, ShoppingCartTransformer shoppingCartTransformer) {
		 CartState.getCart();
		Order order1=orderService.createNewCartForCustomer(CustomerState.getCustomer());		
		order1=shoppingCartTransformer.getTempOrder(order1);
		return order1;
	}*/
}
