package org.ets.ereg.external.service.eias.client.utils;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.external.service.eias.client.types.ADGroups;
import org.ets.ereg.external.service.eias.client.types.ArrayOfADGroups;
import org.ets.ereg.external.service.eias.client.types.ArrayOfAttribute;
import org.ets.ereg.external.service.eias.client.types.ArrayOfSecurity;
import org.ets.ereg.external.service.eias.client.types.ArrayOfXsdString;
import org.ets.ereg.external.service.eias.client.types.Attribute;
import org.ets.ereg.external.service.eias.client.types.ChallengeQuestions;
import org.ets.ereg.external.service.eias.client.types.Security;
import org.ets.ereg.external.service.eias.client.types.User;
import org.ets.ereg.external.service.eias.client.types.UserADGroups;
import org.ets.ereg.external.service.eias.client.types.UserAttributes;
import org.ets.ereg.external.service.eias.client.types.UserVO;

public class ObjectConverter {
	
	public User createXMLCandidateObject(ETSCustomer customer, String ldapDir, String ldapUserType)
	{
		// setting up the password as in required format of attributes
		User userObject = populateCommonData( customer, ldapDir, ldapUserType);
		UserAttributes passwordAttribute = new UserAttributes();
		Attribute passAttr = new Attribute();
		passAttr.setName("password");
		passAttr.setValue(customer.getPassword());
		ArrayOfAttribute arAttbt= new ArrayOfAttribute();
		arAttbt.getItem().add(passAttr);
		passwordAttribute.setAttribute(arAttbt);

		// setting up the challenge question and answer as in required format of security
		ChallengeQuestions cq= new ChallengeQuestions();
		ArrayOfSecurity arrSec = new ArrayOfSecurity();
		Security cqSecurity = new Security();
		cqSecurity.setChallengeQ(customer.getChallengeQuestion().getId().toString());
		cqSecurity.setChallengeA(customer.getChallengeAnswer());
		arrSec.getItem().add(cqSecurity);
		cq.setSecurity(arrSec);

		//setting up the other attributes
		userObject.setUserId(customer.getUsername());

		userObject.setUserAttributes(passwordAttribute);
		userObject.setChallengeQuestions(cq);

		return userObject;
	}
	
	public User createXMLCandidateObjectCSR(ETSCustomer customer, String ldapDir, String ldapUserType)
	{
		User userObject = populateCommonData( customer, ldapDir, ldapUserType);
		// setting up the password as in required format of attributes
		UserAttributes passwordAttribute = new UserAttributes();
		Attribute passAttr = new Attribute();
		ArrayOfAttribute arAttbt= new ArrayOfAttribute();
		arAttbt.getItem().add(passAttr);
		passwordAttribute.setAttribute(arAttbt);
		userObject.setUserAttributes(passwordAttribute);

		return userObject;
	}
	
	private User populateCommonData(ETSCustomer customer, String ldapDir, String ldapUserType)
	{
		User userObject = new User();

		userObject.setFirstName(customer.getFirstName());
		userObject.setLastName(customer.getLastName());
		userObject.setEmailAddress(customer.getEmailAddress());
		userObject.setUserType(ldapUserType);
		//setting up the UserAD Group
		UserADGroups userADGroup = new UserADGroups();
		ArrayOfADGroups arrADGroups = new ArrayOfADGroups();
		ADGroups adGroup = new ADGroups();
		ArrayOfXsdString arrXSDStr = new ArrayOfXsdString();
		arrXSDStr.getItem().add(ldapDir);
		
		adGroup.setGroups(arrXSDStr);
		arrADGroups.getItem().add(adGroup);
		userADGroup.setAdGroups(arrADGroups);
		userObject.setUserADGroups(userADGroup);
		return userObject;
	}

	public User createXMLTCAObject(ETSAdminUser tca, String ldapDir, String ldapUserType)
	{
		User userObject = new User();

		// setting up the password as in required format of attributes
		UserAttributes passwordAttribute = new UserAttributes();
		Attribute passAttr = new Attribute();
		passAttr.setName("password");
		passAttr.setValue(tca.getPassword());
		ArrayOfAttribute arAttbt= new ArrayOfAttribute();
		arAttbt.getItem().add(passAttr);
		passwordAttribute.setAttribute(arAttbt);

		// setting up the challenge question and answer as in required format of security
		ChallengeQuestions cq= new ChallengeQuestions();
		ArrayOfSecurity arrSec = new ArrayOfSecurity();
		Security cqSecurity = new Security();
		cqSecurity.setChallengeQ(tca.getChallengeQuestion().getId().toString());
		cqSecurity.setChallengeA(tca.getChallengeAnswer());
		arrSec.getItem().add(cqSecurity);
		cq.setSecurity(arrSec);

		//setting up the other attributes
		userObject.setUserId(tca.getLogin());
		userObject.setFirstName(tca.getFirstName());
		userObject.setLastName(tca.getLastName());
		userObject.setEmailAddress(tca.getEmail());
		userObject.setUserAttributes(passwordAttribute);
		userObject.setChallengeQuestions(cq);
		userObject.setUserType(ldapUserType);
		//setting up the UserAD Group
		UserADGroups userADGroup = new UserADGroups();
		ArrayOfADGroups arrADGroups = new ArrayOfADGroups();
		ADGroups adGroup = new ADGroups();
		ArrayOfXsdString arrXSDStr = new ArrayOfXsdString();
		arrXSDStr.getItem().add(ldapDir);
		
		adGroup.setGroups(arrXSDStr);
		arrADGroups.getItem().add(adGroup);
		userADGroup.setAdGroups(arrADGroups);
		userObject.setUserADGroups(userADGroup);

		return userObject;
	}
	
	public UserVO getUserVO(User userObject)
	{
		UserVO uservo = new UserVO();
		
		uservo.seteMailID(userObject.getEmailAddress());
		uservo.setFirstName(userObject.getFirstName());
		uservo.setLastName(userObject.getLastName());
		uservo.setUserID(userObject.getUserId());
		uservo.setObjectGuid(userObject.getObjectGUID());
		return uservo;
	}
}
