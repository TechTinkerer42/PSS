/**
 * 
 */
package org.ets.ereg.common.business.util;

import org.ets.ereg.common.helpers.ThreadLocalFacade;

/**
 * ThreadLocal based program context holder.
 * 
 * Due to the nature of ThreadLocal, it is important to clean up the context after use.
 * 
 * @author Mangesh Pardeshi
 * 
 * @version 1.0, Mar 4, 2013 - 5:36:13 PM
 * 
 * @since e-Reg Release 1.0
 */
@Deprecated
public class ProgramContextHolder {
	
	
//	public static String getProgramType() {
//		return contextHolder.get();
//	}
//
//	public static void clearProgramType() {
//		contextHolder.remove();
//	}

	public static String getProgramCode() {
		//CSR Web compatibility 
		if( null == ThreadLocalFacade.getProgramCode() || ThreadLocalFacade.getProgramCode().trim().length() == 0) {
			return "HSE";
		}
		return ThreadLocalFacade.getProgramCode();
	}

}
