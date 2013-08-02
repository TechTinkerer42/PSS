
package org.ets.ereg.domain.interfaces.domain.hierarchy;

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.id.EregHierarchyRuleSetConfigurationId;


public interface EregHierarchyRuleSetConfiguration  extends java.io.Serializable {
	   
	 public EregHierarchyRuleSetConfigurationId getId();
	    
	 public void setId(EregHierarchyRuleSetConfigurationId id);
	 
	 public RuleSetConfiguration getRuleSetConfiguration();
	    
	 public void setRuleSetConfiguration(RuleSetConfiguration ruleSetConfiguration);
	    
	 public EregHierarchy getEregHierarchy();
	    
	 public void setEregHierarchy(EregHierarchy eregHierarchy);

		
	 public AdminRole getAdminRole();
	   
	 public void setAdminRole(AdminRole adminRole);



}
