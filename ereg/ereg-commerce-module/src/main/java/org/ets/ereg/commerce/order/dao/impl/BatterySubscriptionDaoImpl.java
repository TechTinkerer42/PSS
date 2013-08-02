package org.ets.ereg.commerce.order.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.commerce.order.dao.BatterySubscriptionDao;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("batterySubscriptionDao")
public class BatterySubscriptionDaoImpl extends AbstractDaoImpl<BatterySubscription> implements BatterySubscriptionDao {

	private static Logger logger = LoggerFactory.getLogger(BatterySubscriptionDaoImpl.class);

	@Override
	public Class<BatterySubscription> getEntityClass() {
		return BatterySubscription.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BatterySubscription> readBatterySubscriptionsForCustomer(Customer customer) {
		
		logger.debug("readBatterySubscriptionsForCustomer for customer id {}", customer.getId());

		Query query= entityManager.createNamedQuery("BatterySubscription.findByCustomer");
		query.setParameter("customerId", customer.getId());

		return (List<BatterySubscription>)query.getResultList();
	}


}
