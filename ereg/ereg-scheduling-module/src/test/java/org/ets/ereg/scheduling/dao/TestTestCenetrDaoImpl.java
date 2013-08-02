package org.ets.ereg.scheduling.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.domain.common.DeliveryModeTypeImpl;
import org.ets.ereg.domain.common.ProgramTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.DeliveryModeType;
import org.ets.ereg.domain.interfaces.model.common.ProgramType;
import org.ets.ereg.scheduling.vo.TestCenterSearchCriteria;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestTestCenetrDaoImpl {
	@Resource(name="testCenterDao")
	private TestCenterDao testCenterDao;
	
	@Test
	public void testSearchTestCenterByTestType(){
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();
		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		searchCriteria.setProgramType(programType);
		searchCriteria.setLatitudeDegree(40.6298702);
		searchCriteria.setLongitudeDegree(-74.3033176);
		
		List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
		DeliveryModeType testType;
		//only CBT
		testType = new DeliveryModeTypeImpl();
		testType.setCode("CBT");
		testTypes.add(testType);
		searchCriteria.setTestTypes(testTypes);
		Assert.assertEquals(6, testCenterDao.getTestCenterSearchResult(searchCriteria, 0, 10).size());

		//either PBT or CBT
		testType = new DeliveryModeTypeImpl();
		testType.setCode("PBT");
		testTypes.add(testType);
		Assert.assertEquals(10, testCenterDao.getTestCenterSearchResult(searchCriteria, 0, 10).size());
		
		//only PBT
		testTypes.clear();
		testTypes.add(testType);
		Assert.assertEquals(8, testCenterDao.getTestCenterSearchResult(searchCriteria, 0, 10).size());
	}
	
	@Test
	public void testSearchTestCenterByAdminId(){
		Assert.assertEquals(3, testCenterDao.getAssociatedTestCentersForAdmin(112L).size());
	}
	
	
	@Test
	public void testSearchTestCenterPagination(){
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();
		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		searchCriteria.setProgramType(programType);
		searchCriteria.setLatitudeDegree(40.6298702);
		searchCriteria.setLongitudeDegree(-74.3033176);
		
		List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
		DeliveryModeType testType;
		//only CBT
		testType = new DeliveryModeTypeImpl();
		testType.setCode("CBT");
		testTypes.add(testType);
		testType = new DeliveryModeTypeImpl();
		testType.setCode("PBT");
		testTypes.add(testType);
		searchCriteria.setTestTypes(testTypes);
		Assert.assertEquals(5, testCenterDao.getTestCenterSearchResult(searchCriteria, 5, 5).size());
	}
	
	@Test
	public void testSearchTestCenterByName(){
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();
		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		searchCriteria.setProgramType(programType);
		searchCriteria.setLatitudeDegree(40.6298702);
		searchCriteria.setLongitudeDegree(-74.3033176);
		
		List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
		DeliveryModeType testType = new DeliveryModeTypeImpl();
		testType.setCode("PBT");
		testTypes.add(testType);
		searchCriteria.setTestTypes(testTypes);

		searchCriteria.setTestCenterName("Pro-metric");
		
		Assert.assertEquals(1, testCenterDao.getTestCenterSearchResult(searchCriteria, 0, 5).size());
	}
	
	@Test
	public void testSearchTestCenterByDistance(){
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();
		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		searchCriteria.setProgramType(programType);
		searchCriteria.setLatitudeDegree(40.6298702);
		searchCriteria.setLongitudeDegree(-74.3033176);
		
		List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
		DeliveryModeType testType;
		testType = new DeliveryModeTypeImpl();
		testType.setCode("CBT");
		testTypes.add(testType);
		searchCriteria.setTestTypes(testTypes);
		searchCriteria.setDistanceMile(50.0);
		Assert.assertEquals(5, testCenterDao.getTestCenterSearchResult(searchCriteria, 0, 10).size());
	}
	
	@Test
	public void testGetTestCenterSearchCountByDistance(){
		TestCenterSearchCriteria searchCriteria = new TestCenterSearchCriteria();
		ProgramType programType = new ProgramTypeImpl();
		programType.setCode("HSE");
		searchCriteria.setProgramType(programType);
		searchCriteria.setLatitudeDegree(40.6298702);
		searchCriteria.setLongitudeDegree(-74.3033176);
		
		List<DeliveryModeType> testTypes = new ArrayList<DeliveryModeType>();
		DeliveryModeType testType;
		testType = new DeliveryModeTypeImpl();
		testType.setCode("CBT");
		testTypes.add(testType);
		searchCriteria.setTestTypes(testTypes);
		searchCriteria.setDistanceMile(50.0);
		Assert.assertEquals(5, testCenterDao.getTestCenterSearchCount(searchCriteria).intValue());
	}
	
	@Test
	public void testReadTestCenterById(){
		Assert.assertEquals((Long)1l, testCenterDao.readTestCenterById(1l).getId());
		Assert.assertNull(testCenterDao.readTestCenterById(1000l));
	}
}
