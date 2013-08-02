package org.ets.ereg.domain.interfaces.model.scheduling;

import java.io.Serializable;
import java.util.Set;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.scheduling.id.AgencyProgramId;

public interface AgencyProgram extends Serializable {

	AgencyProgramId getAgencyProgramId();

	void setAgencyProgramId(AgencyProgramId agencyProgramId);

	Agency getAgency();

	void setAgency(Agency agency);
	
	ProgramType getProgramType();

	void setProgramType(ProgramType programType);

	SchedulingType getSchedulingType();

	void setSchedulingType(SchedulingType schedulingType);

	Set<TestCenterProgram> getTestCenterPrograms();

	void setTestCenterPrograms(Set<TestCenterProgram> testCenterPrograms);

}
