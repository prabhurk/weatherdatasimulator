package com.simulation.sample.weather.model;

/**
 * The LocationPositionBean is model class representing the Location and Position to where weather corresponds to.
 * Location is an optional label describing one or more positions.
 * Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level
 *
 * @author Prabhu R K
 * @version 0.0.1
 * @since July 13, 2017
 */

public class LocationPositionBean {
	
	private String location;
	private float latitude;
	private float longitude;
	private int elevation;
	
	/**
	 * This is the Constructor for LocationPositionBean
	 */
	public LocationPositionBean(String location, float latitude, float longitude, int elevation) {
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the elevation
	 */
	public int getElevation() {
		return elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

}
