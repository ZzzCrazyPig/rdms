package com.rdms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {
	
	public final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public Object processArrayValue(Object value, JsonConfig config) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig config) {
		return process(value);
	}
	
	private Object process(Object value) {
		if(value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			return sdf.format(value);
		}
		return value == null ? "" : value.toString();
	}

}
