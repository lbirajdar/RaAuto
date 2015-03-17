package com.raauto.reporter;

import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * This class is a placeholder class for generating the customized output on screen.
 * Users can create their methods to generate the customized reports.
 * Once created, add it as a listener class to TestNG in RaAutoRunner class.
 * 
 * @author Laxmikant Birajdar
 */

public class RaAutoReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1,
            String arg2) {

        System.out.println("A total of " + arg1.size() + " Suites were run");

        for (ISuite theSuite : arg1) {

            System.out.println("The suite name : " + theSuite.getName());

            Map<String, ISuiteResult> testsWithResult = theSuite.getResults();

            for (ISuiteResult result : testsWithResult.values()) {

                ITestContext testContext = result.getTestContext();

                ITestNGMethod methods[] = testContext.getAllTestMethods();

                for (int counter = 0; counter < methods.length; counter++) {

                    System.out.println("The Method name is : "
                            + methods[counter].getMethodName());
                    
                    

                }

            }
        }

    }
}
