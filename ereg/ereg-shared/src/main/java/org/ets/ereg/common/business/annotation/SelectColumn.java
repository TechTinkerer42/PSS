/**
 * 
 */
package org.ets.ereg.common.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to tag a field to be used to map to select columns in a query.
 * 
 * @author Mangesh Pardeshi
 * 
 * @version 1.0, Mar 15, 2013 - 11:30:12 AM
 * 
 * @since e-Reg Release 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SelectColumn {

	int order();
}
