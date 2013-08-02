package org.ets.ereg.domain.catalog;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.model.catalog.CartRuleType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity()
@Table(name = "CART_RUL_TYP" )
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "etsStandardElements")
public class CartRuleTypeImpl implements CartRuleType, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CART_RUL_TYP_CDE", nullable = false)
    private String code;

    @Column(name="CART_RUL_TYP_DSC", nullable = false)
    private String description;

    @Column(name="CART_RUL_TYP_DSPLY_SEQ")
    private Long cartRuleTypeDispSeq;

    @Column(name="DSPLY_DTA_FLG", nullable = false, columnDefinition = "char(1) default 'Y'")
    @Type(type="yes_no")
    private Boolean cartRuleTypeDispDataFlag;

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDisplaySequence(Long cartRuleTypeDispSeq) {
        this.cartRuleTypeDispSeq = cartRuleTypeDispSeq;
    }

    @Override
    public Long getDisplaySequence() {
        return cartRuleTypeDispSeq;
    }

    @Override
    public void setDisplayable(Boolean cartRuleTypeDispDataFlag) {
        this.cartRuleTypeDispDataFlag = cartRuleTypeDispDataFlag;
    }

    @Override
    public Boolean isDisplayable() {
        return cartRuleTypeDispDataFlag;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(description)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CartRuleTypeImpl) {
            final CartRuleTypeImpl other = (CartRuleTypeImpl) obj;
            return new EqualsBuilder().append(code, other.code)
                    .append(description, other.description).isEquals();
        } else {
            return false;
        }
    }
}
