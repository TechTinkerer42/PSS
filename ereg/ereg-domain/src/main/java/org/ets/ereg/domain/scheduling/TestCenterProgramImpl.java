package org.ets.ereg.domain.scheduling;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.AgencyProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.id.TestCenterProgramId;

@Entity
@Table(name = "TST_CNTR_PGM")
public class TestCenterProgramImpl implements TestCenterProgram {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "testCenterId", column = @Column(name = "TST_CNTR_ID_NO", nullable = false)),
			@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false, length = 5)) })
	private TestCenterProgramId testCenterProgramId = new TestCenterProgramId();
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AgencyProgramImpl.class)
	@JoinColumns({
			@JoinColumn(name = "AGNCY_ID_NO", referencedColumnName = "AGNCY_ID_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PGM_CDE", referencedColumnName = "PGM_CDE", insertable = false, updatable = false) })
	private AgencyProgram agencyProgram;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=TestCenterImpl.class)
	@JoinColumn(name = "TST_CNTR_ID_NO", nullable = false, insertable = false, updatable = false)
	private TestCenter testCenter;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=ProgramTypeImpl.class)
	@JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
	private ProgramType program; 
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AgencyImpl.class, optional = true)
	@JoinColumn(name = "AGNCY_ID_NO", nullable = true, insertable = false, updatable = false)
	private Agency agency;
	
	@Override
	public TestCenterProgramId getId() {
		return testCenterProgramId;
	}

	@Override
	public void setId(TestCenterProgramId id) {
		this.testCenterProgramId = id;
	}

	@Override
	public AgencyProgram getAgencyProgram() {
		return agencyProgram;
	}

	@Override
	public void setAgencyProgram(AgencyProgram agencyProgram) {
		this.agencyProgram = agencyProgram;
	}

	@Override
	public ProgramType getProgram() {
		return program;
	}

	@Override
	public void setProgram(ProgramType program) {
		this.program = program;
	}

	@Override
	public TestCenter getTestCenter() {
		return testCenter;
	}

	@Override
	public void setTestCenter(TestCenter testCenter) {
		this.testCenter = testCenter;
	}
	
    @Override
    public Agency getAgency() {
        return agency;
    }

    @Override
    public void setAgency(Agency agency) {
        this.agency = agency;
    }

}
