package karro.spike.weatherdataspike;

public class YrWetherData {
	private String mId;
	private String  mTime;
	private String  mEndTime;
	private String mTemperature;
	private String mPrecipitation;
	private String mWindDirection;
	private String mWindSpeed;
	private String mPressure;
	private String mSymbol;// symbol numberEx
	
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		mId = id;
	}

	
	/**
	 * @return the time
	 */
	public String getTime() {
		return mTime;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		mTime = time;
	}

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return mTemperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		mTemperature = temperature;
	}

	
	
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return mEndTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		mEndTime = endTime;
	}

	/**
	 * @return the precipitation
	 */
	public String getPrecipitation() {
		return mPrecipitation;
	}

	/**
	 * @param precipitation the precipitation to set
	 */
	public void setPrecipitation(String precipitation) {
		mPrecipitation = precipitation;
	}

	/**
	 * @return the windDirection
	 */
	public String getWindDirection() {
		return mWindDirection;
	}

	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(String windDirection) {
		mWindDirection = windDirection;
	}

	/**
	 * @return the windSpeed
	 */
	public String getWindSpeed() {
		return mWindSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(String windSpeed) {
		mWindSpeed = windSpeed;
	}

	/**
	 * @return the pressure
	 */
	public String getPressure() {
		return mPressure;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(String pressure) {
		mPressure = pressure;
	}

	public String toString(){
		//TODO make better string Representation
		return mTime + ": " + mTemperature;
	}

	public String getSymbol() {
		return mSymbol;
	}

	public void setSymbol(String symbol) {
		mSymbol = symbol;
	}

}
