package com.raauto.executor;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.internal.BaseTestMethod;

import com.raauto.core.exceptions.TestNotFoundException;

/**
 * This class has the @Test method, which eventually lets MetaDataReader to
 * parse the suite xml file And execute the tests *
 * 
 * @author Laxmikant Birajdar
 */
public class ScriptRunner implements ITest {

    /** The custom test name. */
    private String customTestName;

    /** The _driver. */
    private WebDriver _driver;

    /** The m reader. */
    private MetaDataReader mReader;

    /** The logger. */
    Logger logger;

    /**
     * Instantiates a new script runner.
     */
    public ScriptRunner() {

    }

    /**
     * Instantiates a new script runner.
     *
     * @param mReader
     *            the m reader
     * @param testName
     *            the test name
     * @param driver
     *            the driver
     * @param logger
     *            the logger
     */
    public ScriptRunner(MetaDataReader mReader, String testName,
            WebDriver driver, Logger logger) throws TestNotFoundException {

        this.mReader = mReader;

        customTestName = testName;

        if (customTestName == null || customTestName.trim().equals("")) {

            throw new TestNotFoundException(customTestName);
        }

        _driver = driver;

        this.logger = logger;

    }

    /**
     * Test - This is the main @Test method. This method internally invokes the
     * executeTest method of {@link MetaDataReader}
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void test() throws Exception {

        logger.info("======================================================");

        logger.info("Running test: " + getTestName());

        System.out
                .println("======================================================");

        System.out.println("Running test : " + getTestName());

        if (mReader != null) {

            mReader.executeTest(customTestName, _driver);
        }

        logger.info("Finishing test : " + getTestName());

        System.out.println("Finishing test : " + getTestName());

        System.out
                .println("======================================================");

        logger.info("======================================================\n");

    }

    /**
     * @AfterMethod - Reset browser Resets the browser after the runs are over
     *              The method also ensures correct naming for for the test
     *              cases, which otherwise would have been simply named as
     *              "Test"
     */

    @AfterMethod
    public void setTestNameAsMethod(ITestResult theResult) {

        try {

            BaseTestMethod bastTestMethod = (BaseTestMethod) theResult.getMethod();

            Field field = bastTestMethod.getClass().getSuperclass()
                    .getDeclaredField("m_methodName");

            field.setAccessible(true);

            field.set(bastTestMethod, bastTestMethod.getMethodName() + "." + customTestName);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        _driver.get("");

    }

    public String getTestName() {

        return customTestName;

    }

}
