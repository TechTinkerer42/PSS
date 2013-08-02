package org.ets.ereg.domain.test;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.interfaces.model.test.TestVariation;
import org.ets.ereg.domain.interfaces.model.test.id.TestVarianceId;

@Entity
@Table(name = "TST_VARTN")
public class TestVariationImpl implements TestVariation, Serializable {

	private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
    	@AttributeOverride(name = "testId", column = @Column(name = "TST_ID", nullable = false, length = 5)),
    	@AttributeOverride(name = "languageCode", column = @Column(name = "LANG_CDE", nullable = false, length = 5)),
    	@AttributeOverride(name = "deliveryModeCode", column = @Column(name = "DLVY_MDE_CDE", nullable = false, length = 5))})
    private TestVarianceId id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TestImpl.class)
    @JoinColumn(name = "TST_ID", nullable = false, insertable = false, updatable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = LanguageTypeImpl.class)
    @JoinColumn(name = "LANG_CDE", nullable = false, insertable = false, updatable = false)
    private LanguageType languageType;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = DeliveryModeTypeImpl.class)
    @JoinColumn(name = "DLVY_MDE_CDE", nullable = false, insertable = false, updatable = false)
    private DeliveryModeType deliveryModeType;

    @Column(name="TST_CDE", nullable = false, length = 15)
    private String testCode;

    @Column(name="SCHDLG_TST_CDE", nullable = false, length = 15)
    private String scheduleTestCode;
    
    @Column(name="FUNC_CDE")
    private String functionCode;

    @Override
    public TestVarianceId getId() {
        return id;
    }

    @Override
    public void setId(TestVarianceId id) {
        this.id = id;
    }

    @Override
    public Test getTest() {
        return test;
    }

    @Override
    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public LanguageType getLanguageType() {
        return languageType;
    }

    @Override
    public void setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
    }

    @Override
    public DeliveryModeType getDeliveryModeType() {
        return deliveryModeType;
    }

    @Override
    public void setDeliveryModeType(DeliveryModeType deliveryModeType) {
        this.deliveryModeType = deliveryModeType;
    }

    @Override
    public String getTestCode() {
        return testCode;
    }

    @Override
    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    @Override
    public String getScheduleTestCode() {
        return scheduleTestCode;
    }

    @Override
    public void setScheduleTestCode(String scheduleTestCode) {
        this.scheduleTestCode = scheduleTestCode;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(test)
                .append(languageType).append(deliveryModeType)
                .append(scheduleTestCode).append(testCode)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TestVariationImpl) {
            final TestVariationImpl other = (TestVariationImpl) obj;
            return new EqualsBuilder().append(id, other.id)
                    .append(test, other.test)
                    .append(languageType, other.languageType)
                    .append(deliveryModeType, other.deliveryModeType)
                    .append(scheduleTestCode, other.scheduleTestCode)
                    .append(testCode, other.testCode)
                    .isEquals();
        } else {
            return false;
        }
    }

	@Override
	public String getFunctionCode() {
		return functionCode;
	}

	@Override
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}




}
