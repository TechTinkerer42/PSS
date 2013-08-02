package org.ets.ereg.domain.interfaces.model.catalog;

import org.broadleafcommerce.core.catalog.domain.Product;

public interface DependentProduct  {

    public Product getProduct();

    public void setProduct(Product product);

    public Integer getQuantity();

    public void setQuantity(Integer quantity);

    public Boolean isUnique();

    public void setUnique(Boolean isUnique);

}
