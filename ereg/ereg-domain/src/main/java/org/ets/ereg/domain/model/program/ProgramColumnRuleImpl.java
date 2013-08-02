package org.ets.ereg.domain.model.program;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRule;
import org.ets.ereg.domain.interfaces.model.program.ProgramColumnRuleType;
import org.ets.ereg.domain.interfaces.model.program.id.ProgramColumnRuleId;



@Entity
@Table(name="PGM_CLMN_RUL")
public class ProgramColumnRuleImpl implements ProgramColumnRule,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "programCode", column = @Column(name = "PGM_CDE", nullable = false, length=5)),
			@AttributeOverride(name = "entityName", column = @Column(name = "ENTY_NAM", nullable = false, length=1000)),
			@AttributeOverride(name = "propertyName", column = @Column(name = "PRPRTY_NAM", nullable = false,length=50))
	})
	private ProgramColumnRuleId programCoulmnRuleId = new ProgramColumnRuleId();
	
	
	@JoinColumn(name="PGM_CLMN_RUL_TYP_CDE")
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ProgramColumnRuleTypeImpl.class, optional=true)
	private ProgramColumnRuleType pgmClmnRuleTypCde;
	
	@Column(name="REGEX_TXT", length=2000)
	private String regexTxt;	
	

	@Override
	public ProgramColumnRuleId getId() {
		return this.programCoulmnRuleId;
	}

	@Override
	public void setId(ProgramColumnRuleId programCoulmnRuleId) {
		this.programCoulmnRuleId = programCoulmnRuleId;
	}

	
	@Override
	public void setPgmClmnRuleTypCde(ProgramColumnRuleType pgmClmnRuleTypCde) {
		this.pgmClmnRuleTypCde = pgmClmnRuleTypCde;
		
	}

	@Override
	public ProgramColumnRuleType getpgmClmnRuleTypCde() {
		return pgmClmnRuleTypCde;
	}

	@Override
	public void setRegexTxt(String regexTxt) {
		this.regexTxt = regexTxt;
	}

	@Override
	public String getRegexTxt() {
		return regexTxt;
	}
}
