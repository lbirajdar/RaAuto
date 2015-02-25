package com.waas.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.WebDriver;

import com.waas.executor.InfraManager;

/**
 * The Class ListenerPool. This class provides the provision for running the
 * suites through different sessions and track the results by session
 * 
 * @author Laxmikant Birajdar
 */
public class ListenerPool {

	/** The listener map. */
	private Map<String, Object> listenerMap = new HashMap<String, Object>();

	/** The instance. */
	private static ListenerPool instance = new ListenerPool();

	public static ListenerPool getInstance() {

		return instance;

	}

	/**
	 * Creates the listener.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the result listener
	 */
	public ResultListener createListener(String sessionId) {

		ResultListener listener = new ResultListener();

		listenerMap.put(sessionId + "_listener", listener);

		return listener;

	}

	/**
	 * Gets the listener.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the listener
	 */
	public ResultListener getListener(String sessionId) {

		return (ResultListener) listenerMap.get(sessionId + "_listener");

	}

	/**
	 * Sets the runner properties.
	 *
	 * @param sessionId
	 *            the session id
	 * @param runnerProp
	 *            the runner prop
	 */
	public void setRunnerProperties(String sessionId, Properties runnerProp) {

		listenerMap.put(sessionId + "_runnerProp", runnerProp);

	}

	/**
	 * Gets the runner properties.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the runner properties
	 */
	public Properties getRunnerProperties(String sessionId) {

		return (Properties) listenerMap.get(sessionId + "_runnerProp");

	}

	/**
	 * Sets the driver.
	 *
	 * @param sessionId
	 *            the session id
	 * @param driver
	 *            the driver
	 */
	public void setDriver(String sessionId, WebDriver driver) {

		listenerMap.put(sessionId + "_wDriver", driver);

	}

	/**
	 * Gets the driver.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the driver
	 */
	public WebDriver getDriver(String sessionId) {

		return (WebDriver) listenerMap.get(sessionId + "_wDriver");

	}

	/**
	 * Sets the meta data folder.
	 *
	 * @param sessionId
	 *            the session id
	 * @param metaDataFolder
	 *            the meta data folder
	 */
	public void setMetaDataFolder(String sessionId, String metaDataFolder) {

		listenerMap.put(sessionId + "_metaFolder", metaDataFolder);

	}

	/**
	 * Gets the meta data folder.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the meta data folder
	 */
	public String getMetaDataFolder(String sessionId) {

		return (String) listenerMap.get(sessionId + "_metaFolder");

	}

	/**
	 * Sets the result folder.
	 *
	 * @param sessionId
	 *            the session id
	 * @param basePath
	 *            the base path
	 */
	public void setResultFolder(String sessionId, String basePath) {

		SimpleDateFormat dateFormatDir = new SimpleDateFormat(
				"MM-dd-yy_HH-mm-ss");

		String resultOutputFolder;

		if (basePath != null)

			resultOutputFolder = basePath + File.separator
					+ dateFormatDir.format(new Date());

		else

			resultOutputFolder = "output_result" + File.separator
					+ dateFormatDir.format(new Date());

		System.out
				.println("Results will be stored at : " + resultOutputFolder);

		listenerMap.put(sessionId + "_resultFolder", resultOutputFolder);

	}

	/**
	 * Gets the result folder.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the result folder
	 */
	public String getResultFolder(String sessionId) {

		return (String) listenerMap.get(sessionId + "_resultFolder");

	}

	/**
	 * Sets the logger.
	 *
	 * @param sessionId
	 *            the session id
	 * @param suiteName
	 *            the suite name
	 */
	public void setLogger(String sessionId, String suiteName) {

		Logger logger = Logger.getLogger(InfraManager.class);

		String temp = new SimpleDateFormat("yyyy-MMM-dd hh-mm-ss'.log'")
				.format(new Date());

		FileAppender fa = null;

		try {

			fa = new FileAppender(new PatternLayout(), "./logs/" + suiteName
					+ "-" + temp, false);

		} catch (IOException e) {

			System.out.println("Unable to initialize file appender.");

		}

		logger.addAppender(fa);

		logger.info("Logger is initialized...");

		listenerMap.put(suiteName + sessionId + "_logger", logger);

	}

	/**
	 * Gets the logger.
	 *
	 * @param sessionId
	 *            the session id
	 * @param suiteName
	 *            the suite name
	 * @return the logger
	 */
	public Logger getLogger(String sessionId, String suiteName) {

		return (Logger) listenerMap.get(suiteName + sessionId + "_logger");

	}

}
