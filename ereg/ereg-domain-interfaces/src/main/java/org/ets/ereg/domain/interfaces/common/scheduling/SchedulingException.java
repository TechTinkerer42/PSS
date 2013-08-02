package org.ets.ereg.domain.interfaces.common.scheduling;

public class SchedulingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SchedulingException(String message){
		super(message);
	}
	public SchedulingException(Throwable t){
		super(t);
	}
	public SchedulingException(String message, Throwable t){
		super(message, t);
	}
}