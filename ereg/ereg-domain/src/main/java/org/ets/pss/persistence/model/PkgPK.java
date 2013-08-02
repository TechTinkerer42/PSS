package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PKG database table.
 * 
 */
@Embeddable
public class PkgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PKG_NO", unique=true, nullable=false)
	private long pkgNo;

	@Column(name="TST_ID", unique=true, nullable=false)
	private long tstId;

	public PkgPK() {
	}
	public long getPkgNo() {
		return this.pkgNo;
	}
	public void setPkgNo(long pkgNo) {
		this.pkgNo = pkgNo;
	}
	public long getTstId() {
		return this.tstId;
	}
	public void setTstId(long tstId) {
		this.tstId = tstId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PkgPK)) {
			return false;
		}
		PkgPK castOther = (PkgPK)other;
		return 
			(this.pkgNo == castOther.pkgNo)
			&& (this.tstId == castOther.tstId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.pkgNo ^ (this.pkgNo >>> 32)));
		hash = hash * prime + ((int) (this.tstId ^ (this.tstId >>> 32)));
		
		return hash;
	}
}