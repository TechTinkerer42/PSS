package org.ets.ereg.domain.interfaces.model.order;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.broadleafcommerce.core.offer.domain.Offer;

import java.util.Date;

public interface BatteryScheduledTest {

	public Long getId();

	public void setId(Long id);
	
    public BatterySubscription getBatterySubscription();
    
    public void setBatterySubscription(BatterySubscription batterySubscription);
    
    public TestDiscreteOrderItem getTestDiscreteOrderItem();
    
    public void setTestDiscreteOrderItem(TestDiscreteOrderItem testDiscreteOrderItem);
    
    public Date getDateRedeemed();
    
    public void setDateRedeemed(Date dateRedeemed);
    
    public Boolean getInactive();
    
    public void setInactive(Boolean inactive);

    public String getInactiveReason();
    
    public void setInactiveReason(String inactiveReason);
    
    public Offer getAppliedOffer();
    
    public void setAppliedOffer(Offer offer);
    
}