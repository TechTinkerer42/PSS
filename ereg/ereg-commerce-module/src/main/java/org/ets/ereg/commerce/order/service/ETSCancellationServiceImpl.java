package org.ets.ereg.commerce.order.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.ets.ereg.commerce.order.dao.BatterySubscriptionDao;
import org.ets.ereg.commerce.order.vo.BatteryItemVO;
import org.ets.ereg.commerce.order.vo.CancelTestVO;
import org.ets.ereg.commerce.order.vo.TestItemVO;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.oms.cancellation.service.CancellationServiceImpl;
import com.broadleafcommerce.oms.fulfillment.domain.FulfillmentOrderItem;
import com.broadleafcommerce.oms.fulfillment.service.FulfillmentOrderItemService;

@Service("etsCancellationService")
public class ETSCancellationServiceImpl extends CancellationServiceImpl implements ETSCancellationService{
	
	@Resource(name = "blFulfillmentGroupItemDao")
    private FulfillmentGroupItemDao fulfillmentGroupItemDao;
	
	 @Resource(name="etsBatterySubscriptionService")
	 private ETSBatterySubscriptionService batterySubscriptionService;
	 
	 @Resource(name="batterySubscriptionDao")
	 private BatterySubscriptionDao batterySubscriptionDao;
	 
	 
	 @Resource(name="blFulfillmentOrderItemService")
	 private FulfillmentOrderItemService temp;

	@Override
	public Money calculateAmountToCancel(FulfillmentOrderItem fulfillmentOrderItem) {
		 
	        Money amountToCancel = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, fulfillmentOrderItem.getFulfillmentOrder().getFulfillmentGroup().getOrder().getCurrency());
	        amountToCancel = amountToCancel.add(fulfillmentOrderItem.getFulfillmentItemAmount());
	        
	        //TODO Do we need the following
	      /*  if (fulfillmentOrder.getFulfillmentCharge() != null) {
	            amountToCancel = amountToCancel.add(fulfillmentOrder.getFulfillmentCharge());
	        }*/
	        
	        return amountToCancel;
	 }
	
	@Override
	public CancelTestVO generateCancellationDetails(Long fulfillmentGroupItemId,int quantity){
		CancelTestVO cancelTest=new  CancelTestVO();
		cancelTest.setQuantity(quantity);
		
		//TODO HAve to fix this 
		
		//FulfillmentGroupItem fgi = fulfillmentGroupItemDao.readFulfillmentGroupItemById(fulfillmentGroupItemId);
		FulfillmentOrderItem tempfoi=temp.findById(fulfillmentGroupItemId);
		FulfillmentGroupItem fgi=tempfoi.getFulfillmentGroupItem();
		
		cancelTest.setRefundAmount(getRefundAmount(fulfillmentGroupItemId));
		
		cancelTest.setBatterySubscription(getBatteryItem(fulfillmentGroupItemId));
		TestItemVO testItem=new TestItemVO(fgi,getBatterySubscription(fulfillmentGroupItemId));
		
		cancelTest.setTestItem(testItem);
		
		return cancelTest;
		
	}
	
	private BatterySubscription getBatterySubscription(
			Long fulfillmentGroupItemId) {
//		FulfillmentGroupItem fulfillmentGroupItem = fulfillmentGroupItemDao.readFulfillmentGroupItemById(fulfillmentGroupItemId);
				FulfillmentOrderItem tempfoi=temp.findById(fulfillmentGroupItemId);
				FulfillmentGroupItem fulfillmentGroupItem=tempfoi.getFulfillmentGroupItem();
				
				List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(fulfillmentGroupItem.getOrderItem().getOrder().getCustomer());
				for(BatterySubscription batterySubscription:subs){
					List<BatteryScheduledTest>  batterySchldTests=batterySubscriptionService.readAllScheduledTestsForBattery(batterySubscription);
					if(isScheduledTestBeingCancelled(batterySchldTests,fulfillmentGroupItem.getOrderItem()) ){
						
						return batterySubscription;
					}
				}
				return null;
	}

		//TODO correct Service needs to be invoked. I think should go in BatterySubscriptionService
	// Given a fulfillmentGroupItemId of test ,should get the battery associated with it
		private BatteryItemVO getBatteryItem(
				Long fulfillmentGroupItemId) {
		
		//	FulfillmentGroupItem fulfillmentGroupItem = fulfillmentGroupItemDao.readFulfillmentGroupItemById(fulfillmentGroupItemId);
			FulfillmentOrderItem tempfoi=temp.findById(fulfillmentGroupItemId);
			FulfillmentGroupItem fulfillmentGroupItem=tempfoi.getFulfillmentGroupItem();
			
			List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(fulfillmentGroupItem.getOrderItem().getOrder().getCustomer());
			for(BatterySubscription batterySubscription:subs){
				List<BatteryScheduledTest>  batterySchldTests=batterySubscriptionService.readAllScheduledTestsForBattery(batterySubscription);
				if(isScheduledTestBeingCancelled(batterySchldTests,fulfillmentGroupItem.getOrderItem()) ){
					
					return new BatteryItemVO(fulfillmentGroupItemDao.readFulfillmentGroupItemById(batterySubscription.getBatteryDiscreteOrderItem().getId()));
				}
			}
			return null;
		}


		private boolean isScheduledTestBeingCancelled(
				List<BatteryScheduledTest> batterySchldTests,
				OrderItem orderItem) {
			for(BatteryScheduledTest batterySchldTest:batterySchldTests){
				if(batterySchldTest.getTestDiscreteOrderItem().getId().equals(orderItem.getId())){
					return true;
				}
			}
			return false;
		}

		private Money getRefundAmount(Long fulfillmentGroupItemId) {
			
			return new Money(BigDecimal.TEN);
		}
}
