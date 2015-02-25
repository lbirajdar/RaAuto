package com.waas.core.exceptions;

import org.openqa.selenium.NoSuchElementException;

/**
 * The Class WAaaSElementNotFound.
 * This exception is thrown by {@link ActionHandler} class when they
 * fail to find an element with given descriptor and after waiting for desired duration
 * 
 * @author Laxmikant Birajdar
 */
public class WAaaSElementNotFound extends NoSuchElementException {

	/** The error. */
	String error = "";

	/**
	 * Instantiates a new WAaasElementNotFound exception
	 *
	 * @param by
	 *            the by
	 * @param locator
	 *            the locator
	 */
	public WAaaSElementNotFound(String by, String locator) {

		super("Ater waiting for defulat time, failed to find element with by="
				+ by + " & locator=" + locator);
		error = "Ater waiting for defulat time, failed to find element with by="
				+ by + " & locator=" + locator;

	}

	public String getError() {
		return error;
	}

}
