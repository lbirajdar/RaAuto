package com.waas.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.waas.core.exceptions.WAaaSElementNotFound;

import com.waas.pageobjects.WebPage;

/**
 * The Class ActionHandler. This is a helper class for performing various
 * actions on a webpage which also helps in locating the elements 
 * 
 * @author Laxmikant Birajdar
 */
public class ActionHandler {

	/** The source element. */
	public static WebElement sourceElement;

	/**
	 * Locates the element with given properties
	 *
	 * @param driver
	 *            the driver
	 * @param by
	 *            the by - (className, cssSelector, id, linkText, name,
	 *            partialLinkText, tagName, xpath)
	 * @param locator
	 *            the locator - the HTML value of by
	 * @return the web element
	 */
	public static WebElement locateElement(WebDriver driver, String by,
			String locator) {

		// Ensure the page is loaded before moving to any operation
		WebPage page = PageFactory.initElements(driver, WebPage.class);
		try {
			page.isPageReady(by, locator);
		} catch (TimeoutException e) {
			throw new WAaaSElementNotFound(by, locator);

		}
		WebElement currentElement = null;

		if (by.equalsIgnoreCase("className")) {
			currentElement = driver.findElement(By.className(locator));
		} else if (by.equalsIgnoreCase("cssSelector")) {
			currentElement = driver.findElement(By.cssSelector(locator));
		} else if (by.equalsIgnoreCase("id")) {
			currentElement = driver.findElement(By.id(locator));
		} else if (by.equalsIgnoreCase("linkText")) {
			currentElement = driver.findElement(By.linkText(locator));
		} else if (by.equalsIgnoreCase("name")) {
			currentElement = driver.findElement(By.name(locator));
		} else if (by.equalsIgnoreCase("partialLinkText")) {
			currentElement = driver.findElement(By.partialLinkText(locator));
		} else if (by.equalsIgnoreCase("tagName")) {
			currentElement = driver.findElement(By.tagName(locator));
		} else if (by.equalsIgnoreCase("xpath")) {
			currentElement = driver.findElement(By.xpath(locator));
		}

		return currentElement;

	}

	/**
	 * Gets the table element list.
	 *
	 * @param driver
	 *            the driver
	 * @param table_element
	 *            the table_element
	 * @param by
	 *            the by
	 * @param locator
	 *            the locator
	 * @return the table elements
	 */
	public static List<WebElement> getTableElements(WebDriver driver,
			WebElement table_element, String by, String locator) {

		List<WebElement> tableElements = null;

		if (by.equalsIgnoreCase("className")) {
			tableElements = table_element.findElements(By.className(locator));
		} else if (by.equalsIgnoreCase("cssSelector")) {
			tableElements = table_element.findElements(By.cssSelector(locator));
		} else if (by.equalsIgnoreCase("id")) {
			tableElements = table_element.findElements(By.id(locator));
		} else if (by.equalsIgnoreCase("linkText")) {
			tableElements = table_element.findElements(By.linkText(locator));
		} else if (by.equalsIgnoreCase("name")) {
			tableElements = table_element.findElements(By.name(locator));
		} else if (by.equalsIgnoreCase("partialLinkText")) {
			tableElements = table_element.findElements(By
					.partialLinkText(locator));
		} else if (by.equalsIgnoreCase("tagName")) {
			tableElements = table_element.findElements(By.tagName(locator));
		} else if (by.equalsIgnoreCase("xpath")) {
			tableElements = table_element.findElements(By.xpath(locator));
		}

		return tableElements;

	}

	/**
	 * Select by value.
	 *
	 * @param select
	 *            the select
	 * @param choice
	 *            the choice
	 * @return the string
	 */
	private static String selectByValue(Select select, String choice) {
		String isSelected = "NO_ERROR";
		try {
			// Try selecting with Value
			select.selectByValue(choice);
			System.out.println("Selected by Value");
		} catch (Exception e) {
			System.out.println("Failed to select with Value.");
			isSelected = e.getMessage();
		}

		return isSelected;
	}

	/**
	 * Select by index.
	 *
	 * @param select
	 *            the select
	 * @param choice
	 *            the choice
	 * @return the string
	 */
	private static String selectByIndex(Select select, String choice) {
		String isSelected = "NO_ERROR";
		try {
			// Try selecting with Value
			select.selectByIndex(Integer.parseInt(choice));
			System.out.println("Selected by Index");
		} catch (Exception e) {
			System.out.println("Failed to select with Index.");
			isSelected = e.getMessage();
		}

		return isSelected;
	}

	/**
	 * Select by visible text.
	 *
	 * @param select
	 *            the select
	 * @param choice
	 *            the choice
	 * @return the string
	 */
	private static String selectByVisibleText(Select select, String choice) {
		String isSelected = "NO_ERROR";
		try {
			// Try selecting with Value
			select.selectByVisibleText(choice);
			System.out.println("Selected by Visible Text");
		} catch (Exception e) {
			System.out.println("Failed to select with VisibleText.");
			isSelected = e.getMessage();
		}

		return isSelected;
	}

	/**
	 * Select.
	 *
	 * @param inputData
	 *            the input data
	 * @param currentElement
	 *            the current element
	 * @throws Exception
	 *             the exception
	 */
	public static void select(String inputData, WebElement currentElement)
			throws Exception {

		Select select;
		select = new Select(currentElement);
		String errorText = "Error while selecting an element from drop down";
		final String NO_ERROR = "NO_ERROR";

		if (selectByValue(select, inputData).equalsIgnoreCase(NO_ERROR)) {

		} else if (selectByVisibleText(select, inputData).equalsIgnoreCase(
				NO_ERROR)) {

		} else if (selectByIndex(select, inputData).equalsIgnoreCase(NO_ERROR)) {

		} else {

			throw new Exception(errorText);

		}

	}

	/**
	 * Gets the value in table.
	 *
	 * @param driver
	 *            the driver
	 * @param inputData
	 *            the input data
	 * @param findByAttribute
	 *            the find by attribute
	 * @param findByAttributeValue
	 *            the find by attribute value
	 * @return the value in table
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int[] getValueInTable(WebDriver driver, String inputData,
			String findByAttribute, String findByAttributeValue)
			throws IOException {

		int[] arr = new int[3];
		int temp = 1;
		WebElement table_element = ActionHandler.locateElement(driver,
				findByAttribute, findByAttributeValue);
		System.out.println("Found the table.");
		List<WebElement> tr_collection = ActionHandler.getTableElements(driver,
				table_element, findByAttribute, findByAttributeValue);
		System.out
				.println("Searching for the " + inputData + "value in table.");
		System.out.println("NUMBER OF ROWS IN THIS TABLE = "
				+ tr_collection.size());
		Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		int row_num = 1, col_num, count = 0;

		for (WebElement trElement : tr_collection) {

			/** To print rows and column */
			List<WebElement> td_collection = trElement.findElements(By
					.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 1;
			for (WebElement tdElement : td_collection) {
				System.out.println("row # " + row_num + ", col # " + col_num
						+ "text=" + tdElement.getText());
				if (tdElement.getText().equalsIgnoreCase(inputData)) {
					System.out.println(" inside if = " + row_num + "-"
							+ col_num);
					hashMap.put(row_num, col_num);
					if (temp == 1) {
						arr[1] = row_num;
						arr[2] = col_num;
					}
				}
				col_num++;
			}
			row_num++;
		}
		count = hashMap.size();
		if (count >= 1) {
			Set hashSet = hashMap.entrySet();
			Iterator itr = hashSet.iterator();
			while (itr.hasNext()) {
				System.out
						.println("Cell information for found value in table(Rows/Cols):"
								+ itr.next());
			}
		}
		arr[0] = count;
		return arr;
	}

	/**
	 * Wait time.
	 *
	 * @param seconds
	 *            the seconds
	 */
	public static void waitTime(int seconds) {
		System.out.println("Waitng for " + seconds + " seconds...");
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			System.out.println("Error in thread sleep.");
		}
	}

}
