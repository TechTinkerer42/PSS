package org.ets.ereg.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItemImpl;
import org.ets.ereg.domain.interfaces.model.order.ETSDiscreteOrderItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ETS_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Discrete Order Item")
public class ETSDiscreteOrderItemImpl extends DiscreteOrderItemImpl implements ETSDiscreteOrderItem {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = true,targetEntity=ETSDiscreteOrderItemImpl.class)
    @JoinColumn(name="REF_ORD_ITM_ID",referencedColumnName = "ORDER_ITEM_ID", nullable = true)
    private ETSDiscreteOrderItem refOrderItemId;
 
    @OneToMany(mappedBy="refOrderItemId",targetEntity=ETSDiscreteOrderItemImpl.class, cascade={CascadeType.ALL})
    private List<ETSDiscreteOrderItem> refOrderItemIds = new ArrayList<ETSDiscreteOrderItem>();

    @Override
	public ETSDiscreteOrderItem getRefOrderItemId() {
		return refOrderItemId;
	}

    @Override
	public void setRefOrderItemId(ETSDiscreteOrderItem refOrderItemId) {
		this.refOrderItemId = refOrderItemId;
	}

    @Override
	public List<ETSDiscreteOrderItem> getRefOrderItemIds() {
		return refOrderItemIds;
	}

    @Override
	public void setRefOrderItemIds(List<ETSDiscreteOrderItem> refOrderItemIds) {
		this.refOrderItemIds = refOrderItemIds;
	}        
 
	@Override
	public boolean canCountTowardsItemCount() {		
		return true;
	}

}
