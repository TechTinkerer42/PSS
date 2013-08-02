package org.ets.ereg.domain.interfaces.model.profile;

import java.util.Date;

import org.broadleafcommerce.profile.core.domain.Customer;

public interface CustomerLogonHistory {
	public Long getId();	

	public void setId(Long id);

	public Customer getCustomer(); 
	
	public void setCustomer(Customer customer);

	public Date getLogonTimestamp();

	public void setLogonTimestamp(Date logonTimestamp);

	public Date getLogoutTimestamp() ;

	public void setLogoutTimestamp(Date logoutTimestamp) ;

	public String getJavaSessionId();

	public void setJavaSessionId(String javaSessionId);

	public String getApplicationServerId();

	public void setApplicationServerId(String applicationServerId);

	public String getBrowserName();

	public void setBrowserName(String browserName);

	public String getOperatingSystemname();

	public void setOperatingSystemname(String operatingSystemname);
	
	public String getBrowserUserAgent();

	public void setBrowserUserAgent(String browserUserAgent);

	LogoutReasonType getLogoutReason();

	void setLogoutReason(LogoutReasonType logoutReason);

	

}
