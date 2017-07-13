package com.simulation.sample.weather.exec;

import com.simulation.sample.weather.model.Condition;
import com.simulation.sample.weather.util.Utils;

/**
 * The WeatherLogic Class consists of methods to generate Temperature, Pressure and Humidity based on the given Condition.
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class WeatherLogic {
	
	/**
	 * This method generates temperature (represented in °C), based on given condition
	 * @param Condition condition, the weather condition, rain, snow or sunny.
	 * @return float temperature value
	 */
	public static float getTemperature(Condition condition) {
		float temperature = (float) 0.0;
		
		switch (condition) {
		case RAIN:
//			If condition is rain, temperature ranges from -10°C to 25°C
			temperature = Utils.getRandomDecimalBtw((float) 10.0, (float) 25.0);
			break;
			
		case SNOW:
//			If condition is snow, temperature ranges from -10°C to 10°C
			temperature = Utils.getRandomDecimalBtw((float) -10.0, (float) 10.0);
			break;
			
		case SUNNY:
//			If condition is sunny, temperature ranges from 25°C to 45°C
			temperature = Utils.getRandomDecimalBtw((float) 25.0, (float) 45.0);
			break;

		default:
//			By default, temperature ranges from -10°C to 45°C
			temperature = Utils.getRandomDecimalBtw((float) -10.0, (float) 45.0);			
			break;
		}
		
		return temperature;
	}
	
	/**
	 * This method generates pressure (represented in hPa), based on given condition
	 * @param Condition condition, the weather condition, rain, snow or sunny.
	 * @return float pressure value
	 */
	public static float getPressure(Condition condition) {
		float pressure = (float) 0.0;
		
		switch (condition) {
		case RAIN:
//			If condition is rain, pressure ranges from 1000hPa to 1060hPa
			pressure = Utils.getRandomDecimalBtw((float) 1000.0, (float) 1060.0);
			break;
			
		case SNOW:
//			If condition is snow, pressure ranges from 940hPa to 1000hPa
			pressure = Utils.getRandomDecimalBtw((float) 940.0, (float) 1000.0);
			break;
			
		case SUNNY:
//			If condition is sunny, pressure ranges from 1060hPa to 1200hPa
			pressure = Utils.getRandomDecimalBtw((float) 1060.0, (float) 1200.0);
			break;

		default:
//			By default, pressure ranges from 940hPa to 1060hPa
			pressure = Utils.getRandomDecimalBtw((float) 940.0, (float) 1060.0);			
			break;
		}
		
		return pressure;
	}	

	/**
	 * This method generates humidity (represented in %), based on given condition
	 * @param Condition condition, the weather condition, rain, snow or sunny.
	 * @return int humidity value
	 */
	public static int getHumidity(Condition condition) {
		int humidity = 0;
		
		switch (condition) {
		case RAIN:
//			If condition is rain, humidity ranges from 65% to 95%
			humidity = Utils.getRandomWholeNumBtw(65, 95);
			break;
			
		case SNOW:
//			If condition is snow, humidity ranges from 35% to 65%
			humidity = Utils.getRandomWholeNumBtw(35, 65);
			break;
			
		case SUNNY:
//			If condition is sunny, humidity ranges from 5% to 35%
			humidity = Utils.getRandomWholeNumBtw(5, 35);
			break;

		default:
//			By default, humidity ranges from 5% to 95%
			humidity = Utils.getRandomWholeNumBtw(5, 95);			
			break;
		}
		
		return humidity;
	}
	

}
