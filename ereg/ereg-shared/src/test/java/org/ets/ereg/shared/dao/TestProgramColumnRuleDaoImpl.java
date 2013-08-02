package org.ets.ereg.shared.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ProgramColumnRuleDao;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml" })
public class TestProgramColumnRuleDaoImpl {

	
	@Resource(name="programColumnRuleDao")
	private ProgramColumnRuleDao programColumnRuleDao;
	
	@Test
	public void testGetProgramRules(){
		
		ETSCustomerImpl etsCustomer = new ETSCustomerImpl();		
		
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add("Ethnicity");
		propertyNames.add("Race");
		
		List<ProgramColumnRule> pgmRules = programColumnRuleDao.getProgramRules(etsCustomer.getClass(),
				    "GRE",propertyNames);
		
		Assert.assertNotNull(pgmRules);
	}
	
}
