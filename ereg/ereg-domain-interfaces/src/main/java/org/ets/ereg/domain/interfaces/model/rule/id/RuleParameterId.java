/** --------------------------------------------------------------------------
 * �2013 Educational Testing Service. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF EDUCATIONAL TESTING SERVICE AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * PURPOSE OTHER THAN THAT WHICH IT IS SPECIFICALLY PROVIDED WITHOUT THE WRITTEN
 * PERMISSION OF THE SAID COMPANY
 * --------------------------------------------------------------------------
 * Created on Mar 25, 2013 2:06:22 PM by Hibernate Tools 3.2.4.GA
 */
package org.ets.ereg.domain.interfaces.model.rule.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RuleParameterId generated by hbm2java
 */
@Embeddable
public class RuleParameterId  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    @Column(name="RUL_PARM_CDE", nullable=false, length=3)
	private String ruleParameterCode;


   
    public String getRuleParameterCode() {
        return this.ruleParameterCode;
    }
    
    public void setRuleParameterCode(String ruleParameterCode) {
        this.ruleParameterCode = ruleParameterCode;
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
		 if ( !(other instanceof RuleParameterId) ) 
		 {
			 return false;
		 }
		 RuleParameterId castOther = ( RuleParameterId ) other; 
         
		 return ( (this.getRuleParameterCode()==castOther.getRuleParameterCode()) || ( this.getRuleParameterCode()!=null && castOther.getRuleParameterCode()!=null && this.getRuleParameterCode().equals(castOther.getRuleParameterCode())  ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result +  (getRuleParameterCode() == null ? 0 : this.getRuleParameterCode().hashCode()) ;
         return result;
   }   

}
