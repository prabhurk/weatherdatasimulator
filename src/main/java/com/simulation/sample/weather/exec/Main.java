package com.simulation.sample.weather.exec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulation.sample.weather.model.Condition;
import com.simulation.sample.weather.model.LocationPositionBean;
import com.simulation.sample.weather.model.OutputBean;
import com.simulation.sample.weather.test.TestCaseBean;
import com.simulation.sample.weather.test.TestMain;
import com.simulation.sample.weather.util.Constants;
import com.simulation.sample.weather.util.Utils;

/**
 * The Main Class is the main method which invoked while jar run.
 * The Class contains the entire flow of weather data generation
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	/**
	 * The main method is ivoked while while jar run
	 * @param String[] args, args[0] is the total data to be generated, if not given default to no of locations
	 */
	public static void main(String[] args) {

//		For marking the program run unique and used with log, data and test case file names to identify the run
		Date executionStartDate = new Date();

//		For setting the filename of Constants.CONFIG_LOGFILE
		System.setProperty("weatherdatadatetime", Utils.dateToString(executionStartDate, Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT));
//		For loading log config
		PropertyConfigurator.configure(Constants.CONFIG_LOGFILE); 
		
		log.info("Weather data generation started at: " + Utils.dateToString(executionStartDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		
//		totalNoOfDataString is the total number of weather data to be generated 
		String totalNoOfDataString = null;
			
		try {
//			totalNoOfDataString is to be passed as first argument while jar execution
			totalNoOfDataString = args[0];
			log.debug("Given argument of total data to be generated: " + totalNoOfDataString);
		} catch(ArrayIndexOutOfBoundsException e) {
			log.error("Improper argument of total data to be generated: " + totalNoOfDataString);
			log.error(Utils.exceptionToString(e));
		}
		
//		Sometimes, say 1000 may be given as 1,000 as argument by user. Hence removing all "," from the argument
		if (!Utils.isStringEmpty(totalNoOfDataString)) {
			if (totalNoOfDataString.contains(Constants.COMMA)) {
				totalNoOfDataString.replace(Constants.COMMA, "");
				log.debug("Removed all '" + Constants.COMMA + "' from " + totalNoOfDataString);
			}
		}
		
		int totalNoOfData = 0;
//		If gievn totalNoOfDataString is numeric, then parse string to int value
		if (StringUtils.isNumeric(totalNoOfDataString)) {
			totalNoOfData = Integer.parseInt(totalNoOfDataString);
			log.debug("Derived from argument, total data to be generated: " + totalNoOfData);
		} else {
			log.debug("Invalid input. Only whole numbers < + " + Integer.MAX_VALUE +  " is expected");
		}
		
		execute(totalNoOfData, executionStartDate);
		
		Date executionEndDate = new Date();
		log.info("Weather data generation ended at: " + Utils.dateToString(executionEndDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
	}
	
	/**
	 * The execute method interprets config params, start and end dates within which data corresponds to and 
	 * invokes output data generation and executes test cases
	 * 
	 * @param int totalNoOfData, total data to be generated, if not given default to no of locations
	 * @param Date executionStartDate, program execution start date
	 */
	public static void execute(int totalNoOfData, Date executionStartDate){
//		The below values are taken from properties file
		String startDateInputString = Utils.getProperty(Constants.CONFIG_STARTDATE);
		String endDateInputString = Utils.getProperty(Constants.CONFIG_STARTDATE);
		String inputDateFormatString = Utils.getProperty(Constants.CONFIG_DATEFORMAT);
		String locationsAndPositionsInputString = Utils.getProperty(Constants.CONFIG_LOCNPOS);
		String executeTestCasesString = Utils.getProperty(Constants.CONFIG_EXETEST);
		String outputHederString = Utils.getProperty(Constants.CONFIG_OUTPUTHEADER);
		
		log.debug("From config, startDateInputString: " + startDateInputString);
		log.debug("From config, endDateInputString: " + endDateInputString);
		log.debug("From config, inputDateFormatString: " + inputDateFormatString);
		log.debug("From config, locationsAndPositionsInputString: " + locationsAndPositionsInputString);
		log.debug("From config, executeTestCasesString: " + executeTestCasesString);
		log.debug("From config, outputHederString: " + outputHederString);
		
/*------Calculate start and end dates starts------*/
		boolean dateError = true;
		Date startDate = null;
		try {
			startDate = Utils.stringToDate(startDateInputString, inputDateFormatString);
			dateError = false;
		} catch (Exception e) {
			log.error(Utils.exceptionToString(e));
			startDate = new Date();
		}
		
		Date endDate = null;
		try {
			endDate = Utils.stringToDate(endDateInputString, inputDateFormatString);
		} catch (Exception e) {
			log.error(Utils.exceptionToString(e));
			dateError = true;
		}

		log.debug("Start date before validation: " + Utils.dateToString(startDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		log.debug("End date before validation: " + Utils.dateToString(endDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		
		if (endDate != null && !endDate.before(startDate) && !endDate.equals(startDate)) {
			dateError = false;
		} else {
			dateError = true;
		}

//		If any date error, endDate will be 60 days prior to startDate
		if (dateError) {
			log.error("Start and End dates validation failed");
			Date temp = startDate;
			endDate = temp;
			startDate = DateUtils.addDays(temp, -60);
			log.debug("Assigning Start Date 60 days before " + Utils.dateToString(temp, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		}

		log.info("Weather data generation from: " + Utils.dateToString(startDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		log.info("Weather data generation to: " + Utils.dateToString(endDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
/*------Calculate start and end dates ends------*/
		
		if (Utils.isStringEmpty(locationsAndPositionsInputString)) {
			locationsAndPositionsInputString = Constants.LOCNPOS;
			log.debug("Assigning location & position default: " + locationsAndPositionsInputString);
		}

		Main main = new Main();

/*------Output file generation starts------*/		
		String[] locationsAndPositionsInputArray = locationsAndPositionsInputString.split(Constants.SEMICOLON);
		List<LocationPositionBean> locationPositionBeanList = main.getLocationPositionBeanList(locationsAndPositionsInputArray);
/*------Output file generation starts------*/

//		If totalNoOfData is not greater than 0, then defaults to no of locations specified
		if(!(totalNoOfData > 0)){
			totalNoOfData = locationPositionBeanList.size();
		}
		
		log.info("Weather data generation total data: " + totalNoOfData);

//		Generate OutputBeanList
		List<OutputBean> outputBeanList = main.generateOutputBeanList(totalNoOfData, startDate, endDate, locationPositionBeanList);

/*------Output file generation starts------*/		
		String fileOutput = null;
		fileOutput = main.writeOutputToFile(outputHederString, outputBeanList, executionStartDate);
/*------Output file generation ends------*/		
		
		boolean executeTestCases = false;
		if(!Utils.isStringEmpty(fileOutput)){
			log.info("Weather data generation completed successfully");
			executeTestCases = true;
		} else {
			log.error("Weather data generation failed");
		}
		
/*------Execution of testcases starts------*/
		if (Constants.EXETEST.equalsIgnoreCase(executeTestCasesString)) {
			if(executeTestCases){
				TestMain testMain = new TestMain();
				TestCaseBean tcBean1 = testMain.tcFileExists(fileOutput);
				TestCaseBean tcBean2 = testMain.tcFileNotEmpty(fileOutput);
				int fileLineCount = totalNoOfData;
//				If header is present, total lines in file will be header + totalNoOfData
				if (!Constants.OUTPUTHEADER.equalsIgnoreCase(outputHederString)) {
					fileLineCount++;
				}
				TestCaseBean tcBean3 = testMain.tcFileLineCount(fileLineCount, fileOutput);
				
				List<TestCaseBean> testCaseBeanList = new ArrayList<TestCaseBean>(3);
				testCaseBeanList.add(tcBean1);
				testCaseBeanList.add(tcBean2);
				testCaseBeanList.add(tcBean3);
				
				String fileTestCase = null;
				fileTestCase = main.writeTestCasesToFile(testCaseBeanList, executionStartDate);
				
				if(!Utils.isStringEmpty(fileTestCase)){
					log.info("Weather data testcases completed successfully");
				} else {
					log.error("Weather data test cases generation failed");
				}
			} else {
				log.error("Testcases not executed as output failed");
			}
		}
/*------Execution of testcases ends------*/
	}
	

	/**
	 * The method converts locationsAndPositionsInputArray to list of LocationPositionBean objects
	 * 
	 * @param int totalNoOfDataString[] locationsAndPositionsInputArray, input
	 * @return List<LocationPositionBean>, output
	 */
	public List<LocationPositionBean> getLocationPositionBeanList(String[] locationsAndPositionsInputArray){
		log.debug("Started processing locations and positions");
		List<LocationPositionBean> locationPositionBeanList = new ArrayList<LocationPositionBean>(
				locationsAndPositionsInputArray.length);
		
		for (String locationAndPosition : locationsAndPositionsInputArray) {
			log.debug("Processing locationposition: " + locationAndPosition);
//			Split location and position variable separately
			String[] locationAndPositionArray = locationAndPosition.split(Constants.PIPE_SEPARATOR);
			String location = null;
			String position = null;
			if (locationAndPositionArray.length == 1) {
				location = "";
				position = locationAndPositionArray[0];
			} else if (locationAndPositionArray.length == 2) {
				location = locationAndPositionArray[0];
				position = locationAndPositionArray[1];
			} else {
				log.error("Incorrect format: " + locationAndPosition + "; Correct format: Location"
						+ Constants.PIPE + "Latitude" + Constants.COMMA + "Longitude" + Constants.COMMA + "Elevation");
			}

//			Split position values and assign to bean variables. Datatype assign will make a way for data validation
			if (!Utils.isStringEmpty(position)) {
				String[] positionArray = position.split(Constants.COMMA);
				if (positionArray.length == 3) {
					float latitude = Float.parseFloat(positionArray[0]);
					float longitude = Float.parseFloat(positionArray[1]);
					int elevation = Integer.parseInt(positionArray[2]);
					LocationPositionBean locationPositionBean = new LocationPositionBean(location, latitude, longitude,
							elevation);
					locationPositionBeanList.add(locationPositionBean);
					log.debug("Sucessfully processed locationposition: " + location + Constants.PIPE + position);
				}
				positionArray = null;
			}
			location = null;
			position = null;
			locationAndPositionArray = null;
		}
		log.debug("Completed processing locations and positions");
		return locationPositionBeanList;
	}

	/**
	 * The method converts locationsAndPositionsInputArray to list of LocationPositionBean objects
	 * 
	 * @param int totalNoOfDataString[] locationsAndPositionsInputArray, input
	 * @return List<LocationPositionBean>, output
	 */
	public List<OutputBean> generateOutputBeanList(int totalNoOfData, Date startDate, Date endDate, List<LocationPositionBean> locationPositionBeanList) {
		log.debug("Started processing outputbeans");
//		totalNoOfData = (noOfWeatherDataForEachLocation * noOfUniqueLocations) + totalRemainingData
		int noOfWeatherDataForEachLocation = 0;
		int noOfUniqueLocations = locationPositionBeanList.size();
		if (noOfUniqueLocations > 0) {
			noOfWeatherDataForEachLocation = (totalNoOfData / noOfUniqueLocations);
		}
		log.info("Weather data per location: " + noOfWeatherDataForEachLocation);
		int totalRemainingData = (totalNoOfData - (noOfWeatherDataForEachLocation * noOfUniqueLocations));
		log.debug("Weather data remaining: " + totalRemainingData);

//		Generate noOfWeatherDataForEachLocation of OutputBeans for each locationPositionBean in locationPositionBeanList
		List<OutputBean> outputBeanList = new ArrayList<OutputBean>(totalNoOfData);
		for (int i = 0; i < noOfWeatherDataForEachLocation; i++) {
			for (LocationPositionBean locationPositionBean : locationPositionBeanList) {
				Date randomDate = Utils.getRandomDateBtw(startDate, endDate);
				OutputBean outputBean = generateOutputBean(locationPositionBean, randomDate);
				outputBeanList.add(outputBean);
			}
		}
		
//		Generate totalRemainingData of OutputBeans for random locations from locationPositionBeanList
		if (totalRemainingData > 0) {
			for (int i = 0; i < totalRemainingData; i++) {
				LocationPositionBean locationPositionBean = locationPositionBeanList
						.get(Utils.getRandomWholeNumBtw(0, (noOfUniqueLocations - 1)));
				Date randomDate = Utils.getRandomDateBtw(startDate, endDate);
				OutputBean outputBean = generateOutputBean(locationPositionBean, randomDate);
				outputBeanList.add(outputBean);
			}
		}
		
//		Sorting outputBeanList based on localTime field in OutputBean in ascending order
		log.debug("Started sorting outputbeans");
		Collections.sort(outputBeanList, new Comparator<OutputBean>() {
			public int compare(OutputBean outputBean1, OutputBean outputBean2) {
				return outputBean1.getLocalTime().compareTo(outputBean2.getLocalTime());
			}
		});
		log.debug("Completed processing outputbeans");
		return outputBeanList;
	}
	
	/**
	 * The method generates OutputBean corresponding locationPositionBean and randomDate
	 * 
	 * @param LocationPositionBean locationPositionBean, location and position
	 * @param Date randomDate, the local time
	 * @return OutputBean, the weather output data in outputBean
	 */
	public static OutputBean generateOutputBean(LocationPositionBean locationPositionBean, Date randomDate) {
		log.debug("Started processing outputBean for location: " + locationPositionBean.getLocation() + " and date: " + Utils.dateToString(randomDate, Constants.OUTPUT_LOG_TIMESTAMP_FORMAT));
		OutputBean outputBean = new OutputBean();
		outputBean.setLocationPositionBean(locationPositionBean);
//		Generating weather condition based on random
		Condition condition = Condition.values()[Utils.getRandomWholeNumBtw(0, (Condition.values().length - 1))];
		outputBean.setCondition(condition);
		outputBean.setHumidity(WeatherLogic.getHumidity(condition));
		outputBean.setLocalTime(randomDate);
		outputBean.setPressure(WeatherLogic.getPressure(condition));
		outputBean.setTemperature(WeatherLogic.getTemperature(condition));
		log.debug("Completed processing outputBean");
		return outputBean;
	}
	
	/**
	 * The method writes List<OutputBean> to file
	 * 
	 * @param String outputHederString, if not equals Constants.OUTPUTHEADER, then header will be the first line of file
	 * @param List<OutputBean> outputBeanList, the list if OutputBeans to be written to file
	 * @return String, output file name if success, else null
	 */
	public String writeOutputToFile(String outputHederString, List<OutputBean> outputBeanList, Date executionStartDate) {
		log.debug("Started writing output to file");
		boolean fileWriteStatus = false;
		BufferedWriter bufferedWrter = null;
		FileWriter fileWriter = null;

//		Framing Output file name
		String fileNameTimestamp = Utils.dateToString(executionStartDate, Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT);
		String outputFileName = Constants.OUTPUT_FILENAME_STARTER + fileNameTimestamp + Constants.OUTPUT_FILEEXTENSION;
		log.info("Weather data output file name: " + outputFileName);

		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWrter = new BufferedWriter(fileWriter);
			log.debug("Flusing to output");
			log.debug("---------------------------------------------------------------");
//			Writing header to output file if required
			if (!Constants.OUTPUTHEADER.equalsIgnoreCase(outputHederString)) {
				String header = "Location" + Constants.PIPE + "Position" + Constants.PIPE + "Local Time"
						+ Constants.PIPE + "Conditions" + Constants.PIPE + "Temperature" + Constants.PIPE + "Pressure"
						+ Constants.PIPE + "Humidity";
				bufferedWrter.write(header);
				bufferedWrter.write(Constants.NEW_LINE);
				log.info("Weather data output header: " + header);
			}
			log.debug("---------------------------------------------------------------");
			int i = 1;
			for (OutputBean outputBean : outputBeanList) {
//				Eliminating new line for the first line of file
				if (i != 1) {
					bufferedWrter.write(Constants.NEW_LINE);
				}
				String location = outputBean.getLocationPositionBean().getLocation();
				String latitude = String.valueOf(outputBean.getLocationPositionBean().getLatitude());
				String longitude = String.valueOf(outputBean.getLocationPositionBean().getLongitude());
				String elevation = String.valueOf(outputBean.getLocationPositionBean().getElevation());
				String localTime = Utils.dateToString(outputBean.getLocalTime(), Constants.DATEFORMAT_ISO8601);
//				As per requirement, condition is in title case
				String condition = StringUtils.capitalize(outputBean.getCondition().toString().toLowerCase());
//				As per requirement, temperature is with one decimal precision				
				String temperature = Utils.formatFloat(outputBean.getTemperature());
//				As per requirement, if temperature>0, then must lead with '+' sign
				if (outputBean.getTemperature() > 0) {
					temperature = Constants.PLUS + temperature;
				}
//				As per requirement, pressure is with one decimal precision	
				String pressure = Utils.formatFloat(outputBean.getPressure());
				String humidity = String.valueOf(outputBean.getHumidity());
				String output = location + Constants.PIPE + latitude + Constants.COMMA + longitude + Constants.COMMA
						+ elevation + Constants.PIPE + localTime + Constants.PIPE + condition + Constants.PIPE
						+ temperature + Constants.PIPE + pressure + Constants.PIPE + humidity;
				bufferedWrter.write(output);
				log.debug(i + "---" + output);
				i++;
			}
			log.debug("---------------------------------------------------------------");
			log.info("Weather data number of records in output: " + i);
			fileWriteStatus = true;
		} catch (IOException e) {
			log.error("Error in writing output file");
			log.error(Utils.exceptionToString(e));
		} finally {
//			Close writers
			try {
				if (bufferedWrter != null)
					bufferedWrter.close();
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				log.error("Error in closing writers");
				log.error(Utils.exceptionToString(e));
			}
		}
		log.debug("Completed writing output to file");
//		Return output file name if success, else return null
		if(fileWriteStatus){
			return outputFileName;
		} else {
			return null;
		}
	}

	/**
	 * The method writes List<TestCaseBean> to file
	 * 
	 * @param List<TestCaseBean> testCaseBeanList, the list if TestCaseBean to be written to file
	 * @param Date executionStartDate, for creating the test case file name
	 * @return String, output file name if success, else null
	 */
	public String writeTestCasesToFile(List<TestCaseBean> testCaseBeanList, Date executionStartDate) {
		log.debug("Started writing test cases to file");
		boolean fileWriteStatus = false;
		BufferedWriter bufferedWrter = null;
		FileWriter fileWriter = null;

//		Framing Output file name
		String fileNameTimestamp = Utils.dateToString(executionStartDate, Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT);
		String outputFileName = Constants.OUTPUT_FILENAME_STARTER + fileNameTimestamp +"_tc" + Constants.OUTPUT_FILEEXTENSION;
		log.info("Weather data testcase file name: " + outputFileName);

		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWrter = new BufferedWriter(fileWriter);
			log.debug("Flusing to output");
			log.debug("---------------------------------------------------------------");
//			Writing header to output file if required
			String header = "S No" + Constants.PIPE + "TestCase ID" + Constants.PIPE + "TestCase Status" + Constants.PIPE + "TestCase Desc"
					+ Constants.PIPE + "TestCase Remarks";
			bufferedWrter.write(header);
			bufferedWrter.write(Constants.NEW_LINE);
			log.debug("Weather data testcase header: " + header);
			log.debug("---------------------------------------------------------------");
			int i = 1;
			for (TestCaseBean testCaseBean : testCaseBeanList) {
//				Eliminating new line for the first line of file
				if (i != 1) {
					bufferedWrter.write(Constants.NEW_LINE);
				}

//				If testCaseStatus is true, return pass; else return fail
				String status = (testCaseBean.getTestCaseStatus()) ? "PASS" : "FAIL";
				
				String output = i + Constants.PIPE + testCaseBean.getTestCaseId() + Constants.PIPE + status + Constants.PIPE
						+ testCaseBean.getTestCaseDesc() + Constants.PIPE
						+ testCaseBean.getTestCaseRemarks();
				
				bufferedWrter.write(output);
				log.debug(output);
				i++;
			}
			log.debug("---------------------------------------------------------------");
			log.info("Weather data number of records in testcase: " + i);
			fileWriteStatus = true;
		} catch (IOException e) {
			log.error("Error in writing test case file");
			log.error(Utils.exceptionToString(e));
		} finally {
//			Close writers
			try {
				if (bufferedWrter != null)
					bufferedWrter.close();
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				log.error("Error in closing writers");
				log.error(Utils.exceptionToString(e));
			}
		}
		log.debug("Completed writing testcases to file");
//		Return testcase file name if success, else return null
		if(fileWriteStatus){
			return outputFileName;
		} else {
			return null;
		}
	}
}
