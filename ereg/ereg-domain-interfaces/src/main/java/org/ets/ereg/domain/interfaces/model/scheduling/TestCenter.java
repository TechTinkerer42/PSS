package org.ets.ereg.domain.interfaces.model.scheduling;

import java.util.List;
import java.util.Set;

import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.GlobalTimeZone;
import org.ets.ereg.domain.interfaces.model.organization.Organization;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;

public interface TestCenter extends Organization {
    RestrictedAccessReason getRestrictedAccessReason();
    void setRestrictedAccessReason(RestrictedAccessReason restrictedAccessReason);
    String getOfficeHourText();
    void setOfficeHourText(String officeHourText);
    String getSpecialDrivingDirection();
    void setSpecialDrivingDirection(String drivingDirection);
    String getSpecialOnSiteDirection();
    void setSpecialOnSiteDirection(String onSiteDirection);
    Boolean getAccessRestricted();
    void setAccessRestricted(Boolean isAccessRestricted);
	Set<TestCenterProgram> getTestCenterPrograms();
	Set<TestCenterDeliveryMode> getTestCenterDeliveryModes();
    List<TestCenterAdmin> getEtsAdminUsers();
    void setEtsAdminUsers(List<TestCenterAdmin> etsAdminUsers);
    GlobalTimeZone getGlobalTimeZone();
    void setGlobalTimeZone(GlobalTimeZone globalTimeZone);
    Agency getAgency(String programCode);
	SchedulingType getSchedulingType(String programCode);
	ETSAddress getFirstAddress();
	
	String getExternalTestCenterId();
	void setExternalTestCenterId(String externalTestCenterId);
	
	void setTestCenterPrograms(Set<TestCenterProgram> testCenterPrograms);
	public TestCenterType getTestCenterType();
	public void setTestCenterType(TestCenterType testCenterType);
	public void setTestCenterDeliveryModes(Set<TestCenterDeliveryMode> testCenterDeliveryModes);

}