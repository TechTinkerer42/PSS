package org.ets.ereg.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.interfaces.model.order.BatteryDiscreteOrderItem;
import org.ets.ereg.domain.interfaces.model.order.BatterySubscription;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.scheduling.AgencyImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BTTRY_DSCRT_ORD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Battery Discrete Order Item")
public class BatteryDiscreteOrderItemImpl extends ETSDiscreteOrderItemImpl implements BatteryDiscreteOrderItem {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AgencyImpl.class, optional = true)
    @JoinColumn(name = "AGNCY_ID_NO", nullable = true)
    private Agency agency;

    @Column(name = "STRT_DTE")
    private Date startDate;

    @Column(name = "END_DTE")
    private Date endDate;

    @Column(name = "TOT_TAKES_NO")
    private Integer totalTakes;

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public Integer getTotalTakes() {
		return totalTakes;
	}

	public void setTotalTakes(Integer totalTakes) {
		this.totalTakes = totalTakes;
	}
    
}
