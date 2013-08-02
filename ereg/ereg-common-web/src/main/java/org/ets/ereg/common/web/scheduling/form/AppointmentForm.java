package org.ets.ereg.common.web.scheduling.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.profile.vo.ProfileVO;
import org.ets.ereg.scheduling.vo.AppointmentVO;
import org.ets.ereg.scheduling.vo.TestVO;
import org.springframework.util.AutoPopulatingList;

public class AppointmentForm  {


	public static final String TEST = "booking.testVariation";
	public static final String TEST_DATE_TIME = "booking.appointmentDateTime";
	public static final String TEST_TIME = "testTimeAMPM";
	public static final String TEST_CENTER = "booking.testCenter";
	public static final String TEST_TYPE = "booking.testVariation.deliveryModeType";
	public static final String TEST_LANGUAGE = "booking.testVariation.langCode";
	public static final String TEST_FORM = "booking.form.formType";
	public static final String COMMENTS = "booking.comments";

	public static final String STEP_PERSONAL_INFO = "Personal Information";
	public static final String STEP_APPOINTMENT_INFO = "Appointment Information";
	public static final String STEP_REVIEW_INFO = "Review Information";

	private String step;
	private ProfileVO profile;
	private AutoPopulatingList<AppointmentVO> appointments = null;
	private int appointmentCount;
	private TestCenter testCenter;
	private String testCenterAddress;
	private List<TestVO> tests;
	private TestVO reschedulingTest;
	private Boolean getTestsFlag;
	private Map<Long, String> formMap;
	private String appointmentNumber;
	private Boolean accomodationFlag;
	private Long agencyId;

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	public AutoPopulatingList<AppointmentVO> getAppointments() {
		return appointments;
	}

	public void setAppointments(AutoPopulatingList<AppointmentVO> appointments) {
		this.appointments = appointments;
	}

	public int getAppointmentCount() {
		return appointmentCount;
	}

	public void setAppointmentCount(int appointmentCount) {
		this.appointmentCount = appointmentCount;
	}

	public TestCenter getTestCenter() {
		return testCenter;
	}

	public void setTestCenter(TestCenter testCenter) {
		this.testCenter = testCenter;
	}

	public String getTestCenterAddress() {
		return testCenterAddress;
	}

	public void setTestCenterAddress(String testCenterAddress) {
		this.testCenterAddress = testCenterAddress;
	}

	public List<TestVO> getTests() {
		return tests;
	}

	public void setTests(List<TestVO> tests) {
		this.tests = tests;
	}

	public TestVO getReschedulingTest() {
		return reschedulingTest;
	}

	public void setReschedulingTest(TestVO reschedulingTest) {
		this.reschedulingTest = reschedulingTest;
	}

	public Boolean getGetTestsFlag() {
		return getTestsFlag;
	}

	public void setGetTestsFlag(Boolean getTestsFlag) {
		this.getTestsFlag = getTestsFlag;
	}

	public Map<Long, String> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<Long, String> formMap) {
		this.formMap = formMap;
	}

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

	public Boolean getAccomodationFlag() {
		return accomodationFlag;
	}

	public void setAccomodationFlag(Boolean accomodationFlag) {
		this.accomodationFlag = accomodationFlag;
	}

	public AppointmentForm() {
		step = STEP_PERSONAL_INFO;
		getTestsFlag = false;
		formMap = new HashMap<Long, String>();
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}



}
