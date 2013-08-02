package org.ets.ereg.domain.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BTTRY_PROD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Battery Product")
public class BatteryProductImpl extends ETSProductImpl implements BatteryProduct {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "DURN_MONTHS")
    private Integer durationInMonths;
    
    @Column(name = "TOT_TAKES_NO")
    private Integer totalTakes;
    
    @OneToMany(mappedBy = "batteryProduct", targetEntity = BatteryProductItemImpl.class, cascade = { CascadeType.ALL })
    @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
    @BatchSize(size = 50)
    private List<BatteryProductItem> batteryProductItems = new ArrayList<BatteryProductItem>();

	public Integer getDurationInMonths() {
		return durationInMonths;
	}

	public void setDurationInMonths(Integer durationInMonths) {
		this.durationInMonths = durationInMonths;
	}

	public Integer getTotalTakes() {
		return totalTakes;
	}

	public void setTotalTakes(Integer totalTakes) {
		this.totalTakes = totalTakes;
	}

	public List<BatteryProductItem> getBatteryProductItems() {
		return batteryProductItems;
	}

	public void setBatteryProductItems(List<BatteryProductItem> batteryProductItems) {
		this.batteryProductItems = batteryProductItems;
	}

	@Override
	public Integer getMaxReTakes() {
		return getTotalTakes()-1;
	}

} 
