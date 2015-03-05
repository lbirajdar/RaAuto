package com.raauto.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.util.IteratorIterable;

/**
 * The Class UserInputContainer. The class helps with user inputs provided in
 * the properties file
 */
public class UserInputContainer {

	/** The user data prop. */
	private InputStream userDataProp;

	/** The user data. */
	private HashMap<String, String> userData = new HashMap<String, String>();

	/** The xml meta data doc. */
	private Document xmlMetaDataDoc;

	/** The input reg ex. */
	private final String INPUT_REG_EX = "(.*?)\\$\\{([\\w.]+)\\}";

	/** The user properties. */
	private Properties userProperties = new Properties();

	/**
	 * Instantiates a new user input container.
	 *
	 * @param xmlMetaDataDoc
	 *            the xml meta data doc
	 * @param userDataPropertyFile
	 *            the user data property file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public UserInputContainer(Document xmlMetaDataDoc,
			String userDataPropertyFile) throws IOException {

		userDataProp = new FileInputStream(userDataPropertyFile);
		userProperties.load(userDataProp);

		this.xmlMetaDataDoc = xmlMetaDataDoc;

	}

	/**
	 * Prepares the user input by replacing the variables in the xml (masked
	 * values).
	 */
	public void prepareUserInputdata() {
		IteratorIterable<Element> cIterator = xmlMetaDataDoc
				.getDescendants(new ElementFilter());
		String lineValue;
		String inputRegEx = INPUT_REG_EX;
		if (cIterator != null) {
			while (cIterator.hasNext()) {
				lineValue = cIterator.next().getValue();
				if (lineValue.matches(inputRegEx)) {
					addKeyValueToHashMap(lineValue);
				}
			}
		}
	}

	/**
	 * Adds the key value to hash map.
	 *
	 * @param line
	 *            the line
	 */
	private void addKeyValueToHashMap(String line) {
		Pattern xmlVariablePatter = Pattern.compile(INPUT_REG_EX);
		Matcher substitutionMatcher = xmlVariablePatter.matcher(line);

		StringBuffer buffer;
		if (substitutionMatcher.find()) {
			buffer = new StringBuffer();
			int lastLocation = 0;
			do {
				// Find prefix, preceding ${var} construct
				String prefix = substitutionMatcher.group(1);
				buffer.append(prefix);
				// Retrieve value of variable
				String key = substitutionMatcher.group(2);
				//System.out.println("Key:" + key);
				// String value = getValue(key);
				String value = userProperties.getProperty(key);
				buffer.append(value);

				userData.put(key, value);

				// Update lastLocation
				lastLocation = substitutionMatcher.end();
			} while (substitutionMatcher.find(lastLocation));
			// Append final segment of the string
			buffer.append(line.substring(lastLocation));

			//System.out.println(buffer.toString());
		} else {
			//System.out.println(line);
		}

	}

	/**
	 * Gets the masked data.
	 *
	 * @param key
	 *            the key
	 * @param logger
	 *            the logger
	 * @return the masked data
	 */
	public String getMaskedData(String key, Logger logger) {

		logger.info("Looking for in getMaskedData:" + key);

		String maskedText = key;
		// replace if the inputdata found to be a key
		if (key != null && !(key.equals("")) && key.matches(INPUT_REG_EX)) {
			String lookUpkey = key.replace("${", "");
			lookUpkey = lookUpkey.replace("}", "");
			logger.info("UserInputContainer - Looking for input data with key :"
					+ lookUpkey);
			maskedText = getUserDataFromProperties().get(lookUpkey);
			logger.info("UserInputContainer - maskedText :" + maskedText);
		}
		return maskedText;
	}

	/**
	 * @return HashMap with userData
	 */
	public HashMap<String, String> getUserDataFromProperties() {
		return userData;
	}

	/**
	 * Prints the me.
	 */
	private void printMe() {

		System.out.println("Getting value from the container username:"
				+ userData.get("username"));
		System.out.println("Getting value from the container password:"
				+ userData.get("password"));

	}
}
