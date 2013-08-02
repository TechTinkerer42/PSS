package org.ets.ereg.domain.test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;

@Entity
@Table(name = "TST")
public class TestImpl implements Test, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "TestId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "TestId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "TestImpl", allocationSize = 50)
	@Column(name = "TST_ID")
	private Long testId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ProgramTypeImpl.class)
    @JoinColumn(name = "PGM_CDE", nullable = false, insertable = false, updatable = false)
    private ProgramType programType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="test", targetEntity=TestVariationImpl.class, cascade={CascadeType.ALL})
    private Set<TestVariation> testVariation = new HashSet<TestVariation>();

	@Column(name="TST_NAM")
    private String testName;

    @Column(name="TST_DSPLY_SEQ")
    private String testDispSeq;

    @Column(name="DSPLY_DTA_FLG", nullable = false)
    private String isDisplayable;

    @Column(name="TST_DURN", nullable = false)
    private int testDuration;

    @Column(name="SCHDLG_DURN", nullable = false)
    private int schedulingDuration;


	@Override
    public Long getTestId() {
        return testId;
	}

	@Override
    public void setTestId(Long testId) {
        this.testId = testId;
	}


	@Override
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}


	@Override
	public ProgramType getProgramType() {
		return programType;
	}


	@Override
	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Override
	public String getTestName() {
		return testName;
	}

	@Override
	public void setTestDispSeq(String testDispSeq) {
		this.testDispSeq = testDispSeq;

	}

	@Override
	public String getTestDispSeq() {
		return testDispSeq;
	}

	@Override
	public void setDisplayDataFlag(String isDisplayable) {
		this.isDisplayable = isDisplayable;
	}

	@Override
	public String getDisplayDataFlag() {
		return isDisplayable;
	}

	@Override
	public int getTestDuration() {
		return testDuration;
	}

	@Override
	public void setTestDuration(int testDuration) {
		this.testDuration = testDuration;
	}

	@Override
	public int getSchedulingDuration() {
		return schedulingDuration;
	}

	@Override
	public void setSchedulingDuration(int schedulingDuration) {
		this.schedulingDuration = schedulingDuration;
	}

	@Override
    public Set<TestVariation> getTestVariation() {
        return testVariation;
	}

	@Override
    public void setTestVariation(Set<TestVariation> testVariation) {
        this.testVariation = testVariation;
	}
}
