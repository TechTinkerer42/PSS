package org.ets.ereg.common.business.login;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EregLoginRoles {
	TCA ("TCA"),
	CSR ("CSR"),
	CUSTOMER ("CUST"),
	//invalid role
	NULL (null);

    private static final Map<String, EregLoginRoles> ENUM_LOOK_UP = new HashMap<String, EregLoginRoles>();
    
    static {
    	for(EregLoginRoles re : EnumSet.allOf(EregLoginRoles.class))
    		ENUM_LOOK_UP.put(re.getCode(), re);
    }
	
	private String code;
	
	private EregLoginRoles(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return getCode();
	}
	
	public EregLoginRoles[] getTCARole() {
		return new EregLoginRoles[] {TCA};
	}

	public EregLoginRoles[] getCSRRole() {
		return new EregLoginRoles[] {CSR};
	}
	
	public EregLoginRoles[] getCustRole() {
		return new EregLoginRoles[] {CUSTOMER};
	}
    public static EregLoginRoles get(String code) { 
        return ENUM_LOOK_UP.get(code) != null? ENUM_LOOK_UP.get(code) : NULL; 
   }
}
