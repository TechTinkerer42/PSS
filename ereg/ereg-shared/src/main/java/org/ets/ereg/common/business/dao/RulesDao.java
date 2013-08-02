package org.ets.ereg.common.business.dao;

import java.util.List;

import org.ets.ereg.common.business.vo.RulesResponseVo;

public interface RulesDao {

	public List<RulesResponseVo> executeRules(String rulesQuery,
			List<String> parameterNames, List<Object> parameterValues);

}
