package karro.spike.weatherdataspike.YR;

import java.util.Date;

public interface IWeatherData {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

	/**
	 * @return the time
	 */
	public abstract Date getTime();

	/**
	 * @param time the time to set
	 */
	public abstract void setTime(String time);//TODO eventuellt byta till Date isf String

	/**
	 * @return the temperature
	 */
	public abstract Temperature getTemperature();
	
	/***
	 * 
	 * @return the temperature as Float
	 */
	public abstract Float getFloatTemperature();
	/**
	 * @param temperature the temperature to set
	 */
	public abstract void setTemperature(Temperature temperature);

	/**
	 * @return the endTime
	 */
	public abstract Date getEndTime();

	/**
	 * @param endTime the endTime to set
	 */
	public abstract void setEndTime(String endTime);

	/**
	 * @return the precipitation
	 */
	public abstract Precipitation getPrecipitation();

	/**
	 * @param precipitation the precipitation to set
	 */
	public abstract void setPrecipitation(Precipitation precipitation);

	/**
	 * @return the windDirection
	 */
	public abstract WindDirection getWindDirection();

	/**
	 * @param windDirection the windDirection to set
	 */
	public abstract void setWindDirection(WindDirection windDirection);

	/**
	 * @return the windSpeed
	 */
	public abstract WindSpeed getWindSpeed();

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public abstract void setWindSpeed(WindSpeed windSpeed);

	/**
	 * @return the pressure
	 */
	public abstract Pressure getPressure();

	/**
	 * @param pressure the pressure to set
	 */
	public abstract void setPressure(Pressure pressure);

	public abstract Symbol getSymbol();

	public abstract void setSymbol(Symbol symbol);

}