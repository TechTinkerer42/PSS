package org.ets.ereg.external.service.eias.client.types;

import java.util.Map;

public class UserVO {

	private String firstName;
	private String lastName;
	private String eMailID;
	private String objectGuid;
	private String userID;
	private Map<String, String> securityQuestion;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String geteMailID() {
		return eMailID;
	}
	public void seteMailID(String eMailID) {
		this.eMailID = eMailID;
	}
	public String getObjectGuid() {
		return objectGuid;
	}
	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Map<String, String> getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(Map<String, String> securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
		
}
