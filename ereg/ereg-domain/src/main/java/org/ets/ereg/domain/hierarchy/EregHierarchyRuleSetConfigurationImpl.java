package org.ets.ereg.domain.hierarchy;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRoleImpl;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.RuleSetConfiguration;
import org.ets.ereg.domain.interfaces.model.rule.id.EregHierarchyRuleSetConfigurationId;
import org.ets.ereg.domain.rule.RuleSetConfigurationImpl;


@Entity
@Table(name="EREG_HIER_RUL_SET_CNFGN")
public class EregHierarchyRuleSetConfigurationImpl  implements EregHierarchyRuleSetConfiguration {
	
	private static final long serialVersionUID = 1L;
 	@EmbeddedId    
	@AttributeOverrides( {
    @AttributeOverride(name="ruleSetConfigurationIdentifier", column=@Column(name="RUL_SET_CNFGN_ID", nullable=false) ), 
    @AttributeOverride(name="eregHierarchyIdentifier", column=@Column(name="EREG_HIER_ID", nullable=false) ) } )
	private EregHierarchyRuleSetConfigurationId id;
 	
 	@ManyToOne(fetch=FetchType.EAGER,targetEntity=AdminRoleImpl.class)
 	@JoinColumn(name = "ADMIN_ROLE_ID", nullable = true)
 	private AdminRole adminRole;

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=RuleSetConfigurationImpl.class)
	    @JoinColumn(name="RUL_SET_CNFGN_ID", nullable=false, insertable=false, updatable=false)
	private RuleSetConfiguration ruleSetConfiguration;

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=EregHierarchyImpl.class)
	    @JoinColumn(name="EREG_HIER_ID", nullable=false, insertable=false, updatable=false)
	private EregHierarchy eregHierarchy;
	@Override
    public EregHierarchyRuleSetConfigurationId getId() {
        return this.id;
    }
    @Override
    public void setId(EregHierarchyRuleSetConfigurationId id) {
        this.id = id;
    }
    @Override
    public RuleSetConfiguration getRuleSetConfiguration() {
        return this.ruleSetConfiguration;
    }
    @Override
    public void setRuleSetConfiguration(RuleSetConfiguration ruleSetConfiguration) {
        this.ruleSetConfiguration = ruleSetConfiguration;
    }
    @Override
    public EregHierarchy getEregHierarchy() {
        return this.eregHierarchy;
    }
    @Override
    public void setEregHierarchy(EregHierarchy eregHierarchy) {
        this.eregHierarchy = eregHierarchy;
    }
    @Override
	public AdminRole getAdminRole() {
		return adminRole;
	}
    @Override
	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}



}
