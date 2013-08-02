package org.ets.ereg.domain.interfaces.model.program;

import java.io.Serializable;

import org.ets.ereg.domain.interfaces.model.common.ReferenceEntityInterface;

public interface ProgramColumnRuleType extends ReferenceEntityInterface,Serializable {
	
	public static final String PGM_CLMN_RUL_TYP_CDE_DISP ="DISP";
	public static final String PGM_CLMN_RUL_TYP_CDE_REQVLD ="RQVLD";

}
