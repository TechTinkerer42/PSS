package org.ets.ereg.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHandler {

	private static Logger log = LoggerFactory.getLogger(DateHandler.class);
	
	public static Date convertToDate(int year,int month, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(1900+year, month, day);		
		return calendar.getTime();
	}
	
	public static Date getCurrentDate(){		
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();		
	}
	
	public static String convertDateToString(Date date){
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String strDate = df.format(date);		
		return strDate;
	}	
	
	public static Calendar convertStringToCalendar(String strDate){
		
		Calendar cal=null;
		try { 
			
		 DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		 Date date = (Date)formatter.parse(strDate); 
		 cal=Calendar.getInstance();
		 cal.setTime(date);
		 
		}catch (ParseException e){		
			log.error("unable to parse string {} to calendar ",strDate,e);
		}
		
		return cal;
	}
	
	public static String convertDateToStringyyyyMMdd(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
}
