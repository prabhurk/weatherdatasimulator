package com.simulation.sample.weather.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.simulation.sample.weather.util.Constants;
import com.simulation.sample.weather.util.Utils;

/**
 * The TestMain Class has test cases.
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class TestMain {
	
	/**
	 * This test case tests if file exists
	 * testCaseStatus will be true, if file is present
	 * @param String filePath, the complete absolute path of the file including filename and extension
	 * @return TestCaseBean testCaseBean, the result of test case execution in object form
	 */
	public TestCaseBean tcFileExists(String filePath) {
		TestCaseBean testCaseBean = new TestCaseBean();
		boolean testCaseStatus = false;
		File file = new File(filePath);
		if(file != null && file.isFile()){
			testCaseStatus = true;
		}
		testCaseBean.setTestCaseId("WeatherData_TC_001");
		testCaseBean.setTestCaseDesc("Checks if a file exists");
		testCaseBean.setTestCaseStatus(testCaseStatus);
		testCaseBean.setTestCaseRemarks("File: " + filePath);
		return testCaseBean;
	}
	
	/**
	 * This test case tests if file is not empty
	 * testCaseStatus will be true, if file is not empty
	 * @param String filePath, the complete absolute path of the file including filename and extension
	 * @return TestCaseBean testCaseBean, the result of test case execution in object form
	 */
	public TestCaseBean tcFileNotEmpty(String filePath) {
		TestCaseBean testCaseBean = new TestCaseBean();
		boolean testCaseStatus = false;
		long length = 0;
		String remarks = "";
		try{
			File file = new File(filePath);
			length = file.length();
			if(length > 0){
				testCaseStatus = true;
			}
		} catch (Exception e){
			remarks = Utils.exceptionToString(e) + Constants.SEMICOLON;
		}
		testCaseBean.setTestCaseId("WeatherData_TC_002");
		testCaseBean.setTestCaseDesc("Checks if a file is not empty");
		testCaseBean.setTestCaseStatus(testCaseStatus);
		testCaseBean.setTestCaseRemarks(remarks + "File Length: " + length);
		return testCaseBean;
	}
	
	/**
	 * This test case matches between given count and file line numbers
	 * testCaseStatus will be true, given count equals total line numbers in the file
	 * @param int count, given count, preferably required count in file (user given or logically derived)
	 * @param String filePath, the complete absolute path of the file including filename and extension
	 * @return TestCaseBean testCaseBean, the result of test case execution in object form
	 */
	public TestCaseBean tcFileLineCount(int count, String filePath) {
		TestCaseBean testCaseBean = new TestCaseBean();
		boolean testCaseStatus = false;
		String remarks = "";
		int lines = 0;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			remarks = Utils.exceptionToString(e) + Constants.SEMICOLON;
		}
		if(fileReader != null){
			BufferedReader reader = new BufferedReader(fileReader);
			
			try {
				while (reader.readLine() != null) lines++;
			} catch (IOException e) {
				remarks = Utils.exceptionToString(e) + Constants.SEMICOLON;
			}
			try {
				reader.close();
			} catch (IOException e) {
				remarks = Utils.exceptionToString(e) + Constants.SEMICOLON;
			}
			
			if(count == lines){
				testCaseStatus = true;
			}
			
		}
		testCaseBean.setTestCaseId("WeatherData_TC_003");
		testCaseBean.setTestCaseDesc("Checks if total data count aligns with output file line count");
		testCaseBean.setTestCaseRemarks(remarks + "File lines: " + lines);
		testCaseBean.setTestCaseStatus(testCaseStatus);
		return testCaseBean;
	}
}
