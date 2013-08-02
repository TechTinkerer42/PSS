package org.ets.ereg.common.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.RulesDao;
import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.business.rule.querycomposer.RetakeTestRuleComposer;
import org.ets.ereg.common.business.rule.querycomposer.RulesComposedQuery;
import org.ets.ereg.common.business.service.RulesService;
import org.ets.ereg.common.business.service.RulesSetConfigurationService;
import org.ets.ereg.common.business.vo.RetakeRulesRequestVo;
import org.ets.ereg.common.business.vo.RulesResponseVo;
import org.ets.ereg.common.enums.RuleEnum;
import org.ets.ereg.common.enums.RuleSetEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("reTakeTestRulesService")
public class ReTakeTestRulesServiceImpl implements RulesService<RetakeRulesRequestVo> {

	@Resource(name = "rulesDao")
	private RulesDao rulesDao;
	
	@Resource(name = "rulesSetConfigurationService")
	private RulesSetConfigurationService rulesSetConfigurationService;
	private static Logger logger = LoggerFactory.getLogger(ReTakeTestRulesServiceImpl.class);
	

	public List<RulesResponseVo> applyRules(RetakeRulesRequestVo retakeRulesRequestVo) {
/*		// TODO load rules
		List<Object> rules = new ArrayList<Object>();
		try {
			try {
				rules.add(Class.forName(RuleEnum.RETAKE_TEST_WAIT_PERIOD_RULE.getImplementationClassName())
						.newInstance());
				rules.add(Class.forName(RuleEnum.RETAKE_TEST_MAX_TEST_ATTEMPT_RULE.getImplementationClassName())
						.newInstance());
			} catch (InstantiationException e) {
				logger.error("exception occurred",e);
			} catch (IllegalAccessException e) {
				logger.error("exception occurred",e);
				
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<AbstractDatabaseQueryRule> rules=rulesSetConfigurationService.getRules(1L, "RT_RL",0L);
		
		
		

		// rules.add(new LifetimeLimitRule());

		RetakeTestRuleComposer<RetakeRulesRequestVo> retakeTestRuleComposer = new RetakeTestRuleComposer<RetakeRulesRequestVo>();
		RulesComposedQuery rulesComposedQuery = retakeTestRuleComposer.compose(rules, retakeRulesRequestVo);

		return rulesDao.executeRules(rulesComposedQuery.getQuery(), rulesComposedQuery.getParameterNames(),
				rulesComposedQuery.getParameterValues());
	}
}
