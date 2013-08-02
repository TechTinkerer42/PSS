package org.ets.ereg.commerce.order.service.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.dto.ETSDiscreteOrderItemRequestDTO;
import org.ets.ereg.domain.interfaces.commerce.order.ETSRescheduleTestItemRequest;
import org.ets.ereg.domain.interfaces.model.catalog.CartRule;
import org.ets.ereg.domain.interfaces.model.catalog.DependentProduct;
import org.ets.ereg.domain.interfaces.model.catalog.ETSSku;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

/**
 * This blAddItemWorkflow CART activity is responsible for adding any associated reschedule fees to the
 * rescheduled test being added to the cart. If the rescheduled test SKU has a cart rule associated with it of type
 * "RSCHDL", it will evaluate the MVEL expression to determine whether or not to apply the reschedule fee.
 * If the evaluation is found to be true, then it adds the dependent fee to the cart and associates
 * the fee to the rescheduled test item. That way, if a reschedule is removed from the cart, so are its associated fees.
 * 
 * This activity should be run AFTER the Add Order Item Activity 
 * (i.e. the Rescheduled Test must be already added to the Order)
 */
public class AddRescheduleFeesActivity extends BaseActivity {

    private static final String CART_RULE_RESCHEDULE_TYPE = "RSCHDL";

    @SuppressWarnings("unchecked")
    protected static final Map EXPRESSION_CACHE = Collections.synchronizedMap(new LRUMap(1000));
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
	@Override
	public ProcessContext execute(ProcessContext context) throws Exception {
		CartOperationRequest request = ((CartOperationContext) context).getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
		Order order = request.getOrder();
		
		//If trying to add a reschedule test find the test on the order and apply fees
		if (orderItemRequestDTO instanceof ETSRescheduleTestItemRequest) {
			ETSRescheduleTestItemRequest rescheduleRequest = (ETSRescheduleTestItemRequest) orderItemRequestDTO;
			RescheduleTestDiscreteOrderItem rtdoi = findRescheduledTestOnOrder(rescheduleRequest, order);

			List<ETSDiscreteOrderItemRequestDTO> dependentFees = new ArrayList<ETSDiscreteOrderItemRequestDTO>();

        	if (rtdoi !=null) {
	            ETSSku sku = (ETSSku)rtdoi.getSku();
	            for (CartRule cartRule : sku.getCartRules()) {
	            	 //Only evaluate cart rules of type CART_RULE_RESCHEDULE_TYPE
	                if ( CART_RULE_RESCHEDULE_TYPE.equals(cartRule.getCartRuleType().getCode()) &&
	                		shouldApplyRescheduleFeeToOrder(cartRule, order)) {
	                	for (DependentProduct depProd : cartRule.getDependentProducts()) {
	                		//Create an Item Request for this fee
	                		ETSDiscreteOrderItemRequestDTO itemRequest = new ETSDiscreteOrderItemRequestDTO();
	                		
	                		itemRequest.setProductId(depProd.getProduct().getId());
	                		itemRequest.setSkuId(depProd.getProduct().getDefaultSku().getId());
	        				itemRequest.setQuantity(depProd.getQuantity());
	        				itemRequest.setRefOrderItemId(rtdoi);
	        				
	        				dependentFees.add(itemRequest);
	                	}
	                }
	            }
            }
        	

            //add all fees to order
        	for (ETSDiscreteOrderItemRequestDTO itemRequest : dependentFees) {
        		order = orderService.addItem(order.getId(), itemRequest, false);
        	}
        	
            //create dependency between parent and dependent fees
    		List<ETSDiscreteOrderItem> dependentFeeOrderItems = new ArrayList<ETSDiscreteOrderItem>();
            for (OrderItem oi : order.getOrderItems()) {
            	if (oi instanceof ETSDiscreteOrderItem) {
            		ETSDiscreteOrderItem eoi = (ETSDiscreteOrderItem) oi;
            		if (eoi.getRefOrderItemId() != null && 
            				wasOrderItemADependentFeeOfRescheduledTest(eoi, rtdoi)) {
            			dependentFeeOrderItems.add(eoi);
            		}
            	}
            }
            
            for (OrderItem oi : order.getOrderItems()) {
            	if (oi.getId().equals(rtdoi.getId()) && !dependentFeeOrderItems.isEmpty()) {
            		ETSDiscreteOrderItem eoi = (ETSDiscreteOrderItem) oi;
            		eoi.getRefOrderItemIds().addAll(dependentFeeOrderItems);
            	}
            }        	

            order = orderService.save(order, false);
            request.setOrder(order);

		}
        
        return context;
	}
	
	private RescheduleTestDiscreteOrderItem findRescheduledTestOnOrder(ETSRescheduleTestItemRequest rescheduleRequest, Order order) {
		if (rescheduleRequest != null && order != null) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem instanceof RescheduleTestDiscreteOrderItem) {
					RescheduleTestDiscreteOrderItem rtdoi = (RescheduleTestDiscreteOrderItem) orderItem;
					if (rtdoi.getProduct().getId().equals(rescheduleRequest.getProductId()) && 
							rescheduleRequest.getQuantity().equals(new Integer(rtdoi.getQuantity()))) {
						return rtdoi;
					}
				}
			}
		}
		
		return null;
	}
	
	private boolean wasOrderItemADependentFeeOfRescheduledTest(ETSDiscreteOrderItem eoi, RescheduleTestDiscreteOrderItem rtdoi) {

		if (eoi != null && rtdoi != null && 
				eoi.getRefOrderItemId()!=null && 
				eoi.getRefOrderItemId().getId().equals(rtdoi.getId())) {
				return true;
		}
		
		return false;
	}
	
    /**
     * If the CartRule expression is null or empty, this method will always return true
     *
     * @param cartRule
     * @param order
     * @return boolean
     */
    protected boolean shouldApplyRescheduleFeeToOrder(CartRule cartRule, Order order) {
        boolean applyFee = true;
        String rule = cartRule.getExpression();
        if (!StringUtils.isEmpty(rule)) {
            Serializable exp = (Serializable) EXPRESSION_CACHE.get(rule);
            if (exp == null) {
                ParserContext mvelContext = new ParserContext();
                mvelContext.addImport("MVEL", MVEL.class);
                exp = MVEL.compileExpression(rule, mvelContext);

                EXPRESSION_CACHE.put(rule, exp);
            }
            HashMap<String, Object> vars = new HashMap<String, Object>();
            
            //TODO call Patrick's service to find past eligible reschedules;
            vars.put("pastEligibleReschedules", 3);
            applyFee = (Boolean)MVEL.executeExpression(exp, vars);
            return applyFee;
        }

        return applyFee;
    }

}
