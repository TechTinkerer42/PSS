
package org.ets.ereg.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum RuleEnum {

	NULL(null,null, null),
	RETAKE_TEST_WAIT_PERIOD_RULE("WPR", "Wait Period Rule" , "org.ets.ereg.common.business.rules.RetakeTestWaitPeriodRule"),
	RETAKE_TEST_MAX_TEST_ATTEMPT_RULE("MTA", "Max No. of Test taken in a give period" ,"org.ets.ereg.common.business.rules.RetakeTestMaxTestAttemptRule");

	
	
    private static final Map<String, RuleEnum> RULE_ENUM_LOOK_UP = new HashMap<String, RuleEnum>();
    
    static {
    	for(RuleEnum re : EnumSet.allOf(RuleEnum.class))
    		RULE_ENUM_LOOK_UP.put(re.getCode(), re);
    }
	
	private String code;
	private String desc;
	private String implementationClassName;
	
	private RuleEnum(String code, String desc,String implementationClassName) {
		this.code = code;
		this.desc = desc;
		this.implementationClassName = implementationClassName;
	}
	
	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return getCode();
	}
    
    public static RuleEnum get(String code) { 
        return RULE_ENUM_LOOK_UP.get(code); 
   }

	/**
	 * @return the implementationClassName
	 */
	public String getImplementationClassName() {
		return implementationClassName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
