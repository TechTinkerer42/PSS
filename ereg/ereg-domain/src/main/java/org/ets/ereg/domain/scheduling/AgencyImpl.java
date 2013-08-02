package org.ets.ereg.domain.scheduling;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.AgencyProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.organization.OrganizationImpl;

@Entity
@Table(name="AGNCY")
@PrimaryKeyJoinColumn(name="AGNCY_ID_NO", referencedColumnName="ORG_ID_NO")

public class AgencyImpl extends OrganizationImpl implements Agency {
	
    private static final long serialVersionUID = 1L;
    
	@OneToMany(mappedBy = "agency", targetEntity = AgencyProgramImpl.class)
    private List<AgencyProgram> agencyPrograms;
	
	@Override
	public List<AgencyProgram> getAgencyPrograms() {
		return agencyPrograms;
	}

	@Override
	public void setAgencyPrograms(List<AgencyProgram> agencyPrograms) {
		this.agencyPrograms = agencyPrograms;
	}

	@Override
	public List<TestCenter> getTestCenters() {
		List<TestCenter> testCenters = new ArrayList<TestCenter>();
		for (AgencyProgram agencyProgram : agencyPrograms) {
			for (TestCenterProgram testCenterProgram : agencyProgram.getTestCenterPrograms()) {
				testCenters.add(testCenterProgram.getTestCenter());
			}
		}
		return testCenters;
	}
	
}
