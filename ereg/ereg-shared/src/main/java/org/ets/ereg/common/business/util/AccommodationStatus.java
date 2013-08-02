package org.ets.ereg.common.business.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccommodationStatus {
	ACTIVE("ACTIVE","Active"),
	EXPIRED("EXPIRED","Expired");
	
	 /**
     * A mapping between the  code and its corresponding Status to facilitate lookup by code.
     */
    private static final Map<String, AccommodationStatus> codeToStatusMapping =new HashMap<String, AccommodationStatus>();
    
  
    
    static {
    	for(AccommodationStatus re : EnumSet.allOf(AccommodationStatus.class))
    		codeToStatusMapping.put(re.getCode(), re);
    }
    
	private String code;
	private String label;
	
	private AccommodationStatus (String code,String label) {
		this.code = code;
		this.label=label;
		
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public static AccommodationStatus getStatus(String code) {
		 return codeToStatusMapping.get(code) != null? codeToStatusMapping.get(code) : null;	    
	}
	
	
	 @Override
	    public String toString() {
	        final StringBuilder sb = new StringBuilder();
	        sb.append("Status");
	        sb.append("{code=").append(code);
	        sb.append(", label='").append(label).append('\'');	     
	        sb.append('}');
	        return sb.toString();
	    }

	

}
