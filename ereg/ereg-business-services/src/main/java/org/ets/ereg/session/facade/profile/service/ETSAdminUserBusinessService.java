package org.ets.ereg.session.facade.profile.service;

import java.util.List;

import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.vo.admin.AdminUserVO;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;

public interface ETSAdminUserBusinessService{

    /**
     * Method to delegate to readAdminUserByUserName method in profile module
     */
    public ETSAdminUser readAdminUserByUserName(String userName);
    public TCAProfileVO createProfile();
    public DupCheckResponseObject checkDuplicateProfile(TCAProfileVO profile);
    public List<AdminUserVO> getDuplicateAdminProfiles(TCAProfileVO tcaProfile,
            boolean currentloggedCust);
    public boolean isUsernameAvailable(String username);
    public String saveTCAProfile(TCAProfileVO tcaProfile, boolean newUser);
    public TCAProfileVO readByAdminUserId(Long profileId);
    public Country findCountryByAbbreviation(String countryCode);
    public GenericResponse changePassword(String name, String oldPassword,
            String password, String passwordConfirm);
    public List<ChallengeQuestion> readChallengeQuestions();
    public ChallengeQuestion readChallengeQuestionById(long parseLong);
    public TCAProfileVO authenthicate(String login, String oldPassword);
    public List<Country> findCountries();
    public void createPhone(ETSAdminUser adminUser);
    public List<AdminUser> readUserByEmail(
            String emailAddress);
	void updateGUID(String guid, Long profileID);
}
