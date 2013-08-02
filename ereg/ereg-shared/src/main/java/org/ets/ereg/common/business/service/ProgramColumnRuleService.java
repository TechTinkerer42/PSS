package org.ets.ereg.common.business.service;

import java.util.List;

import org.ets.ereg.common.business.vo.ProgramColumnRuleVO;

public interface ProgramColumnRuleService {
		
	public <T> List<ProgramColumnRuleVO> getProgramRules(T clazz,
			String programCode,List<String> propertyNames);

}
