package org.ets.ereg.session.facade.profile.service;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.profile.vo.ProfileVO;

public interface ProfileBusinessService {
	List<State> findStates(String countryAbbreviation);
	List<Country> findCountries();
	List<ChallengeQuestion> readChallengeQuestions();
	Country findCountryByAbbreviation(String abbreviation);
	State findStateByAbbreviation(String abbreviation);
	ChallengeQuestion readChallengeQuestionById(long challengeQuestionId);
	ProfileVO createProfile();
	String saveProfile(ProfileVO profile);
	void registerProfile(ProfileVO profile);
	boolean isUsernameAvailable(String username);
	ProfileVO readProfileByUsername(String username);
	ProfileVO readProfileById(Long id);
	ProfileVO authenthicate(String username, String password);
	//void saveAccount(ProfileVO profile);
	DupCheckResponseObject checkDuplicateProfile(ProfileVO profile);
	Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust);
	List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust);
	void changePassword(ProfileVO profile);
	void changeSecQuestion(ProfileVO profile);
	void updateGUID(String guid, Long customerId);
	void updateCustomerGuid(UserVO user, Long customerID);
	UserVO saveProfileByCSR(ProfileVO profile);
}