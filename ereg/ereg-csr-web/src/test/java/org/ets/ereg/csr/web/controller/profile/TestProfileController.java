package org.ets.ereg.csr.web.controller.profile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.broadleafcommerce.common.exception.ServiceException;
import org.ets.ereg.common.business.vo.biq.DemographicQuestionVO;
import org.ets.ereg.csr.web.controller.TestRulesController;
import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.ets.ereg.csr.web.validator.profile.ProfileFormValidator;
import org.ets.ereg.domain.interfaces.model.profile.DupCheckResponseObject;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.external.service.eias.client.types.UserVO;
import org.ets.ereg.profile.biq.service.ProfileDemographicQuestionService;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.GeneratePasswordBusinessService;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.util.GenerateBase64StringBusinessService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

@RunWith(JMock.class)
public class TestProfileController {

	private static Logger logger = LoggerFactory.getLogger(TestProfileController.class);
	
	private static ProfileController profileController = new ProfileController();

	private static Mockery mockingContext = new Mockery();
	private static ProfileBusinessService mockedProfileBusinessService;
	private static ProfileDemographicQuestionService profileDemographicService;
	private static GeneratePasswordBusinessService mockedGeneratePasswordBusinessService;
    private static GenerateBase64StringBusinessService mockedGenerateBase64StringBusinessService;
	private static ProfileFormValidator mockedProfileFormValidator;
	private static ProfileForm profileForm;
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static HttpSession session;
	private static Model model;
	private static BindingResult errors;
	private static SessionStatus sessionStatus;

	@BeforeClass
	public static void setup() {
		setupMockObjects();
		profileController.setProfileBusinessService(mockedProfileBusinessService);
		profileController.setGeneratePasswordBusinessService(mockedGeneratePasswordBusinessService);
		profileController.setProfieFormValidator(mockedProfileFormValidator);
		profileController.setprofileDemographicQuestionService(profileDemographicService);
		profileController.setGenerateBase64StringBusinessService(mockedGenerateBase64StringBusinessService);
		//request.setAttribute("generateBase64StringBusinessService", mockedGenerateBase64StringBusinessService);
	}

	private static void setupMockObjects() {
		mockedProfileBusinessService = mockingContext.mock(ProfileBusinessService.class);
		mockedGeneratePasswordBusinessService = mockingContext.mock(GeneratePasswordBusinessService.class);
		profileDemographicService = mockingContext.mock(ProfileDemographicQuestionService.class);
		mockedProfileFormValidator = mockingContext.mock(ProfileFormValidator.class);
		mockedGenerateBase64StringBusinessService = mockingContext.mock(GenerateBase64StringBusinessService.class);
		request = mockingContext.mock(HttpServletRequest.class);
		response = mockingContext.mock(HttpServletResponse.class);
	    session = mockingContext.mock(HttpSession.class);
		model = mockingContext.mock(Model.class);
		errors = mockingContext.mock(BindingResult.class);
		sessionStatus = mockingContext.mock(SessionStatus.class);

	}

	@Before
	public void initialize() {
		profileForm = new ProfileForm();
		profileForm.setProfile(new ProfileVO());
		profileForm.getProfile().setCustomer(new ETSCustomerImpl());
		long id = 101;
		profileForm.getProfile().getCustomer().setId(id);
	}

/*	@Test
	public void testGetViews() {
		assertEquals(ProfileController.getPersonalInfoFormView(),
				profileController.newProfile(request, response, model, profileForm, errors));

		profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
		assertEquals(ProfileController.getAdditionalInfoFormView(),
				profileController.displayAdditionalInfoFormView(request, response, model, profileForm, errors));

		profileForm.setStep(ProfileForm.STEP_ADDITIONAL_INFO);
		assertEquals(ProfileController.getBackgroundInfoInfoFormView(),
				profileController.displayBackgroundInfoFormView(request, response, model, profileForm, errors));

		profileForm.setStep(ProfileForm.STEP_BIQ_INFO);
		assertEquals(ProfileController.getReviewProfileView(),
				profileController.displayReviewProfileView(request, response, model, profileForm, errors));
		profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
		assertEquals(ProfileController.getReviewProfileView(),
				profileController.displayReviewProfileView(request, response, model, profileForm, errors));
	}*/

	@Test
	public void testRedirectForWrongStep() {
		profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
		assertEquals(ProfileController.redirectPersonalInfoFormView(),
				profileController.displayDuplicateAccountView(request, response, model, profileForm, errors));
		assertEquals(ProfileController.redirectPersonalInfoFormView(),
				profileController.overrideDuplicateAccount(request, response, model, profileForm, errors));
		try {
			assertEquals(ProfileController.redirectPersonalInfoFormView(),
					profileController.processPersonalInfo(request, response, model, profileForm, errors));
		} catch (ServiceException e) {
			logger.error("exception occurred",e);
		}
		assertEquals(ProfileController.redirectPersonalInfoFormView(),
				profileController.processAdditionalInfo(request, response, model, profileForm, errors));
		profileForm.setStep(ProfileForm.STEP_DUPLICATE_PROFILE);
		assertEquals(ProfileController.redirectPersonalInfoFormView(),
				profileController.displayAdditionalInfoFormView(request, response, model, profileForm, errors));
	}

	@Test
	public void testProcessContactInfo() {
		profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileFormValidator).validateContactInfo(profileForm, errors);
				oneOf(errors).hasErrors();
				oneOf(mockedProfileBusinessService).checkDuplicateProfile(profileForm.getProfile());
				DupCheckResponseObject dcro = new DupCheckResponseObject(new ArrayList<String>());
				dcro.setDuplicate(false);
				will(returnValue(dcro));
			}
		});
		try {
			assertEquals(ProfileController.redirectAdditionalInfoFormView(),
					profileController.processPersonalInfo(request, response, model, profileForm, errors));
		} catch (ServiceException e) {
			logger.error("exception occurred",e);
		}
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileFormValidator).validateContactInfo(profileForm, errors);
				oneOf(errors).hasErrors();
				oneOf(mockedProfileBusinessService).checkDuplicateProfile(profileForm.getProfile());
				DupCheckResponseObject dcro = new DupCheckResponseObject(new ArrayList<String>());
				dcro.setDuplicate(true);
				will(returnValue(dcro));
			}
		});
		try {
			assertEquals(ProfileController.redirectDuplicateAccountView(),
					profileController.processPersonalInfo(request, response, model, profileForm, errors));
		} catch (ServiceException e) {
			logger.error("exception occurred",e);
		}
	}

	@Test
	public void testProcessContactInfoHasErrors() {
		profileForm.setStep(ProfileForm.STEP_PERSONAL_INFO);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileFormValidator).validateContactInfo(profileForm, errors);
				oneOf(errors).hasErrors();
				will(returnValue(true));
			}
		});
		try {
			assertEquals(ProfileController.getPersonalInfoFormView(),
					profileController.processPersonalInfo(request, response, model, profileForm, errors));
		} catch (ServiceException e) {
			logger.error("exception occurred",e);
		}
	}

	/*@Test
	public void testDisplayDuplicateAccountView() {
		profileForm.setStep(ProfileForm.STEP_DUPLICATE_PROFILE);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileBusinessService).getDuplicateProfiles(profileForm.getProfile(), false);
				List<CustomerVO> dupCustomers = new ArrayList<CustomerVO>();
				dupCustomers.add(new CustomerVO());
				dupCustomers.get(0).setCandidateId(999999L);
				dupCustomers.get(0).setFirstName("Patrick");
				dupCustomers.get(0).setMiddleName("Y");
				dupCustomers.get(0).setLastName("Dong");
				dupCustomers.get(0).setDateOfBirth(new Date());
				will(returnValue(dupCustomers));
				oneOf(model.addAttribute("dupCustomers", dupCustomers));
			}
		});
		assertEquals(ProfileController.getDuplicateAccountView(),
				profileController.displayDuplicateAccountView(request, response, model, profileForm, errors));
	}*/

	@Test
	public void testOverrideDuplicateAccount() {
		profileForm.setStep(ProfileForm.STEP_DUPLICATE_PROFILE);
		assertEquals(ProfileController.redirectAdditionalInfoFormView(),
				profileController.overrideDuplicateAccount(request, response, model, profileForm, errors));
	}
	//Should name be testAdditionalInfo?
	@Test
	public void testprocessPersonalInfo() {
		profileForm.setStep(ProfileForm.STEP_ADDITIONAL_INFO);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileFormValidator).validatePersonalInfo(profileForm, errors);
				oneOf(errors).hasErrors();
			}
		});
		assertEquals(ProfileController.redirectBackgroundInfoFormView(),
				profileController.processAdditionalInfo(request, response, model, profileForm, errors));

	}

/*	@Test
	public void testprocessBackgroundInfo() {
		profileForm.setStep(ProfileForm.STEP_BIQ_INFO);
		mockingContext.checking(new Expectations() {
			{
				oneOf(profileDemographicService).setGotTriggeredFlag(profileForm.getProfile().getDemographicQuestions());

			}
		});
		assertEquals(ProfileController.redirectReviewProfileView(),
				profileController.processBackgroundInfo(request, response, model, profileForm, errors));

	}*/

	@Test
	public void testProcessAccountInfo() {
		profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileFormValidator).validateAccountInfo(profileForm, errors);
				oneOf(errors).hasErrors();
			}
		});
		assertEquals(ProfileController.redirectReviewProfileView(),
				profileController.processAccountInfo(request, response, model, profileForm, errors));
	}

	@Test
	public void testGeneratePassword() {
		profileForm.setStep(ProfileForm.STEP_ACCOUNT_INFO);
		final String generatedPassword = "12qw!@QW";
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedGeneratePasswordBusinessService).generateNewPassword();
				will(returnValue(generatedPassword));
			}
		});
		assertEquals(ProfileController.getAccountInfoFormView(),
				profileController.generatePassword(request, response, model, profileForm));
	}

/*	@Test
	public void testCreateProfile() {
		profileForm.setStep(ProfileForm.STEP_REVIEW_INFO);
		final UserVO usr = new UserVO();
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedProfileBusinessService).saveProfileByCSR(profileForm.getProfile());
				will(returnValue(usr));
				oneOf(mockedProfileBusinessService).registerProfile(profileForm.getProfile());
				oneOf(sessionStatus).setComplete();
				oneOf(mockedProfileBusinessService).updateCustomerGuid(usr,profileForm.getProfile().getCustomer().getId());
				//oneOf(request).setAttribute("generateBase64StringBusinessService", mockedGenerateBase64StringBusinessService);
				oneOf(session).setAttribute("viewProfileBackUrl", "/profile/review");

			}
		});
		assertEquals(ProfileController.getAccountCreatedView(),
		        profileController.createProfile(request, response, session, model, profileForm, errors, sessionStatus));
	}*/

	/*@Test
	public void testUpdateProfile() {
		mockingContext.checking(new Expectations() {
			{
				long id = 101;
				oneOf(mockedProfileFormValidator).validatePersonalInfo(profileForm, errors);
				oneOf(mockedProfileFormValidator).validateContactInfo(profileForm, errors);
				oneOf(errors).hasErrors();
				will(returnValue(false));
				oneOf(mockedProfileBusinessService).readProfileById(profileForm.getProfile().getCustomer().getId());
				will(returnValue(new ProfileVO()));
				oneOf(mockedProfileBusinessService).saveProfile(profileForm.getProfile());
				oneOf(sessionStatus).setComplete();
			}
		});
		assertEquals("redirect:/customersearch/search",
				profileController.updateProfile(profileForm, errors, sessionStatus));
	}
	@Test
	public void testViewProfile() {
		final long id = 101;
		mockingContext.checking(new Expectations() {
			{
				ProfileVO profVO= new ProfileVO();
				oneOf(mockedProfileBusinessService).readProfileById(id);
				will(returnValue(profVO));
				oneOf(sessionStatus).setComplete();
			}
		});
		assertEquals(ProfileController.getUpdateProfileView(),
				profileController.getUpdateView(id,profileForm, sessionStatus));
	}*/
}
