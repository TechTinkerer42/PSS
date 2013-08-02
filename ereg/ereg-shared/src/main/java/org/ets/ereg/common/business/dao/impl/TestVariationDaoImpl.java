package org.ets.ereg.common.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.dao.TestVariationDao;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("testVariationDao")
public class TestVariationDaoImpl extends AbstractDaoImpl<TestVariation> implements TestVariationDao{

	private static Logger logger = LoggerFactory.getLogger(TestVariationDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageType> getAllLanguageTypesForTest(String programCode, Long testId) {

		logger.debug("get getAllLanguageTypesForTest for program code {} testId {}", programCode, testId);

		Query query= entityManager.createNamedQuery("TestVariation.getAllLanguageTypesForTest");
		query.setParameter("programCode", programCode);
		query.setParameter("testId", testId);

		return (List<LanguageType>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<DeliveryModeType> getAllDeliveryModesForTest(String programCode, Long testId) {

        logger.debug("getAllDeliveryModesForTest for program code {} test id {}", programCode, testId);

        Query query= entityManager.createNamedQuery("TestVariation.getAllDeliveryModesForTest");
        query.setParameter("programCode", programCode);
        query.setParameter("testId", testId);

        return (List<DeliveryModeType>)query.getResultList();
    }

	@Override
	public Class<? extends TestVariation> getEntityClass() {
		return TestVariation.class;
	}

    @Override
    public TestVariation getTestVariationForTest(Long testId,
            String deliveryMode, String languageCode, String programCode) {
        logger.debug("getTestVariationForTest for program code {} test id {}", programCode, testId);

        Query query= entityManager.createNamedQuery("TestVariation.getTestVariationForTest");
        query.setParameter("testId", testId);
        query.setParameter("programCode", programCode);
        query.setParameter("deliveryModeCode", deliveryMode);
        query.setParameter("languageCode", languageCode);
        return (TestVariation)query.getSingleResult();
    }



}
