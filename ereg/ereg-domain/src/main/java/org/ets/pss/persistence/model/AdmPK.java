package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADM database table.
 * 
 */
@Embeddable
public class AdmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PGM_CDE", unique=true, nullable=false, length=5)
	private String pgmCde;

	@Column(name="ADM_CDE", unique=true, nullable=false, length=9)
	private String admCde;

	public AdmPK() {
	}
	public String getPgmCde() {
		return this.pgmCde;
	}
	public void setPgmCde(String pgmCde) {
		this.pgmCde = pgmCde;
	}
	public String getAdmCde() {
		return this.admCde;
	}
	public void setAdmCde(String admCde) {
		this.admCde = admCde;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdmPK)) {
			return false;
		}
		AdmPK castOther = (AdmPK)other;
		return 
			this.pgmCde.equals(castOther.pgmCde)
			&& this.admCde.equals(castOther.admCde);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgmCde.hashCode();
		hash = hash * prime + this.admCde.hashCode();
		
		return hash;
	}
}