package org.ets.ereg.csr.web.controller.appointment;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.common.web.scheduling.form.AppointmentForm;
import org.ets.ereg.csr.web.validator.appointment.AppointmentFormValidator;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.session.facade.profile.service.ProfileBusinessService;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

public class TestAppointmentController {

	private static AppointmentController appointmentController = new AppointmentController();

	private static Mockery mockingContext = new Mockery();
	private static ProfileBusinessService mockedProfileBusinessService;
	private static AppointmentForm appointmentForm;
	private static AppointmentFormValidator mockedAppointmentFormValidator;
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static Model model;
	private static BindingResult errors;
	private static SessionStatus sessionStatus;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupMockObjects();
		appointmentForm = new AppointmentForm();
		appointmentForm.setAppointments(new AutoPopulatingList<AppointmentVO>(AppointmentVO.class));
		appointmentForm.setAppointmentCount(0);
		appointmentForm.setProfile(new ProfileVO());
		/*appointmentForm.getProfile().setCustomer(new ETSCustomerImpl());
		appointmentForm.getProfile().getCustomer().setId(999L);*/
		appointmentForm.setStep(AppointmentForm.STEP_PERSONAL_INFO);
		appointmentController.setProfileBusinessService(mockedProfileBusinessService);
	}

	@Before
	public void setUp() throws Exception {
	}

	private static void setupMockObjects() {
		mockedProfileBusinessService = mockingContext.mock(ProfileBusinessService.class);
		mockedAppointmentFormValidator = mockingContext.mock(AppointmentFormValidator.class);
		request = mockingContext.mock(HttpServletRequest.class);
		response = mockingContext.mock(HttpServletResponse.class);
		model = mockingContext.mock(Model.class);
		errors = mockingContext.mock(BindingResult.class);
		sessionStatus = mockingContext.mock(SessionStatus.class);
	}

	@Test
	public void testGetPersonalInfoView() {
		/*final Long customerId = 999L;
		mockingContext.checking(new Expectations() {
			{
				ProfileVO profile = new ProfileVO();
				oneOf(mockedProfileBusinessService).readProfileById(customerId);
				will(returnValue(profile));
			}
		});
		assertEquals(AppointmentController.getPersonalInfoView(),
				appointmentController.newAppointment(request, response, model, customerId, appointmentForm, errors));*/
	}

	@Ignore
	@Test
	public void testProcessPersonalInfo() {
		assertEquals(AppointmentController.redirectAppointmentInfoView(),
				appointmentController.processPersonalInfo());
	}

	@Test
	public void testDisplayAppointmentInfoView() {
		/*assertEquals(AppointmentController.getAppointmentInfoView(),
				appointmentController.displayAppointmentInfoView(request, response, model, appointmentForm, errors));*/
	}

	@Test
	public void testGetAvailableTestForm() {
		/*mockingContext.checking(new Expectations() {
			{
				List<String> availableTestForms = new ArrayList<String>();
				oneOf(model).addAttribute("availableTestForms", availableTestForms);
			}
		});
		assertEquals(AppointmentController.getAvailableTestFormsPanel(),
				appointmentController.getAvailableTestForm(request, response, "testCode", "05-02-2013", model, appointmentForm, errors));*/
	}

	@Test
	public void testAddAnotherAppointment() {
		/*mockingContext.checking(new Expectations() {
			{
				int appointmentCount = 0;
				oneOf(model).addAttribute("appointmentCount", appointmentCount);
			}
		});
		assertEquals(AppointmentController.getAddAnotherAppointmentPanel(),
				appointmentController.addAnotherAppointment(request, response, 0, model, appointmentForm, errors));*/
	}

	@Ignore @Test
	public void testRemoveAppointment() {
		//assertEquals("OK", appointmentController.removeAppointment(request, response, 0, model, appointmentForm, errors));
	}

	@Test
	public void testGoBackToPersonalInfoView() {
		/*mockingContext.checking(new Expectations() {
			{
				oneOf(mockedAppointmentFormValidator).validateAppointmentInfo(appointmentForm, errors);
				oneOf(errors).hasErrors();
				will(returnValue(true));
			}
		});
		assertEquals(AppointmentController.getAppointmentInfoView(),
				appointmentController.goBackToPersonalInfoView(request, response, model, appointmentForm, errors));
		ProfileVO profile = new ProfileVO();
		ETSCustomer customer = new ETSCustomerImpl();
		customer.setId(999L);
		profile.setCustomer(customer);
		appointmentForm.setProfile(profile);
		mockingContext.checking(new Expectations() {
			{
				oneOf(mockedAppointmentFormValidator).validateAppointmentInfo(appointmentForm, errors);
				oneOf(errors).hasErrors();
				will(returnValue(false));
			}
		});
		assertEquals("redirect:/secure/tcaScheduling/new?customerId=" + appointmentForm.getProfile().getCustomer().getId(),
				appointmentController.goBackToPersonalInfoView(request, response, model, appointmentForm, errors));*/
	}


}
