package org.ets.ereg.domain.interfaces.model.catalog;

import org.ets.ereg.domain.interfaces.model.test.TestVariation;

public interface TestSku extends ETSSku {

    TestVariation getTestVariation();

    void setTestVariation(TestVariation testVariation);

    /*Long getSkuId();

    void setSkuId(Long skuId);
*/}
