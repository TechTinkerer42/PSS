package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ASGND_TSK database table.
 * 
 */
@Embeddable
public class AsgndTskPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CUSTOMER_ID", unique=true, nullable=false, precision=19)
	private long customerId;

	@Column(name="TSK_ID", unique=true, nullable=false)
	private long tskId;

	public AsgndTskPK() {
	}
	public long getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getTskId() {
		return this.tskId;
	}
	public void setTskId(long tskId) {
		this.tskId = tskId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AsgndTskPK)) {
			return false;
		}
		AsgndTskPK castOther = (AsgndTskPK)other;
		return 
			(this.customerId == castOther.customerId)
			&& (this.tskId == castOther.tskId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.customerId ^ (this.customerId >>> 32)));
		hash = hash * prime + ((int) (this.tskId ^ (this.tskId >>> 32)));
		
		return hash;
	}
}