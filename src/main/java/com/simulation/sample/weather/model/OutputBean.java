package com.simulation.sample.weather.model;

import java.util.Date;

/**
 * The OutputBean is model class representing the Location, Position, Local Time and Weather Conditions to where weather corresponds to.
 * LocationPositionBean another model class
 * Local time is an ISO8601 date time.
 * Conditions is either Snow, Rain, Sunny.
 * Temperature is in °C.
 * Pressure is in hPa.
 * Relative humidity is a %.
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class OutputBean {
	
	private LocationPositionBean locationPositionBean;
	private Date localTime;
	private Condition condition;
	private float temperature;
	private float pressure;
	private int humidity;
	
	/**
	 * @return the locationPositionBean
	 */
	public LocationPositionBean getLocationPositionBean() {
		return locationPositionBean;
	}
	/**
	 * @param locationPositionBean the locationPositionBean to set
	 */
	public void setLocationPositionBean(LocationPositionBean locationPositionBean) {
		this.locationPositionBean = locationPositionBean;
	}
	/**
	 * @return the localTime
	 */
	public Date getLocalTime() {
		return localTime;
	}
	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	/**
	 * @return the temperature
	 */
	public float getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the pressure
	 */
	public float getPressure() {
		return pressure;
	}
	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	/**
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}
	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

}
