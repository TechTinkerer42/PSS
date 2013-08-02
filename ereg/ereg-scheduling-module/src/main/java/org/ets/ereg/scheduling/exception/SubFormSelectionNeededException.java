package org.ets.ereg.scheduling.exception;

import java.util.List;

import org.ets.ereg.common.exception.ERegCheckedException;
import org.ets.ereg.domain.interfaces.model.form.Form;

public class SubFormSelectionNeededException extends ERegCheckedException {

	private static final long serialVersionUID = 1L;
	
	private List<Form> subForms;
	
	public SubFormSelectionNeededException() {
		
	}
	
	public SubFormSelectionNeededException(List<Form> subForms) {
		this.subForms = subForms;
	}

	public List<Form> getSubForms() {
		return subForms;
	}

	public void setSubForms(List<Form> subForms) {
		this.subForms = subForms;
	}
	
}
