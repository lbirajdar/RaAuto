package com.raauto.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.raauto.core.exceptions.RaAutoElementNotFound;
import com.raauto.pageobjects.WebPage;

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

            throw new RaAutoElementNotFound(by, locator);

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

    /**
     * Takes the screenshot of a given element. The screenshot taken is stored
     * at Screenshots folder as gif file
     *
     * @param driver
     *            the driver
     * @param currentWindowHandle
     *            the handle of the window on which the element is located
     * @param e
     *            The element for which the screenshot is to be captured
     */

    public static void takeElementScreenshot(WebDriver driver,
            String currentWindowHandle, WebElement e) {

        System.out.println("Taking element measurements for screenshot");
        
        File imageDir = new File("Screenshots");

        if (!imageDir.exists() || !imageDir.isDirectory()) {

            imageDir.mkdir();

        }

        driver.switchTo().window(currentWindowHandle);

        String outputFileName;

        String writeInHtml = "ElementWithoutName";
        String elementId = e.getAttribute("id");
        String elementName = e.getAttribute("name");
        String elementAltText = e.getAttribute("alt");
        String elementText = e.getText();

        if (elementId != null && !elementId.equals("")) {

            writeInHtml = elementId;

        } else if (elementName != null && !elementName.equals("")) {

            writeInHtml = elementName;

        } else if (elementAltText != null && !elementAltText.equals("")) {

            writeInHtml = elementAltText;

        } else if (elementText != null && !elementText.equals("")) {

            writeInHtml = elementText;

        }

        byte[] screenshotBytes = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        BufferedImage fullImg = null;
        try {

            fullImg = ImageIO.read(new ByteArrayInputStream(screenshotBytes));

        } catch (IOException e2) {

            e2.printStackTrace();
        }

        // Crop image as needed

        Point p = e.getLocation();

        System.out.println("The element cordinates are : " + p.x + " & " + p.y
                + " respectively");

        Dimension d = e.getSize();

        System.out.println("Widtht and height of the rectangle are : "
                + d.width + " & " + d.height + " respectively");

        BufferedImage imageToCapture = null;

        if (d.width > 0 && d.height > 0) {

            if (e.getAttribute("type") != null
                    && e.getAttribute("type").equalsIgnoreCase("hidden")) {

                System.out.println("The element is hidden, hence skipping it");

            } else {

                driver.switchTo().window(currentWindowHandle);

                imageToCapture = fullImg.getSubimage(p.x, p.y, d.width,
                        d.height);

            }

            outputFileName = imageDir + "/" + writeInHtml;

            File outputFile = new File(outputFileName + ".gif");

            int fileCounter = 1;
            while (outputFile.exists()) {

                outputFile = new File(outputFileName + fileCounter + ".gif");

                fileCounter++;

            }

            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(outputFile);

                ImageIO.write(imageToCapture, "GIF", outputStream);

                outputStream.close();

            } catch (FileNotFoundException e1) {

                e1.printStackTrace();

            } catch (IOException e1) {

                e1.printStackTrace();

            }

        }

    }

    /**
     * Takes the screenshots for every element on the page with given tag. The
     * screenshot taken is stored at Screenshots folder as gif file
     *
     * @param driver
     *            the driver
     * @param currentWindowHandle
     *            the handle of the window on which the element is located
     * @param tagToRead
     *            The tag to read e.g. a, button
     * 
     * @return totalElements - the total count of elements found with given tag
     */

    public static int takeElementScreenshotsForTag(WebDriver driver,

    String currentWindowHandle, String tagToRead) {

        driver.switchTo().window(currentWindowHandle);

        List<WebElement> elementsList = driver.findElements(By
                .tagName(tagToRead));

        int totalElements = 0;

        for (WebElement e : elementsList) {

            takeElementScreenshot(driver, currentWindowHandle, e);

            totalElements++;

        }

        System.out.println("There are total of " + totalElements
                + "elements found with tag=" + tagToRead);

        return totalElements;

    }

}
