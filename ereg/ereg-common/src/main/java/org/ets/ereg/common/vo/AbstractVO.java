/**
 * 
 */
package org.ets.ereg.common.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Base VO for all value objects in e-Reg
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0
 * 
 * @since e-Reg Release 1.0 - Feb 18, 2013
 */
public class AbstractVO {

	@Override
	public String toString() {
		
		return ReflectionToStringBuilder.toString(this);
	}
}
