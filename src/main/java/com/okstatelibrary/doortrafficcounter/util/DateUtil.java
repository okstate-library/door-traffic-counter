package com.okstatelibrary.doortrafficcounter.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.*;

public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//	
//	DateUtil.getTodayDate()
//	
//	LocalDate now = LocalDate.now();
//	LocalDate thirty = now.minusDays( 30 );

	public static String get7DaysBeforeTodayDate() {

		return LocalDate.now().plusDays(-7).toString();
	}

	public static Date getTodayDate() {
		long millis = System.currentTimeMillis();

		return new java.sql.Date(millis);
	}

	public static int getCurrentHour() {

		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.HOUR_OF_DAY);

	}

	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	public static java.util.Date getPreviousDate(java.util.Date date) {

		try {
			String dateString = date.toString();

			java.util.Date myDate = (java.util.Date) dateFormat.parse(dateString);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, -1);

			return (java.util.Date) calendar.getTime();
		} catch (Error er) {
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}

	}

	public static int compareDates(Date date1) {
		return dateFormat.format(date1).compareTo(dateFormat.format(getTodayDate()));
	}

	public static Date getLongDate(String date) {
		try {
			return longDateFormat.parse(date + " 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
