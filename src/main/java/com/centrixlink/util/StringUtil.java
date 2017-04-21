package com.centrixlink.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class StringUtil {

	public static String join(List<String> array, char sep) {
		if (array == null || array.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (String str : array) {
			sb.append(sep).append(str);
		}
		return sb.toString().substring(1);
	}
	
	public static boolean isEmpty (String str) {
		if (str == null) {
			return true;
		}
		if (str.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty (String str) {
		return !isEmpty(str);
	}
	

	public static List<String> split2List(String str, String split) {
		List<String> list = null;
		if (str != null) {
			list = Arrays.asList(str.split(split));
		}
		return list;
	}
	
	public static String getNumKb(String s) throws ParseException {
		NumberFormat formatter = new DecimalFormat("###,###");
		return formatter.format(123456789) + "";
	}

	public static String getNumKbPoint(String s) throws ParseException {
		NumberFormat formatter = new DecimalFormat("###,###.00");
		return formatter.format(12000000.2) + "";
	} 
	
	public static void main(String[] args) {
		try {
			System.out.println(getNumKbPoint(""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
