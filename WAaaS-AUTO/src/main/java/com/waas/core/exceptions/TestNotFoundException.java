package com.waas.core.exceptions;

/**
 * This class represents the exception when a testcase with given name doesn't
 * exist
 * 
 * @author Laxmikant Birajdar
 */

public class TestNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public TestNotFoundException(String testName) {

        if (testName == null || testName.trim().equals("")) {

            new Exception("Testcase name cannot be blank");
            System.out.println("Testcase name cannot be blank");
        } else {

            new Exception("Testcase with name : " + testName + " : Not Found");
            System.out.println("Testcase with name : " + testName
                    + " : Not Found");
        }

    }

}
