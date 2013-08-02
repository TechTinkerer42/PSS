package org.ets.pss.service;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        if(message.getCustFstNam()==null || message.getCustLstNam()==null)
        {
        	jsonObject.addProperty("cust_name", "");
        }
        else
        {
        jsonObject.addProperty("cust_name", message.getCustFstNam()+" "+message.getCustLstNam());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
			 date  = formatter.parse(message.getTsk().getTaskCloseDate().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        jsonObject.addProperty("task_date", message.getTsk().getTaskCloseDate().toString()); 
        return jsonObject;      
    }
	
}
