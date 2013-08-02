package org.ets.ereg.domain.interfaces.model.catalog;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.ets.ereg.domain.interfaces.model.catalog.CartRule;

import java.util.List;

public interface ETSSku extends Sku {

    public List<CartRule> getCartRules();

    public void setCartRules(List<CartRule> cartRules);

}
