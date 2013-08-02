package org.ets.ereg.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.ETSProduct;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BTTRY_SBSCRPTN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Battery Subscription")
public class BatterySubscriptionImpl implements BatterySubscription {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "BatterySubscriptionId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "BatterySubscriptionId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "BatterySubscriptionImpl", allocationSize = 50)
    @Column(name = "BTTRY_SBSCRPTN_ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ETSCustomerImpl.class)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)    
    private Customer customer;
    
    @OneToOne(fetch = FetchType.LAZY, targetEntity = BatteryDiscreteOrderItemImpl.class)
    @JoinColumn(name = "ORDER_ITEM_ID", nullable = false)
    private BatteryDiscreteOrderItem batteryDiscreteOrderItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agency getAgency() {
		
		if (batteryDiscreteOrderItem != null) {
			return batteryDiscreteOrderItem.getAgency();
		}
		
		return null;
	}


	public Date getStartDate() {
		
		if (batteryDiscreteOrderItem != null) {
			return batteryDiscreteOrderItem.getStartDate();
		}
		
		return null;
	}

	public Date getEndDate() {
		
		if (batteryDiscreteOrderItem != null) {
			return batteryDiscreteOrderItem.getEndDate();
		}	
		
		return null;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BatteryDiscreteOrderItem getBatteryDiscreteOrderItem() {
		return batteryDiscreteOrderItem;
	}

	public void setBatteryDiscreteOrderItem(
			BatteryDiscreteOrderItem batteryDiscreteOrderItem) {
		this.batteryDiscreteOrderItem = batteryDiscreteOrderItem;
	}
	
	public BatteryProduct getBatteryProduct() {
		if (this.batteryDiscreteOrderItem != null) {
			ETSProduct prod =  (ETSProduct) this.batteryDiscreteOrderItem.getProduct();
			if (prod instanceof BatteryProduct) {
				return (BatteryProduct) prod;
			}
		}
		
		return null;
	}

}
