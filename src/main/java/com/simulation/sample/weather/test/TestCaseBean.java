package com.simulation.sample.weather.test;

/**
 * The TestCaseBean is model class representing the id, description, pass/fail status and any remarks of test cases
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class TestCaseBean {
	
	private String testCaseId;
//	Test Case Description
	private String testCaseDesc;
//	Test Case pass/fail status, true for pass, else fail
	private boolean testCaseStatus;
	private String testCaseRemarks;
	
	/**
	 * @return the testCaseId
	 */
	public String getTestCaseId() {
		return testCaseId;
	}
	/**
	 * @param testCaseId the testCaseId to set
	 */
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	/**
	 * @return the testCaseDesc
	 */
	public String getTestCaseDesc() {
		return testCaseDesc;
	}
	/**
	 * @param testCaseDesc the testCaseDesc to set
	 */
	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
	}
	/**
	 * @return the testCaseStatus
	 */
	public boolean getTestCaseStatus() {
		return testCaseStatus;
	}
	/**
	 * @param testCaseStatus the testCaseStatus to set
	 */
	public void setTestCaseStatus(boolean testCaseStatus) {
		this.testCaseStatus = testCaseStatus;
	}
	/**
	 * @return the testCaseRemarks
	 */
	public String getTestCaseRemarks() {
		return testCaseRemarks;
	}
	/**
	 * @param testCaseRemarks the testCaseRemarks to set
	 */
	public void setTestCaseRemarks(String testCaseRemarks) {
		this.testCaseRemarks = testCaseRemarks;
	}
	
	

}
