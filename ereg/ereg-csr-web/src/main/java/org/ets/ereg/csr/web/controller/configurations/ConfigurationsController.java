package org.ets.ereg.csr.web.controller.configurations;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.business.service.RulesSetConfigurationService;
import org.ets.ereg.common.business.vo.ConfigurationsVo;
import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.common.enums.RoleEnumGroup;
import org.ets.ereg.common.enums.RuleSetEnum;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.csr.web.form.ConfigurationsForm;
import org.ets.ereg.csr.web.util.CSRJspMappingConstants;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.Rule;
import org.ets.ereg.domain.interfaces.model.rule.RuleAllowableParameter;
import org.ets.ereg.domain.interfaces.model.rule.RuleSet;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfigurationDetail;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/secure/configurations")
public class ConfigurationsController {
	@Resource(name = "rulesSetConfigurationService")
	RulesSetConfigurationService rsConfigService;
	
	

	@Resource(name = "referenceEntityService")
	private ReferenceService referenceService;
		
	
	private static Logger log = LoggerFactory.getLogger(ConfigurationsController.class);
	

	
	private boolean checkForParamConfigType(RuleSet ruleSet){
		Rule rule=ruleSet.getRule();
		String ruleCd=rule.getRuleCode();
		Set<RuleAllowableParameter> setRAP=rule.getRuleAllowableParameters();
		for(RuleAllowableParameter ruleAllwParm:setRAP){
			String paramCd=ruleAllwParm.getRuleParameterCd();
			if(ruleCd.equals(paramCd)){
				return true;
			}
		}
		return false;
	}
	
	public ConfigurationsVo populateConfigFormToConfigVo(ConfigurationsForm configurationsForm,ConfigurationsVo configVo){
		configVo.setHierarchy(configurationsForm.getHierarchy());
		configVo.setRoleName(configurationsForm.getRoleName());
		configVo.setRules(configurationsForm.getRules());
		configVo.setRuleSelectedType(configurationsForm.getRuleSelectedType());
		configVo.setRuleSetType(configurationsForm.getRuleSetType());
		configVo.setParamNames(configurationsForm.getParamNames());
		configVo.setParamValues(configurationsForm.getParamValues());
		return configVo;
	}
	

	
	
    private List<RuleSet> prepareRuleSetConfig(List<RuleSet> targetRuleList,List<RuleSet> rsList){
		for(RuleSet ruleSet:rsList){
			targetRuleList.add(ruleSet);
		}
		return rsList;
    	
    }
	

	
	@RequestMapping(value="/getrulesinfo", method = RequestMethod.POST)
	public String getRulesInfo(ModelMap model,
			@ModelAttribute("configurationsForm") ConfigurationsForm configurationsForm){
		
		RuleSetConfiguration rsConfig;
		List<RuleSet> originalRuleList= new ArrayList<RuleSet>();
		List<RuleSet> targetRuleList= new ArrayList<RuleSet>();
		List<RuleSet> ruleSetList=rsConfigService.getRuleSetByCode(configurationsForm.getRuleSetType());
		rsConfig=getCurrentRuleSetConfiguration(configurationsForm);
		RuleSet ruleSet=ruleSetList.get(0);	
		boolean flagConfigType=checkForParamConfigType(ruleSet);
	
		if(rsConfig==null){
			if(flagConfigType){
				targetRuleList=prepareRuleSetConfig(targetRuleList,ruleSetList);
			}
			else{
				originalRuleList=prepareRuleSetConfig(originalRuleList,ruleSetList);
			}
			model.addAttribute("NewConfiguration",true);
		}
		else{
			
		Set<RuleSetConfigurationDetail> rsConfigDetails=rsConfig.getRuleSetConfigurationDetails();
		for(RuleSet rs:ruleSetList){
			boolean ruleFound=false;
			for(RuleSetConfigurationDetail rsConfigDetail:rsConfigDetails){

				RuleSet ruleSetDtl=rsConfigDetail.getRuleSet();
				if(rs.equals(ruleSetDtl)){
					targetRuleList.add(rs);
					ruleFound=true;
				}
			}
			if(!ruleFound){
				originalRuleList.add(rs);
			}
		}
		}
		List<RoleEnum> roleEnumList=rsConfigService.getEligibleRoles(configurationsForm.getHierarchy(),configurationsForm.getRuleSetType());
		if(!StringUtils.isEmpty(configurationsForm.getRoleName())){
			roleEnumList.add(RoleEnum.get(configurationsForm.getRoleName()));
		}
		model.addAttribute("roleEnumList", roleEnumList);
		model.addAttribute("ruleSet", !flagConfigType);
		model.addAttribute("originalRuleList", originalRuleList);
		model.addAttribute("targetRuleList", targetRuleList);
		model.addAttribute("ruleSetType",RuleSetEnum.get(configurationsForm.getRuleSetType()).getDescription());
		
		if(rsConfig!=null){
			model.addAttribute("configIdentifier",rsConfig.getRuleSetConfigurationIdentifier());
			
			
		}
	
		return CSRJspMappingConstants.CONFIGURATION_RULESET_VIEW;
		
	}
	
	private RuleSetConfiguration getCurrentRuleSetConfiguration(ConfigurationsForm configurationsForm){
		RuleSetConfiguration rsConfig;
		
		Long hierarchy=configurationsForm.getHierarchy();
		String roleName=configurationsForm.getRoleName();
		String ruleSetType=configurationsForm.getRuleSetType();
		Long adminId=0L;
		if(!StringUtils.isEmpty(roleName)){
			AdminRole adminRole=rsConfigService.getAdminRole(roleName);
			adminId=adminRole.getId();
		}
		rsConfig=rsConfigService.getRuleSetConfiguration(hierarchy, ruleSetType, adminId);
		return rsConfig;
	}	
	@RequestMapping(value="/getConfigurationForHierarchy", method = RequestMethod.POST)
	public String getConfigurationForHierarchy(ModelMap model,ConfigurationsForm configurationsForm ){
		EregHierarchy selectedHierarchy=rsConfigService.getEregHierarchy(configurationsForm.getHierarchy());
		Set<EregHierarchyRuleSetConfiguration> eregHierConfigs=selectedHierarchy.getEregHierarchyRuleSetConfigurations();
		model.addAttribute("eregHierconfigs", eregHierConfigs);
		List<EregHierarchyRuleSetConfiguration> availableConfigs=getAvailableConfig(eregHierConfigs);
		model.addAttribute("availableConfigs",availableConfigs);
		return CSRJspMappingConstants.CONFIGURATION_VIEW;
	}
	
	
	private List<EregHierarchyRuleSetConfiguration> getAvailableConfig(Set<EregHierarchyRuleSetConfiguration> eregHierConfigs){
		List<RuleSetType> ruleSetTypes=referenceService.getAll(RuleSetType.class, new ReferenceTypeCriteria(), false);
		List<EregHierarchyRuleSetConfiguration> hierarchyList= new ArrayList<EregHierarchyRuleSetConfiguration>();
		
		for(RuleSetType rsType:ruleSetTypes){
			boolean ruleFound=false;
			for(EregHierarchyRuleSetConfiguration eregHierConfig:eregHierConfigs){
				if(eregHierConfig.getRuleSetConfiguration().getRuleSetType().getCode().equals(rsType.getCode())){
					ruleFound=true;
				}
			}
			if(!ruleFound){
				EregHierarchyRuleSetConfiguration eregHier=rsConfigService.getDefaultHierarchy(rsType.getCode());
				hierarchyList.add(eregHier);
			}
		}
		return hierarchyList;
	}
	
	@RequestMapping(value="/saveconfigurations", method = RequestMethod.POST)
	public @ResponseBody String saveConfigurations(@ModelAttribute("configurationsForm") ConfigurationsForm configurationsForm,
         BindingResult errors, HttpServletRequest request, HttpServletResponse response)  {
		 
		ConfigurationsVo configVo= new ConfigurationsVo();
		configVo=populateConfigFormToConfigVo(configurationsForm, configVo);
		rsConfigService.saveRuleSetConfiguration(configVo);
		  
		
		return null;
	}
		@RequestMapping(method = RequestMethod.GET)
		protected String getConfigurationScreen(ModelMap model,@ModelAttribute("configurationsForm") ConfigurationsForm configurationsForm
				,@RequestParam(value="hierarchyId",defaultValue="0") Long hierarchyId) {			
			if(hierarchyId!=0){
				model.addAttribute("hierachySelected", true);
				model.addAttribute("hierarchyId",hierarchyId);
			}
			@SuppressWarnings("unused")	
			List<RuleSetType> rsTypes=referenceService.getAll(RuleSetType.class , new ReferenceTypeCriteria(), false);
			return CSRJspMappingConstants.CONFIGURATION_VIEW;
		}
	  
	  
	@ModelAttribute("hierarchys")
	public List<EregHierarchy> getEregHierarchys() {
		List<EregHierarchy> hierarchys=rsConfigService.getEregHierachys("HSE");	
		return hierarchys;
	}	
	
	@ModelAttribute("adminRoles")
	public Set<RoleEnum> getAdminRoles() {
		return RoleEnumGroup.ADMIN_ROLES_GROUP.getRolesInGroup();		
	}
	
    @ModelAttribute("ruleSetTypes")
	public List<RuleSetType> getRuleSetTypes() {
		return referenceService.getAll(RuleSetType.class, new ReferenceTypeCriteria(), false);
	}

	

}
