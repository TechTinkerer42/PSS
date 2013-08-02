package org.ets.ereg.commerce.order.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.dao.BatteryScheduledTestDao;
import org.ets.ereg.commerce.order.dao.BatterySubscriptionDao;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.test.Test;

public interface ETSBatterySubscriptionService {
	
	public BatterySubscription createSubscription();
	
	public BatterySubscription saveSubscription(BatterySubscription batterySubscription);
	
	public BatteryScheduledTest createScheduledTest();
	
	public BatteryScheduledTest saveScheduledTest(BatteryScheduledTest scheduledTest);
	
	public List<BatterySubscription> readAllOpenBatteriesForCustomer(Customer customer);
	
	public BatterySubscription readOpenBatteryForCustomerAndAgency(Customer customer, Agency agency);
	
	public BatterySubscription readOpenBatteryForCustomerAndAgency(Customer customer, Long agencyId);
	
	public BatterySubscription readEligibleBatteryForTest(Customer customer, TestDiscreteOrderItem testDiscreteOrderItem);
	
	public Boolean isTestEligibleForBattery(BatterySubscription subscription, TestDiscreteOrderItem testDiscreteOrderItem);

	public Boolean isTestEligibleForAnyBatteryInCart(TestDiscreteOrderItem testDiscreteOrderItem);
	 
	public List<BatteryScheduledTest> readAllScheduledTestsForBattery(BatterySubscription battery);
	
	public List<BatteryScheduledTest> readAllScheduledTestsForBattery(Long subscriptionId);
	
	public List<BatterySubscription> readAllClosedBatteriesForCustomer(Customer customer);
	
	public List<BatterySubscription> readAllExpiredBatteriesForCustomer(Customer customer);
	
	public List<BatterySubscription> readAllBatteriesForCustomer(Customer customer);
	
	public Map<String, List<BatterySubscription>> readAllBatteriesForCustomerByStatus(Customer customer);
	
	public void setBatterySubscriptionDao(BatterySubscriptionDao batterySubscriptionDao );

	public void setBatteryScheduledTestDao(BatteryScheduledTestDao batteryScheduledTestDao);

	public Map<Test, Map<String,Object>> batteryTestInfo(BatterySubscription batterySubscription);
	
	public boolean isTestBelongsToBattery(Customer customer, TestDiscreteOrderItem testDiscreteOrderItem);
}
