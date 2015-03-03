package com.raauto.core.exceptions;

/**
 * This class represents the exception when a testcase with given name doesn't
 * exist
 * 
 * @author Laxmikant Birajdar
 */

public class TestNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public TestNotFoundException(String testName) {

        super("Testcase name :" + testName + " is either null or invalid");

        if (testName == null || testName.trim().equals("")) {

            System.out.println("Testcase name cannot be blank");

        } else {

            System.out.println("Testcase with name : " + testName
                    + " : Not Found");

        }

        printStackTrace();

    }

}
