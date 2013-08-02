package org.ets.ereg.session.facade.profile.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.ets.ereg.common.business.vo.admin.AdminUserVO;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;
import org.ets.ereg.profile.model.service.common.ETSAdminUserService;
import org.ets.ereg.session.facade.profile.service.ETSAdminUserBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("etsAdminUserBusinessService")
public class ETSAdminUserBusinessServiceImpl implements ETSAdminUserBusinessService {

    @Resource(name="etsAdminUserService")
    private ETSAdminUserService etsAdminUserService;

    /*
     * (non-Javadoc)
     * @see org.broadleafcommerce.openadmin.server.security.service.AdminSecurityServiceImpl#readAdminUserByUserName(java.lang.String)
     */
    public ETSAdminUser readAdminUserByUserName(String userName) {
        return (ETSAdminUser) etsAdminUserService.readAdminUserByUserName(userName);
    }

    @Override
    public TCAProfileVO createProfile() {
        return etsAdminUserService.createProfile();
    }

    @Override
    public DupCheckResponseObject checkDuplicateProfile(TCAProfileVO tcaProfile) {
        return etsAdminUserService.checkDuplicateProfile(tcaProfile);
    }

    @Override
    public List<AdminUserVO> getDuplicateAdminProfiles(TCAProfileVO tcaProfile,
            boolean currentloggedCust) {
        return etsAdminUserService.getDuplicateAdminProfiles(tcaProfile, currentloggedCust);
    }

    @Override
    public boolean isUsernameAvailable(String userName) {
        return etsAdminUserService.isUsernameAvailable(userName);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public String saveTCAProfile(TCAProfileVO tcaProfile, boolean newUser) {
        return etsAdminUserService.saveTCAProfile(tcaProfile, newUser);
    }
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
    public void updateGUID(String guid, Long profileID) {
        etsAdminUserService.updateGUID(guid, profileID);

    }
    @Override
    public TCAProfileVO readByAdminUserId(Long profileId) {
        return etsAdminUserService.readByAdminUserId(profileId);
    }

    @Override
    public Country findCountryByAbbreviation(String countryCode) {
        return etsAdminUserService.findCountryByAbbreviation(countryCode);
    }

    @Override
    public GenericResponse changePassword(String name, String oldPassword,
            String password, String passwordConfirm) {
        return etsAdminUserService.changePassword(name, oldPassword, password, passwordConfirm);

    }

    @Override
    public List<ChallengeQuestion> readChallengeQuestions() {
        return etsAdminUserService.readChallengeQuestions();
    }

    @Override
    public ChallengeQuestion readChallengeQuestionById(long parseLong) {
        return etsAdminUserService.readChallengeQuestionById(parseLong);
    }

    @Override
    public TCAProfileVO authenthicate(String login, String oldPassword) {
        return etsAdminUserService.authenthicate(login, oldPassword);
    }

    @Override
    public List<Country> findCountries() {
        return etsAdminUserService.findCountries();
    }

    @Override
    public void createPhone(ETSAdminUser adminUser) {
        etsAdminUserService.createPhone(adminUser);

    }

    @Override
    public List<AdminUser> readUserByEmail(String emailAddress) {
        return etsAdminUserService.readUserByEmail(emailAddress);
    }
}
