package org.ets.ereg.profile.model.service.common;

import java.util.List;

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.vo.admin.AdminUserVO;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;

public interface ETSAdminUserService extends AdminSecurityService{

    TCAProfileVO createProfile();

    DupCheckResponseObject checkDuplicateProfile(TCAProfileVO tcaProfile);

    List<AdminUserVO> getDuplicateAdminProfiles(TCAProfileVO tcaProfile,
            boolean currentloggedCust);

    boolean isUsernameAvailable(String userName);

    String saveTCAProfile(TCAProfileVO tcaProfile, boolean newUser);

    TCAProfileVO readByAdminUserId(Long profileId);

    Country findCountryByAbbreviation(String countryCode);

    List<ChallengeQuestion> readChallengeQuestions();

    ChallengeQuestion readChallengeQuestionById(long challengeQuestionId);

    TCAProfileVO authenthicate(String login, String oldPassword);

    List<Country> findCountries();

    void createPhone(ETSAdminUser adminUser);

    List<AdminUser> readUserByEmail(String email);

	void updateGUID(String guid, Long adminID);
	
	ETSAdminUser findUserByGuId(String guid);
	
	ETSAdminUser findUserByUsernameAndInternalFlag(String username,
			Boolean internalUserFlag);

}
