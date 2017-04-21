package com.centrixlink.log;

import org.apache.log4j.Logger;

public class LogService {

	private static Logger log = Logger.getLogger(LogService.class);
	
	public static String TYPE_AUTH = "[SSO]";
	public static String TYPE_INIT = "[INIT]";
	public static String TYPE_QUERY = "[QUERY]";
	public static String TYPE_ACCESS = "[ACCESS]";

	public static void info(String msg, String logType) {
		log.info(logType + msg);
	}

	public static void debug(String msg, String logType) {
		log.debug(logType + msg);
	}

	public static void error(String msg, String logType) {
		log.error(logType + msg);
	}
	
	public static void fatal(String msg, String logType) {
		log.fatal(logType + msg);
	}

}
