package org.ets.ereg.commerce.order.dao;

import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;

public interface BatteryScheduledTestDao extends Dao<BatteryScheduledTest> {
	
	public List<BatteryScheduledTest> readScheduledTestsForBatterySubscription(BatterySubscription subscription);
	public List<BatteryScheduledTest> readScheduledTestsForBatterySubscription(Long subscriptionId);
	
}
