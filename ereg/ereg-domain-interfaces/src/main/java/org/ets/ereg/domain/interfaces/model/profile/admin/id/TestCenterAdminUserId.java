package org.ets.ereg.domain.interfaces.model.profile.admin.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class TestCenterAdminUserId implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "TST_CNTR_ID_NO", nullable = false)
    private long testCenterId;
    @Column(name = "ADMIN_USER_ID", nullable = false)
    private long etsAdminUserId;


    public long getTestCenterId() {
        return testCenterId;
    }

    public void setTestCenterId(long testCenterId) {
        this.testCenterId = testCenterId;
    }

    public long getEtsAdminUserId() {
        return etsAdminUserId;
    }

    public void setEtsAdminuserId(long etsAdminUserId) {
        this.etsAdminUserId = etsAdminUserId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(etsAdminUserId).append(testCenterId)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TestCenterAdminUserId) {
            final TestCenterAdminUserId other = (TestCenterAdminUserId) obj;
            return new EqualsBuilder().append(etsAdminUserId, other.etsAdminUserId)
                    .append(testCenterId, other.testCenterId).isEquals();
        } else {
            return false;
        }
    }
}
