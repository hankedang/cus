package com.centrixlink.cus.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseController {
	
	protected Map<Object, Object> newWhere(HttpServletRequest req) {
		Map<Object,Object> where = new HashMap<Object, Object>();
		Enumeration<String> names = req.getParameterNames();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			String val = req.getParameter(name);
			where.put(name, val);
		}
		return where;
	}
	
	protected void writeJSON(HttpServletResponse resp, Object result) throws IOException {
		resp.setCharacterEncoding(Constants.CHARACTER_ENCODING);
		Writer writer = resp.getWriter();
		if (result instanceof String) {
			writer.write(String.valueOf(result));
		} else if(result instanceof Map) {
			writer.write(JSONObject.fromObject(result).toString());
		} else if(result instanceof List) {
			writer.write(JSONArray.fromObject(result).toString());
		} else {
			writer.write(result.toString());
		}
	}

}
