package org.ets.ereg.domain.order;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.interfaces.model.order.MembershipDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.scheduling.AgencyImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "MBRSHP_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Membership Discrete Order Item")
public class MembershipDiscreteOrderItemImpl extends ETSDiscreteOrderItemImpl implements MembershipDiscreteOrderItem {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AgencyImpl.class, optional = true)
	@JoinColumn(name = "AGNCY_ID_NO", nullable = true)
    private Agency agency;

    @Column(name = "STRT_DTE")
    private Date startDate;

    @Column(name = "END_DTE")
    private Date endDate;

    @Override
    public Agency getAgency() {
        return agency;
    }

    @Override
    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public boolean canCountTowardsItemCount() {		
		return false;
	}
}
