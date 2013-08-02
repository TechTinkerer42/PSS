package org.ets.ereg.common.web.exception;

public class JspException extends Exception
{
	String errorMessage;
	public JspException() 	{
		super();
		errorMessage = "unknown";
	}
	public JspException(String err)	{
		super(err);
		errorMessage = err;  
	}
	public String getError()	{
		return errorMessage;
	}
}