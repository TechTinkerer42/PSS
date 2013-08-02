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
import org.broadleafcommerce.core.catalog.domain.SkuAttribute;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.ets.ereg.commerce.order.dto.MembershipOrderItemRequestDTO;
import org.ets.ereg.commerce.order.service.ETSMembershipEligibilityService;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.catalog.CartRule;
import org.ets.ereg.domain.interfaces.model.catalog.DependentProduct;
import org.ets.ereg.domain.interfaces.model.catalog.ETSSku;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

/**
 * This CART activity is responsible for adding any associated membership fees to the cart.
 * It is responsible for iterating through the order items and finding any SKU's that have a cart rule of type
 * "MBRSP", it will evaluate the MVEL expression to determine whether or not to apply the membership fee.
 * If the evaluation is found to be true, then it adds the dependent fee to the cart. 
 * 
 * This activity should be run AFTER the Clear Membership Order Items Activity 
 * (i.e. since this is an Order Item level fee, all membership fees should be cleared first in order to rerun the rules)
 */
public class UpdateMembershipOrderItemsActivity extends BaseActivity {

    private static final String CART_RULE_MEMBERSHIP_TYPE = "MBRSP";
    private static final String DURATION_SKU_ATTRIBUTE = "DURATION_IN_MONTHS";
    private static final String START_MONTH = "START_MONTH";

    @SuppressWarnings("unchecked")
    protected static final Map EXPRESSION_CACHE = Collections.synchronizedMap(new LRUMap(1000));

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "etsMembershipEligibilityService")
    protected ETSMembershipEligibilityService membershipEligibilityService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
        CartOperationRequest request = ((CartOperationContext) context).getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        if (!(orderItemRequestDTO instanceof MembershipOrderItemRequestDTO)) {
            Order order = request.getOrder();

            List<MembershipOrderItemRequestDTO> dependentItems = new ArrayList<MembershipOrderItemRequestDTO>();
            for (OrderItem oi : order.getOrderItems()) {
                if (oi instanceof TestDiscreteOrderItem) {
                    TestDiscreteOrderItem testItem = (TestDiscreteOrderItem)oi;
                    Agency agency = testItem.getFirstBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
                    ETSSku sku = (ETSSku)((DiscreteOrderItem) oi).getSku();
                    for (CartRule cartRule : sku.getCartRules()) {
                        //Only evaluate cart rules of type CART_RULE_MEMBERSHIP_TYPE
                        if (shouldApplyMembershipToOrder(cartRule, order, agency) &&
                                CART_RULE_MEMBERSHIP_TYPE.equals(cartRule.getCartRuleType().getCode())) {
                            for (DependentProduct depProd : cartRule.getDependentProducts()) {
                                MembershipOrderItemRequestDTO itemRequest = new MembershipOrderItemRequestDTO();
                                Integer duration = null;
                                Integer startMonth = null;
                                for (SkuAttribute attribute : depProd.getProduct().getDefaultSku().getSkuAttributes()) {
                                    if (DURATION_SKU_ATTRIBUTE.equals(attribute.getName())) {
                                        duration = Integer.parseInt(attribute.getValue());
                                    }
                                    if (START_MONTH.equals(attribute.getName())) {
                                        startMonth = Integer.parseInt(attribute.getValue());
                                    }
                                }

                                //If the current month is before the Start Month of the Membership
                                // the start date would be calculated as the month of the previous year.
                                if (duration != null && startMonth != null) {
                                    MutableDateTime startDate = new MutableDateTime();
                                    Integer thisMonth = startDate.getMonthOfYear();
                                    startDate.setMonthOfYear(startMonth);
                                    startDate.setDayOfMonth(1);
                                    startDate.setMillisOfDay(0);
                                    if (thisMonth < startMonth) {
                                        startDate.addYears(-1);
                                    }

                                    DateTime endDate = startDate.toDateTime();
                                    endDate = endDate.plusMonths(duration);

                                    itemRequest.setStartDate(startDate.toDate());
                                    itemRequest.setEndDate(endDate.toDate());
                                }

                                itemRequest.setAgency(agency);
                                itemRequest.setRefOrderItemId((ETSDiscreteOrderItem)oi);
                                itemRequest.setProductId(depProd.getProduct().getId());
                                itemRequest.setQuantity(depProd.getQuantity());

                                if (depProd.isUnique() ) {
                                    if (!dependentItemsContainDependentItem(dependentItems, depProd)
                                        && !orderItemsContainDependentItem(order.getOrderItems(), depProd)) {
                                        dependentItems.add(itemRequest);
                                    }
                                } else {
                                    dependentItems.add(itemRequest);
                                }
                            }
                        }
                    }
                }
            }

            for (MembershipOrderItemRequestDTO itemRequest : dependentItems){
                order = orderService.addItem(order.getId(), itemRequest, false);
            }

            order = orderService.save(order, false);

            request.setOrder(order);
        }
        return context;
    }

    private boolean dependentItemsContainDependentItem(List<MembershipOrderItemRequestDTO> dependentItems, DependentProduct depProd) {

        for (MembershipOrderItemRequestDTO dto : dependentItems) {
            if (dto.getProductId().equals(depProd.getProduct().getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean orderItemsContainDependentItem(List<OrderItem> orderItems, DependentProduct depProd) {

        for (OrderItem orderItem : orderItems) {
            if (orderItem instanceof DiscreteOrderItem) {
                DiscreteOrderItem doi = (DiscreteOrderItem) orderItem;
                if (doi.getProduct().getId().equals(depProd.getProduct().getId())) {
                    return true;
                }
            }
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
    protected boolean shouldApplyMembershipToOrder(CartRule cartRule, Order order, Agency agency) {
        boolean appliesToOrder = true;
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
            vars.put("agency", agency);
            boolean containsActiveMembership =
                    (membershipEligibilityService.readActiveMembershipByCustomerAndAgency(order.getCustomer(), agency) != null);
            boolean appliesToAgency =  (Boolean)MVEL.executeExpression(exp, vars);
            return !containsActiveMembership && appliesToAgency;
        }

        return appliesToOrder;
    }


}
