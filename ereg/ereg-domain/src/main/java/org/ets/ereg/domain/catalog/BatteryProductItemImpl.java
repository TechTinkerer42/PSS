package org.ets.ereg.domain.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProduct;
import org.ets.ereg.domain.interfaces.model.catalog.BatteryProductItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BTTRY_PROD_ITM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS Battery Product Items")
public class BatteryProductItemImpl implements BatteryProductItem {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "BatteryProductItemId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "BatteryProductItemId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "BatteryProductItemImpl", allocationSize = 50)
    @Column(name = "BTTRY_PROD_ITM_ID")
    private Long id;

    @ManyToOne(targetEntity = BatteryProductImpl.class, optional = false)
    @JoinColumn(name = "BTTRY_PROD_ID", referencedColumnName = "PRODUCT_ID")
    private BatteryProduct batteryProduct;
    
    @OneToOne(targetEntity = ETSProductImpl.class, optional = false)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product eligibleProduct;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BatteryProduct getBatteryProduct() {
		return batteryProduct;
	}

	public void setBatteryProduct(BatteryProduct batteryProduct) {
		this.batteryProduct = batteryProduct;
	}

	public Product getEligibleProduct() {
		return eligibleProduct;
	}

	public void setEligibleProduct(Product eligibleProduct) {
		this.eligibleProduct = eligibleProduct;
	}
	
}
