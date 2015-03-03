package com.raauto.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class WebPage. Every Page is extended from this class. This class defines
 * some basic properties to ensure that the page is loaded correctly
 *
 * @author Laxmikant Birajdar
 */
public class WebPage {

	/** The default medium wait time. */
	public static int DEFAULT_MEDIUM_WAIT_TIME = 30;

	/** The driver. */
	private WebDriver driver;

	/**
	 * Instantiates a new web page.
	 *
	 * @param driver
	 *            the driver
	 */
	public WebPage(WebDriver driver) {
		this.driver = driver;

	}

	/**
	 * Checks if is page ready.
	 *
	 * @param by
	 *            the by
	 * @param locator
	 *            the locator
	 * @return true, if is page ready
	 */
	public boolean isPageReady(final String by, final String locator) {

		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_MEDIUM_WAIT_TIME);

		wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				d = driver;
				return elementExists(by, locator);
			}
		});

		return true;

	}

	/**
	 * Checks if the Element exists and returns the WebElement if it does
	 *
	 * @param by
	 *            the by
	 * @param locator
	 *            the location identifier
	 * @return the webElement if it exists
	 */
	public WebElement elementExists(String by, String locator) {

		WebElement element = null;
		Boolean flag = true;

		if (by.equalsIgnoreCase("className")) {
			try {
				element = driver.findElement(By.className(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("cssSelector")) {
			try {
				element = driver.findElement(By.cssSelector(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("id")) {
			try {
				element = driver.findElement(By.id(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("linkText")) {
			try {
				element = driver.findElement(By.linkText(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("name")) {
			try {
				element = driver.findElement(By.name(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("partialLinkText")) {
			try {
				element = driver.findElement(By.partialLinkText(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("tagName")) {
			try {
				element = driver.findElement(By.tagName(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}
		}
		if (by.equalsIgnoreCase("xpath")) {
			try {
				element = driver.findElement(By.xpath(locator));
			} catch (NoSuchElementException ex) {
				// System.out.println("Element not found");
				flag = false;
			}

		} else {
			flag = false;
		}

		if (flag) {

			System.out.println("Found the element  : with locator=" + by
					+ " & value=" + locator);

		} else {
			System.out.println("Not able to find element : with locator=" + by
					+ " & value=" + locator);

		}

		return element;

	}

}
