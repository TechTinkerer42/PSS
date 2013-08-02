package org.ets.ereg.common.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.ets.ereg.common.business.dao.ProgramColumnRuleDao;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRuleType;
import org.ets.ereg.domain.interfaces.model.program.id.ProgramColumnRuleId;
import org.springframework.stereotype.Repository;

@Repository("programColumnRuleDao")
public class ProgramColumnRuleDaoImpl extends AbstractDaoImpl<ProgramColumnRuleType> implements ProgramColumnRuleDao{

		
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<ProgramColumnRule> getProgramRules(T clazz,
			 String programCode,List<String> propertyNames) {
				
		Query query = entityManager.createNamedQuery("ProgramColumnRuleType.getProgramRules");
		
		ProgramColumnRuleId pgmColumnRuleId = null; 
		List<ProgramColumnRuleId> pgmColumnRuleIds=new ArrayList<ProgramColumnRuleId>();
		for(String propertyName:propertyNames){
			pgmColumnRuleId = new ProgramColumnRuleId();
			pgmColumnRuleId.setEntityName(clazz.getClass().getName());
			pgmColumnRuleId.setProgramCode(programCode);
			pgmColumnRuleId.setPropertyName(propertyName);
			pgmColumnRuleIds.add(pgmColumnRuleId);
		}
		query.setParameter("programCoulmnRuleIds", pgmColumnRuleIds);
				
		return (List<ProgramColumnRule>) query.getResultList();
		
	}

	
	@Override
	public Class<? extends ProgramColumnRuleType> getEntityClass() {
				return ProgramColumnRuleType.class;
	}



}
