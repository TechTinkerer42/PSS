package org.ets.ereg.domain.profile.admin;

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

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.profile.admin.id.TestCenterAdminUserId;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.profile.ETSAdminUserImpl;
import org.ets.ereg.domain.scheduling.TestCenterImpl;

@Entity
@Table(name = "TST_CNTR_ADMIN")
public class TestCenterAdminImpl implements TestCenterAdmin, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "testCenterId", column = @Column(name = "TST_CNTR_ID_NO", nullable = false)),
            @AttributeOverride(name = "etsAdminUserId", column = @Column(name = "ADMIN_USER_ID", nullable = false)) })
    private TestCenterAdminUserId testCenterAdminUserId = new TestCenterAdminUserId();

    @ManyToOne(fetch = FetchType.LAZY,targetEntity=ETSAdminUserImpl.class)
    @JoinColumn(name = "ADMIN_USER_ID", updatable = false, insertable = false)
    private ETSAdminUser etsAdminUser;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity=TestCenterImpl.class)
    @JoinColumn(name = "TST_CNTR_ID_NO", updatable = false, insertable = false)
    private TestCenter testCenter;

    @Override
    public ETSAdminUser getEtsAdminUser() {
        return etsAdminUser;
    }

    @Override
    public void setEtsAdminUser(ETSAdminUser etsAdminUser) {
        this.etsAdminUser = etsAdminUser;
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
    public TestCenterAdminUserId getTestCenterAdminUserId() {
        return testCenterAdminUserId;
    }

    @Override
    public void setTestCenterAdminUserId(TestCenterAdminUserId testCenterAdminUserId) {
        this.testCenterAdminUserId = testCenterAdminUserId;
    }


}
