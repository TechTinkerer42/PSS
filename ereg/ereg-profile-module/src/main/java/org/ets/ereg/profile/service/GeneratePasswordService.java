package org.ets.ereg.profile.service;

public interface GeneratePasswordService {

	/**
	 * This method is used to generate new password having two upper case
	 * letter, three lower case letter, two numbers and one special character
	 * 
	 * @return
	 */
	public abstract String generateNewPassword();
	
	public abstract String generateRandomString();

}
