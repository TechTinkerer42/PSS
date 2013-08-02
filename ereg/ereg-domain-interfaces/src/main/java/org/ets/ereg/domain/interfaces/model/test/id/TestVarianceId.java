package org.ets.ereg.domain.interfaces.model.test.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class TestVarianceId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TST_ID", nullable = false)
    private Long testId;

	@Column(name = "DLVY_MDE_CDE", nullable = false, length = 5)
    private String deliveryModeCode;

	@Column(name = "LANG_CDE", nullable = false, length = 5)
    private String languageCode;


	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getDeliveryModeCode() {
        return deliveryModeCode;
    }

    public void setDeliveryModeCode(String deliveryModeCode) {
        this.deliveryModeCode = deliveryModeCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.testId).append(this.languageCode).append(this.deliveryModeCode)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TestVarianceId) {
            final TestVarianceId other = (TestVarianceId) obj;
            return new EqualsBuilder().append(this.testId, other.testId)
                    .append(this.languageCode, other.languageCode)
                    .append(this.deliveryModeCode, other.deliveryModeCode).isEquals();
        } else {
            return false;
        }
    }
}
