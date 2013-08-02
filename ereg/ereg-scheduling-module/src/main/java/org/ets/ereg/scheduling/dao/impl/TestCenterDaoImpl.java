package org.ets.ereg.scheduling.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.ets.ereg.common.business.util.ProgramContextHolder;
import org.ets.ereg.common.helpers.ThreadLocalFacade;
import org.ets.ereg.domain.interfaces.model.common.ETSAddress;
import org.ets.ereg.domain.interfaces.model.common.ETSPhone;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenter;
import org.ets.ereg.domain.interfaces.model.scheduling.TestCenterDeliveryMode;
import org.ets.ereg.domain.organization.OrganizationAddressImpl;
import org.ets.ereg.domain.organization.OrganizationPhoneImpl;
import org.ets.ereg.domain.scheduling.TestCenterImpl;
import org.ets.ereg.domain.scheduling.TestCenterProgramImpl;
import org.ets.ereg.scheduling.dao.TestCenterDao;
import org.ets.ereg.scheduling.util.GeoUtil;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.ets.ereg.scheduling.vo.TestCenterSearchResultEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("testCenterDao")
public class TestCenterDaoImpl implements TestCenterDao {
	
	private static Logger logger = LoggerFactory.getLogger(TestCenterDaoImpl.class);
	
	private static final String ID = "id";
	private static final String ORGANIZATION = "organization";
	private static final String ADDRESS = "address";
	private static final String LATITUDE_DEGREE = "latitudeDegree";
	private static final String LONGITUDE_DEGREE = "longitudeDegree";
	private static final String TEST_CENTER = "testCenter";	
	private static final String DELIVERY_MODE = "deliveryMode";
	private static final String PROGRAM = "program";
	private static final String GREAT_CIRCLE_DISTANCE = "greatCircleDistance";
	private static final String NAME = "name";
	private static final String ACTIVE_FLAG = "isActive";
	private static final String AGENCY_ID = "agencyId";
	private static final String PROGRAM_CODE = "programCode";
	
	@PersistenceContext(unitName="blPU")
    private EntityManager em;
	
	private TypedQuery<Tuple> buildSearchQuery(TestCenterSearchCriteria searchCriteria, boolean isCountQuery){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		
		Root<TestCenterImpl> rootTestCenter = criteriaQuery.from(TestCenterImpl.class);
		Root<OrganizationAddressImpl> rootOrgAddress = criteriaQuery.from(OrganizationAddressImpl.class);
		Root<OrganizationPhoneImpl> rootOrgPhone = criteriaQuery.from(OrganizationPhoneImpl.class);
		Root<TestCenterProgramImpl> rootTestCenterProgram = criteriaQuery.from(TestCenterProgramImpl.class);
		Join testCenterDeliveryModes = rootTestCenter.join("testCenterDeliveryModes", JoinType.LEFT);
		
		Expression<Double> greatCircleDistance = criteriaBuilder.function(	TestCenterDaoImpl.GREAT_CIRCLE_DISTANCE, 
																				Double.class,
																				criteriaBuilder.literal(searchCriteria.getLatitudeDegree()),
																				criteriaBuilder.literal(searchCriteria.getLongitudeDegree()),
																				rootOrgAddress.get(TestCenterDaoImpl.ADDRESS).get(TestCenterDaoImpl.LATITUDE_DEGREE),
																				rootOrgAddress.get(TestCenterDaoImpl.ADDRESS).get(TestCenterDaoImpl.LONGITUDE_DEGREE)
																			);
		if(isCountQuery){
			criteriaQuery.multiselect(criteriaBuilder.countDistinct(rootTestCenter));
		}
		else{
			criteriaQuery.multiselect(
					rootTestCenter,
					rootOrgAddress.get(TestCenterDaoImpl.ADDRESS).get(TestCenterDaoImpl.LATITUDE_DEGREE).alias(TestCenterDaoImpl.LATITUDE_DEGREE),
	    			rootOrgAddress.get(TestCenterDaoImpl.ADDRESS).get(TestCenterDaoImpl.LONGITUDE_DEGREE).alias(TestCenterDaoImpl.LONGITUDE_DEGREE)
	    	);
			criteriaQuery.distinct(true);
			criteriaQuery.orderBy(criteriaBuilder.asc(greatCircleDistance));
		}
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		criteria.add(criteriaBuilder.equal(rootTestCenter.get(TestCenterDaoImpl.ID), rootOrgAddress.get(TestCenterDaoImpl.ORGANIZATION).get(TestCenterDaoImpl.ID)));
		criteria.add(criteriaBuilder.equal(rootTestCenter.get(TestCenterDaoImpl.ID), rootOrgPhone.get(TestCenterDaoImpl.ORGANIZATION).get(TestCenterDaoImpl.ID)));
		criteria.add(criteriaBuilder.equal(rootTestCenter.get(TestCenterDaoImpl.ID), rootTestCenterProgram.get(TestCenterDaoImpl.TEST_CENTER).get(TestCenterDaoImpl.ID)));
		
		
		
		if( (null != searchCriteria.getTestCenterName()) && (searchCriteria.getTestCenterName().trim().length() > 0)){
			criteria.add(criteriaBuilder.like(criteriaBuilder.upper(rootTestCenter.get(TestCenterDaoImpl.NAME).as(String.class)),  "%"+searchCriteria.getTestCenterName().toUpperCase()+"%"));
		}
		
		if(null != searchCriteria.getDistanceMile()){
			criteria.add(criteriaBuilder.le(greatCircleDistance, searchCriteria.getDistanceMile()));
		}

		
		if(null != searchCriteria.getTestTypes() && searchCriteria.getTestTypes().size() > 0){
			criteria.add(criteriaBuilder.or(
					testCenterDeliveryModes.get(TestCenterDaoImpl.DELIVERY_MODE).in(searchCriteria.getTestTypes()),
					testCenterDeliveryModes.get(TestCenterDaoImpl.DELIVERY_MODE).isNull()
			));
		}
		
		criteria.add(criteriaBuilder.equal(rootTestCenterProgram.get(TestCenterDaoImpl.PROGRAM), searchCriteria.getProgramType()));		

		//added code to pull only active test centers on 05/07/2013- Gopal
		criteria.add(criteriaBuilder.equal(rootTestCenter.get(TestCenterDaoImpl.ACTIVE_FLAG),true));
		
		criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));

		
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);

		return typedQuery;
	}
	@Override
	public List<TestCenter> getAssociatedTestCentersForAdmin(Long adminId){
		TypedQuery<TestCenter> query = em.createNamedQuery("TestCenter.findTestCentersByAdminId", TestCenter.class);
		query.setParameter("adminId", adminId);
		List<TestCenter> result =  query.getResultList();
		for(TestCenter tc:result){
			logger.debug("Test center name {}",tc.getName());
		}
		return result;
		
	}
	
	@Override
	public List<TestCenterSearchResultEntry> getTestCenterSearchResult(
			TestCenterSearchCriteria searchCriteria, int offset, int count) {
		TypedQuery<Tuple> typedQuery = buildSearchQuery(searchCriteria, false);		
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(count);
		List<Tuple> testCenterTuples = typedQuery.getResultList();
		List<TestCenterSearchResultEntry> testCenterSearchResult = new ArrayList<TestCenterSearchResultEntry>();
		for(Tuple t: testCenterTuples){
			TestCenter testCenter = (TestCenter)t.get(0);
			TestCenterSearchResultEntry entry = new TestCenterSearchResultEntry();
			
			entry.setId(testCenter.getId());
			entry.setName(testCenter.getName());
			ETSAddress address = testCenter.getOrganizationAddresses().iterator().next().getAddress();
			entry.setAddressLine1(address.getAddressLine1());
			entry.setAddressLine2(address.getAddressLine2());
			entry.setAddressLine3(address.getAddressLine3());
			entry.setCity(address.getCity());
			entry.setStateAbbreviation(address.getState().getAbbreviation());
			entry.setStateName(address.getState().getName());
			entry.setCountryAbbreviation(address.getCountry().getAbbreviation());
			entry.setCountryName(address.getCountry().getName());
			entry.setPostalCode(address.getPostalCode());
			entry.setLatitudeDegree(address.getLatitudeDegree().doubleValue());
			entry.setLongitudeDegree(address.getLongitudeDegree().doubleValue());
			entry.setDistanceMile(GeoUtil.greatCircleDistance(searchCriteria.getLatitudeDegree(), searchCriteria.getLongitudeDegree(), address.getLatitudeDegree().doubleValue(), address.getLongitudeDegree().doubleValue()));
			ETSPhone phone = testCenter.getOrganizationPhones().iterator().next().getPhone();
			entry.setPhoneCountryCode(phone.getCountry().getIsdCode());
			entry.setPhoneNumber(phone.getPhoneNumber());
			entry.setPhoneExtension(phone.getPhoneExtension());
			List<String> deliveryModes = new ArrayList<String>();
			for(TestCenterDeliveryMode deliveryMode:testCenter.getTestCenterDeliveryModes()){
				deliveryModes.add(deliveryMode.getDeliveryMode().getDescription());
			}
			entry.setDeliveryModes(deliveryModes);
			entry.setSchedulingType(testCenter.getSchedulingType(ThreadLocalFacade.getProgramCode()).getCode());
			testCenterSearchResult.add(entry);
		}
		return testCenterSearchResult;
	}

	@Override
	public Long getTestCenterSearchCount(TestCenterSearchCriteria searchCriteria) {
		TypedQuery<Tuple> typedQuery = buildSearchQuery(searchCriteria, true);
		return (Long)typedQuery.getSingleResult().get(0);
	}

	@Override
	public TestCenter readTestCenterById(Long id) {
		TestCenter testCenter;
		TypedQuery<TestCenter> query = em.createNamedQuery("TestCenter.findById", TestCenter.class);
		query.setParameter(TestCenterDaoImpl.ID, id);
		try{
			List<TestCenter> result =  query.getResultList();
			if(result.isEmpty()){
				testCenter = null;
			}
			else{
				testCenter = result.get(0);
			}
		} catch(NoResultException nre) {
			testCenter = null;
		}
		return testCenter;
	}

	@Override
	public List<TestCenter> getTestCentersByAgency(Long agencyId) {
		List<TestCenter> testCenters;
		TypedQuery<TestCenter> query = em.createNamedQuery("TestCenter.findTestCentersByAgency", TestCenter.class);
		query.setParameter(AGENCY_ID, agencyId);
		query.setParameter(PROGRAM_CODE, ProgramContextHolder.getProgramCode());
		query.setParameter(ACTIVE_FLAG, true);
		try{
			List<TestCenter> result =  query.getResultList();
			if(result.isEmpty()){
				testCenters = null;
			}
			else{
				testCenters = result;
			}
		} catch(NoResultException nre) {
			testCenters = null;
		}
		return testCenters;
	}
}
