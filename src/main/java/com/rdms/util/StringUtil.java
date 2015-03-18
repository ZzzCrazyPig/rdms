package com.rdms.util;

public class StringUtil {
	
	public static boolean isBlank(String str) {
		if(str == null) return true;
		if(str.trim().length() == 0) return true;
		return false;
	}

}
