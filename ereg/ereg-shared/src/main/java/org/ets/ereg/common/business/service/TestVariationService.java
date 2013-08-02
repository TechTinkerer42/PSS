package org.ets.ereg.common.business.service;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;





public interface TestVariationService {

	public List<LanguageType> getAllLanguageTypesForTest(String programCode, Long testId);

	public List<DeliveryModeType> getAllDeliveryModeTypesForTest(String programCode, Long testId);

    public TestVariation getTestVariationForTest(Long testId, String deliveryMode,
            String languageCode, String programCode);
}
