package org.ets.ereg.domain.interfaces.model.catalog;

import org.broadleafcommerce.core.catalog.domain.Product;

public interface BatteryProductItem {

	public Long getId();

	public void setId(Long id);
	
	public BatteryProduct getBatteryProduct();
	
	public void setBatteryProduct(BatteryProduct batteryProduct);
	
	public Product getEligibleProduct();
	
	public void setEligibleProduct(Product eligibleProduct);

}