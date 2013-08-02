package org.ets.ereg.web.taglib;

public class TagUtils {
	public static boolean instanceOf(Object obj, String className){
		boolean isInstanceOf;
		try{
			//TODO : any issues with class loading?
			isInstanceOf = Class.forName(className).isInstance(obj);
		}catch(ClassNotFoundException cnfe){
			isInstanceOf = false;
		}
		
		return isInstanceOf;
	}
}
