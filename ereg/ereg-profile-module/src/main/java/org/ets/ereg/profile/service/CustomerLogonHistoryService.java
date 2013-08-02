package org.ets.ereg.profile.service;


import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;

public interface CustomerLogonHistoryService {
	public CustomerLogonHistory create();
	public CustomerLogonHistory save(CustomerLogonHistory custLogonHistory);
	public CustomerLogonHistory getCustomerLogonHistory(String username,String sessionId);
	public CustomerLogonHistory getCustomerLogonHistory(String sessionId);
	public CustomerLogonHistory createCustomerLogonHistory(
			String username,String sessionId);
	//public CustomerLogonHistory createNewCustomerLogonHistory();
	//public CustomerLogonHistory readCustomerLogonHistoryById(Long id);
}
