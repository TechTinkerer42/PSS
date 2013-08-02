package org.ets.ereg.domain.biq;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ets.ereg.domain.interfaces.model.biq.DemographicQuestionSet;

@Entity
@Table(name="DMGRPH_QSTN_SET")
public class DemographicQuestionSetImpl implements DemographicQuestionSet,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="SET_ID",nullable=false,length=5)
	private Long setId;
	
	@Column(name="SET_DSC",length=50)
	private String setDesc;
	
	@Override
	public Long getSetId() {
		return setId;
	}

	@Override
	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Override
	public String getSetDesc() {
		return setDesc;
	}

	@Override
	public void setSetDesc(String setDesc) {
		this.setDesc = setDesc;
	}

}
