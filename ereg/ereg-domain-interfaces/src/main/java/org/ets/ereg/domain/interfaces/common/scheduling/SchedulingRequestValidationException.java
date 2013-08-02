package org.ets.ereg.domain.interfaces.common.scheduling;

import java.util.List;

import org.ets.ereg.domain.interfaces.common.scheduling.SchedulingException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

public class SchedulingRequestValidationException extends SchedulingException {

	public SchedulingRequestValidationException(BindException bindException) {
		super(bindException);
	}

	public boolean hasErrors(){
		return ((BindException)this.getCause()).hasErrors();
	}
	
	public List<ObjectError> getAllErrors(){
		return ((BindException)this.getCause()).getAllErrors();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
