package org.ets.ereg.domain.scheduling;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.AgencyProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.SchedulingType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.id.AgencyProgramId;

@Entity
@Table(name = "AGNCY_PGM")
public class AgencyProgramImpl implements AgencyProgram {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "agencyId", column = @Column(name = "AGNCY_ID_NO", nullable = false)),
		@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false, length = 5))
	})
	private AgencyProgramId agencyProgramId = new AgencyProgramId();
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AgencyImpl.class)
	@JoinColumn(name = "AGNCY_ID_NO", nullable = false, insertable = false, updatable = false)
	private Agency agency;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ProgramTypeImpl.class)
	@JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
	private ProgramType programType;
	
	@OneToMany(targetEntity = TestCenterProgramImpl.class)
	@JoinColumns({
			@JoinColumn(name = "AGNCY_ID_NO", referencedColumnName = "AGNCY_ID_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PGM_CDE", referencedColumnName = "PGM_CDE", insertable = false, updatable = false) })
	private Set<TestCenterProgram> testCenterPrograms;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = SchedulingTypeImpl.class)
	@JoinColumn(name = "SCHDLG_TYP_CDE", nullable = false, insertable = false, updatable = false)
	private SchedulingType schedulingType;

	@Override
	public AgencyProgramId getAgencyProgramId() {
		return agencyProgramId;
	}

	@Override
	public void setAgencyProgramId(AgencyProgramId agencyProgramId) {
		this.agencyProgramId = agencyProgramId;
	}

	@Override
	public Agency getAgency() {
		return agency;
	}

	@Override
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	@Override
	public ProgramType getProgramType() {
		return programType;
	}

	@Override
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	@Override
	public Set<TestCenterProgram> getTestCenterPrograms() {
		return testCenterPrograms;
	}

	@Override
	public void setTestCenterPrograms(Set<TestCenterProgram> testCenterPrograms) {
		this.testCenterPrograms = testCenterPrograms;
	}

	@Override
	public SchedulingType getSchedulingType() {
		return schedulingType;
	}

	@Override
	public void setSchedulingType(SchedulingType schedulingType) {
		this.schedulingType = schedulingType;
	}
	
}
