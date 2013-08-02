package org.ets.ereg.common.business.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.dao.RulesDao;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.ets.ereg.common.business.vo.RulesResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("rulesDao")
public class RulesDaoImpl extends AbstractDaoImpl<Object> implements RulesDao {
	
	private static Logger logger = LoggerFactory.getLogger(RulesDaoImpl.class);
	
	@Override
	public Class<Object> getEntityClass() {
		return Object.class;
	}

	public List<RulesResponseVo> executeRules(String rulesQuery,
			List<String> parameterNames, List<Object> parameterValues) {
		List<RulesResponseVo> listRulesResponseVo = new ArrayList<RulesResponseVo>();
		ParameterBinder parameterBinder = new NameBasedParameterBinder();
		List<Object[]> listResults = executeNativeSQL(rulesQuery,
				parameterNames, parameterValues, parameterBinder);
		
		for (Object[] record : listResults) {
			RulesResponseVo rulesResponseVo = new RulesResponseVo();
			rulesResponseVo.setRuleAppliedDate((Timestamp) record[0]);
			for (int columnNo = 1; columnNo < record.length; columnNo++) {
				if ("Yes".equals(record[columnNo])) {
					rulesResponseVo.getConflictRuleCode().add(
							record[++columnNo].toString());
				} else {
					++columnNo;
				}
			}
			listRulesResponseVo.add(rulesResponseVo);

		}
		//do we really need this? this is going to put lot of data into log file..
		for (RulesResponseVo rulesResponseVo : listRulesResponseVo) {
			logger.debug("Rule applied date: {} , Conflicting rule code: {}",rulesResponseVo.getRuleAppliedDate(), rulesResponseVo.getConflictRuleCode());
		}
		return listRulesResponseVo;
	}
}
