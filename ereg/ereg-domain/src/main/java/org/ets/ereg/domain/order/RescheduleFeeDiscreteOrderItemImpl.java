package org.ets.ereg.domain.order;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "RSCHDL_FEE_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Reschedule Fee Discrete Order Item")
public class RescheduleFeeDiscreteOrderItemImpl extends ETSDiscreteOrderItemImpl {

	private static final long serialVersionUID = 1L;
	
    public boolean canCountTowardsItemCount() {		
		return false;
	}

}
