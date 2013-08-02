package org.ets.ereg.profile.domain.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityServiceImpl;
import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.service.ChallengeQuestionService;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.vo.admin.AdminUserVO;
import org.ets.ereg.domain.interfaces.model.common.AdminUserPhone;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.domain.interfaces.model.profile.admin.id.AdminUserPhoneId;
import org.ets.ereg.domain.profile.ETSAdminUserImpl;
import org.ets.ereg.external.service.eias.client.service.EIASWebServiceClient;
import org.ets.ereg.profile.admin.vo.TCAProfileVO;
import org.ets.ereg.profile.model.dao.ETSAdminUserDao;
import org.ets.ereg.profile.model.service.common.ETSAdminUserPhoneService;
import org.ets.ereg.profile.model.service.common.ETSAdminUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


@Service("etsAdminUserService")
public class ETSAdminUserServiceImpl extends AdminSecurityServiceImpl implements ETSAdminUserService , InitializingBean {

    private static final Long TCA_PERMISSION = 70l;
    private static final String ADMIN_PHONE_NAME = "Admin_Phone";
    @Resource(name="etsAdminUserDao")
    private ETSAdminUserDao etsAdminUserDao;

    @Resource(name = "applicationConfigurationService")
    private ApplicationConfigurationService applicationConfigurationService;

    @Resource(name = "blCountryService")
    private CountryService countryService;

    @Resource(name = "blPhoneService")
    private PhoneService phoneService;

    @Resource(name = "blAdminSecurityService")
    private AdminSecurityService adminSecurityService;

    @Resource(name = "blChallengeQuestionService")
    private ChallengeQuestionService challengeQuestionService;

    @Resource(name = "etsAdminUserPhoneService")
    private ETSAdminUserPhoneService etsAdminUserPhoneService;

    @Resource(name="sendAdminUserRegistrationMail")
    private EmailInfo sendAdminUserRegistrationMail;

    @Resource(name="blEmailService")
    private EmailService emailService;

	@Resource(name="EIASWebServiceClient")
	protected EIASWebServiceClient eiasWebServiceClient;
	
	@Resource(name = "eregUtils")
	private EregUtils eregUtils;
	
    public EmailInfo getSendAdminUserRegistrationMail() {
        return sendAdminUserRegistrationMail;
    }

    public void setSendAdminUserRegistrationMail(
            EmailInfo sendAdminUserRegistrationMail) {
        this.sendAdminUserRegistrationMail = sendAdminUserRegistrationMail;
    }

    @Override
    public TCAProfileVO createProfile() {
        TCAProfileVO tcaProfile = new TCAProfileVO();

        ETSAdminUser adminUser = new ETSAdminUserImpl();

        createPhone(adminUser);
        tcaProfile.setAdminUser(adminUser);
        return tcaProfile;
    }

    @Override
    public void createPhone(ETSAdminUser adminUser) {
        ETSPhone phone = (ETSPhone)phoneService.create();
        adminUser.setEtsPhone(phone);
    }

    @Override
    public DupCheckResponseObject checkDuplicateProfile(TCAProfileVO tcaProfile) {
        List<AdminUser> adminUsers = readUserByEmail(tcaProfile.getAdminUser().getEmail());
        DupCheckResponseObject duplicateObject = new DupCheckResponseObject(new ArrayList<String>());
        duplicateObject.setDuplicate(!adminUsers.isEmpty());
        return duplicateObject;
    }

    @Override
        public List<AdminUserVO> getDuplicateAdminProfiles(TCAProfileVO tcaProfile,
                boolean currentloggedCust) {
        List<AdminUser> adminUsers = readUserByEmail(tcaProfile.getAdminUser().getEmail());
        List<AdminUserVO> adminUserList = new ArrayList<AdminUserVO>();
        ETSAdminUser estAdminUser = null;
        AdminUserVO adminUserVO = null;
        for (AdminUser adminUser : adminUsers) {
            estAdminUser = (ETSAdminUser)adminUser;
            adminUserVO = new AdminUserVO();
            populateAdminUserVO(estAdminUser, adminUserVO);
            adminUserList.add(adminUserVO);
        }
        return adminUserList;
    }

    private void populateAdminUserVO(ETSAdminUser estAdminUser,
            AdminUserVO adminUserVO) {

        adminUserVO.setAdminId(estAdminUser.getId());
        adminUserVO.setEmail(estAdminUser.getEmail());
        adminUserVO.setFirstName(estAdminUser.getFirstName());
        adminUserVO.setLastName(estAdminUser.getLastName());
        adminUserVO.setMiddleName(estAdminUser.getMiddleName());
    }

    @Override
    public boolean isUsernameAvailable(String userName) {
		boolean isAvailable = true;
		if(eregUtils.isOAMAuthentication())
		{
			isAvailable = eiasWebServiceClient.searchUser(userName);
        }
        else{
        	isAvailable = (null == etsAdminUserDao.readAdminUserByUserName(userName));
        }
		return isAvailable;
    }

    @Override
    public String saveTCAProfile(TCAProfileVO tcaProfile, boolean newUser) {
        ETSAdminUser adminUser = tcaProfile.getAdminUser();
        Set<AdminPermission> permissionSet = new HashSet<AdminPermission>();
        AdminPermission adminPermission = adminSecurityService.readAdminPermissionById(TCA_PERMISSION);
        permissionSet.add(adminPermission);
        adminUser.setAllPermissions(permissionSet);
        adminUser.setInternalUserFlag(true);
        ETSAdminUser updatedAdminUser = (ETSAdminUser) adminSecurityService.saveAdminUser(adminUser);
        updatedAdminUser.setEtsPhone(adminUser.getEtsPhone());
        tcaProfile.setAdminUser(updatedAdminUser);
        savePhone(tcaProfile);
    	//Integrate with EIAS        
        String guidID ="";
        if(eregUtils.isOAMAuthentication())
        {
            if(newUser)
            {
            	guidID = eiasWebServiceClient.createUser(adminUser);
            }
            else
            {
            	eiasWebServiceClient.modifyUser(adminUser);
            }

        }
        if(newUser){
            sendConfirmationEmail(tcaProfile);
        }        
            //authenticate
        return guidID;
    }
    
	@Override
	public void updateGUID(String guid, Long adminID)
	{
		TCAProfileVO profile = readByAdminUserId(adminID);
		profile.getAdminUser().setLdapGUIDID(guid);
			
		adminSecurityService.saveAdminUser(profile.getAdminUser());
	}    

    private void sendConfirmationEmail(TCAProfileVO tcaProfile) {

        ETSAdminUser adminUser = tcaProfile.getAdminUser();
        HashMap<String, Object> vars = new HashMap<String, Object>();
        vars.put("admin", adminUser);
        emailService.sendTemplateEmail(adminUser.getEmail(), getSendAdminUserRegistrationMail(), vars);
    }

    @Override
    public TCAProfileVO readByAdminUserId(Long profileId) {
        ETSAdminUser adminUser = (ETSAdminUser) etsAdminUserDao.readAdminUserById(profileId);
        TCAProfileVO tcaProfile = new TCAProfileVO();
        tcaProfile.setAdminUser(adminUser);
        return tcaProfile;
    }

    @Override
    public Country findCountryByAbbreviation(String countryCode) {
        return countryService.findCountryByAbbreviation(countryCode);
    }

    @Override
    public List<ChallengeQuestion> readChallengeQuestions() {
        return challengeQuestionService.readChallengeQuestions();
    }

    @Override
    public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
        return challengeQuestionService.readChallengeQuestionById(challengeQuestionId);
    }

    @Override
    public TCAProfileVO authenthicate(String login, String password) {
        ETSAdminUser adminUser = (ETSAdminUser) adminSecurityService.readAdminUserByUserName(login);
        TCAProfileVO tcaProfile = null;
        if(null != adminUser){
            tcaProfile = new TCAProfileVO();
            tcaProfile.setAdminUser(adminUser);
            if(null == password || !password.equalsIgnoreCase(adminUser.getPassword()))
            {
                tcaProfile = null;
            }
        }
        return tcaProfile;
    }

    @Override
    public List<Country> findCountries() {
        return countryService.findCountries();
    }

    private void savePhone(TCAProfileVO tcaProfile){
            ETSPhone etsPhone = tcaProfile.getAdminUser().getEtsPhone();
            if(etsPhone!=null){
                AdminUserPhone adminPhone = null;
                AdminUserPhoneId adminUserPhoneId = null;
                if(tcaProfile.getAdminUser().getAdminUserPhone()!=null && tcaProfile.getAdminUser().getAdminUserPhone().getAdminUserPhoneId()!=null){
                    adminPhone = tcaProfile.getAdminUser().getAdminUserPhone();
                     etsPhone = tcaProfile.getAdminUser().getEtsPhone();
                     etsPhone.setId(adminPhone.getEtsPhone().getId());
                    copyPhone(tcaProfile.getAdminUser().getEtsPhone(), etsPhone);
                    phoneService.savePhone(etsPhone);
                } else{
                    etsPhone = (ETSPhone)phoneService.savePhone(tcaProfile.getAdminUser().getEtsPhone());
                    adminUserPhoneId = new AdminUserPhoneId();
                    adminUserPhoneId.setPhoneId(etsPhone.getId());
                    adminUserPhoneId.setEtsAdminUserId(tcaProfile.getAdminUser().getId());
                    tcaProfile.getAdminUser().setEtsPhone(etsPhone);

                    ETSAdminUser adminUser = tcaProfile.getAdminUser();
                    adminPhone = etsAdminUserPhoneService.createAdminUserPhone();
                    adminPhone.setAdminUserPhoneId(adminUserPhoneId);
                    adminPhone.setEtsAdminUser(adminUser);
                    adminPhone.setEtsPhone(etsPhone);
                    adminPhone.setName(ADMIN_PHONE_NAME);
                }

                etsAdminUserPhoneService.saveAdminUserPhone(adminPhone);
            }
            tcaProfile.getAdminUser().setEtsPhone(etsPhone);
    }

    @Override
    public List<AdminUser> readUserByEmail(String email){
        return etsAdminUserDao.readAdminUserByEmail(email);
    }

    private void copyPhone(ETSPhone srcPhone, ETSPhone destPhone){
        destPhone.setCountry(srcPhone.getCountry());
        destPhone.setPhoneNumber(srcPhone.getPhoneNumber());
        destPhone.setPhoneExtension(srcPhone.getPhoneExtension());
        destPhone.setPhoneType(srcPhone.getPhoneType());
    }

	@Override
	public ETSAdminUser findUserByGuId(String guid) {
		return etsAdminUserDao.findUserByGuId(guid);
	}

	@Override
	public ETSAdminUser findUserByUsernameAndInternalFlag(String username,
			Boolean internalUserFlag) {
		return etsAdminUserDao.findUserByUsernameAndInternalFlag(username, internalUserFlag);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
