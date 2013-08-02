package org.ets.ereg.common.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ets.ereg.common.business.dao.ProgramColumnRuleDao;
import org.ets.ereg.common.business.service.ProgramColumnRuleService;
import org.ets.ereg.common.business.vo.ProgramColumnRuleVO;
import org.ets.ereg.domain.common.LanguageTypeImpl;
import org.ets.ereg.domain.interfaces.model.common.LanguageType;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRuleType;
import org.ets.ereg.domain.profile.ETSCustomerImpl;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("programColumnServiceImpl")
public class ProgramColumnRuleServiceImpl implements ProgramColumnRuleService {

	private static Logger logger = LoggerFactory.getLogger(ProgramColumnRuleServiceImpl.class);
	
	@Resource(name="programColumnRuleDao")
	private ProgramColumnRuleDao programColumnRuleDao;
	
	public void setProgramColumnRuleDao(ProgramColumnRuleDao programColumnRuleDao){
		this.programColumnRuleDao = programColumnRuleDao;
	}
	
	@Override
	public <T> List<ProgramColumnRuleVO> getProgramRules(T clazz,
			String programCode,List<String> propertyNames) {

		   
    	
		List<ProgramColumnRule> pgmClmRules=programColumnRuleDao.getProgramRules(clazz, programCode, propertyNames);

		List<ProgramColumnRuleVO> programColumnRuleVOList = new ArrayList<ProgramColumnRuleVO>(); 
		
		if(pgmClmRules.size()>0){
				
			String regExTxt;
			ProgramColumnRuleVO programColumnRuleVO=null;
			ProgramColumnRuleType pgmClmRuleType=null;			
			String pgmClmRuleTypCode=null;
					
			ETSCustomerImpl etsCustomer = new ETSCustomerImpl();
			etsCustomer.setSourceId("NET");
			LanguageType ltype = new LanguageTypeImpl();
			ltype.setCode("SPAN");
			etsCustomer.setPrmrySpkngLang(ltype);
			
			for(ProgramColumnRule pgmClmRule:pgmClmRules){
				regExTxt = pgmClmRule.getRegexTxt();
				Boolean boolVal = (Boolean)MVEL.eval(regExTxt, etsCustomer);
								
				programColumnRuleVO = new ProgramColumnRuleVO();
				programColumnRuleVO.setEntityName(pgmClmRule.getId().getEntityName());
				programColumnRuleVO.setPropertyName(pgmClmRule.getId().getPropertyName());
				programColumnRuleVO.setProgramCode(pgmClmRule.getId().getProgramCode());
				
				pgmClmRuleType = pgmClmRule.getpgmClmnRuleTypCde();
				pgmClmRuleTypCode = pgmClmRuleType.getCode();
				
				if(ProgramColumnRuleType.PGM_CLMN_RUL_TYP_CDE_DISP.equals(pgmClmRuleTypCode)){
					programColumnRuleVO.setDisplayable(boolVal);					
				}else if(ProgramColumnRuleType.PGM_CLMN_RUL_TYP_CDE_REQVLD.equals(pgmClmRuleTypCode)){
					programColumnRuleVO.setRequired(boolVal);	
				}
				
				programColumnRuleVOList.add(programColumnRuleVO);
			}
		}else{
			
			logger.info("no program rules found for entity {}",clazz.getClass().getName());
		}
		
		return programColumnRuleVOList;
	}

}
