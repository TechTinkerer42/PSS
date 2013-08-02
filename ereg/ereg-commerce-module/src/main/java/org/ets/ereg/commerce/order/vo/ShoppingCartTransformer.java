package org.ets.ereg.commerce.order.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.catalog.service.ETSBatteryProductService;
import org.ets.ereg.commerce.order.service.ETSBatterySubscriptionService;
import org.ets.ereg.commerce.order.util.OrderUtil;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.order.type.ETSFulfillmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("shoppingCartTransformer")
public class ShoppingCartTransformer {
	private static Logger log = LoggerFactory
			.getLogger(ShoppingCartTransformer.class);
	
	

	 @Resource(name="etsBatterySubscriptionService")
	 private ETSBatterySubscriptionService batterySubscriptionService;
	 
	 @Resource(name="etsBatteryProductService")
	 private ETSBatteryProductService batteryProductService;

	 public ShoppingCartVO transform(Order order) {		
		 
		long time = System.currentTimeMillis();
		
		
		ShoppingCartVO cartVO = new ShoppingCartVO();
		
		cartVO.setOrder(order);
		
		ETSCustomer customer = (ETSCustomer)order.getCustomer();
		cartVO.setCustomer(customer);
		
		cartVO.setTotalQuantity(OrderUtil.getTotalQuantity(order));
		if(null!=order.getFulfillmentGroups() && !order.getFulfillmentGroups().isEmpty()){
			for (FulfillmentGroup fg : order.getFulfillmentGroups()) {			
				if (ETSFulfillmentType.TEST.equals(fg.getType())) {
					List<TestItemVO> testItems = getTestItems(fg.getFulfillmentGroupItems());
					for(TestItemVO testItem : testItems){
						addTest(cartVO, testItem);
						updateTestItemFlags(testItem);
					}
				}else if (ETSFulfillmentType.MEMBERSHIP_FEE.equals(fg.getType())) {
					List<MembershipItemVO>	membershipFees = getMembershipFeeItems(fg.getFulfillmentGroupItems());
					for(MembershipItemVO membershipFee : membershipFees){
						AgencyVO agencyVO = getAgencyVO(cartVO, membershipFee.getAgency());
						agencyVO.setMembershipItem(membershipFee);
					}
				}else if (ETSFulfillmentType.BATTERY_SUBSCRIPTION.equals(fg.getType())) {
                    List<BatteryItemVO> batteryItems = getBatteryItems(fg.getFulfillmentGroupItems());
                    for(BatteryItemVO batteryItem : batteryItems){
                    	AgencyVO agencyVO = getAgencyVO(cartVO, batteryItem.getAgency());
                    	agencyVO.setBatteryItem(batteryItem);
                    }
                }
			}
		}

		if(null!=customer){
			List<BatterySubscription> openBatteries = batterySubscriptionService.readAllOpenBatteriesForCustomer(customer);
			for(BatterySubscription openBattery : openBatteries){
				AgencyVO agencyVO = cartVO.getAgency(openBattery.getAgency().getId());
				if(agencyVO != null){
					agencyVO.setBatterySubscription(openBattery);
				}
			}
		}

		time = System.currentTimeMillis() - time;
		log.info("time taken in msec to create shopping cart vo for cart {} time {}", order.getId(), time);
		
		log.info("cart amount {}", order.getTotal());
		if(null!=order.getTotal()){
		cartVO.setTotalAmount(order.getTotal().getAmount());
		}
		else{
			cartVO.setTotalAmount(BigDecimal.ZERO);
		}
		return cartVO;

	}
	 
	protected void addTest(ShoppingCartVO cartVO, TestItemVO testItem){
		if(testItem.getAgency() != null){
			 
			BatteryProduct batteryProduct = batteryProductService.readBatteryProductByEligibleProduct((ETSProduct)testItem.getTestDiscreteOrderItem().getProduct());
			if(batteryProduct != null){
				// add the test to the agency
				AgencyVO agencyVO = getAgencyVO(cartVO, testItem.getAgency());
				agencyVO.getTests().add(testItem);
				testItem.setBatteryProduct(batteryProduct);
			}else{
				// add the test to the shopping cart vo
				cartVO.getTests().add(testItem);
			}
		}else{
			cartVO.getTests().add(testItem);
		}
	}

	protected AgencyVO getAgencyVO(ShoppingCartVO cartVO, Agency agency){
		AgencyVO agencyVO = cartVO.getAgency(agency.getId());
		if(agencyVO == null){
			agencyVO = transformToAgencyVO(agency);
			cartVO.getAgencies().add(agencyVO);
		}
		return agencyVO;
	}
	
	protected void updateTestItemFlags(TestItemVO testItem){
		// shopping cart does not update the flags
		// flags are set for post order actions
	}
	
	
	protected boolean isTestBelongsToBattery(Customer customer, TestDiscreteOrderItem testDiscreteOrderItem){
		return batterySubscriptionService.isTestBelongsToBattery(customer,testDiscreteOrderItem);
	}

	private List<MembershipItemVO> getMembershipFeeItems(
			List<FulfillmentGroupItem> fulfillmentGroupItems) {
		List<MembershipItemVO> memberShipitems = new ArrayList<MembershipItemVO>();
		
		if(null!=fulfillmentGroupItems && !fulfillmentGroupItems.isEmpty()){
		for (FulfillmentGroupItem membershipFgItem : fulfillmentGroupItems) {
			memberShipitems.add(transformToMemberShip(membershipFgItem));
		}
		}
		return memberShipitems;
	}

	private MembershipItemVO transformToMemberShip(
			FulfillmentGroupItem membershipFgItem) {
		return new MembershipItemVO( membershipFgItem);//??
		
	}



	private List<TestItemVO> getTestItems(
			List<FulfillmentGroupItem> fulfillmentGroupItems) {
		List<TestItemVO> testItems = new ArrayList<TestItemVO>();

		for (FulfillmentGroupItem testFgItem : fulfillmentGroupItems) {
			testItems.add(transformToTest(testFgItem));
		}
		return testItems;
	}

    private List<BatteryItemVO> getBatteryItems(
            List<FulfillmentGroupItem> fulfillmentGroupItems) {
        List<BatteryItemVO> batteryItems = new ArrayList<BatteryItemVO>();

        for (FulfillmentGroupItem batteryFgItem : fulfillmentGroupItems) {
            batteryItems.add(transformToBattery(batteryFgItem));
        }
        return batteryItems;
    }

	private TestItemVO transformToTest(FulfillmentGroupItem testFgItem) {	
		BatterySubscription eligibleBattery = batterySubscriptionService.readEligibleBatteryForTest(
				testFgItem.getOrderItem().getOrder().getCustomer(), (TestDiscreteOrderItem)testFgItem.getOrderItem());
		return new TestItemVO(testFgItem, eligibleBattery);
	}

    private BatteryItemVO transformToBattery(FulfillmentGroupItem batteryFgItem) {
        return new BatteryItemVO(batteryFgItem);
    }
    
    private AgencyVO transformToAgencyVO(Agency agency){
    	return new AgencyVO(agency);
    }
}
