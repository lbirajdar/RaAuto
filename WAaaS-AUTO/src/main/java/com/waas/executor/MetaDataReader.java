package com.waas.executor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import com.waas.core.exceptions.WAaaSElementNotFound;
import com.waas.jaxb.Context;
import com.waas.jaxb.CorrectiveStep;
import com.waas.jaxb.ExecutionEvent;
import com.waas.jaxb.TestCase;
import com.waas.jaxb.VerificationSteps;
import com.waas.jaxb.WAaasMetaData;
import com.waas.pageobjects.WebPage;
import com.waas.utils.ActionHandler;
import com.waas.utils.UserInputContainer;
import com.waas.utils.UserOutput;

/**
 * This class reads the provided xml & handles the test execution flow This also
 * provides some helper methods to read from the suite xml file
 * 
 * @author Laxmikant Birajdar
 */

public class MetaDataReader {

	/** The meta_data_file. */
	private String meta_data_file;

	/** The meta_data_document. */
	private Document meta_data_document;
	// private Suite testSuite;
	/** The suite tets. */
	private List<TestCase> suiteTests;

	/** The test_data_file. */
	private String test_data_file;

	/** The user output writer. */
	private UserOutput userOutputWriter;

	/** The input_container. */
	private UserInputContainer input_container;

	/** The logger. */
	Logger logger;

	/**
	 * Instantiates a new meta data reader.
	 *
	 * @param theXml
	 *            the the xml
	 * @param logger
	 *            the logger
	 */
	public MetaDataReader(String theXml, Logger logger) {
		meta_data_file = theXml;
		JAXBContext jc;
		this.logger = logger;

		try {

			jc = JAXBContext.newInstance(WAaasMetaData.class);
			WAaasMetaData waasMeta = (WAaasMetaData) jc.createUnmarshaller()
					.unmarshal(new File(meta_data_file));
			suiteTests = (List<TestCase>) waasMeta.getSuite().getTestCases();

			SAXBuilder builder = new SAXBuilder();
			meta_data_document = builder.build(new File(meta_data_file));

			userOutputWriter = new UserOutput(meta_data_file);

		} catch (JAXBException e) {

			logger.error("Unable to read xml \n" + e.getLocalizedMessage());
			e.printStackTrace();

		} catch (IOException e) {

			logger.error("Unable to locate xml \n" + e.getLocalizedMessage());
			e.printStackTrace();

		} catch (JDOMException e) {

			logger.error("Unable to locate xml \n" + e.getLocalizedMessage());
			e.printStackTrace();

		}

	}

	// Get all the tests inside the suite
	public Collection<TestCase> getTests() {

		return suiteTests;
	}

	// Return the tests where isCorrective=false - we consider them as tests
	public Collection<TestCase> getTestsToRun() {

		List<TestCase> testsToRun = new ArrayList<TestCase>();
		Iterator<TestCase> i = suiteTests.iterator();
		while (i.hasNext()) {
			TestCase t = i.next();
			boolean isCorrective = t.getIsCorrective();
			if (!isCorrective)
				testsToRun.add(t);
		}

		return (Collection<TestCase>) testsToRun;
	}

	// To prepare the user input by reading the xml file
	/**
	 * Prepare user input.
	 */
	private void prepareUserInput() {

		try {

			input_container = new UserInputContainer(meta_data_document,
					test_data_file);
		} catch (IOException e) {

			logger.error("Error reading the test data file" + test_data_file
					+ "\n " + e.getLocalizedMessage());
			e.printStackTrace();

		}

		input_container.prepareUserInputdata();

	}

	// prepares the ordered list considering depends on chain
	/**
	 * Gets the all dependent tests.
	 *
	 * @param testName
	 *            the test name
	 * @return the all dependent tests
	 */
	private List<TestCase> getAllDependentTests(String testName) {

		List<TestCase> allDependentTests = new ArrayList<TestCase>();
		TestCase lastStep = getTestWithName(suiteTests, testName);
		allDependentTests.add(0, lastStep);
		String stepInputDataFile = lastStep.getTestDataFile();
		if (stepInputDataFile != null && !stepInputDataFile.equals("")) {
			test_data_file = stepInputDataFile;
			prepareUserInput();
		}

		Iterator<TestCase> i = suiteTests.iterator();
		int counter = 1;
		while (i.hasNext()) {
			// This step is just moving the iterator to next step
			i.next();

			String currentStepName = lastStep.getName();
			String dependsOn = lastStep.getDependsOn();
			while (dependsOn != null && !dependsOn.equals("")) {

				logger.info("Testcase :" + currentStepName + "- Depends on - "
						+ dependsOn);

				lastStep = getTestWithName(suiteTests, dependsOn);
				dependsOn = lastStep.getDependsOn();

				allDependentTests.add(counter, lastStep);

				counter++;
			}

		}
		logger.info("Total size of the array with dependencies = "
				+ allDependentTests.size());

		return allDependentTests;

	}

	// Gets a test with a give name
	/**
	 * Gets the test with name.
	 *
	 * @param allTests
	 *            the all tests
	 * @param stepName
	 *            the step name
	 * @return the test with name
	 */
	private TestCase getTestWithName(List<TestCase> allTests, String stepName) {

		Iterator<TestCase> i = allTests.iterator();
		TestCase step, findStep = null;

		while (i.hasNext()) {

			step = (TestCase) i.next();

			if (step.getName().equals(stepName)) {
				findStep = step;
				logger.info("Found Testcase with name : " + stepName);

				break;
			}

		}

		return findStep;
	}

	/**
	 * Execute test - This method outlines the execution flow of a test case It
	 * does three main steps 1. Perform given actions 2. Verify actual and
	 * expected results 3. Perform corrective actions (if any)
	 * 
	 * @param testName
	 *            the test name
	 * @param driver
	 *            the driver
	 * @throws Exception
	 *             the exception
	 */
	public void executeTest(String testName, WebDriver driver) throws Exception {

		List<TestCase> orderderdTests = getAllDependentTests(testName);
		TestCase test;
		VerificationSteps verification;

		if (orderderdTests != null && orderderdTests.size() > 0) {

			for (int numberOfSteps = orderderdTests.size(); numberOfSteps > 0; numberOfSteps--) {

				test = orderderdTests.get(numberOfSteps - 1);

				logger.info("Step name :" + test.getName());

				Collection<ExecutionEvent> events = test.getExecuteSteps();

				if (events != null && events.size() > 0) {

					Iterator<ExecutionEvent> eventIterator = events.iterator();

					while (eventIterator.hasNext()) {

						performAction(eventIterator.next(), driver);

					}
				}

				verification = test.getVerificationSteps();

				verify(verification, driver);

				performCorrectiveSteps(verification, driver);
			}
		}
		userOutputWriter.close();
	}

	/**
	 * Performs the action, following actions are the permitted operations
	 * <ul>
	 * <li>switchToDefault</li>
	 * <li>
	 * <li>navigate</li>
	 * <li>wait</li>
	 * <li>SwitchToWindow</li>
	 * <li>select</li>
	 * <li>setText</li>
	 * <li>clear</li>
	 * <li>getText</li>
	 * <li>submit</li>
	 * <li>doubleClick</li>
	 * <li>rightClick</li>
	 * <li>swithToFrame</li>
	 * <li>browseFile</li>
	 * <li>searchValueInTable</li>
	 * <li>pickDate</li>
	 * <li>check</li>
	 * <li>unCheck</li>
	 * <li>dragFrom</li>
	 * <li>dropTo</li>
	 * </ul>
	 *
	 * @param event
	 * @param driver
	 *            - WebDriver
	 * @throws Exception
	 *             the exception
	 */
	public void performAction(ExecutionEvent event, WebDriver driver)
			throws Exception {

		String action = event.getName();
		Context actionContext = event.getContext();

		Reporter.log("Execution step:" + action + ":"+event.getDesc());
		logger.info("Execution step:" + action + ":"+event.getDesc());
		String inputData = actionContext.getText();

		inputData = input_container.getMaskedData(inputData, logger);

		WebElement currentElement = null;

		if (action.equalsIgnoreCase("switchToDefault")) {
			driver.switchTo().defaultContent();
		}
		if (action.equalsIgnoreCase("navigate")) {
			driver.get(inputData);
		}
		if (action.equalsIgnoreCase("wait")) {
			ActionHandler.waitTime(Integer.parseInt(inputData));
		}
		if (action.equalsIgnoreCase("SwitchToWindow")) {
			if (inputData != null) {
				Set<String> winHandles = driver.getWindowHandles();
				Iterator<String> i = winHandles.iterator();
				if (inputData.equalsIgnoreCase("ChildWindow")) {
					while (i.hasNext()) {
						String h = i.next().toString();
						System.out.println("** " + h);
						driver.switchTo().window(h);
					}
				} else if (inputData.equalsIgnoreCase("ParentWindow")) {
					String parent = i.next().toString();
					System.out.println("Parent window handle " + parent);
					driver.switchTo().window(parent);
				} else {
					driver.switchTo().window(inputData);
				}
				Thread.sleep(WebPage.DEFAULT_MEDIUM_WAIT_TIME);
			} else {
				logger.info("Blank Window name.");
			}
		}

		if (actionContext.getValue() != null
				&& !actionContext.getValue().equals("")) {

			currentElement = ActionHandler.locateElement(driver,
					actionContext.getType(), actionContext.getValue());

			logger.info("Current Element : " + actionContext.getType() + ":"
					+ actionContext.getValue());

			// possible operations - as taged in meta-data file
			if (action.equalsIgnoreCase("select")) {
				ActionHandler.select(inputData, currentElement);
			} else if (action.equalsIgnoreCase("setText")) {
			    currentElement.clear();
				currentElement.sendKeys(inputData);
			} else if (action.equalsIgnoreCase("clear")) {
				currentElement.clear();
			} else if (action.equalsIgnoreCase("getText")) {
				userOutputWriter.write(currentElement.getText());
				userOutputWriter
						.write("*************************************************************");
			} else if (action.equalsIgnoreCase("getTable")) {
				// printTable(driver);
				userOutputWriter
						.write("*************************************************************");
			} else if (inputData.equalsIgnoreCase("submit")) {
				currentElement.submit();
			} else if (action.equalsIgnoreCase("doubleClick")) {
				Actions builder = new Actions(driver);
				builder.doubleClick(currentElement).build().perform();
			} else if (action.equalsIgnoreCase("rightClick")) {
				Actions builder = new Actions(driver);
				builder.contextClick(currentElement).build().perform();
			} else if (action.equalsIgnoreCase("switchToFrame")) {
				driver.switchTo().frame(currentElement);
			} else if (action.equalsIgnoreCase("browseFile")) {
				currentElement.sendKeys(inputData);
			} else if (action.equalsIgnoreCase("searchValueInTable")) {
				int[] tempArr = new int[3];
				tempArr = ActionHandler.getValueInTable(driver, inputData,
						actionContext.getType(), actionContext.getValue());
				if (tempArr[0] >= 1)
					logger.info("Found " + tempArr[0]
							+ " Occurrences of the value in table.");
				else
					logger.info("Value Not Found in table.");
			} else if (action.equalsIgnoreCase("searchInTableAndClick")) {
				int[] tempArr = new int[3];
				tempArr = ActionHandler.getValueInTable(driver, inputData,
						actionContext.getType(), actionContext.getValue());
				if (tempArr[0] >= 1) {
					logger.info("Found " + tempArr[0]
							+ " Occurrences of the value in table.");
					String valueLocator = "", columnNo = actionContext
							.getColumnNo();
					if (columnNo != "") {
						valueLocator = actionContext.getValue() + "["
								+ tempArr[1] + "]/td[" + columnNo + "]";
					} else {
						valueLocator = actionContext.getValue() + "["
								+ tempArr[1] + "]/td[" + tempArr[2] + "]";
					}
					logger.info("New Locator : " + valueLocator);
					WebElement tempElement = ActionHandler.locateElement(
							driver, actionContext.getType(), valueLocator);
					tempElement.click();
				} else
					logger.info("Value Not Found in table.");
			} else if (action.equalsIgnoreCase("pickDate")) {
				currentElement.clear();
				currentElement.sendKeys(inputData);
			} else if (action.equalsIgnoreCase("check")) {
				if (!currentElement.isSelected()) {
					currentElement.click();
					logger.info("***** Check box checked ");
				}
			} else if (action.equalsIgnoreCase("uncheck")) {
				if (currentElement.isSelected()) {
					currentElement.click();
					logger.info("***** Check box unchecked ");
				}
			} else if (action.equalsIgnoreCase("dragFrom")) {
				ActionHandler.sourceElement = currentElement;
			} else if (action.equalsIgnoreCase("dropTo")) {
				logger.info("Performing drag and drop.");
				logger.info("Source element" + ActionHandler.sourceElement);
				logger.info("Target element" + currentElement);
				Actions builder = new Actions(driver);
				builder.dragAndDrop(ActionHandler.sourceElement, currentElement)
						.build().perform();

			} else if (action.equalsIgnoreCase("dropToOffset")) {
				String[] splitInput = inputData.split(",");
				Actions builder = new Actions(driver);
				builder.dragAndDropBy(currentElement,
						Integer.parseInt(splitInput[0]),
						Integer.parseInt(splitInput[1])).build().perform();

			} else {
				currentElement.click();
			}
		}
	}

	/**
	 * Verify - This is what you would like to verify to determine whether test
	 * passes/fails.
	 *
	 * @param verify
	 *            the verify
	 * @param driver
	 *            - Webdriver
	 * @return boolean - true/false
	 */
	public boolean verify(VerificationSteps verify, WebDriver driver) {
		boolean finalResult = true, flag = false;

		List<Context> contexts = (List<Context>) verify.getContexts();

		// 1. Handle the context verification part
		if (contexts != null && contexts.size() > 0) {
			Iterator<Context> contextIterator = contexts.iterator();
			Context context;
			String operation, findBy, findByVal;
			WebElement currentElement;
			while (contextIterator.hasNext()) {
				context = contextIterator.next();
				operation = context.getAction();
				findBy = context.getType();
				findByVal = context.getValue();

				if (operation.equalsIgnoreCase("wait")) {
					ActionHandler.waitTime(Integer.parseInt(context.getText()));
					System.out.println("Wait for "
							+ Integer.parseInt(context.getText()) + "seconds");
				}

				if (findByVal != "" && findBy != "") {
					Reporter.log("Performing verify operation:" + operation);
					try {
						currentElement = null;
						currentElement = ActionHandler.locateElement(driver,
								findBy, findByVal);
						logger.info("Trying to locate : " + findBy + "-"
								+ findByVal);

						if (operation.equalsIgnoreCase("gettext")) {
							String actualText = currentElement.getText();
							String expectedText = context.getText();

							// replace if the inputdata found to be a key
							expectedText = input_container.getMaskedData(
									expectedText, logger);

							logger.info("Checking if expected text is found in actual text :::\nExpected : "
									+ expectedText + "\nActual : " + actualText);

							if (actualText.contains(expectedText)
									|| actualText
											.equalsIgnoreCase(expectedText)) {
								logger.info("Text Matches hence verification passed.");
								flag = true;

							} else {

								logger.info("Text doesn't match hence verification failed.");
							}

							finalResult = finalResult && flag;
						}

						else if (operation
								.equalsIgnoreCase("getOccurencesInTable")) {

							String searchValue = context.getText();
							int actualOccurences = 0;
							String expectedOccurences = "";
							int[] arr = new int[3];
							searchValue = context.getText();
							expectedOccurences = context.getExpectedCount();

							try {
								arr = ActionHandler.getValueInTable(driver,
										searchValue, findBy, findByVal);
								actualOccurences = arr[0];
								if (actualOccurences == Integer
										.parseInt(expectedOccurences)) {

									flag = true;

								} else {
									logger.info("Expected and actual occurances count doesn't match: Expected= "
											+ expectedOccurences
											+ " And Actual is="
											+ actualOccurences);
								}

								finalResult = finalResult && flag;

							} catch (IOException e) {
								logger.error("Value Not Found in table \n"
										+ e.getLocalizedMessage());
								e.printStackTrace();
							}
						}

						else if (operation
								.equalsIgnoreCase("ElementShouldNotExist")) {

							finalResult = finalResult && flag;
							logger.info("Element was expected to be deleted but found available on the page, hence verification failed.");

						}

					} catch (WAaaSElementNotFound e) {

						if (operation.equalsIgnoreCase("ElementShouldNotExist")) {
							logger.info("Element is not present on the page, hence verification passed.");
							flag = true;

						} else {

							logger.error("Element is not present on the page.");

							throw e;

						}

						finalResult = finalResult && flag;

					}

				}

			}
		} else {
			logger.info("Nothing to verify");

		}
		Assert.assertTrue(finalResult);
		return finalResult;
	}

	/**
	 * Perform corrective steps - If you would like to perform further operation
	 * when the verification is passed.
	 *
	 * @param verify
	 *            the verify
	 * @param driver
	 *            - WebDriver
	 * @throws Exception
	 *             the exception
	 */
	public void performCorrectiveSteps(VerificationSteps verify,
			WebDriver driver) throws Exception {

		List<CorrectiveStep> correctiveSteps = (List<CorrectiveStep>) verify
				.getCorrectiveSteps();

		// 2. Handle the corrective steps part

		if (correctiveSteps != null && correctiveSteps.size() > 0) {
			Iterator<CorrectiveStep> correctiveStepIterator = correctiveSteps
					.iterator();
			CorrectiveStep correctiveStep;
			String stepToPerform, operation, findBy, findByVal;
			WebElement currentElement;

			while (correctiveStepIterator.hasNext()) {
				correctiveStep = correctiveStepIterator.next();
				stepToPerform = correctiveStep.getName();

				operation = correctiveStep.getApplicableWhen().getContext()
						.getAction();
				findBy = correctiveStep.getApplicableWhen().getContext()
						.getType();
				findByVal = correctiveStep.getApplicableWhen().getContext()
						.getValue();

				// WebElement currentElement = null;
				if (findByVal != null && !findByVal.equals("")) {

					currentElement = ActionHandler.locateElement(driver,
							findBy, findByVal);

					if (operation.equalsIgnoreCase("gettext")) {
						String expectedText = correctiveStep
								.getApplicableWhen().getContext().getText();

						// replace if the inputdata found to be a key
						expectedText = input_container.getMaskedData(
								expectedText, logger);
						String actualText = currentElement.getText();

						// check for the applicability
						if (actualText.contains(expectedText)) {
							if (stepToPerform != null
									&& !stepToPerform.equals("")) {
								// assumption is that there is only one
							    logger.info("Performing correctiveStep : " +stepToPerform );
								executeTest(stepToPerform, driver);

								break;
							}
						}

					}
				}
			}
		}
	}

}
