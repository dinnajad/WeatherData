/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.util.Date;

/**
 * @author Karro
 *
 */
public class OneDayWeatherData {

	private String place;
	private Date day;
	
	private float mMaxTemperature= 100;
	private float mMinTemperature= 100;
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OneDayWeatherData [place=" + place + ", day=" + day
				+ ", maxTemperature=" + mMaxTemperature + ", mintemperature="
				+ mMinTemperature + "]";
	}
	
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	/**
	 * @return the maxTemperature
	 */
	public float getMaxTemperature() {
		return mMaxTemperature;
	}
	
	/***
	 * 
	 * @return the maxTemperature
	 */
	public String getMaxTemperatureString(){
		return Float.toString(mMaxTemperature);
	}
	/**
	 * @param maxTemperature the maxTemperature to set
	 */
	public void setMaxTemperature(float maxTemperature) {
		this.mMaxTemperature = maxTemperature;
	}
	/**
	 * @return the mintemperature
	 */
	public float getMintemperature() {
		return mMinTemperature;
	}
	
	public String getMinTemperatureString(){
		return Float.toString(mMinTemperature);
	}
	/**
	 * @param mintemperature the mintemperature to set
	 */
	public void setMintemperature(float mintemperature) {
		this.mMinTemperature = mintemperature;
	}
	
	
}
