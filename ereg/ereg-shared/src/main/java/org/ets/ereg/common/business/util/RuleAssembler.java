/*--------------------------------------------------------------------------
 * ©2009 Educational Testing Service. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Sep 2, 2010, 11:34:53 PM
 */

package org.ets.ereg.common.business.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.ets.ereg.common.business.rule.querycomposer.AbstractDatabaseQueryRule;
import org.ets.ereg.common.enums.RuleEnum;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;

/**
 * Assembler to assemble a Rule Instance (one that the distribution engine uses)
 * from a RuleSetConfiguration object retrieved from the database.
 *  
 * @author Mangesh Pardeshi
 * 
 * @version 1.0, Sep 2, 2010, 11:34:53 PM
 * 
 * @since 'ONE' (CRS) Release 1.0
 */
public class RuleAssembler {
	
	/**
	 * Assembles the rules from database (i.e. instances of {@link RuleSetConfigurationDetail} class to instances of
	 * {@link AbstractRule} classes
	 * 
	 * @param ruleSetConfiguration
	 * @return
	 */
	public static List<AbstractDatabaseQueryRule> assemble(RuleSetConfiguration ruleSetConfiguration) {

		
		try {
			List<AbstractDatabaseQueryRule> rules = new ArrayList<AbstractDatabaseQueryRule>();
			
			for (RuleSetConfigurationDetail ruleSetConfigurationDetail : ruleSetConfiguration.getRuleSetConfigurationDetails()) {
				//get the rule class name and and assemble rule only if ruleClassName is not null
				String ruleClassName = RuleEnum.get(ruleSetConfigurationDetail.getRuleSet().getId().getRuleCode()).getImplementationClassName();
				
				if( ruleClassName != null && ! ruleClassName.equalsIgnoreCase("") )
				{
					rules.add(assembleRule(ruleSetConfigurationDetail));
				}
			}
			
			return rules;
		}
		catch (Exception e){
			throw new ERegRuntimeException(e);
		}
	}
	
	private static AbstractDatabaseQueryRule assembleRule(RuleSetConfigurationDetail ruleSetConfigurationDetail) throws Exception {
		String ruleClassName = RuleEnum.get(ruleSetConfigurationDetail.getRuleSet().getId().getRuleCode()).getImplementationClassName();
		AbstractDatabaseQueryRule rule = (AbstractDatabaseQueryRule) Class.forName(ruleClassName).newInstance();
		List<Field> ruleParameters = ReflectionUtils.getInheritedFields(rule.getClass());
		for(Field param : ruleParameters) {
			MappedRuleParameter mappedRuleParameterAnnotation  = param.getAnnotation(MappedRuleParameter.class);
			if(mappedRuleParameterAnnotation != null) {

				if(mappedRuleParameterAnnotation.isMultiValued())
					ReflectionUtils.setFieldValueByReflection(rule, param.getName(), getAllRuleParameterValues(mappedRuleParameterAnnotation.dbCode().toString(), ruleSetConfigurationDetail));
				else 
					ReflectionUtils.setFieldValueByReflection(rule, param.getName(), getUniqueRuleParameterValue(mappedRuleParameterAnnotation.dbCode().toString(), ruleSetConfigurationDetail));
			}
		}
		return rule;
	}

	private static List<Object> getAllRuleParameterValues(String parameterCode, RuleSetConfigurationDetail ruleDetail) {
		
		List<Object> ruleParamList = new ArrayList<Object>();
		Object ruleParam = null;
		
		List<RuleSetConfigurationParameterValue> rscpLst = new ArrayList<RuleSetConfigurationParameterValue>();
		rscpLst.addAll(ruleDetail.getRuleSetConfigurationParameterValues());
		//Collections.sort(rscpLst,new RuleSetConfigurationParameterValueComparator());
		for(RuleSetConfigurationParameterValue p : rscpLst) {
			ruleParam = null;
			if(parameterCode.equals(p.getRuleAllowableParameter().getRuleParameterCd())) {
				ruleParam = new Object();				
				ruleParam = p.getRuleParameterValueText();
				ruleParamList.add(ruleParam);
			}
			
		}
		
		return ruleParamList.isEmpty() ? null : ruleParamList;
	}

	private static String getUniqueRuleParameterValue(String parameterCode, RuleSetConfigurationDetail ruleDetail) {
		
		for(RuleSetConfigurationParameterValue p : ruleDetail.getRuleSetConfigurationParameterValues()) {
			
			if(parameterCode.equals(p.getRuleAllowableParameter().getRuleParameterCd())) {
				return p.getRuleParameterValueText();
			}
		}
		return null;
	}
}
