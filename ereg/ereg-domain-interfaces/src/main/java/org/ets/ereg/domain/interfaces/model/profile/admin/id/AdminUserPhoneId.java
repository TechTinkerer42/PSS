package org.ets.ereg.domain.interfaces.model.profile.admin.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class AdminUserPhoneId implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "PHONE_ID", nullable = false)
    private long phoneId;
    @Column(name = "ADMIN_USER_ID", nullable = false)
    private long etsAdminUserId;


    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public long getEtsAdminUserId() {
        return etsAdminUserId;
    }

    public void setEtsAdminUserId(long etsAdminUserId) {
        this.etsAdminUserId = etsAdminUserId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(etsAdminUserId).append(phoneId)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AdminUserPhoneId) {
            final AdminUserPhoneId other = (AdminUserPhoneId) obj;
            return new EqualsBuilder().append(etsAdminUserId, other.etsAdminUserId)
                    .append(phoneId, other.phoneId).isEquals();
        } else {
            return false;
        }
    }
}
