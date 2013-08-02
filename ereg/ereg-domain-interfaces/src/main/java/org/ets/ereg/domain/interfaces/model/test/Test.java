package org.ets.ereg.domain.interfaces.model.test;

import java.io.Serializable;
import java.util.Set;

import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;

public interface Test extends Serializable {

    Long getTestId();
    void setTestId(Long testId);

	void setProgramType(ProgramType programCode);
	ProgramType getProgramType();

	void setTestName(String testName);
	String getTestName();

	void setTestDispSeq(String testDispSeq);
	String getTestDispSeq();

	void setDisplayDataFlag(String displayDataFlag);
	String getDisplayDataFlag();

	int getTestDuration();
	void setTestDuration(int testDuration);

	int getSchedulingDuration();
	void setSchedulingDuration(int schedulingDuration);
    Set<TestVariation> getTestVariation();
    void setTestVariation(Set<TestVariation> testVariation);

	

}
