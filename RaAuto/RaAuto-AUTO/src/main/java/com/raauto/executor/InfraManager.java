package com.raauto.executor;

import com.raauto.core.ListenerPool;

/**
 * The Class InfraManager.
 *
 * @author Laxmikant Birajdar
 */
public class InfraManager {

	// public static WebDriver driver;
	// public static Properties runnerProperties;
	// public static String meta_data_folder;
	// static HashMap<String, Logger> suiteLoggerMap = new HashMap<String,
	// Logger>();
	// public static Logger SUITE_LOGGER;
	// public static String logFileName;
	// public static String report_folder;
	/** The pool instance. */
	public static ListenerPool poolInstance;

	/*
	 * public static Logger initializeLogger(String suiteName) {
	 * 
	 * Logger logger = Logger.getLogger(InfraManager.class);
	 * 
	 * String temp = new SimpleDateFormat("yyyy-MMM-dd hh-mm-ss'.log'")
	 * .format(new Date());
	 * 
	 * FileAppender fa = null;
	 * 
	 * logFileName = suiteName + "-" + temp;
	 * 
	 * try {
	 * 
	 * fa = new FileAppender(new PatternLayout(), "./logs/" + suiteName + "-" +
	 * temp, false);
	 * 
	 * } catch (IOException e) {
	 * 
	 * System.out.println("Unable to initialize file appender.");
	 * 
	 * }
	 * 
	 * logger.addAppender(fa);
	 * 
	 * logger.info("Logger is initialized...");
	 * 
	 * suiteLoggerMap.put(suiteName, logger);
	 * 
	 * return logger; }
	 * 
	 * public static Logger getLogger(String suiteName) {
	 * 
	 * return suiteLoggerMap.get(suiteName);
	 * 
	 * }
	 */

}
