package org.ets.ereg.commerce.catalog.service;

import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;

public interface ETSBatteryProductService {
	
	public BatteryProduct readBatteryProductByEligibleProduct(ETSProduct product);

}
