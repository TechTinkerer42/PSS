package org.ets.ereg.commerce.order.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.commerce.order.dao.BatteryScheduledTestDao;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.order.BatteryScheduledTest;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("batteryScheduledTestDao")
public class BatteryScheduledTestDaoImpl extends AbstractDaoImpl<BatteryScheduledTest> implements BatteryScheduledTestDao {

	private static Logger logger = LoggerFactory.getLogger(BatteryScheduledTestDaoImpl.class);
	
	@Override
	public Class<BatteryScheduledTest> getEntityClass() {
		return BatteryScheduledTest.class;
	}

	//TODO - may be removed. The one below by subscription id is enough
	@SuppressWarnings("unchecked")
	@Override
	public List<BatteryScheduledTest> readScheduledTestsForBatterySubscription(
			BatterySubscription subscription) {
		
		logger.debug("readScheduledTestsForBatterySubscription for subscription id {}", subscription.getId());

		Query query= entityManager.createNamedQuery("BatteryScheduledTest.findBySubscription");
		query.setParameter("subscriptionId", subscription.getId());

		return (List<BatteryScheduledTest>)query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BatteryScheduledTest> readScheduledTestsForBatterySubscription(
			Long subscriptionId) {
		
		logger.debug("readScheduledTestsForBatterySubscription for subscription id {}", subscriptionId);

		Query query= entityManager.createNamedQuery("BatteryScheduledTest.findBySubscription");
		query.setParameter("subscriptionId", subscriptionId);

		return (List<BatteryScheduledTest>)query.getResultList();
	}



}
