package org.ets.ereg.commerce.catalog.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.commerce.catalog.dao.BatteryProductItemDao;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("batteryProductItemDao")
public class BatteryProductItemDaoImpl extends AbstractDaoImpl<BatteryProductItem> implements BatteryProductItemDao {

	private static Logger logger = LoggerFactory.getLogger(BatteryProductItemDaoImpl.class);
	
	@Override
	public Class<BatteryProductItem> getEntityClass() {
		return BatteryProductItem.class;
	}
	
	@Override
	public BatteryProductItem readBatteryProductItemByProduct(ETSProduct product) {
		logger.debug("readBatteryProductItemByProductId for product id {}", product.getId());

		Query query= entityManager.createNamedQuery("BatteryProductItem.findByProduct");
		query.setParameter("productId", product.getId());
		
		List batteryProductItems=  query.getResultList();

		if(null!= batteryProductItems && !batteryProductItems.isEmpty()){
			return (BatteryProductItem)batteryProductItems.get(0);
		}
		return null;
	}

}
