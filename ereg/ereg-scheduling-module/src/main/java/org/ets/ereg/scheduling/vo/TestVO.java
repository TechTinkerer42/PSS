package org.ets.ereg.scheduling.vo;

import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;

public class TestVO {

	public static final int TEST_AVAILABLE = 0;
	public static final int TEST_HELD = 1;
	public static final int TEST_SCHEDULED = 2;

	private Test test;
	private TestVariation testVariation;
	private Boolean scheduledFlag;
	private int status;

	public TestVO() {

	}

	public TestVO(Test test, Boolean scheduledFlag) {
		this.test = test;
		this.scheduledFlag = scheduledFlag;
	}

	public TestVO(TestVariation testVariation, Boolean scheduledFlag) {
        this.testVariation = testVariation;
        this.scheduledFlag = scheduledFlag;
    }

	public TestVO(Test test, Boolean scheduledFlag, int status) {
		this(test, scheduledFlag);
		this.status = status;
	}

	   public TestVO(TestVariation testVariation, Boolean scheduledFlag, int status) {
	        this(testVariation, scheduledFlag);
	        this.status = status;
	    }

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}



	public Boolean getScheduledFlag() {
		return scheduledFlag;
	}

	public TestVariation getTestVariation() {
        return testVariation;
    }

    public void setTestVariation(TestVariation testVariation) {
        this.testVariation = testVariation;
    }

    public void setScheduledFlag(Boolean scheduledFlag) {
		this.scheduledFlag = scheduledFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
