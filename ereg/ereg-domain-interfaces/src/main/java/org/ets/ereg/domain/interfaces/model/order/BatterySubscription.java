package org.ets.ereg.domain.interfaces.model.order;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;

import java.util.Date;

public interface BatterySubscription {
	
	public Long getId();

	public void setId(Long id);
    
    public BatteryDiscreteOrderItem getBatteryDiscreteOrderItem();
    
    public void setBatteryDiscreteOrderItem(BatteryDiscreteOrderItem batteryDiscreteOrderItem);
    
    public Customer getCustomer();
    
    public void setCustomer(Customer customer);
    
    public Agency getAgency();

    public Date getStartDate();

    public Date getEndDate();
    
    public BatteryProduct getBatteryProduct();

}