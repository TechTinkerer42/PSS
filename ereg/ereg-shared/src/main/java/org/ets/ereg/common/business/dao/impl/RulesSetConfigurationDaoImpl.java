package org.ets.ereg.common.business.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.common.business.dao.NativeQuery;
import org.ets.ereg.common.business.dao.RulesSetConfigurationDao;
import org.ets.ereg.common.business.query.binder.NameBasedParameterBinder;
import org.ets.ereg.common.business.query.binder.ParameterBinder;
import org.ets.ereg.common.business.vo.CodeValuePairVo;
import org.ets.ereg.common.business.vo.ConfigurationsVo;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.enums.RoleEnumGroup;
import org.ets.ereg.domain.hierarchy.EregHierarchyImpl;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationParameterValue;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetId;
import org.ets.ereg.domain.rule.RuleSetImpl;
import org.springframework.stereotype.Repository;

@Repository("rulesSetConfigurationDao")
public class RulesSetConfigurationDaoImpl extends AbstractDaoImpl<RuleSetConfiguration> implements RulesSetConfigurationDao {

	
    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    

	
	@Override
	public Class<RuleSetConfiguration> getEntityClass() {
		return RuleSetConfiguration.class;
	}
	
	private Set<RuleSetConfigurationParameterValue> addRuleSetConfigurationParameterValues(RuleSetConfigurationDetail ruleSetConfgDtl,ConfigurationsVo configVo){
		
		Set<RuleSetConfigurationParameterValue> ruleSetConfigParmVals= new HashSet<RuleSetConfigurationParameterValue>();
		long paramSeqNo=1;
		String[]rules=configVo.getRules();
		String[]paramNames=configVo.getParamNames();
		String[]paramValues=configVo.getParamValues();
		Set<RuleAllowableParameter> ruleAllowParams=ruleSetConfgDtl.getRuleSet().getRule().getRuleAllowableParameters();
		for(RuleAllowableParameter ruleAllowParam:ruleAllowParams){
			RuleSetConfigurationParameterValue rsConfigParamVal= createRuleSetConfigurationParameterValue();
			rsConfigParamVal.setRuleSetConfigurationDetail(ruleSetConfgDtl);
			//rsConfigParamVal.setRuleSetConfigurationDetail(ruleSetConfgDtl);
			rsConfigParamVal.setRuleAllowableParameter(ruleAllowParam);
			rsConfigParamVal.setParameterValueSequenceNumber(paramSeqNo);	
			
			for(String rule:rules){
				int index=0;
				for(String paramName:paramNames){
					String ruleCd=rsConfigParamVal.getRuleSetConfigurationDetail().getRuleSet().getRule().getRuleCode();
					String paramCd=rsConfigParamVal.getRuleAllowableParameter().getRuleParameterCd();
					if(rule.equals(ruleCd) && paramCd.equals(paramName)){
						rsConfigParamVal.setRuleParameterValueText(paramValues[index]);
					}
					index++;
				}
			}
			ruleSetConfigParmVals.add(rsConfigParamVal);
		}
		return ruleSetConfigParmVals;
	}
	
	class RolePredicate implements Predicate{
		private String adminRoleCd;
		RolePredicate(String adminRoleCd){
			this.adminRoleCd=adminRoleCd;
		}
		@Override
		public boolean evaluate(Object object) {
			RoleEnum roleEnum=(RoleEnum)object;
			if(adminRoleCd.equals(roleEnum.getCode())){
					return true;
			}	
			return false;
		}
		
	}
	
	public List<RoleEnum>  getEligibleRoles(Long programHierarchyId,String ruleSetTypeCode){
		
		TypedQuery<AdminRole> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getEregHierarchyRoles", AdminRole.class);
		query.setParameter("ruleSetTypeCode", ruleSetTypeCode);
		query.setParameter("eregHierarchyIdentifier", programHierarchyId);
		List<AdminRole> results =  query.getResultList();
		List<RoleEnum> adminEnumSet=null;
		for(AdminRole result:results){
			RolePredicate rolePred= new RolePredicate(result.getName());
			EnumSet<RoleEnum> enumSet=RoleEnumGroup.ADMIN_ROLES_GROUP.getRolesInGroup();
			if(adminEnumSet!=null){
				enumSet=EnumSet.copyOf(adminEnumSet);
			}
			adminEnumSet= (List<RoleEnum>) CollectionUtils.selectRejected(enumSet, rolePred);
		}
		if(results.isEmpty()){
			RolePredicate rolePred= new RolePredicate("");
			adminEnumSet= (List<RoleEnum>) CollectionUtils.selectRejected(RoleEnumGroup.ADMIN_ROLES_GROUP.getRolesInGroup(), rolePred);
		}
		return adminEnumSet;
	}

	
	private RuleSetConfigurationDetail addRuleSetConfigurationDetail(String ruleCd,String ruleSetTypeCd,ConfigurationsVo configVo,RuleSetConfiguration rsConfig){
		RuleSetConfigurationDetail rsConfigDetail=createRuleSetConfigurationDetail();
		RuleSetId ruleSetId= new RuleSetId();
		ruleSetId.setRuleCode(ruleCd);
		ruleSetId.setRuleSetTypeCode(ruleSetTypeCd);
		RuleSet ruleSet= getEntityManager().find(RuleSetImpl.class, ruleSetId);
		rsConfigDetail.setRuleSet(ruleSet);
		rsConfigDetail.setRuleSetConfiguration(rsConfig);
	
		Set<RuleSetConfigurationParameterValue> ruleSetParamVal=addRuleSetConfigurationParameterValues(rsConfigDetail,configVo);
		
		rsConfigDetail.setRuleSetConfigurationParameterValues(ruleSetParamVal);
		
		return rsConfigDetail;
	}
	
	public RuleSetConfiguration addRuleSetConfiguration(ConfigurationsVo configVo){
		RuleSetConfiguration rsConfig=createRuleSetConfiguration();
		String[]ruleCodes=configVo.getRules();
		for(String ruleCd:ruleCodes){
			RuleSetConfigurationDetail rsConfigDetail= addRuleSetConfigurationDetail(ruleCd,configVo.getRuleSetType(), configVo,rsConfig);
			rsConfig.getRuleSetConfigurationDetails().add(rsConfigDetail);
		}
		
		return rsConfig;
	}

	
	@Override
	public List<EregHierarchy> getChildren(Long eregHierarchyId){
		TypedQuery<EregHierarchy> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getChildren", EregHierarchy.class);
		query.setParameter("parentHierarchy", eregHierarchyId);
		List<EregHierarchy> results =  query.getResultList();
		return results;
	}
	
	@Override
	public String getConfigurationValue(Long eregHierarchyId,String ruleSetTypeCd, String ruleCd,Long adminRoleId){
		Query query=getEntityManager().createNativeQuery("SELECT GET_RUL_PARM_VAL(?1,?2,?3,?4,?5) FROM DUAL");
		query.setParameter(1, eregHierarchyId);
		query.setParameter(2, ruleSetTypeCd);
		query.setParameter(3, ruleCd);
		query.setParameter(4, ruleCd);
		query.setParameter(5, adminRoleId);
		List vals=query.getResultList();
		if(vals!=null && !vals.isEmpty()){
			String val=(String)vals.get(0);
			return val;
		}
	    
		return null;
		
	}
	@Override
	public EregHierarchy getEregHierarchy(Long hierarchy){
		return getEntityManager().find(EregHierarchyImpl.class, hierarchy);
	}
	@Override
	public List<RuleSet> getRuleSetByCode(String ruleSetCd){
		
		
		TypedQuery<RuleSet> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getRuleSetByCode", RuleSet.class);
		query.setParameter("ruleSetCd", ruleSetCd);
		List<RuleSet> results =  query.getResultList();
		if(!results.isEmpty()){
			return results;
		
		}
		return null;
	}
	
	private CodeValuePairVo populateCodeValuePairVo(String code,String value){
		CodeValuePairVo codeVo= new CodeValuePairVo(code, value);
		return codeVo;
		
	}
	
	@Override
	public List<CodeValuePairVo> getRuleSetConfigurationParameterValue(String ruleCode){
		
		
		
		TypedQuery<RuleSetConfigurationParameterValue> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getRuleSetConfigurationParameterValue", RuleSetConfigurationParameterValue.class);
		query.setParameter("ruleCode", ruleCode);
		List<RuleSetConfigurationParameterValue> results =  query.getResultList();
		List<CodeValuePairVo> list = new ArrayList<CodeValuePairVo>();
		for(RuleSetConfigurationParameterValue result:results){
			CodeValuePairVo codeValuePairVo=populateCodeValuePairVo(result.getRuleAllowableParameter().getRuleParameterCd(),result.getRuleParameterValueText());
			list.add(codeValuePairVo);
		}
		
		
		if(results.isEmpty()){
			TypedQuery<RuleAllowableParameter> qury =getEntityManager().createNamedQuery("RuleSetConfiguration.getRuleAllowableParameter", RuleAllowableParameter.class);
			qury.setParameter("ruleCode", ruleCode);
			List<RuleAllowableParameter> rulAllwPas=qury.getResultList();
			
			for(RuleAllowableParameter rulAllwParam:rulAllwPas){
				CodeValuePairVo codeValuePairVo=populateCodeValuePairVo(rulAllwParam.getRuleParameterCd(), "");
				list.add(codeValuePairVo);
			}
			
			return list;
		
		}
		return list;
	}
	
	@Override
	public EregHierarchyRuleSetConfiguration getDefaultHierarchy(String ruleSetCd){
		TypedQuery<EregHierarchyRuleSetConfiguration> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getEregHierarchyRuleSetConfig", EregHierarchyRuleSetConfiguration.class);
		query.setParameter("ruleSetCd", ruleSetCd);
		List<EregHierarchyRuleSetConfiguration> results =  query.getResultList();
		return results.get(0);
		
	}
	
	
	
	@Override
	public List<EregHierarchy> getEregHierachys(String pgmCode){
		
		String[]paramNames={"hier_nam"};
		List<String> paramValues= new ArrayList<String>();
		paramValues.add(pgmCode);
		ParameterBinder parameterBinder =new NameBasedParameterBinder();
		Object object =executeNativeSQL(NativeQuery.GET_HIERARCHY_TREE,paramNames,paramValues.toArray(),parameterBinder);
		List<EregHierarchy> results =new ArrayList<EregHierarchy>();
		List<BigDecimal> hierarchyIds=(List<BigDecimal>)object;
		for(BigDecimal hierarchyId:hierarchyIds){
			EregHierarchy eregHier=getEntityManager().find(EregHierarchyImpl.class,(long) hierarchyId.longValue());
			results.add(eregHier);
		}
		
		return results;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Long getRuleSetConfigurationIdTobeApplied(Long programHierarchyId, String ruleSetTypeCode,Long adminRoleId) {
		
		Query query=getEntityManager().createNativeQuery("SELECT Get_ActiveRulset_ConfigId(?1,?2,?3) FROM DUAL");
		query.setParameter(1,programHierarchyId);
		query.setParameter(2, ruleSetTypeCode);
		query.setParameter(3, adminRoleId);
		List vals=query.getResultList(); 
		if(vals!=null && !vals.isEmpty()){
			BigDecimal bg=(BigDecimal)vals.get(0); 
			if(bg==null)
				return null;
			return bg.longValue();
		}
	    
		return null;
	}
	
	@Override
	public AdminRole getAdminRole(String adminRoleCd){
		TypedQuery<AdminRole> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getAdminRoleId", AdminRole.class);
		query.setParameter("adminRoleCd", adminRoleCd);
		List<AdminRole> results =  query.getResultList();
		if(!results.isEmpty()){
			AdminRole adminId=(AdminRole)results.get(0);
			return adminId;
		}
		return null;
	}
	@Override
	public RuleSet getRuleSet(String ruleCd,String ruleSetTypeCd){
		RuleSetId ruleSetId= new RuleSetId();
		ruleSetId.setRuleCode(ruleCd);
		ruleSetId.setRuleSetTypeCode(ruleSetTypeCd);
		return getEntityManager().find(RuleSetImpl.class, ruleSetId);
	}
	
	
	@Override 
	public EregHierarchyRuleSetConfiguration createEregHierarchyRuleSetConfiguration(){
		EregHierarchyRuleSetConfiguration eregHierRSConfig =  (EregHierarchyRuleSetConfiguration) entityConfiguration.createEntityInstance(EregHierarchyRuleSetConfiguration.class.getName());
		return eregHierRSConfig;
	}
	

	
	@Override
	public RuleSetConfiguration createRuleSetConfiguration(){
		RuleSetConfiguration rsConfig =  (RuleSetConfiguration) entityConfiguration.createEntityInstance(RuleSetConfiguration.class.getName());
		return rsConfig;
	}
	@Override
	public RuleSetConfigurationDetail createRuleSetConfigurationDetail(){
		RuleSetConfigurationDetail rsConfigDetail =  (RuleSetConfigurationDetail) entityConfiguration.createEntityInstance(RuleSetConfigurationDetail.class.getName());	
		return rsConfigDetail;
		
	}
	
	@Override
	public RuleSetConfigurationParameterValue createRuleSetConfigurationParameterValue(){
		RuleSetConfigurationParameterValue rsConfigParamVal =  (RuleSetConfigurationParameterValue) entityConfiguration.createEntityInstance(RuleSetConfigurationParameterValue.class.getName());	
		return rsConfigParamVal;	
	}
	

	
	
	@Override
	public RuleSetConfiguration save(RuleSetConfiguration rsConfig) {
		return getEntityManager().merge(rsConfig);
		
	}
	@Override
	public EregHierarchyRuleSetConfiguration saveHierarchyRuleSetConfiguration(EregHierarchyRuleSetConfiguration eregHierRSConfig){
		return getEntityManager().merge(eregHierRSConfig);
		
	}
	
	
	@Override
	public List<RuleSetConfigurationParameterValue> getConfigurations(String ruleSetTypeCd) {
		TypedQuery<RuleSetConfigurationDetail> query = getEntityManager().createNamedQuery("RuleSetConfiguration.getConfigurationDetails", RuleSetConfigurationDetail.class);
		query.setParameter("customerId", ruleSetTypeCd);
		List<RuleSetConfigurationDetail> results =  query.getResultList();
		
		List<RuleSetConfigurationParameterValue> values= new ArrayList<RuleSetConfigurationParameterValue>();
		for(RuleSetConfigurationDetail result:results){
			String ruleSetCode=result.getRuleSet().getRuleSetType().getCode();
			if(ruleSetTypeCd.equals(ruleSetCode))
				values.addAll(result.getRuleSetConfigurationParameterValues());
			
			
		}
		
		return values;
	}

}
