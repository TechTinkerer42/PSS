package org.ets.ereg.common.business.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quick way to clone something which is not Clonable (but is Serializable)
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0, Feb 25, 2013 - 6:15:56 PM
 * 
 * @since e-Reg Release 1.0
 */
public class ObjectCloner {
	
	private static Logger logger = LoggerFactory.getLogger(ObjectCloner.class);
	
	// so that nobody can accidentally create an ObjectCloner object
	private ObjectCloner() {
	}

	// returns a deep copy of an object
	static public Object clone(Object oldObj)  {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);

			// serialize and pass the object
			oos.writeObject(oldObj);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin);

			// return the new object
			return ois.readObject();
		} 
		catch (Exception e) {
			ERegRuntimeException re = new ERegRuntimeException("Exception when cloning");
			re.addData("Object to clone: ", ReflectionToStringBuilder.toString(oldObj));
			throw re;
		} 
		finally {
			try {
				oos.close();
				ois.close();
			}
			catch (Exception e) {
				logger.error("exception occurred",e);
			}
		}
	}
}