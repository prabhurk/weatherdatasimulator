package com.simulation.sample.weather.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * The Utils Class has generic or commonly used functions
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class Utils {

	/**
	 * This method checks if given string is empty or not
	 * returns true if string is empty or null. returns false otherwise
	 * @param String string to be checked.
	 * @return boolean
	 */
	public static boolean isStringEmpty(String string) {
		boolean isEmpty = true;
		if (!StringUtils.isEmpty(string)) {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * This method generates random whole number within the range of two given whole numbers
	 * @param int min, minimum value of the range.
	 * @param int max, maximum value of the range.
	 * @return int random number
	 */
	public static int getRandomWholeNumBtw(int min, int max) {
		Random random = new Random();
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber;
	}

	/**
	 * This method generates random decimal number within the range of two given decimal numbers
	 * @param float min, minimum value of the range.
	 * @param float max, maximum value of the range.
	 * @return float random number
	 */
	public static float getRandomDecimalBtw(float min, float max) {
		Random random = new Random();
		float randomNumber = random.nextFloat() * (max - min) + min;
		return randomNumber;
	}
	
	/**
	 * This method generates random date within the range of two given dates
	 * @param Date startDate, start date value of the range.
	 * @param Date endDate, end date value of the range.
	 * @return Date random date
	 */
	public static Date getRandomDateBtw(Date startDate, Date endDate) {
		long randomLong = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
		Date randomDate = new Date(randomLong);
		return randomDate;
	}

	/**
	 * This method formats the given float number to one decimal precision and returns as String
	 * @param float number, number to be formatted.
	 * @return String numberString,  formatted number in String
	 */
	public static String formatFloat(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("0.0");
		String numberString = decimalFormat.format(number);
		return numberString;
	}
		
	/**
	 * This method converts the given Date object to string based on the given date format
	 * @param Date date, date to be formatted.
	 * @param String dateFormatString, required format of date.
	 * @return String dateString,  formatted date in String
	 */
	public static String dateToString(Date date, String dateFormatString) {
		String dateString = null;
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		dateString = dateFormat.format(date);
		return dateString;
	}
	
	/**
	 * This method converts the given string in a given date format to Date object
	 * @param String dateString, date in string to be converted to date object.
	 * @param String dateFormatString, date format of the param dateString.
	 * @return Date date, Date object
	 * @throws ParseException 
	 */
	public static Date stringToDate(String dateString, String dateFormatString) throws ParseException {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		date = dateFormat.parse(dateString);
		return date;
	}
	
	/**
	 * This method converts the given Exception object to string
	 * @param Exception exception, to be converted to string.
	 * @return String exceptionString,  exception message in string
	 */
	public static String exceptionToString(Exception exception) {
		String exceptionString = null;
//		exceptionString = exception.getMessage();
		exceptionString = ExceptionUtils.getStackTrace(exception);
		return exceptionString;
	}

	/**
	 * This method leverages getValue() in ConfigReader class 
	 * and returns the value from loaded properties based on the key 
	 * @param String key.
	 * @return String value corresponding to the key from loaded properties.
	 */
	public static String getProperty(String key) {
		String value = null;
		try {
			value = ConfigReader.getInstance().getValue(key);
		} catch (IOException e) {
			value = null;
		}
		return value;
	}

}
