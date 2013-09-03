package org.ets.ereg.profile.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.ChallengeQuestionService;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.broadleafcommerce.profile.core.service.StateService;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;
import org.ets.ereg.common.business.service.ProgramService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.util.EregUtils;
import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.business.vo.CustomerVO;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.common.business.vo.biq.DmgrphQstnTriggerVO;
import org.ets.ereg.domain.interfaces.model.common.AddressType;
import org.ets.ereg.domain.interfaces.model.common.CustomerLinkage;
import org.ets.ereg.domain.interfaces.model.common.CustomerType;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.common.LinkageType;
import org.ets.ereg.domain.interfaces.model.common.MilitaryStatusType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.interfaces.model.profile.ETSCustomer;
import org.ets.ereg.external.service.eias.client.service.EIASWebServiceClient;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.model.service.common.ETSCustomerService;
import org.ets.ereg.profile.service.GeneratePasswordService;
import org.ets.ereg.profile.service.ProfileService;
import org.ets.ereg.profile.util.ProfileUtils;
import org.ets.ereg.profile.vo.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService, InitializingBean {
	private static Logger LOG = LoggerFactory.getLogger(ProfileServiceImpl.class);
	@Resource(name = "blCountryService")
	private CountryService countryService;

	@Resource(name = "blStateService")
	private StateService stateService;

	@Resource(name = "blChallengeQuestionService")
	private ChallengeQuestionService challengeQuestionService;

	@Resource(name = "blPhoneService")
	private PhoneService phoneService;

	@Resource(name = "blAddressService")
	private AddressService addressService;

	@Resource(name = "etsCustomerService")
	private ETSCustomerService customerService;

	@Resource(name = "blCustomerAddressService")
    private CustomerAddressService customerAddressService;

	@Resource(name = "blCustomerPhoneService")
    private CustomerPhoneService customerPhoneService;

	@Resource(name = "referenceEntityService")
	private ReferenceService referenceService;

	@Resource(name = "programService")
	private ProgramService programService;

	@Resource(name="generatePasswordService")
	private GeneratePasswordService generatePasswordService;

	@Resource(name="profileDemographicQuestionService")
	protected ProfileDemographicQuestionService profileDmgrphService;

	@Resource(name="EIASWebServiceClient")
	protected EIASWebServiceClient eiasWebServiceClient;

	@Resource(name="profileService")
	protected ProfileService prfleService;

	@Resource(name = "eregUtils")
	private EregUtils eregUtils;
	
	
	private ETSPhone clonePhone(ETSPhone phone){
		ETSPhone clone = (ETSPhone)phoneService.create();
		clone.setId(phone.getId());
		clone.setCountry(phone.getCountry());
		clone.setPhoneNumber(phone.getPhoneNumber());
		clone.setPhoneExtension(phone.getPhoneExtension());
		clone.setPhoneType(phone.getPhoneType());
		return clone;
	}

	private void copyPhone(ETSPhone srcPhone, ETSPhone destPhone){
		destPhone.setId(srcPhone.getId());
		destPhone.setCountry(srcPhone.getCountry());
		destPhone.setPhoneNumber(srcPhone.getPhoneNumber());
		destPhone.setPhoneExtension(srcPhone.getPhoneExtension());
		destPhone.setPhoneType(srcPhone.getPhoneType());
	}

	private ProfileVO loadProfile(ETSCustomer customer){
		ProfileVO profile = new ProfileVO();

		CustomerLinkage custLink=customerService.getCustomerLinkage(customer.getId(), LinkageType.USER_DISP_ID);
		if(custLink!=null)
			profile.setCustLinkageKey(custLink.getLinkageKey());

		//customer
		profile.setCustomer(customer);

		if(null != profile.getCustomer().getMilitaryStatus() && MilitaryStatusType.NOT_A_MEMBER.equals(profile.getCustomer().getMilitaryStatus().getCode())){
			profile.getCustomer().setMilitaryMemberShip(false);
		}else if(null!=profile.getCustomer().getMilitaryStatus()){
			profile.getCustomer().setMilitaryMemberShip(true);
		}
		else{
			profile.getCustomer().setMilitaryMemberShip(false);
		}

		//TODO integrate with EIAS

		//address
		CustomerAddress customerAddress = customerAddressService.findDefaultCustomerAddress(customer.getId());

		if(null != customerAddress){
			profile.setAddress((ETSAddress)addressService.readAddressById(customerAddress.getAddress().getId()));
		}

		//phones
		//primary
		for(CustomerPhone customerPhone: customerPhoneService.readAllCustomerPhonesByCustomerId(customer.getId())){
			if(customerPhone.getPhoneName().equalsIgnoreCase(ETSPhone.PrimaryPhoneName)){
				profile.setPrimaryPhone(clonePhone((ETSPhone)customerPhone.getPhone()));
			}
			else if(customerPhone.getPhoneName().equalsIgnoreCase(ETSPhone.AlternatePhoneName)){
				profile.setAlternatePhone(clonePhone((ETSPhone)customerPhone.getPhone()));
			}
		}
		//alternate
		if(null == profile.getAlternatePhone()){
			profile.setAlternatePhone((ETSPhone)phoneService.create());
		}

		return profile;
	}

	private void saveAddress(ProfileVO profile){
		ETSCustomer customer = profile.getCustomer();
		ETSAddress address = (ETSAddress)addressService.saveAddress(profile.getAddress());

		CustomerAddress customerAddress = customerAddressService.findDefaultCustomerAddress(customer.getId());
		if(null == customerAddress){
			customerAddress = customerAddressService.create();
			customerAddress.setAddress(address);
			customerAddress.setAddressName(ETSAddress.HomeAddressName);
			customerAddress.setCustomer(customer);
		}
		customerAddressService.saveCustomerAddress(customerAddress);
	}

	private void savePhones(ProfileVO profile){
		ETSCustomer customer = profile.getCustomer();

		CustomerPhone primaryCustomerPhone = null;
		CustomerPhone alternateCustomerPhone = null;

		for(CustomerPhone customerPhone: customerPhoneService.readAllCustomerPhonesByCustomerId(customer.getId())){
			if(customerPhone.getPhoneName().equalsIgnoreCase(ETSPhone.PrimaryPhoneName)){
				primaryCustomerPhone = customerPhone;
			}
			else if(customerPhone.getPhoneName().equalsIgnoreCase(ETSPhone.AlternatePhoneName)){
				alternateCustomerPhone = customerPhone;
			}
		}


        if(null == primaryCustomerPhone){
        	ETSPhone primaryPhone = (ETSPhone)phoneService.savePhone(profile.getPrimaryPhone());
        	primaryCustomerPhone = customerPhoneService.create();
        	primaryCustomerPhone.setPhone(primaryPhone);
        	primaryCustomerPhone.setPhoneName(ETSPhone.PrimaryPhoneName);
        	primaryCustomerPhone.setCustomer(customer);
        }
        else{
        	copyPhone(profile.getPrimaryPhone(), (ETSPhone)primaryCustomerPhone.getPhone());
        	phoneService.savePhone(primaryCustomerPhone.getPhone());
        }

    	customerPhoneService.saveCustomerPhone(primaryCustomerPhone);

    	ETSPhone alternatePhone = profile.getAlternatePhone();
        if(null != alternatePhone.getPhoneNumber() && alternatePhone.getPhoneNumber().trim().length() > 0){
        	if(null == alternateCustomerPhone){
        		alternatePhone = (ETSPhone)phoneService.savePhone(profile.getAlternatePhone());
        		alternateCustomerPhone = customerPhoneService.create();
        		alternateCustomerPhone.setPhone(alternatePhone);
        		alternateCustomerPhone.setPhoneName(ETSPhone.AlternatePhoneName);
        		alternateCustomerPhone.setCustomer(customer);
        	}
        	else{
        		copyPhone(alternatePhone, (ETSPhone)alternateCustomerPhone.getPhone());
            	phoneService.savePhone(alternateCustomerPhone.getPhone());
        	}
       		customerPhoneService.saveCustomerPhone(alternateCustomerPhone);
        }
        else{
        	if(null != alternateCustomerPhone){
        		customerPhoneService.deleteCustomerPhoneById(alternateCustomerPhone.getId());
        	}
        }
	}

	@Override
    public void afterPropertiesSet() throws Exception {

	}

	@Override
	public List<State> findStates(String countryAbbreviation) {
		return stateService.findStates(countryAbbreviation);
	}

	@Override
	public List<Country> findCountries() {
    	return countryService.findCountries();
	}

	@Override
	public List<ChallengeQuestion> readChallengeQuestions() {
    	return challengeQuestionService.readChallengeQuestions();
	}

	@Override
	public Country findCountryByAbbreviation(String abbreviation) {
		return countryService.findCountryByAbbreviation(abbreviation);
	}

	@Override
	public State findStateByAbbreviation(String abbreviation) {
		return stateService.findStateByAbbreviation(abbreviation);
	}

	@Override
	public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
		return challengeQuestionService.readChallengeQuestionById(challengeQuestionId);
	}

	@Override
	public ProfileVO createProfile() {
		ProfileVO profile = new ProfileVO();

		//initialize customer
		ETSCustomer customer = (ETSCustomer)customerService.createCustomerFromId(null);
		CustomerType customerType = referenceService.getEntityByPrimaryKey(CustomerType.class, CustomerType.TestTaker);
		customer.setCustomerType(customerType);
		profile.setCustomer(customer);

		//initialize address
		ETSAddress address =  (ETSAddress)addressService.create();
		AddressType addressType = referenceService.getEntityByPrimaryKey(AddressType.class, AddressType.HomeAddress);
		address.setAddressType(addressType);
		address.setDefault(true);
        profile.setAddress(address);

        //initialize primary phone
        ETSPhone primaryPhone = (ETSPhone)phoneService.create();
        profile.setPrimaryPhone(primaryPhone);

        //initialize alternate phone
        ETSPhone alternatePhone = (ETSPhone)phoneService.create();
        profile.setAlternatePhone(alternatePhone);

		return profile;
	}
	/*
	@Override
	public void saveProfile(ProfileVO profile) {
	try{
		Long customerID = profile.getCustomer().getId();
		String ldapGuidID = "";
		ldapGuidID = prfleService.persistProfile(profile);

		if(null != authMechanism && authMechanism.equalsIgnoreCase(ApplicationConfiguration.AUTH_MECHANISM_OAM))
		{
			if(!(profile.getCustomer().isRegistered()))
			{
				LOG.debug("11111111111111111");
				prfleService.updateGUID(ldapGuidID, customerID);
				LOG.debug("11111111111111111");
			}
		}
		if(null == profile.getCustomer().getMilitaryMemberShip() || !profile.getCustomer().getMilitaryMemberShip()){
			MilitaryStatusType value = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, MilitaryStatusType.NOT_A_MEMBER);
			profile.getCustomer().setMilitaryStatus(value);
		}

		ETSCustomer customer = (ETSCustomer)customerService.saveCustomer(profile.getCustomer(), profile.getCustomer().isRegistered());
		if( !profile.getCustomer().isRegistered()){
			ProgramType programType = programService
					.getProgramByPrimaryKey(ProgramContextHolder.getProgramCode());
			customerService.addCustomerProgramInterest(customer, programType);

			String linkageKey = generatePasswordService.generateRandomString();
			while (true) {
				boolean keyPresent = customerService
						.checkForCustomerLinkageKey(linkageKey);
				if (!keyPresent) {
					LinkageType linkageType = referenceService
							.getEntityByPrimaryKey(LinkageType.class,
									LinkageType.USER_DISP_ID);
					customerService.addCustomerLinkage(customer, linkageType,
							linkageKey);
					break;
				}
			}
		}

		saveAddress(profile);

		savePhones(profile);

		saveBIQ(profile.getCustomer().getId(),profile.getDemographicQuestions());

        if(null != authMechanism && authMechanism.equalsIgnoreCase(ApplicationConfiguration.AUTH_MECHANISM_OAM)){

        	if(!(profile.getCustomer().isRegistered()))
        	{
	        	String guidID = eiasWebServiceClient.createUser(profile.getCustomer());
	        	try
	        	{
		            LinkageType linkageType = referenceService.getEntityByPrimaryKey(LinkageType.class, LinkageType.EIAS_OIM_GUID);
		            customerService.addCustomerLinkage(customer, linkageType, guidID);
		        	//ETSCustomer cst = (ETSCustomer)customerService.readCustomerById(customer.getId());
		            customer.setLdapGUIDID(guidID);
	        		customerService.saveCustomer(customer,false);
	        	}
	        	catch(Exception e)
	        	{

	        	}
        	}
        	else
        	{
        		eiasWebServiceClient.modifyUser(profile.getCustomer());
        	}
        }
	}
	catch(Exception e)
	{
		LOG.error("------------------>"+e);
	}
	}*/

	@Override
	public String saveProfile(ProfileVO profile)
	{
		
		String guidID ="";
		if(null == profile.getCustomer().getMilitaryMemberShip() || !profile.getCustomer().getMilitaryMemberShip()){
			MilitaryStatusType value = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, MilitaryStatusType.NOT_A_MEMBER);
			profile.getCustomer().setMilitaryStatus(value);
		}

		
		ETSCustomer customerFromUI = profile.getCustomer();

		//Encrypt password
		try {
					customerFromUI.setPassword(ProfileUtils.encryptString(customerFromUI.getPassword()));
				} catch (Exception e) {
					LOG.debug(e.getMessage());
				}
		
		ETSCustomer customer = (ETSCustomer)customerService.saveCustomer(customerFromUI, customerFromUI.isRegistered());
		if( !profile.getCustomer().isRegistered()){
			ProgramType programType = programService
					.getProgramByPrimaryKey(ProgramContextHolder.getProgramCode());
			customerService.addCustomerProgramInterest(customer, programType);

			String linkageKey = generatePasswordService.generateRandomString();
			while (true) {
				boolean keyPresent = customerService
						.checkForCustomerLinkageKey(linkageKey);
				if (!keyPresent) {
					LinkageType linkageType = referenceService
							.getEntityByPrimaryKey(LinkageType.class,
									LinkageType.USER_DISP_ID);
					customerService.addCustomerLinkage(customer, linkageType,
							linkageKey);
					profile.setCustLinkageKey(linkageKey);
					break;
				}
			}
		}

		saveAddress(profile);

		savePhones(profile);
		if( !profile.getCustomer().isRegistered()){
			LOG.debug("Calling BIQ Save");
		saveBIQ(profile.getCustomer().getId(),profile.getDemographicQuestions());
		}

		if(eregUtils.isOAMAuthentication())
        {

        	if(!(profile.getCustomer().isRegistered()))
        	{
	        	guidID = eiasWebServiceClient.createUser(profile.getCustomer());
        	}
        	else
        	{
        		eiasWebServiceClient.modifyUser(profile.getCustomer());
        	}
        }
		return guidID;
	}

    @Override
	public void updateGUID(String guid, Long customerID)
	{
			ProfileVO profile = prfleService.readProfileById(customerID);
			profile.getCustomer().setLdapGUIDID(guid);
			customerService.saveCustomer(profile.getCustomer(), profile.getCustomer().isRegistered());
	}

	@Override
	public UserVO saveProfileByCSR(ProfileVO profile)
	{
		UserVO usr = null;
		profile.getCustomer().setInternalUserFlag(false);
		if(null == profile.getCustomer().getMilitaryMemberShip() || !profile.getCustomer().getMilitaryMemberShip()){
			MilitaryStatusType value = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, MilitaryStatusType.NOT_A_MEMBER);
			profile.getCustomer().setMilitaryStatus(value);
		}

		ETSCustomer customer = (ETSCustomer)customerService.saveCustomer(profile.getCustomer(), profile.getCustomer().isRegistered());
		if( !profile.getCustomer().isRegistered()){
			ProgramType programType = programService
					.getProgramByPrimaryKey(ProgramContextHolder.getProgramCode());
			customerService.addCustomerProgramInterest(customer, programType);

			String linkageKey = generatePasswordService.generateRandomString();
			while (true) {
				boolean keyPresent = customerService
						.checkForCustomerLinkageKey(linkageKey);
				if (!keyPresent) {
					LinkageType linkageType = referenceService
							.getEntityByPrimaryKey(LinkageType.class,
									LinkageType.USER_DISP_ID);
					customerService.addCustomerLinkage(customer, linkageType,
							linkageKey);
					profile.setCustLinkageKey(linkageKey);
					break;
				}
			}
		}

		saveAddress(profile);

		savePhones(profile);
		if( !profile.getCustomer().isRegistered()){
			LOG.debug("Calling BIQ Save");
		saveBIQ(profile.getCustomer().getId(),profile.getDemographicQuestions());
		}

        if(eregUtils.isOAMAuthentication())
		{

        	if(!(profile.getCustomer().isRegistered()))
        	{
	        	usr = eiasWebServiceClient.createUserFromCSR(profile.getCustomer()); 
        	}
        	else
        	{
        		usr = new UserVO();
        		eiasWebServiceClient.modifyUser(profile.getCustomer());
        	}
        }
		return usr;
	}

	@Override
	public void updateCustomerGuid(UserVO user, Long customerID) {
		
		ProfileVO profile = prfleService.readProfileById(customerID);
		profile.getCustomer().setLdapGUIDID(user.getObjectGuid());
		profile.getCustomer().setUsername(user.getUserID());
		customerService.saveCustomer(profile.getCustomer(), profile.getCustomer().isRegistered());
	}

	public void saveBIQ(Long customerId, List<DemographicQuestionVO> biqs) {
		if(null!=biqs && !biqs.isEmpty()){
		List<String> dependentQidAnswerToSend = new ArrayList<String>();
		for (DemographicQuestionVO QVO : biqs) {

			if (QVO.isIndependent()) {
				if (QVO.isAnswered()) {
					dependentQidAnswerToSend.addAll(getTriggerQEnabled(QVO));
				}

			} else {// dependent
				if (dependentQidAnswerToSend.contains(QVO.getQstnNo().toString())) {// only if main Q answered then add answer of this dependednt Q

					if (QVO.isAnswered()) {
						dependentQidAnswerToSend
								.addAll(getTriggerQEnabled(QVO));
					}
				} else { // if dependent Q's triggering resposne not selected
							// then selectedResponse should be reset to null for
							// the depenedent Q
					QVO.setSelectedResponseIds(null);
				}
			}

		}

		profileDmgrphService.saveProfileDemographicResponses(customerId, biqs);
		}

	}

	/**
	 * @param qVO
	 * @return List of dependent Question Numbers which are now enabled due to resposne selected
	 */
	private List<String> getTriggerQEnabled(DemographicQuestionVO qVO) {
		List<String> triggerQEnabled=new ArrayList<String>();
		if(null!=qVO.getDependentQuestionVO()&& !qVO.getDependentQuestionVO().isEmpty()){
			for(String ans:qVO.getSelectedResponseIds()){
				for(DmgrphQstnTriggerVO triggerQ:qVO.getDependentQuestionVO()){
					if(triggerQ.getTriggerRespNo().toString().equalsIgnoreCase(ans)){
						triggerQEnabled.add(triggerQ.getDependentQstnNo().toString());
					}
				}
			}
		}
		return triggerQEnabled;
	}



	@Override
	public void registerProfile(ProfileVO profile) {
		Customer customer = profile.getCustomer();
        if(!customer.isRegistered()){
        	customerService.registerCustomer(customer, customer.getPassword(), customer.getPassword());
        }
	}

	@Override
	public boolean isUsernameAvailable(String username) {
		boolean isAvailable = true;

		if(eregUtils.isOAMAuthentication())
		{
			isAvailable = eiasWebServiceClient.searchUser(username);
        }
        else{
        	isAvailable = (null != customerService.readCustomerByUsername(username));
        }
		return isAvailable;
	}

	@Override
	public ProfileVO readProfileByUsername(String username) {
		ETSCustomer customer = (ETSCustomer)customerService.readCustomerByUsername(username);
		return loadProfile(customer);
	}

	@Override
	public ProfileVO readProfileById(Long id) {
		ETSCustomer customer = (ETSCustomer)customerService.readCustomerById(id);
		return loadProfile(customer);
	}

	@Override
	public ProfileVO authenthicate(String username, String password) {
		ProfileVO profile = readProfileByUsername(username);
		if(null != profile)
		{
			if(null == password || !password.equalsIgnoreCase(profile.getCustomer().getPassword()))
			{
				profile = null;
			}
		}
		return profile;
	}
/*
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void saveAccount(ProfileVO profile) {

		customerService.saveCustomer(profile.getCustomer());

        if(null != authMechanism && authMechanism.equalsIgnoreCase(ApplicationConfiguration.AUTH_MECHANISM_OAM)){
        	eiasWebServiceClient.modifyUser(profile.getCustomer());
        }

	}
	*/
	@Override
	public DupCheckResponseObject checkDuplicateProfile(ProfileVO profile) {

		return customerService.checkDuplicateProfile(profile);

	}
	@Override
	public Boolean hasDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return customerService.hasDuplicateProfiles(profile, currentloggedCust);
	}

	@Override
	public List<CustomerVO> getDuplicateProfiles(ProfileVO profile,boolean currentloggedCust)
	{
		return customerService.getDuplicateProfiles(profile, currentloggedCust);
	}

	public void setETSCustomerService(ETSCustomerService customerService)
	{
		this.customerService = customerService;
	}

	public ETSCustomerService getETSCustomerService()
	{
		return customerService;
	}

	@Override
	public void changePassword(ProfileVO profile) {
		if(null == profile.getCustomer().getMilitaryMemberShip() || !profile.getCustomer().getMilitaryMemberShip()){
			MilitaryStatusType value = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, MilitaryStatusType.NOT_A_MEMBER);
			profile.getCustomer().setMilitaryStatus(value);
		}
		ETSCustomer customer = profile.getCustomer();
		//LinkageType linkageType = referenceService.getEntityByPrimaryKey(LinkageType.class, LinkageType.EIAS_OIM_GUID);
		
		//Encrypt password
		try {
			 customer.setPassword(ProfileUtils.encryptString(customer.getPassword()));
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		
        if(eregUtils.isOAMAuthentication())
        {
			eiasWebServiceClient.resetPassword(customer.getUsername(), customer.getLdapGUIDID(), customer.getPassword(), "Y");
        }
        else
        {
        	customerService.saveCustomer(profile.getCustomer(), profile.getCustomer().isRegistered());
        }
	}

	@Override
	public void changeSecQuestion(ProfileVO profile) {
		if(null == profile.getCustomer().getMilitaryMemberShip() || !profile.getCustomer().getMilitaryMemberShip()){
			MilitaryStatusType value = referenceService.getEntityByPrimaryKey(MilitaryStatusType.class, MilitaryStatusType.NOT_A_MEMBER);
			profile.getCustomer().setMilitaryStatus(value);
		}

		ETSCustomer customer = profile.getCustomer();
		
		//Encrypt password
		try {
			 customer.setPassword(ProfileUtils.encryptString(customer.getPassword()));
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}

		
		if(eregUtils.isOAMAuthentication())
		{
        	eiasWebServiceClient.modifyUser(profile.getCustomer());
        }
        else
        {
        	customerService.saveCustomer(customer, customer.isRegistered());
        }
	}



}
