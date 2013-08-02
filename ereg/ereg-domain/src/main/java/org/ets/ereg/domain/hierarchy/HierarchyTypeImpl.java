package org.ets.ereg.domain.hierarchy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.domain.hierarchy.HierarchyType;

@Entity
@Table(name="HIER_TYP")
public class HierarchyTypeImpl  implements HierarchyType {
	private static final long serialVersionUID = 1L;
 	@Id 
	@Column(name="HIER_TYP_CDE", length=5)
	private String primaryKey;
	@Column(name="DSC", nullable=false, length=175)
	private String description;
	@Override
    public String getPrimaryKey() {
        return this.primaryKey;
    }
	@Override
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
	@Override
    public String getDescription() {
        return this.description;
    }
	@Override
    public void setDescription(String description) {
        this.description = description;
    }

}
