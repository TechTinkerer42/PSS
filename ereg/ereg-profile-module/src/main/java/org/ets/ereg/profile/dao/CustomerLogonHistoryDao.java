package org.ets.ereg.profile.dao;


import org.ets.ereg.domain.interfaces.model.profile.CustomerLogonHistory;

public interface CustomerLogonHistoryDao {
	public CustomerLogonHistory create();
	public CustomerLogonHistory save(CustomerLogonHistory custLogonHistory);
	public CustomerLogonHistory getCustomerLogonHistory(String username,String sessionId);
	public CustomerLogonHistory getCustomerLogonHistory(String sessionId);
	public CustomerLogonHistory readCustomerLogonHistoryById(Long id);

}
