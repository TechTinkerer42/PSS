package org.ets.ereg.common.business.rule.querycomposer;

import java.text.SimpleDateFormat;
import java.util.List;

import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetakeTestRuleComposer<RequestVo> {
	
	private static Logger logger = LoggerFactory.getLogger(RetakeTestRuleComposer.class);
	
	private static final String COMMA_DELIMITER = ",";

	public RulesComposedQuery compose(List<AbstractDatabaseQueryRule> rules, RequestVo requestVo) {
		RulesComposedQuery composedQuery = new RulesComposedQuery();
		setMainQueryParam(composedQuery, requestVo);

		for (Object rule : rules) {
			AbstractDatabaseQueryRule<RequestVo> abstractDatabaseQueryRule = (AbstractDatabaseQueryRule<RequestVo>) rule;
			String ruleSql = abstractDatabaseQueryRule.getRuleSql(requestVo);
			composedQuery.setSelectQuery(composedQuery.getSelectQuery() + COMMA_DELIMITER + abstractDatabaseQueryRule.geSelectQuery());
			composedQuery.setRuleQuery(composedQuery.getRuleQuery()+ ruleSql);
			composedQuery.setParameterNamesAll(abstractDatabaseQueryRule.getParameterNames());
			composedQuery.setParameterValuesAll(abstractDatabaseQueryRule.getParameterValues());

		}

		logger.debug("Composed Query: {}",composedQuery.getQuery());

		return composedQuery;
	}

	private void setMainQueryParam(RulesComposedQuery composedQuery, RequestVo requestVo) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		RetakeRulesRequestVo retakeRulesRequestVo = (RetakeRulesRequestVo) requestVo;
		String startDate = formatter.format((java.util.Date) retakeRulesRequestVo.getRuleStartDate());
		String endDate = formatter.format((java.util.Date) retakeRulesRequestVo.getRuleEndDate());
		composedQuery.setParameterNameVlue("STARTDATE1", startDate);
		composedQuery.setParameterNameVlue("STARTDATE2", startDate);
		composedQuery.setParameterNameVlue("ENDDATE", endDate);
	}

}
