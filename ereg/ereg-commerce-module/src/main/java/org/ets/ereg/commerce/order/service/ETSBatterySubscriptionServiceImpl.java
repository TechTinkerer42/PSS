package org.ets.ereg.commerce.order.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.dao.BatteryScheduledTestDao;
import org.ets.ereg.commerce.order.dao.BatterySubscriptionDao;
import org.ets.ereg.commerce.order.util.BatteryStatusEnum;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.ets.ereg.domain.interfaces.model.catalog.TestSku;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("etsBatterySubscriptionService")
public class ETSBatterySubscriptionServiceImpl implements ETSBatterySubscriptionService {

	@Resource(name = "batterySubscriptionDao")
	protected BatterySubscriptionDao batterySubscriptionDao;

	@Resource(name = "batteryScheduledTestDao")	
	protected BatteryScheduledTestDao batteryScheduledTestDao;
	
	@Override
	public BatterySubscription createSubscription() {
		return batterySubscriptionDao.create();
	}	
	
	@Override
	@Transactional
	public BatterySubscription saveSubscription(BatterySubscription batterySubscription) {
		return batterySubscriptionDao.save(batterySubscription);
	}	
	
	@Override
	public BatteryScheduledTest createScheduledTest() {
		return batteryScheduledTestDao.create();
	}
	
	@Override
	@Transactional
	public BatteryScheduledTest saveScheduledTest(BatteryScheduledTest scheduledTest) {
		return batteryScheduledTestDao.save(scheduledTest);
	}	
	
	@Override
	public List<BatterySubscription> readAllOpenBatteriesForCustomer(Customer customer) {
		List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(customer);
		List<BatterySubscription> openSubs = new ArrayList<BatterySubscription>();
		
		for (BatterySubscription sub : subs) {
			if (!isBatteryExpired(sub) && !isBatteryConsumed(sub)) {
				openSubs.add(sub);
			}
		}
		
		return openSubs;
	}

	//TODO - probably be replaced by method below
	@Override
	public BatterySubscription readOpenBatteryForCustomerAndAgency(
			Customer customer, Agency agency) {
		
		List<BatterySubscription> openSubs = readAllOpenBatteriesForCustomer(customer);
		for (BatterySubscription sub : openSubs) {
			if (sub.getAgency()!= null && agency != null &&
					sub.getAgency().getId().equals(agency.getId())) {
				return sub;
			}
		}
		
		return null;
	}
	
	@Override
	public BatterySubscription readOpenBatteryForCustomerAndAgency(
			Customer customer, Long agencyId) {
		
		List<BatterySubscription> openSubs = readAllOpenBatteriesForCustomer(customer);
		for (BatterySubscription sub : openSubs) {
			if (sub.getAgency()!= null && agencyId != null &&
					sub.getAgency().getId().equals(agencyId)) {
				return sub;
			}
		}
		
		return null;
	}

	@Override
	public BatterySubscription readEligibleBatteryForTest(Customer customer, TestDiscreteOrderItem testDiscreteOrderItem) {
		Agency agency = testDiscreteOrderItem.getFirstBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
		BatterySubscription agencySub = readOpenBatteryForCustomerAndAgency(customer, agency);
		if (agencySub != null) {
			if (isTestEligibleForBattery(agencySub, testDiscreteOrderItem)) {
				return agencySub;
			}
		}
		
		return null;
	}

	/**
	 * This method checks:
	 *  - Is the Test consumed for this battery?
	 *  - Is this Test listed as an eligible Test Item for this Battery Product
	 *  - Is the Test Date before the expiration of the Battery
	 */
	@Override
	public Boolean isTestEligibleForBattery(BatterySubscription subscription,
			TestDiscreteOrderItem testDiscreteOrderItem) {
		Agency agency = testDiscreteOrderItem.getFirstBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
		if (agency != null && subscription.getAgency().getId().equals(agency.getId())) {
			BatteryProduct product = subscription.getBatteryProduct();
			ETSProduct testProduct = (ETSProduct) testDiscreteOrderItem.getProduct();
			return (!isTestConsumed(testProduct, subscription) &&
						isTestAnEligibleBatteryProductItem(product, testProduct) &&
						isTestDateBeforeBatteryEndDate(testDiscreteOrderItem, subscription.getBatteryDiscreteOrderItem()));
			
		}

		return false;
	}
	
	private boolean isTestAnEligibleBatteryProductItem(BatteryProduct product, ETSProduct test) {
		for (BatteryProductItem bpi : product.getBatteryProductItems()) {
			//Check that the product in the given test order item is an eligible product
			if (bpi.getEligibleProduct().getId().equals(test.getId())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isBatteryExpired(BatterySubscription subscription) {
		DateTime endDate = new DateTime(subscription.getEndDate());
		return endDate.isBeforeNow();
	}
	
	private boolean isTestConsumed(ETSProduct test, BatterySubscription subscription) {
		BatteryProduct bp = subscription.getBatteryProduct();
		if (bp != null) {
			int totalTakes = bp.getTotalTakes();
			int actualTakes = calculateNumberOfTakesForTest(test, subscription);
			if (actualTakes < totalTakes) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isTestDateBeforeBatteryEndDate(TestDiscreteOrderItem testDiscreteOrderItem, BatteryDiscreteOrderItem bdoi) {
		DateTime testDate = new DateTime(testDiscreteOrderItem.getFirstBooking().getAppointmentDateTime());
		DateTime batteryExipration = new DateTime(bdoi.getEndDate());
		if (testDate.isBefore(batteryExipration)) {
			return true;
		}		
		
		return false;
	}
	
	private boolean isBatteryConsumed(BatterySubscription subscription) {
		//Iterate through all the eligible tests in the battery
		// and check to see if any are consumed. If one is still available the the entire battery is still open.
		BatteryProduct bp = subscription.getBatteryProduct();
		if (bp != null) {
			for (BatteryProductItem bpi : bp.getBatteryProductItems()) {
				ETSProduct test =  (ETSProduct) bpi.getEligibleProduct();
				if (!isTestConsumed(test, subscription)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private int calculateNumberOfTakesForTest(ETSProduct test, BatterySubscription subscription) {
		int testCount = 0;
		if (test != null & subscription != null) {
			List<BatteryScheduledTest> scheduled = readAllScheduledTestsForBattery(subscription);
			if (scheduled != null) {
				for (BatteryScheduledTest st : scheduled) {
					if (st.getTestDiscreteOrderItem().getProduct().getId().equals(test.getId())
							&& !st.getInactive()){
						testCount++;
					}
				}
			}
		}
		return testCount;
	}
	
	@Override
	public Boolean isTestEligibleForAnyBatteryInCart(TestDiscreteOrderItem testDiscreteOrderItem) {

		if (findEligibleBatteryInCart(testDiscreteOrderItem) != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method checks:
	 *  - Is this Test listed as an eligible Test Item for this Battery Product
	 *  - Is the Test Date before the expiration of the Battery
	 */
	private BatteryDiscreteOrderItem findEligibleBatteryInCart(TestDiscreteOrderItem testDiscreteOrderItem) {
		Order cart = testDiscreteOrderItem.getOrder();
		
		for (OrderItem orderItem : cart.getOrderItems()) {
			if (orderItem instanceof BatteryDiscreteOrderItem) {
				BatteryDiscreteOrderItem bdoi = (BatteryDiscreteOrderItem) orderItem;
				Agency agency = testDiscreteOrderItem.getFirstBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
				ETSProduct test = (ETSProduct) testDiscreteOrderItem.getProduct();
				BatteryProduct product = (BatteryProduct) bdoi.getProduct();
				
				if (agency != null && bdoi.getAgency().getId().equals(agency.getId()) &&
						isTestAnEligibleBatteryProductItem(product, test) &&
						isTestDateBeforeBatteryEndDate(testDiscreteOrderItem, bdoi)) {
					return bdoi;
				}

			}
		}	
		
		return null;
	}

	@Override
	public List<BatteryScheduledTest> readAllScheduledTestsForBattery(
			BatterySubscription battery) {
		return batteryScheduledTestDao.readScheduledTestsForBatterySubscription(battery);
	}
	
	@Override
	public List<BatteryScheduledTest> readAllScheduledTestsForBattery(
			Long subscriptionId) {
		return batteryScheduledTestDao.readScheduledTestsForBatterySubscription(subscriptionId);
	}

	
	@Override
	public List<BatterySubscription> readAllClosedBatteriesForCustomer(Customer customer) {
		List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(customer);
		List<BatterySubscription> closedSubs = new ArrayList<BatterySubscription>();
		
		for (BatterySubscription sub : subs) {
			if (isBatteryExpired(sub) || isBatteryConsumed(sub)) {
				closedSubs.add(sub);
			}
		}
		
		return closedSubs;
	}
	
	@Override
	public List<BatterySubscription> readAllExpiredBatteriesForCustomer(Customer customer) {
		List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(customer);
		List<BatterySubscription> expiredSubs = new ArrayList<BatterySubscription>();
		
		for (BatterySubscription sub : subs) {
			if (isBatteryExpired(sub)) {
				expiredSubs.add(sub);
			}
		}
		
		return expiredSubs;
	}

	@Override
	public List<BatterySubscription> readAllBatteriesForCustomer(Customer customer) {
		
		List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(customer);
		
		return subs;
	}

	@Override
	public Map<String, List<BatterySubscription>> readAllBatteriesForCustomerByStatus(Customer customer) {
		
		Map<String, List<BatterySubscription>> subscriptionByStatus = new HashMap<String, List<BatterySubscription>>();
		
		List<BatterySubscription> subs = batterySubscriptionDao.readBatterySubscriptionsForCustomer(customer);
		
		subscriptionByStatus.put(BatteryStatusEnum.EXPIRED.getCode(), new ArrayList<BatterySubscription>(5));
		subscriptionByStatus.put(BatteryStatusEnum.CLOSED.getCode(), new ArrayList<BatterySubscription>(5));
		subscriptionByStatus.put(BatteryStatusEnum.OPEN.getCode(), new ArrayList<BatterySubscription>(3));
		subscriptionByStatus.put(BatteryStatusEnum.CANCELED.getCode(), new ArrayList<BatterySubscription>(5));
		
		
		for(BatterySubscription bs : subs){
			
			
			//TODO- put here logic for cancellation of battery
			if(isBatteryConsumed(bs)){
					
				subscriptionByStatus.get(BatteryStatusEnum.CLOSED.getCode()).add(bs);
				
			}
			
			if(isBatteryExpired(bs)){
			
				subscriptionByStatus.get(BatteryStatusEnum.EXPIRED.getCode()).add(bs);
			
			}
			
			if(!isBatteryConsumed(bs) && ! isBatteryExpired(bs))
			{
				
				subscriptionByStatus.get(BatteryStatusEnum.OPEN.getCode()).add(bs);
			} 
			
		}
		
		return subscriptionByStatus;
	}

	public void setBatterySubscriptionDao(
			BatterySubscriptionDao batterySubscriptionDao) {
		this.batterySubscriptionDao = batterySubscriptionDao;
	}

	public void setBatteryScheduledTestDao(
			BatteryScheduledTestDao batteryScheduledTestDao) {
		this.batteryScheduledTestDao = batteryScheduledTestDao;
	}
	
	
	
	public Map<Test, Map<String,Object>> batteryTestInfo(BatterySubscription batterySubscription){
		

       Map<Long, Map<String,Object>> batteryTakenTestMap = new HashMap<Long, Map<String,Object>>();
       

       if(batterySubscription != null){
	        List<BatteryScheduledTest> batteryScheduledTests = readAllScheduledTestsForBattery(batterySubscription);

	        for(BatteryScheduledTest batteryScheduledTest : batteryScheduledTests){

				if (!batteryScheduledTest.getInactive()){
					if(batteryTakenTestMap.get(batteryScheduledTest.getTestDiscreteOrderItem().getProduct().getId()) == null)
					{
						Map temp = new HashMap();
						temp.put("NUMBER_REMAINING", 1L);
						temp.put("BOOKINGS", batteryScheduledTest.getTestDiscreteOrderItem().getAllBookings());
						batteryTakenTestMap.put(batteryScheduledTest.getTestDiscreteOrderItem().getProduct().getId(), temp);
						
					}else{
						
						Long temp = (Long)batteryTakenTestMap.get(batteryScheduledTest.getTestDiscreteOrderItem().getProduct().getId()).get("NUMBER_REMAINING");
						temp = temp + 1;
					}
				}
	        }

	       
	        Map<Test, Map<String,Object>> batteryTestInfo = new TreeMap<Test, Map<String,Object>>(new Comparator<Test>(){

				@Override
				public int compare(Test test1, Test test2) {

					return test1.getTestName().compareTo(test2.getTestName());

				}
			});

	        for(BatteryProductItem batteryProductItem : batterySubscription.getBatteryProduct().getBatteryProductItems()){
	        	if (!batteryProductItem.getEligibleProduct().getAdditionalSkus().isEmpty() && batteryProductItem.getEligibleProduct().getAdditionalSkus().get(0) instanceof TestSku) {
	  				  TestSku testSku = (TestSku) batteryProductItem.getEligibleProduct().getAdditionalSkus().get(0);
	  				  Test test = testSku.getTestVariation().getTest();
	  				  Long temp;
	  				  
		        	if(batteryTakenTestMap.get(batteryProductItem.getEligibleProduct().getId()) != null){
		        		
		        		temp = (Long )batteryTakenTestMap.get(batteryProductItem.getEligibleProduct().getId()).get("NUMBER_REMAINING");
		        		temp = batteryProductItem.getBatteryProduct().getTotalTakes() - temp;
		        		batteryTestInfo.put(test, batteryTakenTestMap.get(batteryProductItem.getEligibleProduct().getId()));
		        	}else{
		        		Map tempMap = new HashMap<String,Object>();
		        		tempMap.put("NUMBER_REMAINING",batteryProductItem.getBatteryProduct().getTotalTakes() - 0L);
		        		tempMap.put("BOOKINGS", new ArrayList<Booking>());
		        		batteryTestInfo.put(test,tempMap);
		        	}
    			}
	        }
	        
	        return batteryTestInfo;
       	}
        
       return new HashMap<Test, Map<String,Object>>();
    }
	
	
	@Override
	public boolean isTestBelongsToBattery(Customer customer, TestDiscreteOrderItem testDiscreteOrderItem) {
		Agency agency = testDiscreteOrderItem.getFirstBooking().getTestCenter().getAgency(ProgramContextHolder.getProgramCode());
		BatterySubscription batterySubscription = readOpenBatteryForCustomerAndAgency(customer, agency);
		if (batterySubscription != null) {
			List<BatteryScheduledTest> listBatteryScheduledTest = readAllScheduledTestsForBattery(batterySubscription);
			for(BatteryScheduledTest batteryScheduledTest : listBatteryScheduledTest){
				if(testDiscreteOrderItem.getId().equals(batteryScheduledTest.getTestDiscreteOrderItem().getId())){
					return true;
				}
				
			}
		}
		
		return false;
	}
	

}
