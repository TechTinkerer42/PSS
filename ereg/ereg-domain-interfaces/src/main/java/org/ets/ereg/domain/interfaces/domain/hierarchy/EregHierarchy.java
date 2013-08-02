package org.ets.ereg.domain.interfaces.domain.hierarchy;

import java.util.Set;

public interface EregHierarchy extends java.io.Serializable {	
    public Long getEregHierarchyIdentifier();
    public void setEregHierarchyIdentifier(Long eregHierarchyIdentifier);
    public EregHierarchy getParentHierarchy();
    public void setParentHierarchy(EregHierarchy parentHierarchy);
    public HierarchyType getHierarchyType();
    public void setHierarchyType(HierarchyType hierarchyType);
    public String getHierarchyName();
    public void setHierarchyName(String hierarchyName);
    public Set<EregHierarchyRuleSetConfiguration> getEregHierarchyRuleSetConfigurations();
    public void setEregHierarchyRuleSetConfigurations(Set<EregHierarchyRuleSetConfiguration> eregHierarchyRuleSetConfigurations);
    public Set<EregHierarchy> getEregHierarchies();
    public void setEregHierarchies(Set<EregHierarchy> eregHierarchies);
    public Long getEregHierarchyLevel();
	public void setEregHierarchyLevel(Long eregHierarchyLevel);

}
