package com.simulation.sample.weather.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The ConfigReader is a singleton Class to load Constants.CONFIG_FILE (contains
 * config params for the program to run). The Constants.CONFIG_FILE should be in
 * the location as the simulator.jar. Otherwise default params will be taken.
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class ConfigReader {

	private static ConfigReader configReader = null;
	private Properties properties = null;

	/**
	 * This is the Constructor for ConfigReader which loads Constants.CONFIG_FILE
	 * private constructor for singleton purpose
	 * 
	 * @param Nothing.
	 * @return Nothing.
	 * @exception throws IOException On Constants.CONFIG_FILE not present or invalid.
	 */
	private ConfigReader() throws IOException {
		InputStream input = null;
		input = new FileInputStream(new File(Constants.CONFIG_FILE));
		this.properties = new Properties();
		properties.load(input);
	}

	/**
	 * This is the Constructor for ConfigReader which creates and returns a new ConfigReader if null. Otherwise returns already initiated.
	 * synchronization makes ConfigReader thread safe
	 * 
	 * @param Nothing.
	 * @return ConfigReader object which loaded the properties file
	 * @exception throws IOException On ConfigReader failed to create the object.
	 */
	public static synchronized ConfigReader getInstance() throws IOException {
		if (configReader == null) {
			configReader = new ConfigReader();
		}
		return configReader;
	}

	/**
	 * This method returns the value from loaded properties based on the key 
	 * 
	 * @param String key.
	 * @return String value corresponding to the key from properties.
	 */
	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

}
