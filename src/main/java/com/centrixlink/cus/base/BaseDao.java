package com.centrixlink.cus.base;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.centrixlink.log.Logging;

public abstract class BaseDao {
	
	protected String buildSql(String sql, Map<? extends Object, ? extends Object> where) {
		
		VelocityEngine engine = new VelocityEngine(); 
		VelocityContext context = new VelocityContext(where);  
		StringWriter writer = new StringWriter();  
	    engine.evaluate(context, writer, "", sql); 
	    
	    String res = writer.toString();
	    
	    Logging.info(res, Logging.TYPE_QUERY);
		
		return res;
		
	}
	
	
}
