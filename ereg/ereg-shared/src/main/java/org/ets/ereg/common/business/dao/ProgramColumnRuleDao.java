package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;

public interface ProgramColumnRuleDao {
	
	public <T>List<ProgramColumnRule> getProgramRules(T clazz,
			String programCode,List<String> propertyNames);

}
