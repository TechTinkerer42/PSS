package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;


public interface TestVariationDao {

	List<LanguageType> getAllLanguageTypesForTest(String programCode, Long testId);

    List<DeliveryModeType> getAllDeliveryModesForTest(String programCode,
            Long testId);

    TestVariation getTestVariationForTest(Long testId,
            String deliveryMode, String languageCode, String programCode);
}
