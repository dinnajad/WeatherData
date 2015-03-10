package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;



@Root(strict=false, name="time")
public class YrWetherData {
	
	
	
	private String mId;
	
	@Attribute(name="from")
	private String  mTime;
	
	@Attribute(name="to")
	private String  mEndTime;
	
	
	@Element(name="temperature")// hur tar man ut ett attribut på ett element? nästade objekt
	private Temperature mTemperature;
	
	@Element(name="precipitation")
	private Precipitation mPrecipitation;

	@Element(name="windDirection")
	private WindDirection mWindDirection;

	@Element(name="windSpeed")
	private WindSpeed mWindSpeed;
	
	@Element(name="pressure")
	private Pressure mPressure;
	
	@Element(name="symbol")
	private Symbol mSymbol;// symbol numberEx
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YrWetherData [mTime=" + mTime + ", mEndTime=" + mEndTime
				+ ", mTemperature=" + mTemperature + "]";
	}
	
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
	public Temperature getTemperature() {
		return mTemperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Temperature temperature) {
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
	public Precipitation getPrecipitation() {
		return mPrecipitation;
	}

	/**
	 * @param precipitation the precipitation to set
	 */
	public void setPrecipitation(Precipitation precipitation) {
		mPrecipitation = precipitation;
	}

	/**
	 * @return the windDirection
	 */
	public WindDirection getWindDirection() {
		return mWindDirection;
	}

	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(WindDirection windDirection) {
		mWindDirection = windDirection;
	}

	/**
	 * @return the windSpeed
	 */
	public WindSpeed getWindSpeed() {
		return mWindSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(WindSpeed windSpeed) {
		mWindSpeed = windSpeed;
	}

	/**
	 * @return the pressure
	 */
	public Pressure getPressure() {
		return mPressure;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(Pressure pressure) {
		mPressure = pressure;
	}

	

	public Symbol getSymbol() {
		return mSymbol;
	}

	public void setSymbol(Symbol symbol) {
		mSymbol = symbol;
	}

}
