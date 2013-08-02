package org.ets.ereg.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.order.TestDiscreteOrderItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TST_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Test Discrete Order Item")
public class TestDiscreteOrderItemImpl extends ETSDiscreteOrderItemImpl implements TestDiscreteOrderItem {

    private static final long serialVersionUID = 1L;  
    
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = BookingImpl.class)
    @JoinTable(name = "TST_DSCRT_ORD_ITM_BKNG", joinColumns = @JoinColumn(name = "ORDER_ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "BKNG_ID"))
    protected List<Booking> allBookings = new ArrayList<Booking>();
    
    @Transient
    private boolean eligibleForBatteryInCart;
    
    @Transient
    private boolean eligibleForPurchasedBattery;

    @Override
	public List<Booking> getAllBookings() {
		return allBookings;
	}

	@Override
	public void setAllBookings(List<Booking> allBookings) {
		this.allBookings = allBookings;
	}
	
	@Override
	public boolean getEligibleForBatteryInCart() {
		return eligibleForBatteryInCart;
	}

	@Override
	public void setEligibleForBatteryInCart(boolean eligibleForBatteryInCart) {
		this.eligibleForBatteryInCart = eligibleForBatteryInCart;
	}
	
	@Override
	public boolean getEligibleForPurchasedBattery() {
		return eligibleForPurchasedBattery;
	}

	@Override
	public void setEligibleForPurchasedBattery(boolean eligibleForPurchasedBattery) {
		this.eligibleForPurchasedBattery = eligibleForPurchasedBattery;
	}

	@Override
	public Booking getFirstBooking() {
		if(!allBookings.isEmpty()){
			return allBookings.get(0);
		}
		return null;
	}
    

}
