package org.ets.ereg.commerce.catalog.service;

import javax.annotation.Resource;

import org.ets.ereg.commerce.catalog.dao.BatteryProductItemDao;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.springframework.stereotype.Service;


@Service("etsBatteryProductService")
public class ETSBatteryProductServiceImpl implements ETSBatteryProductService {

	@Resource(name = "batteryProductItemDao")
	protected BatteryProductItemDao batteryProductItemDao;

	@Override
	public BatteryProduct readBatteryProductByEligibleProduct(ETSProduct product) {
		BatteryProductItem bpi = batteryProductItemDao.readBatteryProductItemByProduct(product);
		if (bpi != null) {
			return bpi.getBatteryProduct();
		}
		return null;
	}

}
