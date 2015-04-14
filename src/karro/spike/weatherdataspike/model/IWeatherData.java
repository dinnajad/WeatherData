package karro.spike.weatherdataspike.model;

import java.util.Date;

import karro.spike.weatherdataspike.YR.YrPrecipitation;
import karro.spike.weatherdataspike.YR.YrPressure;
import karro.spike.weatherdataspike.YR.YrSymbol;
import karro.spike.weatherdataspike.YR.YrTemperature;
import karro.spike.weatherdataspike.YR.YrWindDirection;
import karro.spike.weatherdataspike.YR.YrWindSpeed;

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
	public abstract void setTime(String time);

	/**
	 * @return the temperature
	 */
	public abstract YrTemperature getTemperature();
	
	/***
	 * 
	 * @return the temperature as Float
	 */
	public abstract Float getFloatTemperature();
	/**
	 * @param temperature the temperature to set
	 */
	public abstract void setTemperature(YrTemperature temperature);

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
	public abstract YrPrecipitation getPrecipitation();

	/**
	 * @param precipitation the precipitation to set
	 */
	public abstract void setPrecipitation(YrPrecipitation precipitation);

	/**
	 * @return the windDirection
	 */
	public abstract YrWindDirection getWindDirection();

	/**
	 * @param windDirection the windDirection to set
	 */
	public abstract void setWindDirection(YrWindDirection windDirection);

	/**
	 * @return the windSpeed
	 */
	public abstract YrWindSpeed getWindSpeed();

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public abstract void setWindSpeed(YrWindSpeed windSpeed);

	/**
	 * @return the pressure
	 */
	public abstract YrPressure getPressure();

	/**
	 * @param pressure the pressure to set
	 */
	public abstract void setPressure(YrPressure pressure);

	public abstract YrSymbol getSymbol();

	public abstract void setSymbol(YrSymbol symbol);

}