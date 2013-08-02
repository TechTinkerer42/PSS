package org.ets.ereg.domain.interfaces.model.form;

import java.io.Serializable;
import java.util.Date;

import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.FormType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;

public interface Form extends Serializable {

	void setFormID(Long id);
	Long getFormID();

    /*TestVariation getTestVariation();
    void setTestVariation(TestVariation testVariation);*/

	void setParentFormID(Form parentForm);
	Form getParentFormID();

	void setFormType(FormType formType);
	FormType getFormType();

	AccommodationType getAccommodation();
	void setAccommodation(AccommodationType accommodation);

	void setEffDate(Date effDate);
	Date getEffDate();

	void setExpiryDate(Date expiryDate);
	Date getExpiryDate();

	void setFormDesc(String formDesc);
	String getFormDesc();

	void setFormCode(String formCode);
	String getFormCode();

	void setTest(Test test);
    Test getTest();

    void setLangCode(LanguageType lang);
    LanguageType getLangCode();

    void setDlvyMode(DeliveryModeType dlvyMode);
    DeliveryModeType getDlvyMode();

}
