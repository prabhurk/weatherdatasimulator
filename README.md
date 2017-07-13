# Weather Data Simulator

Weather Data Simulator is toy simulation program that generates mock weather data.

## Build

### Prerequisites

* JDK 1.7 or higher (```JAVA_HOME``` and ```PATH``` set) for compile and execution
* Apache Maven 3.3 or higher (```MVN_HOME``` and ```PATH``` set) for build

### Installation

Goto project root directory ```weatherdatasimulator/```, the directory where ```pom.xml``` for weatherdatasimulator is present. 

Execute the command:
```
mvn clean install
```

The following required outputs (there will be other stuffs as well along with the required) will be generated in ```weatherdatasimulator/target```:
* log4j.properties
* weatherdata_config.properties
* weatherdatasimulator-jar-with-dependencies.jar

Before executing the command, make sure to close all opened files from ```weatherdatasimulator/target``` folder. Also exit from ```weatherdatasimulator/target``` if command prompt or shell is presently locked in.


## Configuration / Input

### log4j.properties

The property file contains necessary parameters, such as logging levels (INFO, DEBUG, ERROR), appender (CONSOLE, FILE), log file name etc for logging of program execution. The property file is related to ```com.simulation.sample.weather.util.Constants.CONFIG_LOGFILE```. The property file should be in same location as the ```weatherdatasimulator-jar-with-dependencies.jar```. Otherwise logging will not be available.

### weatherdata_config.properties

The property file contains following configuration parameters:

* ```locnpos```			-	Location|Latitude,Longtitude,Elevation of the location in in metres above sea level;<br/>
						wherein Location is String, Latitude and Longitude are float and Elevation is int<br/>
						Latitude, Longitude and Elevation are collectively referred as Position<br/>
						You can append as many location/position values (should be unique) as you want to generate weather data against.<br/>
						e.g.: Sydney|-33.87,151.21,27;Melbourne|-37.81,144.96,35;Adelaide|-34.93,138.60,45<br/>
						If locnpos is not given input, defaults to ```com.simulation.sample.weather.util.Constants.LOCNPOS```<br/>
						If datatype is improper, that particular location/position will be skipped.
			
* ```startdate```			-	Start date of local time from when weather data to be generated.<br/>
						If startdate is not given or improper, defaults to present program execution time (in the program run time zone).
				
* ```enddate```			-	End date of local time to when weather data to be generated.<br/>
						If enddate is not given, or improper, or enddate is proper but not the startdate, or enddate not greater than startdate, defaults to 60 days prior to start date.
				
* ```configdateformat```	-	Date format of both enddate and startdate.<br/>
						If configdateformat is not given or improper, default startdate (now) and enddate (60 days before) is to follow.

* ```exetest```			-	If exetest equals (case insensitive) ```com.simulation.sample.weather.util.Constants.EXETEST``` (="Y"), test cases will be executed. <br/>
						For any other values of exetest or if exetest is not given or improper, defaults to no test case execution.
						
* ```outputheader```		-	If outputheader equals (case insensitive) ```com.simulation.sample.weather.util.Constants.OUTPUTHEADER``` (="N"), header will not be present in the weather data output file. <br/>
						For any other values of outputheader or if outputheader is not given or improper, defaults to header in the weather data output file.
												

The property file is related to ```com.simulation.sample.weather.util.Constants.CONFIG_FILE```. The property file should be in same location as the ```weatherdatasimulator-jar-with-dependencies.jar```, otherwise default values will be considered against each parameter.


## Execution

Either the program can be run from ```weatherdatasimulator/target``` or the ```weatherdatasimulator-jar-with-dependencies.jar```, ```weatherdata_config.properties``` and ```log4j.properties``` can be copied to a common location and can be run from there.

In anyways, goto the location where ```weatherdatasimulator-jar-with-dependencies.jar``` is present (```weatherdata_config.properties``` and ```log4j.properties``` are also to be present)

The following command is to be used for the execution of the program:

```
java -jar weatherdatasimulator-jar-with-dependencies.jar <X>
```

where X is the total number of weather data to be generated. X must be a whole number below 2147483647.
If X is not given or improper, defaults to number of location/position

Example execution command:  ```java -jar weatherdatasimulator-jar-with-dependencies.jar 10```

## Results

### Weather Data

After execution of the program, result, the required number of mock weather data will be generated in a text file in the same location as in ```weatherdatasimulator-jar-with-dependencies.jar``` is present.

Previously present results will not be replaced/removed. However, if you are running the program from ```weatherdatasimulator/target```, then on next clean-install, ````target``` directory will be cleaned up.

The weather data text file name will be ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_STARTER``` + program execution date in ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT``` + ```com.simulation.sample.weather.util.Constants.OUTPUT_FILEEXTENSION```

Example weather data output file name: ```weatherdatasimulation_2017-07-15_16-25-04.txt```

Header (presence based on configuration) in the file or format of the file: ```Location|Position|Local Time|Conditions|Temperature|Pressure|Humidity```

where 
* Location is an optional label describing one or more positions,
* Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea
level,
* Local time is an ISO8601 date time,
* Conditions is either Snow, Rain, Sunny,
* Temperature is in °C,
* Pressure is in hPa, and
* Relative humidity is a %.

Example data: ```Brisbane|-27.47,153.03,22|2016-11-24T19:20:10Z|Rain|+24.0|1027.6|80```


### Test Case

Along with weather data, test case execution results/details (execution of test cases based on configuration) will be generated in a text file in the same location as in ```weatherdatasimulator-jar-with-dependencies.jar``` is present.

Previously present results will not be replaced/removed. However, if you are running the program from ```weatherdatasimulator/target```, then on next clean-install, ```target``` directory will be cleaned up.

The text file name will be ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_STARTER``` + program execution date in ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT``` + ```_tc``` + ```com.simulation.sample.weather.util.Constants.OUTPUT_FILEEXTENSION```

Example test case result file name: ```weatherdatasimulation_2017-07-15_16-25-04_tc.txt```

Header in the file or format of the file: ```S No|TestCase ID|TestCase Status|TestCase Desc|TestCase Remarks```

where 
* S No is serial number of test case,
* TestCase ID is the id of test cases,
* TestCase Status is the PASS or FAIL status of test case,
* TestCase Desc is the description of the case,
* TestCase Remarks is any remarks if available,


Example data: ```1|WeatherData_TC_001|PASS|Checks if a file exists|File: weatherdatasimulation_2017-07-15_16-25-04.txt```


### Logs

Along with weather data, log file based on log4j.properties will be generated in the same location as in ```weatherdatasimulator-jar-with-dependencies.jar``` is present. If only stdout logs are enabled, logs will be redirected to console and no log file will be generated.

Previously present results will not be replaced/removed. However, if you are running the program from ```weatherdatasimulator/target```, then on next clean-install, target directory will be cleaned up.

The text file name will be ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_STARTER``` + program execution date in ```com.simulation.sample.weather.util.Constants.OUTPUT_FILENAME_TIMESTAMP_FORMAT``` + ```_log```

Example log file name: ```weatherdatasimulation_2017-07-15_16-25-04_log```


## How the program works

* Firstly, valid startdate, enddate, location/position and totaldatacount are made ready.
* The quotient of (totaldatacount/[count of location/position]) will be minimum number of mock weather data against each location & position. If any remainder, random location & position will be chosen, to fulfill the totaldatacount of data.
* For each location/position, random date between startdate and enddate will be chosen as localtime. Also random weathercondition is tagged to it.
* Based on the weather condition, temperature, pressure and humidity values are generated as random between a given specific range and the whole data is assigned to particular iteration.
* Temperature range is -10°C to 25°C if the weather condition is rain. For snow, it will be from -10°C to 10°C and if sunny, it will be 25°C to 45°C.
* Pressure range is 1000hPa to 1060hPa if the weather condition is rain. For snow, it will be from 940hPa to 1000hPa and if sunny, it will be 1060hPa to 1200hPa.
* Humidity range is 65% to 95% if the weather condition is rain. For snow, it will be from 35% to 65% and if sunny, it will be 5% to 35%.
* Default ranges are -10°C to 45°C for temperature, 940hPa to 1060hPa for pressure and 5% to 95% for humidity
* Temperature and pressure are float values and humidity is an int
* The ranges are based on practical experience or simply logical wild guess (if rain, humidity will be relatively high; if snow temperature will be relatively low etc)
* The process is continued till totaldatacount of data is generated, after which the data will be sorted in ascending based on localtime and will be written to weather data output file.
* In case of test case execution, test cases will be executed and written to separate file.

## Author / Contribution

Prabhu R K
 
## Version

0.0.1 

## License

This project is licensed under the MIT License. See the LICENSE file for details
