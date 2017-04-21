package com.centrixlink.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.centrixlink.log.Logging;

public class Utils {
	
	public static String randomString() {
		char[] str = "1234567890qwertyuiopasdfghjklzxcvbnm".toCharArray();
		int len = str.length;
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<18; i++) {
			int index = random.nextInt(len-1); 
			sb.append(str[index]);
		}
		return sb.toString();
	}
	
	public static String dateFormat(String date, String format) {
		SimpleDateFormat simp = new SimpleDateFormat(format);
		Date dt = null;
		try {
			dt = simp.parse(date);
		} catch (ParseException e) {
			Logging.error("date time parse fail, " + e.getMessage());
		}
		
		return simp.format(dt);
		
		
	}

}
