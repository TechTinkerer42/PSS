package org.ets.ereg.domain.order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItemImpl;
import org.ets.ereg.domain.interfaces.model.order.RescheduleTestDiscreteOrderItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "RSCHDL_TST_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Reschedule Test Discrete Order Item")
public class RescheduleTestDiscreteOrderItemImpl extends TestDiscreteOrderItemImpl implements RescheduleTestDiscreteOrderItem {
	  
	private static final long serialVersionUID = 1L;
	
    @ManyToOne(fetch = FetchType.EAGER, targetEntity=FulfillmentGroupItemImpl.class, optional = false)
    @JoinColumn(name = "ORG_FG_ITM_ID", referencedColumnName = "FULFILLMENT_GROUP_ITEM_ID", nullable=false)
	protected FulfillmentGroupItem originalTestItem;

	public FulfillmentGroupItem getOriginalTestItem() {
		return originalTestItem;
	}

	public void setOriginalTestItem(FulfillmentGroupItem originalTestItem) {
		this.originalTestItem = originalTestItem;
	}

}
