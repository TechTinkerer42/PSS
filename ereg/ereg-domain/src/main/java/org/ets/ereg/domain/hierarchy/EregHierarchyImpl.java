package org.ets.ereg.domain.hierarchy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchy;
import org.ets.ereg.domain.interfaces.domain.hierarchy.EregHierarchyRuleSetConfiguration;
import org.ets.ereg.domain.interfaces.domain.hierarchy.HierarchyType;


@Entity
@Table(name="EREG_HIER")
public class EregHierarchyImpl  implements EregHierarchy {
	
	private static final long serialVersionUID = 1L;
 	@Id     
	@Column(name="EREG_HIER_ID")
	private Long eregHierarchyIdentifier;
 	@ManyToOne(fetch=FetchType.LAZY,targetEntity=EregHierarchyImpl.class)
	    @JoinColumn(name="PARNT_EREG_HIER_ID")
	private EregHierarchy parentHierarchy;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=HierarchyTypeImpl.class)
	    @JoinColumn(name="HIER_TYP_CDE", nullable=false)
	private HierarchyType hierarchyType;
	@Column(name="HIER_LVL")
	private Long eregHierarchyLevel;
 
	
	    
	@Column(name="HIER_NAM", nullable=false, length=256)
	private String hierarchyName;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="eregHierarchy",targetEntity=EregHierarchyRuleSetConfigurationImpl.class)
	private Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations = new HashSet<EregHierarchyRuleSetConfiguration>(0);


	@OneToMany(fetch=FetchType.LAZY, mappedBy="parentHierarchy",targetEntity=EregHierarchyImpl.class)
	private Set<EregHierarchy> eregHierarchies = new HashSet<EregHierarchy>(0);

	@Override
    public Long getEregHierarchyIdentifier() {
        return this.eregHierarchyIdentifier;
    }
	@Override
    public void setEregHierarchyIdentifier(Long eregHierarchyIdentifier) {
        this.eregHierarchyIdentifier = eregHierarchyIdentifier;
    }
	@Override
    public EregHierarchy getParentHierarchy() {
        return this.parentHierarchy;
    }
	@Override
    public void setParentHierarchy(EregHierarchy parentHierarchy) {
        this.parentHierarchy = parentHierarchy;
    }
	@Override
    public HierarchyType getHierarchyType() {
        return this.hierarchyType;
    }
	@Override
    public void setHierarchyType(HierarchyType hierarchyType) {
        this.hierarchyType = hierarchyType;
    }
	@Override
	public Long getEregHierarchyLevel() {
		return eregHierarchyLevel;
	}
	@Override
	public void setEregHierarchyLevel(Long eregHierarchyLevel) {
		this.eregHierarchyLevel = eregHierarchyLevel;
	}
	@Override
    public String getHierarchyName() {
        return this.hierarchyName;
    }
	@Override
    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }
	@Override
    public Set<EregHierarchyRuleSetConfiguration> getEregHierarchyRuleSetConfigurations() {
        return this.eregHierarchyRuleSetConfigurations;
    }
	@Override
    public void setEregHierarchyRuleSetConfigurations(Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations) {
        this.eregHierarchyRuleSetConfigurations = eregHierarchyRuleSetConfigurations;
    }
	@Override
    public Set<EregHierarchy> getEregHierarchies() {
        return this.eregHierarchies;
    }
	@Override
    public void setEregHierarchies(Set<EregHierarchy> eregHierarchies) {
        this.eregHierarchies = eregHierarchies;
    }
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder().append(eregHierarchyIdentifier).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof EregHierarchyImpl) {
			final EregHierarchyImpl other = (EregHierarchyImpl) obj;
	        return new EqualsBuilder().append(eregHierarchyIdentifier, other.eregHierarchyIdentifier)
	        		.isEquals();
	    } else {
	           return false;
	           }
	   }


}
