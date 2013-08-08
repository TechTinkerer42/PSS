package org.ets.pss.service;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ets.pss.persistence.model.AsgndTsk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AsgndTskAdapter implements JsonSerializer<AsgndTsk> {
	@Override
    public JsonElement serialize(AsgndTsk message, Type type, JsonSerializationContext jsc) {
		Date date=null;
        JsonObject jsonObject = new JsonObject();//message.getTsk().getTaskId()
        jsonObject.addProperty("task_name", message.getTsk().getTitle());
        jsonObject.addProperty("customer_id", message.getEtsCust().getCustomerId());
        jsonObject.addProperty("task_id", message.getTsk().getTaskId()); 
        if(message.getEtsCust().getBlcCustomer().getFirstName()==null ||message.getEtsCust().getBlcCustomer().getLastName()==null)
        {
        	jsonObject.addProperty("cust_name", "");
        }
        else
        {
        jsonObject.addProperty("cust_name", message.getEtsCust().getBlcCustomer().getFirstName()+" "+message.getEtsCust().getBlcCustomer().getLastName());
        }
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       
        // SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
      /*  SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
			 date  = myFormat.parse(message.getTsk().getTaskCloseDate().toString());
			
			 System.out.println("the date now is "+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
         try {
			date = (Date)formatter.parse(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(date);    */
        if(message.getSbmtDte()!=null && !message.getSbmtDte().toString().isEmpty())
        {
        Calendar cal = Calendar.getInstance();
        cal.setTime(message.getSbmtDte());
        String formatedDate = (cal.get(Calendar.MONTH)+ 1)+ "/" + cal.get(Calendar.DATE)  + "/" + cal.get(Calendar.YEAR);
        //System.out.println("formatedDate : " + formatedDate);    
        jsonObject.addProperty("task_date", formatedDate); 
        }
        else
        {
        	jsonObject.addProperty("task_date", ""); 
        }
        return jsonObject;      
    }
	
	public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
}
