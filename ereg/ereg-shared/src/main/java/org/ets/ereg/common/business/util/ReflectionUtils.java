/**
 * 
 */
package org.ets.ereg.common.business.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.common.business.annotation.SelectColumn;
import org.ets.ereg.common.exception.ERegRuntimeException;

/**
 * Common reflection utility methods. Useful when writing framework code to get set values 
 * from an object using reflection
 * 
 * @author Mangesh Pardesi
 * 
 * @version 1.0, Feb 25, 2013 - 6:16:29 PM
 * 
 * @since e-Reg Release 1.0
 */
public class ReflectionUtils {

	/**
	 * Gets a value from an object using reflection
	 */
	public static Object getFieldValueByReflection(Object targetObject,
			String fieldName) {

		try {
			String getMethodName = "get"
					+ Character.toUpperCase(fieldName.charAt(0))
					+ fieldName.substring(1);
			Method m = targetObject.getClass().getMethod(getMethodName,
					(Class[]) null);
			return m.invoke(targetObject, (Object[]) null);

		} catch (Exception e) {
			throw new ERegRuntimeException(
					"Error getting field using reflection. Class: "
							+ targetObject.getClass().getName() + " Field: "
							+ fieldName, e);
		}
	}

	/**
	 * Sets a value in an object using reflection.
	 * 
	 * NOTE: If the target object has a typed list this method will convert the
	 * passed list to a list of that type
	 * 
	 * @param targetObject
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValueByReflection(Object targetObject,
			String fieldName, Object value) {

		try {
			// if(value != null) {
			String setMethodName = "set"
					+ Character.toUpperCase(fieldName.charAt(0))
					+ fieldName.substring(1);

			// Field[] allFields = targetObject.getClass().getDeclaredFields();
			List<Field> allFields = getInheritedFields(targetObject.getClass());

			for (Field field : allFields) {

				if (field.getName().equals(fieldName)) {

					Method m = targetObject.getClass().getMethod(setMethodName,
							new Class[] { field.getType() });

					if (field.getType() == Integer.class)
					{
						m.invoke(targetObject,
								value != null ? new Integer[] { Integer
										.valueOf(value.toString()) }
										: new Integer[] { null });
					}
					else if (field.getType() == Long.class)
					{
						m.invoke(targetObject,
								value != null ? new Long[] { Long.valueOf(value
										.toString()) } : new Long[] { null });
					}
					else if (field.getType() == Double.class)
					{
						m.invoke(targetObject,
								value != null ? new Double[] { Double
										.valueOf(value.toString()) }
										: new Double[] { null });
					}
					else if (field.getType() == Boolean.class)
					{
						m.invoke(targetObject,
								value != null ? new Boolean[] { getBooleanFromObject(value.toString()) }
										: new Boolean[] { null });
					}
					else if (field.getType() == List.class)
					{
						m.invoke(targetObject, new Object[] { collectAs(
								(List) value, getCollectionType(field)) });
					}
					else if (field.getType() == Date.class)
					{
						DateFormat formatter ; 
						formatter = new SimpleDateFormat("yyyy-mm-dd h:m:s.a");
						m.invoke(targetObject,value != null ? new Date[] { (Date)formatter.parse(value.toString()) }: new Date[] { null });
					}
					else
					{
						m.invoke(targetObject, new Object[] { value });
					}
				}
			}
			// }
		} catch (Exception e) {
			throw new ERegRuntimeException(
					"Error setting field using reflection. Class: "
							+ targetObject.getClass().getName() + " Field: "
							+ fieldName, e);
		}
	}

	public static List<Field> getInheritedFields(Class<?> type) {
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}

	
	/**
	 * Extracts a Boolean from an Object
	 * @param value the Object for which a Boolean should be extracted.
	 * @return a <code>Boolean</code> with the value of TRUE if either a "Y" or
	 * "true" value can be extracted; otherwise, false is returned.
	 */
	public static Boolean getBooleanFromObject(Object value) {
		Boolean booleanVal = null;
		
		if( value != null && StringUtils.isNotBlank(value.toString()) ) {
			if( "Y".equalsIgnoreCase(value.toString()) || "true".equalsIgnoreCase(value.toString()) ) {
				booleanVal = true;
			} else {
				booleanVal = false;
			}
		}
		
		return booleanVal;
	}

	
	public static List<String> getAnnotationPropertyNames(final Class returnType) {
		List<Field> listField = getInheritedFields(returnType);
		Map<Integer, String> propertyNames = new TreeMap<Integer, String>();
		for (Field field : listField) {
			if (field != null) {
				SelectColumn sc = (SelectColumn) field
						.getAnnotation(SelectColumn.class);
				if (sc != null) {
					propertyNames.put(sc.order(), field.getName());
				}
			}
		}
		return new ArrayList<String>(propertyNames.values());
	}
	
	private static Type getCollectionType(Field field) throws Exception {

		Type genericFieldType = field.getGenericType();

		if (genericFieldType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			return fieldArgTypes[0];
		}
		return null;
	}

	
	private static List collectAs(List list, Type type) {

		if (type == Long.class)
		{
			CollectionUtils.transform(list, TRANSFORM_TO_LONG);
		}
		else if (type == Integer.class)
		{
			CollectionUtils.transform(list, TRANSFORM_TO_INTEGER);
		}
		else if (type == Double.class)
		{
			CollectionUtils.transform(list, TRANSFORM_TO_DOUBLE);
		}
		else
		{
			// Same has before
			return list;
		}

		return list;
	}

	static Transformer TRANSFORM_TO_LONG = new Transformer() {
		public Object transform(Object input) {
			return new Long((String) input);
		}
	};

	static Transformer TRANSFORM_TO_INTEGER = new Transformer() {
		public Object transform(Object input) {
			return new Integer((String) input);
		}
	};

	static Transformer TRANSFORM_TO_DOUBLE = new Transformer() {
		public Object transform(Object input) {
			return new Double((String) input);
		}
	};
}

