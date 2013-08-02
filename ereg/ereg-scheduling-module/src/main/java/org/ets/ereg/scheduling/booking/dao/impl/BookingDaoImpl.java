package org.ets.ereg.scheduling.booking.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.common.business.vo.SearchParameters;
import org.ets.ereg.common.util.DateTimeUtil;
import org.ets.ereg.domain.booking.BookingImpl;
import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.form.FormImpl;
import org.ets.ereg.domain.interfaces.model.booking.Booking;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.form.Form;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestTakerTest;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.domain.scheduling.TestCenterImpl;
import org.ets.ereg.domain.scheduling.TestTakerTestImpl;
import org.ets.ereg.domain.test.TestImpl;
import org.ets.ereg.scheduling.booking.dao.BookingDao;
import org.ets.ereg.scheduling.util.BookingStatusEnum;
import org.ets.ereg.scheduling.util.Constants;
import org.ets.ereg.scheduling.util.TestStatusEnum;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("bookingDao")
public class BookingDaoImpl extends AbstractDaoImpl<Booking> implements BookingDao {

	private static Logger log = LoggerFactory.getLogger(BookingDaoImpl.class);
	
	private static final String TEST_TAKER_TEST_ID = "testTakerTestId";
	private static final String TEST_STATUS_TYPE_CODE = "testStatusTypeCode";
	private static final String BOOKING_STATUS_TYPE_CODE = "bookingStatusTypeCode";

	@Override
	public Class<BookingImpl> getEntityClass() {
		return BookingImpl.class;
	}

	/**
	 * @param productCode
	 * @param customerId
	 * @param searchParams - default sort by apptDate in ascending order. Yet to customize for other parameters
	 * @return List of future bookings for a Product for a particular customer
	 */
	public List<Booking> getFutureBookingByProduct(String productCode, Long customerId, SearchParameters searchParams ){
		String sortBy=null, des=null;
		int numofRows=-1,pageSize=-1;;
		if(null!=searchParams){
			 sortBy=searchParams.getSortBy();
			 des=searchParams.getDes();
			 numofRows=searchParams.getNumofRows();
			 pageSize=searchParams.getPageSize();
		}

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Object[]> bookingCQ=cb.createQuery(Object[].class);

		Root<BookingImpl> bookingRoot = bookingCQ.from(BookingImpl.class);
		Root<TestTakerTestImpl> testTakerTestRoot = bookingCQ.from(TestTakerTestImpl.class);
		Root<FormImpl> formRoot = bookingCQ.from(FormImpl.class);

		List<Predicate> criteria = new ArrayList<Predicate>();

		criteria.add(cb.equal(bookingRoot.get("testTakerTest").get("TTTid"), testTakerTestRoot.get("TTTid")));
		criteria.add(cb.equal(bookingRoot.get("form").get("id"), formRoot.get("id")));

		Expression<Long> strCustId=testTakerTestRoot.get("customer").get("id");
		ParameterExpression<Long> pCustId =cb.parameter(Long.class,"CUSTOMER_ID");
		criteria.add(cb.equal(strCustId,pCustId));

		List<Predicate> criteriaOr = new ArrayList<Predicate>();
		Expression<String> strTestStatusCodeSchld=testTakerTestRoot.get("code").get("code");
		ParameterExpression<String> ptestStatusCodeSchld =cb.parameter(String.class,"SCHLD_TST_STS_CODE");
		criteriaOr.add(cb.equal(strTestStatusCodeSchld,ptestStatusCodeSchld));

		Expression<String> strTestStatusCodeReSchld=testTakerTestRoot.get("code").get("code");
		ParameterExpression<String> ptestStatusCodeReSchld =cb.parameter(String.class,"RESCHLD_TST_STS_CODE");
		criteriaOr.add(cb.equal(strTestStatusCodeReSchld,ptestStatusCodeReSchld));

		Expression<String> strBookStatusCode=bookingRoot.get("bookingStatusType").get("code");
		ParameterExpression<String> pBookStatusCode =cb.parameter(String.class,"BOOKING_STS_CODE");
		criteria.add(cb.equal(strBookStatusCode,pBookStatusCode));


		Expression<String> strPgmCode=formRoot.get("test").get("programType").get("code");
		ParameterExpression<String> pPgmCode =cb.parameter(String.class,"PROGRAM_TYPE");
		criteria.add(cb.equal(strPgmCode,pPgmCode));

		Expression<Date> strApptDate = bookingRoot.get("apptDate");
		ParameterExpression<Date> pApptDate =cb.parameter(Date.class,"APPT_DATE");
		criteria.add(cb.greaterThan(strApptDate,pApptDate));

		Predicate criteriaFinal = cb.or(criteriaOr.toArray(new Predicate[0]));

		criteria.add(1,criteriaFinal);

		bookingCQ.where(cb.and(criteria.toArray(new Predicate[0])));

		bookingCQ.multiselect(bookingRoot.get("id"),bookingRoot.get("form").get("test").get("testName"),bookingRoot.get("testCenter").get("name"),
				bookingRoot.get("apptDate"),bookingRoot.get("deliveryModeType").get("description"),
				bookingRoot.get("form").get("formCode"),bookingRoot.get("form").get("lang").get("description"),bookingRoot.get("etsApptID"));

		bookingCQ.distinct(true);
		if(StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(des)){
			bookingCQ.orderBy(cb.asc(bookingRoot.get("apptDate")));
		}



		TypedQuery<Object[]> bookingTQ=entityManager.createQuery(bookingCQ);
		if(numofRows>0)
		{
			bookingTQ.setFirstResult(numofRows);
		}

		if(pageSize>0)
		{
			bookingTQ.setMaxResults(pageSize);
		}

		bookingTQ.setParameter("CUSTOMER_ID", customerId);
		bookingTQ.setParameter("SCHLD_TST_STS_CODE", Constants.TESTTAKER_STATUS_TYPE_SCHEDULED);
		bookingTQ.setParameter("RESCHLD_TST_STS_CODE", Constants.TESTTAKER_STATUS_TYPE_RESCHEDULED);
		bookingTQ.setParameter("PROGRAM_TYPE", productCode);
		bookingTQ.setParameter("BOOKING_STS_CODE", Constants.BOOKING_STATUS_TYPE_RESERVED);
		bookingTQ.setParameter("APPT_DATE", new Date());

		List<Object[]> BookingTuple=bookingTQ.getResultList();

		List<Booking> bookinglst = new ArrayList<Booking>();

		for (Object[] t : BookingTuple) {
			Booking bkn=populateBookingResult(t);
			bookinglst.add(bkn);
		}
		return bookinglst;

	}

	public List<Booking> getFutureBookingByProduct(String productCode, Long customerId) {
		return getFutureBookingByProduct(productCode, customerId, null);
	}

	@Override
	public List<Form> getAvailableForms(Long customerId, Long testId, Date suppliedDate) {
		List<Form> form =null;
		TypedQuery<Form> query = entityManager.createNamedQuery("Booking.availableForms", Form.class);
		query.setParameter("suppliedTestId", testId);
		query.setParameter("suppliedDate",suppliedDate);
		query.setParameter("customerid", customerId);
		try{
			List<Form> result =  query.getResultList();
			if(result.isEmpty()){
				form = new ArrayList<Form>();
			}
			else{
				form = result;
			}
		} catch(NoResultException nre) {
			form = null;
		}
		return form;
	}

	@Override
	public List<Form> getAvailableTestFormsByTestId(Long testId) {
		List<Form> form =null;
		TypedQuery<Form> query = entityManager.createNamedQuery("Booking.availableFormsByTestId", Form.class);
		query.setParameter("suppliedTestId", testId);
		try{
			List<Form> result =  query.getResultList();
			if(result.isEmpty()){
				form = new ArrayList<Form>();
			}
			else{
				form = result;
			}
		} catch(NoResultException nre) {
			form = null;
		}
		return form;
	}


	@Override
	public List<Test> getAvailableTest(Long customerId, String programCode) {
		List<Test> test =null;
		TypedQuery<Test> query = entityManager.createNamedQuery("Booking.availableTest", Test.class);
		query.setParameter("programcode",programCode);
		query.setParameter("customerid", customerId);
		try{
			List<Test> result =  query.getResultList();
			if(result.isEmpty()){
				test = new ArrayList<Test>();
			}
			else{
				test = result;
			}
		} catch(NoResultException nre) {
			test = null;
		}
		return test;
	}

	@Override
	public List<Test> getHeldTest(Long customerId, String programCode) {
		List<Test> test =null;
		TypedQuery<Test> query = entityManager.createNamedQuery("Booking.getHeldTest", Test.class);
		query.setParameter("programcode",programCode);
		query.setParameter("customerid", customerId);
		try{
			List<Test> result =  query.getResultList();
			if(result.isEmpty()){
				test = new ArrayList<Test>();
			}
			else{
				test = result;
			}
		} catch(NoResultException nre) {
			test = null;
		}
		return test;
	}

	@Override
	public List<Test> getAllTest(String programCode) {
		List<Test> test =null;
		TypedQuery<Test> query = entityManager.createNamedQuery("Booking.allTestByProgram", Test.class);
		query.setParameter("programcode",programCode);
		try{
			List<Test> result =  query.getResultList();
			if(result.isEmpty()){
				test = new ArrayList<Test>();
			}
			else{
				test = result;
			}
		} catch(NoResultException nre) {
			test = null;
		}
		return test;
	}

	@Override
	public Form getSubForm(Long formID, Long testId,String langCode, String delvryMode) {
		Form subForm =null;
		TypedQuery<Form> query = entityManager.createNamedQuery("Booking.subFormQuery", Form.class);
		query.setParameter("formID",formID);
		query.setParameter("testId",testId);
		query.setParameter("langCode",langCode);
		query.setParameter("delvryMode",delvryMode);
		try
		{
			subForm =  (Form) query.getSingleResult();
		}
		catch(NoResultException nre)
		{
		}
		return subForm;
	}

	@Override
	public List<Form> getSubForms(Long parentFormId, Long testId, String langCode,
			String delvryMode, List<String> accommodations) {
		List<Form> subForms = Collections.emptyList();
		TypedQuery<Form> query = entityManager.createNamedQuery(
				"Booking.getSubForms", Form.class);
		query.setParameter("formID", parentFormId);
		query.setParameter("testId", testId);
		query.setParameter("langCode", langCode);
		query.setParameter("delvryMode", delvryMode);
		query.setParameter("accommodations", accommodations);
		try {
			subForms = query.getResultList();
		} catch (NoResultException nre) {
		}

		return subForms;
	}

	@Override
	public Test getAllTestById(String programCode, Long testId) {
		Test test =null;
		TypedQuery<Test> query = entityManager.createNamedQuery("Booking.allTestByPrgmTstCode", Test.class);
		query.setParameter("programcode",programCode);
		query.setParameter("testId",testId);
		try
		{
			test =  (Test) query.getSingleResult();
		}
		catch(NoResultException nre)
		{
		}
		return test;
	}

	@Override
	public List<Form> getAllFormById(Long formID) {
		List<Form> form =null;
		TypedQuery<Form> query = entityManager.createNamedQuery("Booking.allFormById", Form.class);
		query.setParameter("formid",formID);
		try{
			List<Form> result =  query.getResultList();
			if(result.isEmpty()){
				form = new ArrayList<Form>();
			}
			else{
				form = result;
			}
		} catch(NoResultException nre) {
			form = null;
		}
		return form;
	}

	@Override
	public Booking getBookingById(Long id) {
		Booking bkng =null;
		TypedQuery<Booking> query = entityManager.createNamedQuery("Booking.bookingById", Booking.class);
		query.setParameter("bookingid",id);
		try
		{
			bkng =  query.getSingleResult();
		}
		catch(NoResultException nre)
		{
		}
		return bkng;
	}

    private Booking populateBookingResult(Object[] t){
    	Booking bkng = new BookingImpl();
    	//Form frm = new FormImpl();
    	DeliveryModeType dlvy = new DeliveryModeTypeImpl();
    	Test tst = new TestImpl();
    	LanguageType lang = new LanguageTypeImpl();
    	TestCenter tstcenter = new TestCenterImpl();

    	tstcenter.setName((String)t[2]);
    	dlvy.setDescription((String)t[4]);
    	lang.setDescription((String)t[6]);
    	tst.setTestName((String)t[1]);
    	//to check later
    	/*frm.setTest(tst);
    	frm.setDlvyMode(dlvy);
    	frm.setFormCode((String)t[5]);
    	frm.setLangCode(lang);


    	bkng.setId((Long)t[0]);
		bkng.setForm(frm);
		bkng.setTest(tst);
		bkng.setLangCode(lang);
		bkng.setDeliveryModeType(dlvy);*/
		bkng.setTestCenter(tstcenter);
//		bkng.setAppointmentDateTime((Date)t[3]);
		bkng.setEtsApptID((String)t[7]);

		return bkng;

    }

    @Override
    public boolean checkAppointmentNumber(String appointmentNumber) {
        TypedQuery<Booking> query = entityManager.createNamedQuery("Booking.bookingByAppointNumber", Booking.class);
        query.setParameter("appointNumber",appointmentNumber);
        List<Booking> bookingList =  query.getResultList();
        return bookingList!=null && bookingList.size() > 0 ? true : false;
    }

    @Override
    public List<Booking> getBookingByCustomerId(Long customerId, String productCode, int maxResult) {
        TypedQuery<Booking> query = entityManager.createNamedQuery("Booking.getBookingByCustomerId", Booking.class);
        query.setParameter("customerId",customerId);
        query.setParameter("productCode",productCode);
        query.setParameter("date", new DateTime().toDateMidnight().toDate());
        List<Booking> bookingList =   query.setFirstResult(0).setMaxResults(maxResult).getResultList();
        return bookingList;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Booking> getBookingsWithAccommodation(Long customerIdLong,
			String accommodationTypeCode, Date fromDate, Date toDate) {

		Query query = entityManager.createNamedQuery("Booking.getBookingsWithAccommodation");

		query.setParameter("customerId", customerIdLong);
		query.setParameter("accommodationTypeCode", accommodationTypeCode);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return (List<Booking>)query.getResultList();
	}

	@Override
	public TestTakerTest getTestTakerTestByBookingId(Long bookingId) {

		TypedQuery<TestTakerTest>  query = entityManager.createNamedQuery("Booking.getTestTakerTestByBookingId",TestTakerTest.class);
		query.setParameter("bookingId",bookingId);
		return query.getSingleResult();

	}

	@Override
	public boolean updateBookingStatus(Long bookingId,
			String bookingStatus) {

	  Query query = entityManager.createNamedQuery("Booking.updateBookingStatus");
      query.setParameter("bookingId",bookingId);
      query.setParameter("bookingStatus",bookingStatus);

      int udpatedRowCount = query.executeUpdate();
      if(udpatedRowCount > 0){
		return true;
      }

	   return false;
	}

	@Override
	public Long getRescheduleCountForBooking(Booking booking) {
		Long count = null;
		Query query = entityManager.createNamedQuery("Booking.getRescheduleCountForBooking");
		if (booking.getTestTakerTestId() != null) {
			query.setParameter(TEST_TAKER_TEST_ID, booking.getTestTakerTestId().getTestTakerTestId());
		} else {
			return count;
		}
		query.setParameter(TEST_STATUS_TYPE_CODE, TestStatusEnum.SCHEDULED.getCode());
		query.setParameter(BOOKING_STATUS_TYPE_CODE, BookingStatusEnum.CANCELED.getCode());

		try {
			count = (Long) query.getSingleResult();
		} catch(NoResultException nre) {
			log.error("Error when getting reschedule count for booking: {}", nre.toString());
		}
		
		return count;
	}
	
	@Override
	public Boolean isEligibleForReschedule(Booking booking) {
		Boolean isEligible = null;
		Date testDate = booking.getAppointmentDateTime();
		Float testDateOffset = DateTimeUtil.getTimeZoneOffset(booking.getTestCenter().getGlobalTimeZone().getCode(), testDate);
		Date newDate = new Date();
		Float localDateOffset = DateTimeUtil.getTimeZoneOffset(TimeZone.getDefault().getID(), new Date());
		if (testDate.compareTo(DateUtils.addMinutes(newDate, (int) ((48 + localDateOffset - testDateOffset) * 60))) > 0) {
			isEligible = true;
		} else {
			isEligible = false;
		}
		return isEligible;
	}
}
