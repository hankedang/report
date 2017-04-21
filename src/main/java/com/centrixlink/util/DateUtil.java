package com.centrixlink.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.centrixlink.xreport.domain.web.DateSpan;

public class DateUtil {

	public static final String SPAN_CURR = "curr";
	public static final String SPAN_MONTH = "month";
	public static final String SPAN_YEAR = "year";
	public static final String SPAN_BEFORE = "before";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	public static synchronized final DateSpan getDateSpan(String type) {
		Calendar now = Calendar.getInstance();
		String start = null, end = null;

		if (SPAN_CURR.equals(type)) {
			start = end = SDF.format(now.getTime());
		} else if (SPAN_MONTH.equals(type)) {
			now.add(Calendar.DAY_OF_MONTH, -1);
			end = SDF.format(now.getTime());
			now.set(Calendar.DAY_OF_MONTH, 1);
			start = SDF.format(now.getTime());
		} else if (SPAN_YEAR.equals(type)) {
			now.add(Calendar.DAY_OF_MONTH, -1);
			end = SDF.format(now.getTime());
			now.set(Calendar.MONTH, 0);
			start = SDF.format(now.getTime());
		} else {// default before
			now.add(Calendar.DAY_OF_MONTH, -1);
			start = end = SDF.format(now.getTime());
		}
		return new DateSpan(start, end);
	}

	public static synchronized final boolean isToday(DateSpan dateSpan) {
		if (isOneday(dateSpan)) {
			String start = dateSpan.getStartDate();
			String nowStr = SDF.format(new Date());
			if (nowStr.equals(start)) {
				return true;
			}
		}
		return false;
	}

	public static synchronized final boolean isCurMonth(DateSpan dateSpan) {
		DateSpan monthSpan = getDateSpan("month");
		if (dateSpan.isEqual(monthSpan)) {
			return true;
		}
		return false;
	}
	
	public static synchronized final boolean isOneday(DateSpan dateSpan) {
		String start = dateSpan.getStartDate();
		String end = dateSpan.getEndDate();
		if (!start.equals(end)) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

		DateSpan dateSpan = new DateSpan("2016-07-01", "2016-07-21");

		System.out.println(isCurMonth(dateSpan));

	}
}
