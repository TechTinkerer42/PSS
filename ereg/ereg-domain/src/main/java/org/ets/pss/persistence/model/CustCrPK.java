package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CUST_CR database table.
 * 
 */
@Embeddable
public class CustCrPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CUSTOMER_ID", unique=true, nullable=false, precision=19)
	private long customerId;

	@Column(name="PRMPT_ID", unique=true, nullable=false)
	private long prmptId;

	@Column(name="TSK_ID", unique=true, nullable=false)
	private long tskId;

	public CustCrPK() {
	}
	public long getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getPrmptId() {
		return this.prmptId;
	}
	public void setPrmptId(long prmptId) {
		this.prmptId = prmptId;
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
		if (!(other instanceof CustCrPK)) {
			return false;
		}
		CustCrPK castOther = (CustCrPK)other;
		return 
			(this.customerId == castOther.customerId)
			&& (this.prmptId == castOther.prmptId)
			&& (this.tskId == castOther.tskId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.customerId ^ (this.customerId >>> 32)));
		hash = hash * prime + ((int) (this.prmptId ^ (this.prmptId >>> 32)));
		hash = hash * prime + ((int) (this.tskId ^ (this.tskId >>> 32)));
		
		return hash;
	}
}