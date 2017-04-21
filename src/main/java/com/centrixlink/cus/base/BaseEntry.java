package com.centrixlink.cus.base;

import java.lang.reflect.Field;

import net.sf.json.JSONObject;

import com.centrixlink.log.Logging;

public abstract class BaseEntry {
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		Class<? extends BaseEntry> clazz = this.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();
			Object val = null;
			try{
				val = field.get(this);
			} catch (Exception e) {
				Logging.error("use reflect parse field fail, " + e.getMessage());
			}
			obj.put(name, val);
		}
		return obj.toString();
	}
	
	

	

}
