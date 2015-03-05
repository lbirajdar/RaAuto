package com.raauto.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.TestNG;
import org.testng.annotations.Factory;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.raauto.core.ListenerPool;
import com.raauto.core.ResultListener;
import com.raauto.jaxb.TestCase;

/**
 * This class is the entry point for running any tests using this framework.
 * Users can create their own instance of this class to invoke the automation
 * run using runSuites method
 * 
 * @author Laxmikant Birajdar
 */

public class RaAutoRunner {

    /** The runner properties. */
    private Properties runnerProperties;

    /** The suites to run. */
    private String suitesToRun;

    /** The suite meta data map. */
    HashMap<String, String> suiteMetaDataMap;

    /** The Constant META_DATA_FILE_KEY. */
    public static final String META_DATA_FILE_KEY = "raauto.suites";

    /** The Constant META_DATA_FOLDER_KEY. */
    public static final String META_DATA_FOLDER_KEY = "raauto.suites.folder";

    /** The Constant REMOTE_HUB_URL_KEY. */
    public static final String REMOTE_HUB_URL_KEY = "raauto.remote.hub.url";

    /** The Constant BROWSER_KEY. */
    public static final String BROWSER_KEY = "raauto.browser";

    /** The Constant BROWSER_DRIVER_PATH_KEY. */
    public static final String BROWSER_DRIVER_PATH_KEY = "raauto.path.to.browser.driver.exe";

    /** The Constant BROWSER_PROXY_SERVER. */
    public static final String BROWSER_PROXY_SERVER = "raauto.browser.proxy.server";

    /** The Constant BROWSER_PROXY_USER. */
    public static final String BROWSER_PROXY_USER = "raauto.browser.proxy.user";

    /** The Constant BROWSER_PROXY_PASSWORD. */
    public static final String BROWSER_PROXY_PASSWORD = "raauto.browser.proxy.password";

    /** The Constant SESSION_ID. */
    public static final String SESSION_ID = "raauto.sessionId";

    /** The meta_data_folder. */
    private String meta_data_folder;

    /** The p driver. */
    private WebDriver pDriver;

    /** The result base path. */
    private String resultBasePath;

    public String getResultBasePath() {
        return resultBasePath;
    }

    public void setResultBasePath(String resultBasePath) {
        this.resultBasePath = resultBasePath;
    }

    /** The result folder. */
    private String resultFolder;

    public String getResultFolder() {

        return this.resultFolder;

    }

    /**
     * The main method. The main method is provided for the user's who wish to
     * use the framework by simply providing a suites.properties file path
     * 
     * @param arg
     *            the arguments
     * @throws Exception
     *             the exception
     */

    public static void main(String arg[]) throws Exception {

        RaAutoRunner theRunner = new RaAutoRunner();

        if (arg.length == 1) {

            ListenerPool poolInstance = ListenerPool.getInstance();
            poolInstance.createListener("Default-Session");

            theRunner.runSuites(arg[0], poolInstance, "Default-Session");

        } else if (arg.length > 1) {

            System.out
                    .println("Enter path to suites.properties\n Only file name containing comma separated list of xml suites is required, try again");

        } else {

            System.out
                    .println("Enter path to suites.properties\n File containing comma separated list of xml suites is required, try again");

        }

    }

    /**
     * The method provides way to run the tests when user creates his own
     * RaAutoRunner instance.
     * <ul>
     * <li>
     * 
     * @param propFile
     *            = the file location of suite.properties file, which contains
     *            the list of suites to be executed. suites.properties need to
     *            have following properties defined raauto.suites.folder= The
     *            location where the suite.xml files are residing
     *            raauto.suites=comma separated list of suites to be executed
     *            (essentially they are xml files listing the test cases)
     *            raauto.remote.hub.url= the path of remote hub raauto.browser=
     *            e.g. firefox, internetexplorer, chrome
     *            raauto.path.to.browser.exe= the path to exe files of IE and
     *            Chrome
     * 
     *            <li>
     * @throws Exception
     *             the exception
     * 
     *             </ul>
     */

    public void runSuites(String propFile) throws Exception {

        String sessionId = "" + Math.random();
        runSuites(propFile, ListenerPool.getInstance(), sessionId);

    }

    /**
     * The method provides way to run the tests when user creates his own
     * RaAutoRunner instance and wants ability to run in different sessions.
     * 
     * @param propFile
     *            = the file location of suite.properties file, which contains
     *            the list of suites to be executed. suites.properties need to
     *            have following properties defined raauto.suites.folder= The
     *            location where the suite.xml files are residing
     *            raauto.suites=comma separated list of suites to be executed
     *            (essentially they are xml files listing the test cases)
     *            raauto.remote.hub.url= the path of remote hub raauto.browser=
     *            e.g. firefox, internetexplorer, chrome
     *            raauto.path.to.browser.exe= the path to exe files of IE and
     *            Chrome
     * @param poolInstance
     *            the pool instance
     * @param sessionId
     *            the session id
     * @throws Exception
     *             the exception
     */

    public void runSuites(String propFile, ListenerPool poolInstance,
            String sessionId) throws Exception {

        try {

            InfraManager.poolInstance = poolInstance;

            ITestListener rListener = poolInstance.getListener(sessionId);

            runnerProperties = new Properties();

            String resourcesPath = new File(propFile).getAbsolutePath();

            System.out.println("Runnins suites from :  " + resourcesPath);

            runnerProperties.load(new FileInputStream(propFile));

            poolInstance.setRunnerProperties(sessionId, runnerProperties);

            suitesToRun = runnerProperties.getProperty(META_DATA_FILE_KEY);

            meta_data_folder = runnerProperties
                    .getProperty(META_DATA_FOLDER_KEY);

            poolInstance.setMetaDataFolder(sessionId, meta_data_folder);

            StringTokenizer suiteTokens = new StringTokenizer(suitesToRun, ",");

            TestNG tng = new TestNG();

            tng.addListener(rListener);
            tng.setDefaultSuiteName("RaAuto-Suite");

            poolInstance.setResultFolder(sessionId, resultBasePath);

            resultFolder = poolInstance.getResultFolder(sessionId);

            File directory = new File(resultFolder);
            if (!directory.exists()) {
                directory.mkdir();
            }

            tng.setOutputDirectory(resultFolder);

            List<XmlSuite> suites = new ArrayList<XmlSuite>();

            while (suiteTokens.hasMoreTokens()) {

                String suiteXml = suiteTokens.nextToken().trim();

                String suiteName = suiteXml.substring(0,
                        suiteXml.lastIndexOf('.'));

                XmlSuite suite = new XmlSuite();

                suite.setName(suiteName);

                suiteMetaDataMap = new HashMap<String, String>();

                suiteMetaDataMap.put(META_DATA_FOLDER_KEY, meta_data_folder);

                suiteMetaDataMap.put(META_DATA_FILE_KEY, suiteXml == null ? ""
                        : suiteXml);

                String remoteHubUrl = runnerProperties
                        .getProperty(REMOTE_HUB_URL_KEY);

                suiteMetaDataMap.put(REMOTE_HUB_URL_KEY,
                        remoteHubUrl == null ? "" : remoteHubUrl);

                String browserKey = runnerProperties.getProperty(BROWSER_KEY);
                suiteMetaDataMap.put(BROWSER_KEY, browserKey == null ? ""
                        : browserKey);

                String browserDriverPath = runnerProperties
                        .getProperty(BROWSER_DRIVER_PATH_KEY);
                suiteMetaDataMap.put(BROWSER_DRIVER_PATH_KEY,
                        browserDriverPath == null ? "" : browserDriverPath);

                suiteMetaDataMap.put(SESSION_ID, sessionId);

                suite.setParameters(suiteMetaDataMap);

                XmlTest test = new XmlTest(suite);
                test.setName("RaAuto - Rapidly Automated Tests");

                List<XmlClass> classes = new ArrayList<XmlClass>();
                classes.add(new XmlClass("com.raauto.executor.RaAutoRunner"));

                test.setXmlClasses(classes);

                suites.add(suite);

            }

            tng.setXmlSuites(suites);

            init(sessionId);

            tng.run();

            if (((ResultListener) rListener).getTestRunStatus() == true) {

                pDriver.close();
                pDriver.quit();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    /**
     * Creates the executor instances. The method reads the tests from the suite
     * files and run the tests one by one. The method provides the tests to the
     * test runner class - ScriptRunner along with the {@link MetaDataReader }
     * instance that it created
     * 
     * @param context
     *            the context
     * @return the object[]
     * @throws Exception
     *             the exception
     */
    @Factory
    public Object[] createExecutorInstances(ITestContext context)
            throws Exception {

        String suiteName = context.getSuite().getName();
        String sessionId = context.getSuite().getParameter(SESSION_ID);

        InfraManager.poolInstance.setLogger(sessionId, suiteName);

        Logger logger = InfraManager.poolInstance.getLogger(sessionId,
                suiteName);

        String meta_data_file = (String) context.getSuite().getParameter(
                META_DATA_FILE_KEY);

        logger.info("********************************************************");

        logger.info("Running suite: " + suiteName + " from :" + meta_data_file);

        System.out.println("Running suite: " + suiteName + " from :"
                + meta_data_file);

        logger.info("********************************************************");

        String meta_data = InfraManager.poolInstance
                .getMetaDataFolder(sessionId) + meta_data_file;

        MetaDataReader mReader = new MetaDataReader(meta_data, logger);

        Collection<TestCase> tests = mReader.getTestsToRun();

        Iterator<TestCase> testIterator = tests.iterator();

        Object[] scriptRunners = new ScriptRunner[tests.size()];

        int i = 0;

        while (testIterator.hasNext()) {

            scriptRunners[i] = new ScriptRunner(mReader, testIterator.next()
                    .getName(), InfraManager.poolInstance.getDriver(sessionId),
                    logger);

            i++;

        }

        return scriptRunners;

    }

    /**
     * Initialize everything including Driver, DesiredCapabilities before
     * running any suite.
     *
     * @param sessionId
     *            the session id
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    private boolean init(String sessionId) throws Exception {

        boolean setupReady = false;

        // Set suites to run
        suitesToRun = InfraManager.poolInstance.getRunnerProperties(sessionId)
                .getProperty(META_DATA_FILE_KEY);

        if (initializeBrowser(sessionId)) {

            setupReady = true;

        }

        return setupReady;

    }

    /**
     * Initialize browser.
     *
     * @param sessionId
     *            the session id
     * @return true, if successful
     * @throws Exception
     *             the exception If browsername/url is not specified default to
     *             Firefox
     */
    private boolean initializeBrowser(String sessionId) throws Exception {

        boolean ready = false;
        String browserExecutableProperty = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        URL server = null;

        if (runnerProperties.getProperty(REMOTE_HUB_URL_KEY) != null
                && !runnerProperties.getProperty(REMOTE_HUB_URL_KEY).equals("")) {

            server = new URL(runnerProperties.getProperty(REMOTE_HUB_URL_KEY));
            System.out.println("Running suite on :" + server.toString());
        }

        String browserName = runnerProperties.getProperty(BROWSER_KEY);

        if (server != null && !server.equals("")) {
            if (browserName != null && !browserName.equals("")) {

                System.out.println("Running suite  in :" + browserName);

                if (browserName.equalsIgnoreCase("ie")
                        || browserName.equalsIgnoreCase("Internet Explorer")
                        || browserName.equalsIgnoreCase("InternetExplorer")) {

                    browserExecutableProperty = "webdriver.ie.driver";
                    System.setProperty(browserExecutableProperty,
                            runnerProperties
                                    .getProperty(BROWSER_DRIVER_PATH_KEY));
                    capabilities = DesiredCapabilities.internetExplorer();

                } else if (browserName.equals("Chrome")
                        || browserName.equalsIgnoreCase("Google Chrome")
                        || browserName.equalsIgnoreCase("GoogleChrome")) {

                    browserExecutableProperty = "webdriver.chrome.driver";
                    System.setProperty(browserExecutableProperty,
                            runnerProperties
                                    .getProperty(BROWSER_DRIVER_PATH_KEY));
                    capabilities = DesiredCapabilities.chrome();
                } else {

                    capabilities = DesiredCapabilities.firefox();
                }

                pDriver = new RemoteWebDriver(server, capabilities);

            } else {
                capabilities = DesiredCapabilities.firefox();
                pDriver = new RemoteWebDriver(server, capabilities);
            }

        } else {
            if (browserName != null && !browserName.equals("")) {
                if (browserName.equalsIgnoreCase("ie")
                        || browserName.equalsIgnoreCase("Internet Explorer")
                        || browserName.equalsIgnoreCase("InternetExplorer")) {
                    browserExecutableProperty = "webdriver.ie.driver";
                    System.setProperty(browserExecutableProperty,
                            runnerProperties
                                    .getProperty(BROWSER_DRIVER_PATH_KEY));
                    pDriver = new InternetExplorerDriver();
                } else if (browserName.equals("chrome")) {

                    browserExecutableProperty = "webdriver.chrome.driver";
                    System.setProperty(browserExecutableProperty,
                            runnerProperties
                                    .getProperty(BROWSER_DRIVER_PATH_KEY));
                    capabilities = DesiredCapabilities.chrome();
                    pDriver = new ChromeDriver();
                } else {
                    pDriver = new FirefoxDriver();
                }
            } else {
                pDriver = new FirefoxDriver();
            }

        }

        pDriver.manage().window().maximize();

        InfraManager.poolInstance.setDriver(sessionId, pDriver);
        ready = true;

        return ready;

    }
}
