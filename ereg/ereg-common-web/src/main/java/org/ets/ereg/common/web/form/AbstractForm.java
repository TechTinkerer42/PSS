/**
 * 
 */
package org.ets.ereg.common.web.form;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
/**
 * 
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */

public class AbstractForm {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
