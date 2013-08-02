package org.ets.ereg.commerce.catalog.dao;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;

public interface BatteryProductItemDao extends Dao<BatteryProductItem> {
	
	public BatteryProductItem readBatteryProductItemByProduct(ETSProduct product);

}
