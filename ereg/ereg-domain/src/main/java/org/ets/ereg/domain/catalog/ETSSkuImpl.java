package org.ets.ereg.domain.catalog;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.ets.ereg.domain.interfaces.model.catalog.CartRule;
import org.ets.ereg.domain.interfaces.model.catalog.ETSSku;
import org.ets.ereg.domain.order.type.ETSFulfillmentType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ETS_SKU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="etsStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "ETS SKU")
public class ETSSkuImpl extends SkuImpl implements ETSSku {

    private static final long serialVersionUID = 1L;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = CartRuleImpl.class)
    @JoinTable(name = "CART_RUL_SKU",
            joinColumns = @JoinColumn(name = "SKU_ID", referencedColumnName = "SKU_ID", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "CART_RUL_ID", referencedColumnName = "CART_RUL_ID", nullable = true))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
    protected List<CartRule> cartRules = new ArrayList<CartRule>();

    @Override
    public List<CartRule> getCartRules() {
        return cartRules;
    }

    @Override
    public void setCartRules(List<CartRule> cartRules) {
        this.cartRules = cartRules;
    }
    
    @Override
    public FulfillmentType getFulfillmentType() {
        return ETSFulfillmentType.getInstance(super.fulfillmentType);
    }
    
}
