package org.ets.ereg.domain.interfaces.model.catalog;

import java.util.List;

public interface BatteryProduct extends ETSProduct {
	
	public Integer getDurationInMonths();
	
	public void setDurationInMonths(Integer durationInMonths);
	
	public Integer getTotalTakes();
	
	public void setTotalTakes(Integer totalTakes);
	
	public List<BatteryProductItem> getBatteryProductItems();
	
	public void setBatteryProductItems(List<BatteryProductItem> batteryProductItems);
	
	public Integer getMaxReTakes();

}