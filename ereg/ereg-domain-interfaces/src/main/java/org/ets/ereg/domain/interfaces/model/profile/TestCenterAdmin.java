package org.ets.ereg.domain.interfaces.model.profile;

import org.ets.ereg.domain.interfaces.model.profile.admin.id.TestCenterAdminUserId;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;



public interface TestCenterAdmin {

    ETSAdminUser getEtsAdminUser();

    void setEtsAdminUser(ETSAdminUser etsAdminUser);

    TestCenter getTestCenter();

    void setTestCenter(TestCenter testCenter);

    TestCenterAdminUserId getTestCenterAdminUserId();

    void setTestCenterAdminUserId(TestCenterAdminUserId testCenterAdminUserId);

}
