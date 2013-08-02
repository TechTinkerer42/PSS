package org.ets.ereg.domain.catalog;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.catalog.domain.CrossSaleProductImpl;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.ets.ereg.domain.booking.BookingStatusTypeImpl;
import org.ets.ereg.domain.interfaces.model.booking.BookingStatusType;
import org.ets.ereg.domain.interfaces.model.catalog.CartRule;
import org.ets.ereg.domain.interfaces.model.catalog.CartRuleType;
import org.ets.ereg.domain.interfaces.model.catalog.DependentProduct;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CART_RUL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="etsStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "Cart Rule")
public class CartRuleImpl implements CartRule {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CartRuleId")
    @GenericGenerator(
            name = "CartRuleId",
            strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name="segment_value", value="CartRuleImpl"),
                    @Parameter(name = "entity_name", value = "org.ets.ereg.domain.catalog.CartRuleImpl")
            }
    )
    @Column(name = "CART_RUL_ID")
    protected Long id;

    @Column(name = "CART_RUL_NAM")
    protected String name;

    @Column(name = "CART_RUL_DSC")
    protected String description;

    @Column(name = "CART_RUL_EXPRSN")
    protected String expression;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = CartRuleTypeImpl.class)
    @JoinColumn(name = "CART_RUL_TYP_CDE")
    private CartRuleType cartRuleType;

    @ManyToMany(targetEntity = DependentProductImpl.class)
    @JoinTable(name = "CART_RUL_DEP_PROD", joinColumns = @JoinColumn(name = "CART_RUL_ID"), inverseJoinColumns = @JoinColumn(name = "DEP_PROD_ID", nullable = true))
    @Cascade(value={org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    protected List<DependentProduct> dependentProducts = new ArrayList<DependentProduct>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public CartRuleType getCartRuleType() {
        return cartRuleType;
    }

    @Override
    public void setCartRuleType(CartRuleType cartRuleType) {
        this.cartRuleType = cartRuleType;
    }

    @Override
    public List<DependentProduct> getDependentProducts() {
        return dependentProducts;
    }

    @Override
    public void setDependentProducts(List<DependentProduct> dependentProducts) {
        this.dependentProducts = dependentProducts;
    }
}
