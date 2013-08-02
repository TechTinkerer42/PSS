/**
 * 
 */
package org.ets.ereg.domain.interfaces.model.program;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.program.id.ProgramColumnRuleId;


public interface ProgramColumnRule extends Serializable{
	
	public ProgramColumnRuleId getId();
	public void setId(ProgramColumnRuleId programCoulmnRuleId);
	
	public void setPgmClmnRuleTypCde(ProgramColumnRuleType pgmClmnRuleTypCde);
	public ProgramColumnRuleType getpgmClmnRuleTypCde();
	
	public void setRegexTxt(String regexTxt);
	public String getRegexTxt();
}
