package org.ets.ereg.common.business.biq.dao;

import java.util.List;

import org.ets.ereg.common.business.dao.Dao;
import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSetEntry;
import org.ets.ereg.domain.interfaces.model.i18n.Translation;

public interface DemographicQuestionSetEntryDao extends Dao<DemographicQuestionSetEntry>{
	
	public List<DemographicQuestionSetEntry> getDemographicQuestions(String programCode, String setTypeCode, boolean isCustomerSort);
	public Translation getTranslationText(String languageCode,Long i18nId);

}
