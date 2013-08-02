package org.ets.ereg.domain.scheduling;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.ets.ereg.domain.common.GlobalTimeZoneImpl;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.GlobalTimeZone;
import org.ets.ereg.domain.interfaces.model.organization.OrganizationAddress;
import org.ets.ereg.domain.interfaces.model.profile.TestCenterAdmin;
import org.ets.ereg.domain.interfaces.model.scheduling.Agency;
import org.ets.ereg.domain.interfaces.model.scheduling.RestrictedAccessReason;
import org.ets.ereg.domain.interfaces.model.scheduling.SchedulingType;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterDeliveryMode;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterProgram;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterType;
import org.ets.ereg.domain.organization.OrganizationImpl;
import org.ets.ereg.domain.profile.admin.TestCenterAdminImpl;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TST_CNTR")
@PrimaryKeyJoinColumn(name="TST_CNTR_ID_NO",referencedColumnName="ORG_ID_NO")
public class TestCenterImpl extends OrganizationImpl implements TestCenter {

    private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=RestrictedAccessReasonImpl.class, optional=true)
	@JoinColumn(name = "RSTRCT_ACS_RSN_CDE", nullable = true)
	private RestrictedAccessReason restrictedAccessReason; 
	   

	
	@Column(name="OFC_HR_TXT", nullable=true, length=2000)
	private String officeHourText;
	
	@Column(name="SPCL_DRV_DIRCTN_TXT", nullable=true, length=2000)
	private String specialDrivingDirection;
	
	@Column(name="SPCL_ON_SITE_DIRCTN_TXT", nullable=true, length=2000)
	private String specialOnSiteDirection;
	
    @Column(name = "RSTRCT_ACS_FLG", nullable = false, columnDefinition = "char(1) default 'N'")
    @Type(type="yes_no")
    private Boolean isAccessRestricted;
    
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = GlobalTimeZoneImpl.class, optional=true)
	@JoinColumn(name="TM_ZN_STD_CDE", nullable=true)
	private GlobalTimeZone globalTimeZone;
    
    @OneToMany(mappedBy="testCenter", targetEntity=TestCenterProgramImpl.class, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<TestCenterProgram> testCenterPrograms;
    
    @OneToMany(mappedBy="testCenter", targetEntity=TestCenterDeliveryModeImpl.class, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<TestCenterDeliveryMode> testCenterDeliveryModes;

    @OneToMany(mappedBy="testCenter", targetEntity=TestCenterAdminImpl.class)
    private List<TestCenterAdmin> etsAdminUsers;
    
    @Column(name = "EXTRNL_TST_CNTR_ID")
    private  String externalTestCenterId; 
    
    
    @ManyToOne(fetch=FetchType.LAZY,targetEntity=TestCenterTypeImpl.class)
    @JoinColumn(name="TST_CNTR_TYP_CDE")
    private TestCenterType testCenterType;


	@Override
    public RestrictedAccessReason getRestrictedAccessReason() {
        return restrictedAccessReason;
    }

    @Override
    public void setRestrictedAccessReason(
            RestrictedAccessReason restrictedAccessReason) {
        this.restrictedAccessReason = restrictedAccessReason;
    }

    @Override
    public String getOfficeHourText() {
        return officeHourText;
    }

    @Override
    public void setOfficeHourText(String officeHourText) {
        this.officeHourText = officeHourText;
    }

    @Override
    public String getSpecialDrivingDirection() {
        return specialDrivingDirection;
    }

    @Override
    public void setSpecialDrivingDirection(String drivingDirection) {
        this.specialDrivingDirection = drivingDirection;

    }

    @Override
    public String getSpecialOnSiteDirection() {
        return specialOnSiteDirection;
    }

    @Override
    public void setSpecialOnSiteDirection(String onSiteDirection) {
        this.specialOnSiteDirection = onSiteDirection;
    }

    @Override
    public Boolean getAccessRestricted() {
        return isAccessRestricted;
    }

    @Override
    public void setAccessRestricted(Boolean isAccessRestricted) {
        this.isAccessRestricted = isAccessRestricted;
    }
    
	@Override
	public Set<TestCenterProgram> getTestCenterPrograms() {
		return testCenterPrograms;
	}
	
	@Override
	public void setTestCenterPrograms(Set<TestCenterProgram> testCenterPrograms){
		this.testCenterPrograms = testCenterPrograms;
	}

	@Override
	public Set<TestCenterDeliveryMode> getTestCenterDeliveryModes() {
		return testCenterDeliveryModes;
	}
	
	@Override
	public void setTestCenterDeliveryModes(Set<TestCenterDeliveryMode> testCenterDeliveryModes){
		this.testCenterDeliveryModes = testCenterDeliveryModes;
	}

    @Override
    public List<TestCenterAdmin> getEtsAdminUsers() {
         return etsAdminUsers;
    }

    @Override
    public void setEtsAdminUsers(List<TestCenterAdmin> etsAdminUsers) {
        this.etsAdminUsers = etsAdminUsers;
    }

	@Override
	public GlobalTimeZone getGlobalTimeZone() {
		return globalTimeZone;
	}

	@Override
	public void setGlobalTimeZone(GlobalTimeZone globalTimeZone) {
		this.globalTimeZone = globalTimeZone;
	}

	@Override
	public Agency getAgency(String programCode) {
		Agency agency = null;
		for (TestCenterProgram testCenterProgram : testCenterPrograms) {
			if (testCenterProgram.getProgram().getCode().equalsIgnoreCase(programCode)) {
				agency = testCenterProgram.getAgency();
				break;
			}
		}
		return agency;
	}
	
	@Override
	public SchedulingType getSchedulingType(String programCode) {
		SchedulingType schedulingType = null;
		for (TestCenterProgram testCenterProgram : testCenterPrograms) {
			if (testCenterProgram.getProgram().getCode().equalsIgnoreCase(programCode)) {
				schedulingType = testCenterProgram.getAgencyProgram().getSchedulingType();
				break;
			}
		}
		return schedulingType;
	}
	
	@Override
	public ETSAddress getFirstAddress() {
		Iterator<OrganizationAddress> it = getOrganizationAddresses().iterator();
		if (it.hasNext()) {
			return it.next().getAddress();
		} else {
			return null;
		}
	}
	

    @Override
    public String getExternalTestCenterId() {
		return externalTestCenterId;
	}

    @Override
	public void setExternalTestCenterId(String externalTestCenterId) {
		this.externalTestCenterId = externalTestCenterId;
	}

    @Override
	public TestCenterType getTestCenterType() {
		return testCenterType;
	}

    @Override
	public void setTestCenterType(TestCenterType testCenterType) {
		this.testCenterType = testCenterType;
	}
    
}