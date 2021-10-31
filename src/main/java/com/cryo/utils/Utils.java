package com.cryo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

	public static String getTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(new Date());
	}
}
