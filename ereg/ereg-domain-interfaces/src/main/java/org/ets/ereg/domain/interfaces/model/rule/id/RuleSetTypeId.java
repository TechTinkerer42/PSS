package org.ets.ereg.domain.interfaces.model.rule.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RuleSetTypeId  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="PGM_CDE", nullable=false, length=5)
	private String programCode;
	@Column(name="RUL_SET_TYP_CDE", nullable=false, length=10)
	private String code;

   
    public String getProgramCode() {
        return this.programCode;
    }
    
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
		 if ( !(other instanceof RuleSetTypeId) ) 
		 {
			 return false;
		 }
		 RuleSetTypeId castOther = ( RuleSetTypeId ) other; 
         
		 return ( (this.getProgramCode()==castOther.getProgramCode()) || ( this.getProgramCode()!=null && castOther.getProgramCode()!=null && this.getProgramCode().equals(castOther.getProgramCode()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProgramCode() == null ? 0 : this.getProgramCode().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         return result;
   }   

}
