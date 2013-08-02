package org.ets.ereg.common.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.TestVariationDao;
import org.ets.ereg.common.business.service.TestVariationService;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.springframework.stereotype.Service;

@Service("testVariationService")
public class TestVariationServiceImpl implements TestVariationService {

	@Resource(name="testVariationDao")
	private TestVariationDao testVariationDao;

    public TestVariationDao getTestVariationDao() {
        return testVariationDao;
    }

    public void setTestVariationDao(TestVariationDao testVariationDao) {
        this.testVariationDao = testVariationDao;
    }

    @Override
    public List<LanguageType> getAllLanguageTypesForTest(String programCode,
            Long testId) {
        return testVariationDao.getAllLanguageTypesForTest(programCode, testId);
    }

    @Override
    public List<DeliveryModeType> getAllDeliveryModeTypesForTest(
            String programCode, Long testId) {
        return testVariationDao.getAllDeliveryModesForTest(programCode, testId);
    }

    @Override
    public TestVariation getTestVariationForTest(Long testId,
            String deliveryMode, String languageCode, String programCode) {
        return testVariationDao.getTestVariationForTest(testId, deliveryMode, languageCode, programCode);
    }


}
