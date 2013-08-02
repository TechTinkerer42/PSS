package org.ets.ereg.common.exception;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.ets.ereg.common.util.ExceptionUtil;
/**
 * 
 * @author Mangesh Pardeshi
 * 
 *  History
 *  -------
 */
public class ERegRuntimeException extends RuntimeException {

    private Date date;
    private Map<String[], Object> data = new LinkedHashMap<String[], Object>();
    private String refNumber;
	
	public ERegRuntimeException() {
        super();
        refNumber = ExceptionUtil.buildRefNumber();
	}

	public ERegRuntimeException(String message, Throwable anOriginalException) {
		super(message, anOriginalException);
		
        if (anOriginalException instanceof ERegCheckedException) {
        	ERegCheckedException anERegException = (ERegCheckedException) anOriginalException;
            date = anERegException.getDate ();
            refNumber = anERegException.getRefNumber ();
        } else if (anOriginalException instanceof ERegRuntimeException) {
        	ERegRuntimeException anERegRuntimeException = (ERegRuntimeException) anOriginalException;
            date = anERegRuntimeException.getDate ();
            refNumber = anERegRuntimeException.getRefNumber ();
        } else {
            //Find out if any Exception in the exception chain is a EReg Exception.
            //If so get the date and refNumber from there.
            Throwable cause = getCauseCause(anOriginalException);
            boolean foundERegException = false;
            while (cause != null & !foundERegException) {
                if (cause instanceof ERegCheckedException) {
                	ERegCheckedException anERegException = (ERegCheckedException) cause;
                    date = anERegException.getDate ();
                    refNumber = anERegException.getRefNumber ();
                    foundERegException = true; //break
                } else if (cause instanceof ERegRuntimeException) {
                	ERegRuntimeException anERegRuntimeException = (ERegRuntimeException) cause;
                    date = anERegRuntimeException.getDate ();
                    refNumber = anERegRuntimeException.getRefNumber ();
                    foundERegException = true; //break
                }
                cause = getCauseCause(cause);
            }

            if (!foundERegException) {
                //None of the Exceptions in the exception chain was a ERegException.
                //Therefore we now have to initiate the local membes
                refNumber = ExceptionUtil.buildRefNumber();
            }
        }		
	}

	public ERegRuntimeException(String message) {
		super(message);
        date = new Date();
        //refNumber = "" + date.getTime();
        refNumber = UUID.randomUUID().toString();
	}

	public ERegRuntimeException(Throwable anOriginalException) {
		super(anOriginalException);
        if (anOriginalException instanceof ERegCheckedException) {
        	ERegCheckedException anERegException = (ERegCheckedException) anOriginalException;
            date = anERegException.getDate ();
            refNumber = anERegException.getRefNumber ();
        } else if (anOriginalException instanceof ERegRuntimeException) {
        	ERegRuntimeException anERegRuntimeException = (ERegRuntimeException) anOriginalException;
            date = anERegRuntimeException.getDate ();
            refNumber = anERegRuntimeException.getRefNumber ();
        } else {
            //Find out if any Exception in the exception chain is a EReg Exception.
            //If so get the date and refNumber from there.
            Throwable cause = getCauseCause(anOriginalException);
            boolean foundERegException = false;
            while (cause != null & !foundERegException) {
                if (cause instanceof ERegCheckedException) {
                	ERegCheckedException anERegException = (ERegCheckedException) cause;
                    date = anERegException.getDate ();
                    refNumber = anERegException.getRefNumber ();
                    foundERegException = true; //break
                } else if (cause instanceof ERegRuntimeException) {
                	ERegRuntimeException anERegRuntimeException = (ERegRuntimeException) cause;
                    date = anERegRuntimeException.getDate ();
                    refNumber = anERegRuntimeException.getRefNumber ();
                    foundERegException = true; //break
                }
                cause = getCauseCause(cause);
            }

            if (!foundERegException) {
                //None of the Exceptions in the exception chain was a ERegException.
                //Therefore we now have to initiate the local membes
                refNumber = ExceptionUtil.buildRefNumber();
                date = new Date();
            }
        }		

	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<String[], Object> getData() {
		return data;
	}

	public void setData(Map<String[], Object> data) {
		this.data = data;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	
    /**
     * Adds one key/value pair to the <b>data</b> instance variable.
     */
    public void addData(String aKey, Object aValue) {
        if (aKey == null) 
    	{
        	return;
    	}

        //Find out who added it and add the caller to the key
        Throwable t = new Throwable();
        StackTraceElement astacktraceelement[] = t.getStackTrace();
        String[] internalKey = new String[] {aKey, ""};
        if (astacktraceelement.length > 0) {
            internalKey[1] = "" + astacktraceelement[1].getFileName().substring(0,astacktraceelement[1].getFileName().indexOf(".")+1) + astacktraceelement[1].getMethodName() + ":" + astacktraceelement[1].getLineNumber();
        }

        data.put(internalKey, aValue);
    }

    private Throwable getCauseCause(Throwable cause) {
        if (cause == null) {
            return null;
        } else {
            return cause.getCause();
        }
    }
    
	
    public String toString() {
        String message = getMessage() == null ? getClass().getName() : getClass().getName() + ": " + getMessage();
        StringBuffer sbMessage = new StringBuffer(message);
        sbMessage.append("\n\tEReg Reference Number: ").append(this.refNumber);
        sbMessage.append("\n\tDate: ").append(this.date);

        for (Iterator i = data.keySet().iterator(); i.hasNext(); ) {
            String[] internalKey = (String[]) i.next();
            Object value = data.get(internalKey);
            sbMessage.append("\n\t").append(internalKey[0]).append(": ").append(( value != null ? ((value instanceof String)?value:ReflectionToStringBuilder.toString(value)) : null)).append(" (added by " + internalKey[1]).append(")");
        }
        return sbMessage.toString();
    }
}
