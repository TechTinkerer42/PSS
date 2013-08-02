package org.ets.ereg.domain.interfaces.model.scheduling;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.organization.Organization;

public interface Agency extends Organization {
	
	List<TestCenter> getTestCenters();

	List<AgencyProgram> getAgencyPrograms();

	void setAgencyPrograms(List<AgencyProgram> agencyPrograms);

}
