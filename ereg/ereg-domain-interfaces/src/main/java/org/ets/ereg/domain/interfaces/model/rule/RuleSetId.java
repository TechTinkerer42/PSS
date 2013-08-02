package org.ets.ereg.domain.interfaces.model.rule;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RuleSetId generated by hbm2java
 */
@Embeddable
public class RuleSetId  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name="RUL_CDE", nullable=false, length=20)
	private String ruleCode;
	@Column(name="RUL_SET_TYP_CDE", nullable=false, length=10)
	private String ruleSetTypeCode;
    public String getRuleCode() {
        return this.ruleCode;
    }
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
    public String getRuleSetTypeCode() {
        return this.ruleSetTypeCode;
    } 
    public void setRuleSetTypeCode(String ruleSetTypeCode) {
        this.ruleSetTypeCode = ruleSetTypeCode;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) 
		 {
        	 return true;
		 }
		 if ( (other == null ) ) 
		 {
		 	return false;
		 }
		 if ( !(other instanceof RuleSetId) ) 
		 { 
			 return false;
		 }			 
		 RuleSetId castOther = ( RuleSetId ) other; 
         
		 return ( (this.getRuleCode()==castOther.getRuleCode()) || ( this.getRuleCode()!=null && castOther.getRuleCode()!=null && this.getRuleCode().equals(castOther.getRuleCode()) ) )
 && ( (this.getRuleSetTypeCode()==castOther.getRuleSetTypeCode()) || ( this.getRuleSetTypeCode()!=null && castOther.getRuleSetTypeCode()!=null && this.getRuleSetTypeCode().equals(castOther.getRuleSetTypeCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRuleCode() == null ? 0 : this.getRuleCode().hashCode() );
         result = 37 * result + ( getRuleSetTypeCode() == null ? 0 : this.getRuleSetTypeCode().hashCode() );
         return result;
   }   

}
