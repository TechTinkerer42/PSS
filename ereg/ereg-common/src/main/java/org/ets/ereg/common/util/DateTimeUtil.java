package org.ets.ereg.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {
	
	private static Logger log = LoggerFactory.getLogger(DateTimeUtil.class);
	
	private static SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat dateFormatYMDHma = new SimpleDateFormat("yyyy/MM/dd/h/m/a");
	
	private static final Map<String, String> timeZoneMap;
	
    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("EST", "America/New_York");
        map.put("CST", "America/Chicago");
        map.put("MST", "America/Denver");
        map.put("PST", "America/Los_Angeles");
        timeZoneMap = Collections.unmodifiableMap(map);
    }
	
	public static Date getDateTime(Date date, String hour, String minute, String a) {
		try {
			date = dateFormatYMDHma.parse(dateFormatYMD.format(date) + "/" + hour
					+ "/" + minute + "/" + a);
		} catch (ParseException e) {
			log.error("Error when parse date: {}", e.toString());
		}
		return date;
	}
	
	public static Date getDate(Date date){
		try {
			date=dateFormatYMD.parse(dateFormatYMD.format(date));
		} catch (ParseException e) {
			log.error("failed to parse the date: {} ",date,e);
		}
		return date;
	}
	
	public static float getTimeZoneOffset(String timeZoneCode, Date date) {
		if (timeZoneMap.get(timeZoneCode) != null) {
			timeZoneCode = timeZoneMap.get(timeZoneCode);
		}
		TimeZone tz = TimeZone.getTimeZone(timeZoneCode);
		Calendar calendar = Calendar.getInstance(tz);
		calendar.setTime(date);
        float offset = (float) (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / 1000 / 60 / 60;
		log.info("Getting time zone offset: time zone code: {}, date: {}, offset: {}.", new Object[] { timeZoneCode, date, offset });
        return offset;
	}
	
}
