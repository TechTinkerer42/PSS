package org.ets.ereg.commerce.order.service;

import org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice;
import org.broadleafcommerce.core.order.service.OrderItemServiceImpl;
import org.ets.ereg.commerce.order.service.call.BatteryOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.ETSDiscreteOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.MembershipOrderItemRequest;
import org.ets.ereg.commerce.order.service.call.RescheduleOrderItemRequest;
import org.ets.ereg.commerce.order.service.type.ETSOrderItemType;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class ETSOrderItemServiceImpl extends OrderItemServiceImpl implements ETSOrderItemService {

	private void populateETSDiscreteOrderItem(ETSDiscreteOrderItem item, ETSDiscreteOrderItemRequest itemRequest) {
		
        populateDiscreteOrderItem(item, itemRequest);

        item.setBundleOrderItem(itemRequest.getBundleOrderItem());
        item.setBaseSalePrice(itemRequest.getSku().getSalePrice());
        item.setBaseRetailPrice(itemRequest.getSku().getRetailPrice());
        item.setDiscreteOrderItemFeePrices(itemRequest.getDiscreteOrderItemFeePrices());
	    
        if (itemRequest.getSalePriceOverride() != null) {
        	item.setSalePriceOverride(Boolean.TRUE);
        	item.setSalePrice(itemRequest.getSalePriceOverride());
        	item.setBaseSalePrice(itemRequest.getSalePriceOverride());
        }
	
        if (itemRequest.getRetailPriceOverride() != null) {
        	item.setRetailPriceOverride(Boolean.TRUE);
        	item.setRetailPrice(itemRequest.getRetailPriceOverride());
        	item.setBaseRetailPrice(itemRequest.getRetailPriceOverride());
        }
	    
        for (DiscreteOrderItemFeePrice feePrice : item.getDiscreteOrderItemFeePrices()) {
        	feePrice.setDiscreteOrderItem(item);
        }
	
        item.updateSaleAndRetailPrices();
        item.assignFinalPrice();
        item.setPersonalMessage(itemRequest.getPersonalMessage());	
	 
        item.setRefOrderItemId(itemRequest.getRefOrderItemId());
        item.setRefOrderItemIds(itemRequest.getRefOrderItemIds());

	}
	
    @Override
    public MembershipDiscreteOrderItem createMembershipDiscreteOrderItem(final MembershipOrderItemRequest itemRequest) {
        final MembershipDiscreteOrderItem item = (MembershipDiscreteOrderItem) orderItemDao.create(ETSOrderItemType.MEMBERSHIP);

        populateETSDiscreteOrderItem(item, itemRequest);

        item.setAgency(itemRequest.getAgency());
        item.setStartDate(itemRequest.getStartDate());
        item.setEndDate(itemRequest.getEndDate());

        return item;
    }

    @Override
    public BatteryDiscreteOrderItem createBatteryDiscreteOrderItem(final BatteryOrderItemRequest itemRequest) {
        final BatteryDiscreteOrderItem item = (BatteryDiscreteOrderItem) orderItemDao.create(ETSOrderItemType.BATTERY);

        populateETSDiscreteOrderItem(item, itemRequest);
        
        item.setAgency(itemRequest.getAgency());
        BatteryProduct bp = (BatteryProduct) itemRequest.getProduct();
        
        DateTime today = new DateTime();
        MutableDateTime endDate = new MutableDateTime();
        endDate.addMonths(bp.getDurationInMonths());
        
        item.setStartDate(today.toDate());
        item.setEndDate(endDate.toDate());
        item.setTotalTakes(bp.getTotalTakes());

        return item;
    }

    @Override
    public TestDiscreteOrderItem createTestDiscreteOrderItem(final ETSDiscreteOrderItemRequest itemRequest) {
        final TestDiscreteOrderItem item = (TestDiscreteOrderItem) orderItemDao.create(ETSOrderItemType.TEST);        
        
        populateETSDiscreteOrderItem(item, itemRequest);

        return item;
    }
    
    @Override
    public RescheduleTestDiscreteOrderItem createRescheduleTestDiscreteOrderItem(final RescheduleOrderItemRequest itemRequest) {
    	final RescheduleTestDiscreteOrderItem item = (RescheduleTestDiscreteOrderItem) orderItemDao.create(ETSOrderItemType.RESCHEDULE); 	
    	
    	populateETSDiscreteOrderItem(item, itemRequest);
    	
    	item.setOriginalTestItem(itemRequest.getOriginalTestItem());
    	
    	return item;
    }
}
