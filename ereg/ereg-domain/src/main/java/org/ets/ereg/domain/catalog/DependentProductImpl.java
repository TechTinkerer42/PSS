package org.ets.ereg.domain.catalog;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.ets.ereg.domain.interfaces.model.catalog.DependentProduct;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DEP_PROD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="etsStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "Dependent Product")
public class DependentProductImpl implements DependentProduct {

    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "DependentProductId")
    @GenericGenerator(
            name="DependentProductId",
            strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name="table_name", value="SEQUENCE_GENERATOR"),
                    @Parameter(name="segment_column_name", value="ID_NAME"),
                    @Parameter(name="value_column_name", value="ID_VAL"),
                    @Parameter(name="segment_value", value="DependentProductImpl"),
                    @Parameter(name="increment_size", value="50"),
                    @Parameter(name="entity_name", value="org.ets.ereg.domain.catalog.DependentProductImpl")
            }
    )
    @Column(name = "DEP_PROD_ID")
    protected Long id;

    @ManyToOne(targetEntity = ProductImpl.class)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QTY")
    private Integer quantity;

    @Column(name = "UNQ_FLG")
    @Type(type="yes_no")
    private Boolean isUnique;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isUnique() {
        return isUnique;
    }

    public void setUnique(Boolean unique) {
        isUnique = unique;
    }
}
