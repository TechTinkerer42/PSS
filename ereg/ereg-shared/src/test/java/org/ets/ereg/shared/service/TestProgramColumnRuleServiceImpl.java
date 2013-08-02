package org.ets.ereg.shared.service;

import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.dao.ProgramColumnRuleDao;
import org.ets.ereg.common.business.service.impl.ProgramColumnRuleServiceImpl;
import org.ets.ereg.common.business.vo.ProgramColumnRuleVO;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRuleType;
import org.ets.ereg.domain.interfaces.model.program.id.ProgramColumnRuleId;
import org.ets.ereg.domain.model.program.ProgramColumnRuleImpl;
import org.ets.ereg.domain.model.program.ProgramColumnRuleTypeImpl;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestProgramColumnRuleServiceImpl{

	private static Mockery mockingContext = new Mockery();
	private static ProgramColumnRuleDao mockedProgramColumnRuleDao;
	private static ProgramColumnRuleServiceImpl programColumnRuleServiceImpl = new ProgramColumnRuleServiceImpl();
	
	@BeforeClass
	public static void setup() {
		setupMockObjects();
		programColumnRuleServiceImpl.setProgramColumnRuleDao(mockedProgramColumnRuleDao);
	}
	
	private static void setupMockObjects() {
		mockedProgramColumnRuleDao = mockingContext.mock(ProgramColumnRuleDao.class);
		
	}
	
	@Test
	public void testGetProgramRules(){
		
		final ETSCustomerImpl etsCustomer = new ETSCustomerImpl();
			
		final List<String> propertyNames = new ArrayList<String>();
		propertyNames.add("Ethnicity");
		propertyNames.add("Race");
		
		
		
		final List<ProgramColumnRule> pgmRuleList = new ArrayList<ProgramColumnRule>();
		
		ProgramColumnRule pgmRule = new ProgramColumnRuleImpl();
		ProgramColumnRuleId id = new ProgramColumnRuleId();
		id.setEntityName("org.ets.ereg.domain.profile.ETSCustomerImpl");
		id.setProgramCode("GRE");
		id.setPropertyName("sourceId");
		
		
		pgmRule.setId(id);
		pgmRule.setRegexTxt("(sourceId=='NET' && prmrySpkngLang.code =='ENG')");
		
		
		ProgramColumnRuleType type = new ProgramColumnRuleTypeImpl();
		type.setCode("DISP");
		type.setDescription("display");
		
		pgmRule.setPgmClmnRuleTypCde(type);
		
		pgmRuleList.add(pgmRule);
		
		ProgramColumnRule pgmRule1 = new ProgramColumnRuleImpl();
		ProgramColumnRuleId id1 = new ProgramColumnRuleId();
		id1.setEntityName("org.ets.ereg.domain.profile.ETSCustomerImpl");
		id1.setProgramCode("GRE");
		id1.setPropertyName("prmrySpkngLang");
		
		
		pgmRule1.setId(id);
		pgmRule1.setRegexTxt("(sourceId=='NET' && prmrySpkngLang.code =='SPAN')");
		
		
		ProgramColumnRuleType type1 = new ProgramColumnRuleTypeImpl();
		type1.setCode("RQVLD");
		type1.setDescription("mandatory");
		
		pgmRule1.setPgmClmnRuleTypCde(type1);
		
		pgmRuleList.add(pgmRule1);
		
		
		
		etsCustomer.setSourceId("NET");
		LanguageType ltype = new LanguageTypeImpl();
		ltype.setCode("SPAN");
		etsCustomer.setPrmrySpkngLang(ltype);
		
		//set expectations
		mockingContext.checking(new Expectations() {
		   {
             one(mockedProgramColumnRuleDao).getProgramRules(etsCustomer, "GRE",propertyNames);
             will(returnValue(pgmRuleList));
           }
		});
				
		List<ProgramColumnRuleVO> pgmClmRulesLst = programColumnRuleServiceImpl.getProgramRules(etsCustomer,
				    "GRE",propertyNames);
				
		Assert.assertNotNull(pgmClmRulesLst);		
		mockingContext.assertIsSatisfied();
	}	
	
}
