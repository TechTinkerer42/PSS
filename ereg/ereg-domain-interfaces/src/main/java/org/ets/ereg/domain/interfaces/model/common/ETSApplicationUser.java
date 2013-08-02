package org.ets.ereg.domain.interfaces.model.common;

import java.io.Serializable;

public interface ETSApplicationUser extends Serializable{

	public String getUserID();
	public void setUserID(String userID);
	
	public String getUserName();
	public void setUserName(String userName);
	
	public String getPassword();
	public void setPassword(String password);
	
}
