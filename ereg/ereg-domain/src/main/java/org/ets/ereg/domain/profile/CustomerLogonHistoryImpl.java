package org.ets.ereg.domain.profile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;
import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;
import org.ets.ereg.domain.interfaces.model.profile.LogoutReasonType;

@Entity
@Table(name="CUST_LGN_HIST")
public class CustomerLogonHistoryImpl implements CustomerLogonHistory{
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getLogonTimestamp() {
		return logonTimestamp;
	}

	public void setLogonTimestamp(Date logonTimestamp) {
		this.logonTimestamp = logonTimestamp;
	}

	public Date getLogoutTimestamp() {
		return logoutTimestamp;
	}

	public void setLogoutTimestamp(Date logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}

	public String getJavaSessionId() {
		return javaSessionId;
	}

	public void setJavaSessionId(String javaSessionId) {
		this.javaSessionId = javaSessionId;
	}

	public String getApplicationServerId() {
		return applicationServerId;
	}

	public void setApplicationServerId(String applicationServerId) {
		this.applicationServerId = applicationServerId;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getOperatingSystemname() {
		return operatingSystemname;
	}

	public void setOperatingSystemname(String operatingSystemname) {
		this.operatingSystemname = operatingSystemname;
	}

	
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}

	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}

	@Override
	public LogoutReasonType getLogoutReason() {
		return logoutReason;
	}

	@Override
	public void setLogoutReason(LogoutReasonType logoutReason) {
		this.logoutReason = logoutReason;
	}

	@Id
	@Column(name="CUST_LGN_HIST_ID")	
	protected Long id;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=CustomerImpl.class)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity=LogoutReasonTypeImpl.class)
	@JoinColumn(name="LOGOUT_RSN_TYP_CDE")
	protected LogoutReasonType logoutReason;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGON_DTM")
	private Date logonTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGOUT_DTM")
	private Date logoutTimestamp;
	
	@Column(name="JAVA_SESN_ID")
	protected String javaSessionId;
	
	@Column(name="APLCTN_SRVR_ID")
	protected String applicationServerId;
	
	@Column(name="BRWSR_NAM")
	protected String browserName;
	
	@Column(name="OPSYS_NAM")
	protected String operatingSystemname;
	
	@Column(name="USR_AGNT_TXT")
	protected String browserUserAgent;	
	
	

}
