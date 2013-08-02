package org.ets.ereg.domain.interfaces.model.test;

import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;

public interface TestVariation {

    Test getTest();

    void setTest(Test test);

    LanguageType getLanguageType();

    void setLanguageType(LanguageType languageType);

    DeliveryModeType getDeliveryModeType();

    void setDeliveryModeType(DeliveryModeType deliveryModeType);

    String getTestCode();

    void setTestCode(String testCode);

    String getScheduleTestCode();

    void setScheduleTestCode(String scheduleTestCode);

    TestVarianceId getId();

    void setId(TestVarianceId id);

    String getFunctionCode();

    void setFunctionCode(String functionCode);


}
