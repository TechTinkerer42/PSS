package org.ets.ereg.domain.interfaces.model.scheduling;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.scheduling.id.TestCenterProgramId;

public interface TestCenterProgram extends Serializable {
	TestCenterProgramId getId();
	void setId(TestCenterProgramId id);
	Agency getAgency();
    void setAgency(Agency agency);
	ProgramType getProgram();
	void setProgram(ProgramType program);
	TestCenter getTestCenter();
	void setTestCenter(TestCenter testCenter);
	AgencyProgram getAgencyProgram();
	void setAgencyProgram(AgencyProgram agencyProgram);
}
