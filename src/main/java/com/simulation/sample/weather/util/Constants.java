package com.simulation.sample.weather.util;

/**
 * The Constants Class has unchanged constants used in various places in the program.
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class Constants {

	public static final String CONFIG_LOGFILE = "log4j.properties";
	public static final String CONFIG_FILE = "weatherdata_config.properties";
//	Below constants represent the key strings for getting the value from CONFIG_FILE
	public static final String CONFIG_STARTDATE = "startdate";
	public static final String CONFIG_ENDDATE = "enddate";
	public static final String CONFIG_DATEFORMAT = "configdateformat";
	public static final String CONFIG_EXETEST = "exetest";
	public static final String CONFIG_LOCNPOS = "locnpos";
	public static final String CONFIG_OUTPUTHEADER = "outputheader";

//	DATEFORMAT_ISO8601 is the format of local time in output
	public static final String DATEFORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

//	Below constants represent the delimiters and symbols
	public static final String PLUS = "+";
	public static final String PIPE = "|";
	public static final String COMMA = ",";	
	public static final String SEMICOLON = ";";
	public static final String PIPE_SEPARATOR = "\\|";
	public static final String NEW_LINE = "\r\n";

//	If value of CONFIG_EXETEST in CONFIG_FILE is EXETEST (case insensitive), then test cases will be executed after the program
	public static final String EXETEST = "Y";
	
//	If value of CONFIG_OUTPUTHEADER in CONFIG_FILE is OUTPUTHEADER (case insensitive), then header will be eliminated from the output file
	public static final String OUTPUTHEADER = "N";
	
//	If value of CONFIG_LOCNPOS in CONFIG_FILE is undefined, LOCNPOS is considered as location/position data for weather data to generate
	public static final String LOCNPOS = "Sydney|-33.86,151.21,39;Melbourne|-37.83,144.98,7;Adelaide|-34.92,138.62,48";
	
//	Weather data will be output to a file with filename OUTPUT_FILENAME_STARTER + present execution time stamp in the format OUTPUT_FILENAME_TIMESTAMP_FORMAT + OUTPUT_FILEEXTENSION 
	public static final String OUTPUT_FILENAME_STARTER = "weatherdatasimulation_";
	public static final String OUTPUT_FILENAME_TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";
	public static final String OUTPUT_FILEEXTENSION = ".txt";
	
//	Date values in the program will be logged in the below format in log
	public static final String OUTPUT_LOG_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
