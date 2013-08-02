package org.ets.ereg.csr.web.controller.profile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.ets.ereg.common.business.vo.CustomerResultsVO;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.csr.web.form.profile.CustomerSearchForm;
import org.ets.ereg.session.facade.profile.service.ETSCustomerBusinessService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

public class TestCustomerSearchController {
	private static CustomerSearchController custSearchController = new CustomerSearchController();
	private static Mockery mockingContext = new Mockery();
	private static ETSCustomerBusinessService eTSCustomerBusinessService;
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static BindingResult errors;
	private static CustomerSearchForm customerSearchForm;
	private static MessageSource messageSource;
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		custSearchController.setEtsCustomerBusinessService(eTSCustomerBusinessService);
		custSearchController.setMessageSource(messageSource);
	}
	private static void setupMockObjects() {
		eTSCustomerBusinessService = mockingContext
				.mock(ETSCustomerBusinessService.class);
		request = mockingContext.mock(HttpServletRequest.class);
		response = mockingContext.mock(HttpServletResponse.class);
		messageSource=mockingContext.mock(MessageSource.class);
		errors = mockingContext.mock(BindingResult.class);
	}

	@Before
	public void initialize() {
		customerSearchForm = new CustomerSearchForm();
		customerSearchForm.setAppointmentNumber(0L);
	}
	@Test
	public void testGetViews() {
		/*assertEquals(custSearchController.getCustomerSearchView(),
				custSearchController.searchCustomer(customerSearchForm));*/

	}

	@Test
	public void searchByCriteria() throws ERegCheckedException {
		/*mockingContext.checking(new Expectations() {
			{
				SearchParameters searchParams= new SearchParameters();
				searchParams.setNumofRows(10);
				searchParams.setPageSize(10);
				searchParams.setDes(null);
				searchParams.setSortBy(null);
				oneOf(eTSCustomerBusinessService).searchCustomerByCriteria(customerSearchForm.getCustomer(),
						customerSearchForm.getAddress(), customerSearchForm.getPhone(), searchParams,
						customerSearchForm.getDisplaySequenceId(),0L, "randomString",null);
				CustomerResultsVO custResultsVO = new CustomerResultsVO();
				will(returnValue(custResultsVO));
				oneOf(messageSource).getMessage("candidateSearch.empty.invalid", null, Locale.US);
				will(returnValue(null));


			}
		});
		custSearchController.searchCustomerByCriteria(customerSearchForm, errors, request, response);
*/




	}


}
