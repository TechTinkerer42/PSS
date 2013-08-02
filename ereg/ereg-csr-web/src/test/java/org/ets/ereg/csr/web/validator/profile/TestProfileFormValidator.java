package org.ets.ereg.csr.web.validator.profile;

import static org.junit.Assert.assertEquals;

import org.ets.ereg.csr.web.form.profile.ProfileForm;
import org.ets.ereg.domain.common.ETSAddressImpl;
import org.ets.ereg.domain.common.ETSPhoneImpl;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.ets.ereg.session.facade.shared.service.ReferenceBusinessService;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BindingResult;

public class TestProfileFormValidator {

	private static ProfileFormValidatorImpl profileFormValidator = new ProfileFormValidatorImpl();

	private static Mockery mockingContext = new Mockery();
	private static ProfileBusinessService mockedProfileBusinessService;
	private static ReferenceBusinessService mockedReferenceEntityBusinessService;
	private static ProfileForm profileForm;
	private static BindingResult errors;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupMockObjects();
		profileFormValidator.setProfileBusinessService(mockedProfileBusinessService);
		profileFormValidator.setReferenceEntityBusinessService(mockedReferenceEntityBusinessService);
		/*errors = new ();*/
	}

	private static void setupMockObjects() {
		mockedProfileBusinessService = mockingContext.mock(ProfileBusinessService.class);
		mockedReferenceEntityBusinessService = mockingContext.mock(ReferenceBusinessService.class);
	}

	@Before
	public void setUp() throws Exception {
		profileForm = new ProfileForm();
		profileForm.setProfile(new ProfileVO());
		profileForm.getProfile().setCustomer(new ETSCustomerImpl());
		profileForm.getProfile().setAddress(new ETSAddressImpl());
		profileForm.getProfile().setPrimaryPhone(new ETSPhoneImpl());
		profileForm.getProfile().setAlternatePhone(new ETSPhoneImpl());
	}

	@Test
	public void testValidateContactInfo() {

	}

}
