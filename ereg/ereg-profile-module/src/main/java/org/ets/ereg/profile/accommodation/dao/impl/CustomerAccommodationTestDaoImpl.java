package org.ets.ereg.profile.accommodation.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.ets.ereg.common.business.dao.impl.AbstractDaoImpl;
import org.ets.ereg.common.business.util.AccommodationStatus;
import org.ets.ereg.common.util.DateHandler;
import org.ets.ereg.domain.accommodation.CustomerAccommodationTestImpl;
import org.ets.ereg.domain.interfaces.model.accommodation.AccommodationType;
import org.ets.ereg.domain.interfaces.model.accommodation.CustomerAccommodationTest;
import org.ets.ereg.domain.interfaces.model.accommodation.ProgramAccommodationDeliveryMode;
import org.ets.ereg.domain.interfaces.model.test.Test;
import org.ets.ereg.profile.accommodation.dao.CustomerAccommodationTestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("customerAccommodationTestDao")
public class CustomerAccommodationTestDaoImpl extends AbstractDaoImpl<CustomerAccommodationTest>
															implements CustomerAccommodationTestDao{

	private static Logger logger = LoggerFactory.getLogger(CustomerAccommodationTestDaoImpl.class);

	@Override
	public Class<? extends CustomerAccommodationTest> getEntityClass() {
		return CustomerAccommodationTest.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * returns all active accommodations of customer by comparing with user supplied expiration date
	 */
	public List<CustomerAccommodationTest> getActiveAccommodations(Long customerId, Date testDate) {
		logger.debug("getActiveAccommodations for customer id {}",customerId);

		Query query=entityManager.createNamedQuery("CustomerAccommodations.getActiveAccmodationsForCustomer");
		query.setParameter("customerId", customerId);
		query.setParameter("testDate", testDate,TemporalType.DATE);

		return (List<CustomerAccommodationTest>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * returns all active accommodations for customer id
	 * for a particular test in a program
	 */
	public List<CustomerAccommodationTest> getActiveAccommodationsForTest(
			Long customerId, Long testId, String programCode, Date testDate) {

		logger.debug("getActiveAccommodationsForTest - customer id {} for test {}",customerId,testId);
		Query query=entityManager.createNamedQuery("CustomerAccommodations.getActiveAccmodationsForTest");

		query.setParameter("customerId", customerId);
		query.setParameter("testId", testId);
		query.setParameter("programCode", programCode);
		query.setParameter("testDate", testDate,TemporalType.DATE);

		return (List<CustomerAccommodationTest>)query.getResultList();

	}


	@SuppressWarnings("unchecked")
	@Override
	/**
	 * returns all the accommodations (both active and expired) for a give customer id
	 */
	public List<CustomerAccommodationTest> getAllAccommodations(Long customerId) {
		logger.debug("getAllAccommodations for customer id {}",customerId);
		Query query=entityManager.createNamedQuery("CustomerAccommodations.getAllAccmodationsForCustomer");
		query.setParameter("customerId", customerId);
		return (List<CustomerAccommodationTest>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Test> getTestsWithoutAccommodations(
			Long customerId, String programCode, String deliveryModeCode) {

		Query query=entityManager.createNamedQuery("CustomerAccommodations.getTestsWithoutAccommodations");
		query.setParameter("customerId", customerId);
		query.setParameter("programCode", programCode);
		query.setParameter("deliveryModeCode", deliveryModeCode);

		return (List<Test>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccommodationType> getAllNotApprovedAccommodations(
			Long customerId, String programCode, String deliveryModeCode) {

		Query query=entityManager.createNamedQuery("CustomerAccommodations.getAllNotApprovedAccommodationsForCustomer");
		query.setParameter("customerId", customerId);
		query.setParameter("programCode", programCode);
		query.setParameter("deliveryModeCode", deliveryModeCode);

		return (List<AccommodationType>)query.getResultList();
	}

	@Override
	public List<CustomerAccommodationTestImpl> getAllAccommodations(
			Long customerId, String programCode, Long testId,
			String deliveryModeCode,AccommodationStatus accommodationStatus,Date testDate) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<CustomerAccommodationTestImpl> creteriaQuery= criteriaBuilder.createQuery(CustomerAccommodationTestImpl.class);
		Root<CustomerAccommodationTestImpl> customerAccommodation =  creteriaQuery.from(CustomerAccommodationTestImpl.class);
		customerAccommodation.fetch("accommodationTypeValue",JoinType.LEFT);
		customerAccommodation.fetch("test",JoinType.LEFT);
		Fetch<CustomerAccommodationTest,ProgramAccommodationDeliveryMode> programAccommodationDeliveryMode = customerAccommodation.fetch("programAccommodationDeliveryMode",JoinType.LEFT);
		programAccommodationDeliveryMode.fetch("accommodationType",JoinType.LEFT);
		programAccommodationDeliveryMode.fetch("deliveryModeType",JoinType.LEFT);


		List<Predicate> criteria = new ArrayList<Predicate>();

		Expression<Long> custIdLong=customerAccommodation.get("customerAccommodationTestId").get("customerId");
		ParameterExpression<Long> pCustId = criteriaBuilder.parameter(Long.class,"CUSTOMER_ID");
		criteria.add(criteriaBuilder.equal(custIdLong,pCustId));



		Expression<String> strprogramCode=customerAccommodation.get("customerAccommodationTestId").get("programCode");
		ParameterExpression<String> pProgramCode = criteriaBuilder.parameter(String.class,"PROGRAM_CODE");
		criteria.add(criteriaBuilder.and(criteriaBuilder.equal(strprogramCode,pProgramCode)));

		if(testId!=null &&  testId> 0){
			Expression<String> strTestCode=customerAccommodation.get("customerAccommodationTestId").get("testId");
			ParameterExpression<Long> pTestCode = criteriaBuilder.parameter(Long.class,"TEST_ID");
			criteria.add(criteriaBuilder.and( criteriaBuilder.equal(strTestCode,pTestCode)));
		}

		if(StringUtils.isNotEmpty(deliveryModeCode)){
			Expression<String> strDeliveryModeCode=customerAccommodation.get("customerAccommodationTestId").get("deliveryModeCode");
			ParameterExpression<String> pDeliveryModeCode = criteriaBuilder.parameter(String.class,"DELIVERY_MODE_CODE");
			criteria.add(criteriaBuilder.and( criteriaBuilder.equal(strDeliveryModeCode,pDeliveryModeCode)));
		}

		if(accommodationStatus!=null){
			Expression<Date> strExpiryDate=customerAccommodation.get("expiryDate");
			ParameterExpression<Date> pExpiryDate = criteriaBuilder.parameter(Date.class,"EXPIRY_DATE");
			Predicate expiryDatePredicate = null;
			if(AccommodationStatus.ACTIVE.equals(accommodationStatus)){
				expiryDatePredicate = criteriaBuilder.lessThanOrEqualTo(pExpiryDate,strExpiryDate);
			}else if(AccommodationStatus.EXPIRED.equals(accommodationStatus)){
				expiryDatePredicate = criteriaBuilder.greaterThan(pExpiryDate,strExpiryDate);
			}
			criteria.add(criteriaBuilder.and(expiryDatePredicate));
		}

		creteriaQuery.where(criteria.toArray(new Predicate[]{}));
		creteriaQuery.orderBy(criteriaBuilder.asc(customerAccommodation.get("test").get("testName")),
				criteriaBuilder.asc(customerAccommodation.get("programAccommodationDeliveryMode").get("deliveryModeType").get("description")),
				criteriaBuilder.asc(customerAccommodation.get("expiryDate")));

		TypedQuery<CustomerAccommodationTestImpl> typedQuery = entityManager.createQuery(creteriaQuery);
		typedQuery.setParameter("CUSTOMER_ID", customerId);
		typedQuery.setParameter("PROGRAM_CODE", programCode);

		if(testId!=null &&  testId> 0){
			typedQuery.setParameter("TEST_ID", testId);
		}

		if(StringUtils.isNotEmpty(deliveryModeCode)){
			typedQuery.setParameter("DELIVERY_MODE_CODE", deliveryModeCode);
		}

		if(testDate!=null){
			typedQuery.setParameter("EXPIRY_DATE", testDate);
		}else{

			if(accommodationStatus!=null){
				typedQuery.setParameter("EXPIRY_DATE", DateHandler.getCurrentDate(),TemporalType.DATE);
			}
		}

		return 	typedQuery.getResultList();

	}

    @Override
    @SuppressWarnings("unchecked")
    public List<CustomerAccommodationTest> getAllActiveAccommodationsByDeliveryMode(
            Long customerId, String programCode, Long testId,
            String deliveryModeCode, Date expirationDate) {
        logger.debug("getAllActiveAccommodationsByDeliveryMode - customer id {} for test {} ",customerId,testId);
        Query query=entityManager.createNamedQuery("CustomerAccommodations.getActiveAccmodationsForTestByDeliveryMode");

        query.setParameter("customerId", customerId);
        query.setParameter("testId", testId);
        query.setParameter("programCode", programCode);
        query.setParameter("deliveryModeCode", deliveryModeCode);
        query.setParameter("expirationDate", expirationDate,TemporalType.DATE);

        return (List<CustomerAccommodationTest>)query.getResultList();

    }

	@Override
	public CustomerAccommodationTest getCustomerProgramAccommodationTest(
			Long customerId, String programCode, Long testId,
			String accommodationTypeCode,String deliveryModeCode) {
		logger.debug("getCustomerProgramAccommodationTest - customer id {} for test {} ",customerId,testId);

        Query query=entityManager.createNamedQuery("CustomerAccommodations.getCustomerAccmodation");
        query.setParameter("customerId", customerId);
        query.setParameter("testId", testId);
        query.setParameter("programCode", programCode);
        query.setParameter("deliveryModeCode", deliveryModeCode);
        query.setParameter("accommodationTypeCode", accommodationTypeCode);

        return (CustomerAccommodationTest)query.getSingleResult();

	}



}
