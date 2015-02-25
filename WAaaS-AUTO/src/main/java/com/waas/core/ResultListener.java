package com.waas.core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * The listener interface for receiving result events. The class that is
 * interested in processing a result event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's addResultListener method. When the result event occurs, that
 * object's appropriate method is invoked.
 *
 * @author Laxmikant Birajdar
 */

public class ResultListener implements ITestListener {

	/** The run over. */
	private boolean runOver = false;

	public void onFinish(ITestContext arg0) {

		runOver = true;

		System.out.println("All tests finished");

	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public boolean getTestRunStatus() {

		return runOver;

	}

}
