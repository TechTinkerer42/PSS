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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.admin.id.AdminUserPhoneId;
import org.ets.ereg.domain.profile.ETSAdminUserImpl;

@Entity
@Table(name = "ADMIN_USR_PHONE")
public class AdminUserPhoneImpl implements AdminUserPhone, Serializable{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "phoneId", column = @Column(name = "PHONE_ID", nullable = false)),
            @AttributeOverride(name = "etsAdminUserId", column = @Column(name = "ADMIN_USER_ID", nullable = false)) })
    private AdminUserPhoneId adminUserPhoneId = new AdminUserPhoneId();

    @OneToOne(fetch = FetchType.EAGER, targetEntity=ETSAdminUserImpl.class)
    @JoinColumn(name = "ADMIN_USER_ID", nullable = false, insertable = false, updatable = false)
    private ETSAdminUser etsAdminUser;

    @OneToOne(fetch = FetchType.EAGER, targetEntity=ETSPhoneImpl.class)
    @JoinColumn(name = "PHONE_ID", nullable = false, insertable = false, updatable = false)
    private ETSPhone etsPhone;

    @Column(name="PHONE_NAME")
    private String name;

    @Override
    public AdminUserPhoneId getAdminUserPhoneId() {
        return adminUserPhoneId;
    }

    @Override
    public void setAdminUserPhoneId(AdminUserPhoneId adminUserPhoneId) {
        this.adminUserPhoneId = adminUserPhoneId;
    }

    @Override
    public ETSAdminUser getEtsAdminUser() {
        return etsAdminUser;
    }

    @Override
    public void setEtsAdminUser(ETSAdminUser etsAdminUser) {
        this.etsAdminUser = etsAdminUser;
    }

    @Override
    public ETSPhone getEtsPhone() {
        return etsPhone;
    }

    @Override
    public void setEtsPhone(ETSPhone etsPhone) {
        this.etsPhone = etsPhone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
