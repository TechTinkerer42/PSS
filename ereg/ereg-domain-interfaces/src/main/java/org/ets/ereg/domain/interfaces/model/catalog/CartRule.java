package org.ets.ereg.domain.interfaces.model.catalog;

import org.broadleafcommerce.core.catalog.domain.Product;

import java.util.List;

public interface CartRule {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public String getExpression();

    public void setExpression(String expression);

    public List<DependentProduct> getDependentProducts();

    public void setDependentProducts(List<DependentProduct> products);

    public void setCartRuleType(CartRuleType cartRuleType);

    public CartRuleType getCartRuleType();
}
