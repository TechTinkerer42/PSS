package org.ets.ereg.commerce.order.dao;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;

public interface BatterySubscriptionDao extends Dao<BatterySubscription> {
	
	List<BatterySubscription> readBatterySubscriptionsForCustomer(Customer customer);

}
