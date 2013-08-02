package org.ets.ereg.session.facade.profile.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.profile.service.ProfileService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.booking.service.BookingService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("profileBusinessService")
public class ProfileBusinessServiceImpl implements ProfileBusinessService {

	@Resource(name = "profileService")
	private ProfileService profileService;

    @Resource(name = "bookingService")
    private BookingService bookingService;

	@Override
	public List<State> findStates(String countryAbbreviation) {
		return profileService.findStates(countryAbbreviation);
	}

	@Override
	public List<Country> findCountries() {
    	return profileService.findCountries();
	}

	@Override
	public List<ChallengeQuestion> readChallengeQuestions() {
    	return profileService.readChallengeQuestions();
	}

	@Override
	public Country findCountryByAbbreviation(String abbreviation) {
		return profileService.findCountryByAbbreviation(abbreviation);
	}

	@Override
	public State findStateByAbbreviation(String abbreviation) {
		return profileService.findStateByAbbreviation(abbreviation);
	}

	@Override
	public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
		return profileService.readChallengeQuestionById(challengeQuestionId);
	}

	@Override
	public ProfileVO createProfile() {
		return profileService.createProfile();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public String saveProfile(ProfileVO profile) {
		String guid = profileService.saveProfile(profile);
		if(profile.getCustomer().isNameUpdated()){
		    bookingService.saveBookingWithNewName(profile.getCustomer());
        }
		return guid;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void updateGUID(String guid, Long customerId) {
		profileService.updateGUID(guid, customerId);
	}

	@Override
	public void registerProfile(ProfileVO profile) {
		profileService.registerProfile(profile);
	}

	@Override
	public boolean isUsernameAvailable(String username) {
		return profileService.isUsernameAvailable(username);
	}

	@Override
	public ProfileVO readProfileByUsername(String username) {
		return profileService.readProfileByUsername(username);
	}

	@Override
	public ProfileVO readProfileById(Long id) {
		return profileService.readProfileById(id);
	}

	@Override
	public ProfileVO authenthicate(String username, String password) {
		return profileService.authenthicate(username, password);
	}
/*
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void saveAccount(ProfileVO profile) {
		profileService.saveAccount(profile);
	}
*/
	@Override
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile) {

		return profileService.checkDuplicateProfile(profile);

	}
	@Override
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return profileService.hasDuplicateProfiles(profile, currentloggedCust);
	}

	@Override
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return profileService.getDuplicateProfiles(profile, currentloggedCust);
	}

	@Override
	public void changePassword(ProfileVO profile) {
		profileService.changePassword(profile);
	}

	@Override
	public void changeSecQuestion(ProfileVO profile) {
		profileService.changeSecQuestion(profile);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void updateCustomerGuid(UserVO user, Long customerID) {
		profileService.updateCustomerGuid(user, customerID);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public UserVO saveProfileByCSR(ProfileVO profile) {
		UserVO usr = profileService.saveProfileByCSR(profile);
		if(profile.getCustomer().isNameUpdated()){
		    bookingService.saveBookingWithNewName(profile.getCustomer());
        }
		return usr;
	}
}